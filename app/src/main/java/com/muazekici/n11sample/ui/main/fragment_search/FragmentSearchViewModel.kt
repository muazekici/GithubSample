package com.muazekici.n11sample.ui.main.fragment_search

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.muazekici.n11sample.gateways_adapters.models.UserSimple
import com.muazekici.n11sample.use_cases.FavUpdatedUseCase
import com.muazekici.n11sample.use_cases.NOT_CHANGE_IDX
import com.muazekici.n11sample.use_cases.UserFavUseCase
import com.muazekici.n11sample.use_cases.base.UseCaseResult
import com.muazekici.n11sample.use_cases.UserSearchUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class FragmentSearchViewModel @Inject constructor(
    private val userSearchUseCase: UserSearchUseCase,
    private val userFavUseCase: UserFavUseCase,
    private val favUpdatedUseCase: FavUpdatedUseCase
) : ViewModel() {


    private val _searchText =
        MutableSharedFlow<String>()

    private val searchText = _searchText.debounce(400).distinctUntilChanged()

    private val _friends = searchText.flatMapLatest { userSearchUseCase.invoke(it) }
        .buffer(1, BufferOverflow.DROP_OLDEST)

    private val friendList = MutableStateFlow(emptyList<UserSimple>())
    /* _friends.filter { it is UseCaseResult.Success }.map { (it as UseCaseResult.Success).data }
         .buffer(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)*/

    private val _updateId =
        MutableSharedFlow<Long>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val updateId = _updateId.combine(friendList) { u, f -> Pair(f, u) }.flatMapLatest {
        favUpdatedUseCase.getResultFlow(it)
    }.filter {
        it != NOT_CHANGE_IDX
    }.buffer(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .asLiveData(viewModelScope.coroutineContext)

    val friends = friendList.asLiveData(viewModelScope.coroutineContext)

    init {
        viewModelScope.launch {
            _friends.filter { it is UseCaseResult.Success }
                .map { (it as UseCaseResult.Success).data }
                .collect {
                    friendList.value = it
                }
        }
    }

    fun textChanged(text: Editable) {
        viewModelScope.launch {
            _searchText.emit(text.toString())
        }
    }

    fun favUser(u: UserSimple) {
        GlobalScope.launch {
            try {
                userFavUseCase.execution(Triple(u.id, u.isFavorite, u.userName))
            } catch (e: Exception) {
            }
        }
    }

    fun favUserUpdate(userId: Long) {
        viewModelScope.launch {
            _updateId.emit(userId)
        }
    }
}