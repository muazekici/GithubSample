package com.muazekici.n11sample.ui.main.fragment_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.muazekici.n11sample.gateways_adapters.models.UserDetail
import com.muazekici.n11sample.ui.exts.toErrorFlow
import com.muazekici.n11sample.ui.exts.toLoadFlow
import com.muazekici.n11sample.use_cases.GetUserDetailUseCase
import com.muazekici.n11sample.use_cases.UserFavUseCase
import com.muazekici.n11sample.use_cases.base.UseCaseResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val favUseCase: UserFavUseCase
) :
    ViewModel() {

    private val _userName =
        MutableSharedFlow<String>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val _userDetail = _userName.flatMapLatest {
        getUserDetailUseCase(it)
    }.buffer(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val userDetailResult = _userDetail.filter {
        it is UseCaseResult.Success
    }.map { (it as UseCaseResult.Success).data }//.asLiveData(viewModelScope.coroutineContext)

    private val _favChange =
        MutableSharedFlow<Boolean>()

    private val favChange = userDetailResult.combine(_favChange) { u, f ->
        UserDetail(u.id, u.userName, u.avatarUrl, f)
    }

    val favUpdate = favChange.asLiveData(viewModelScope.coroutineContext)

    @ExperimentalCoroutinesApi
    val userDetail = merge(favChange, userDetailResult).asLiveData(viewModelScope.coroutineContext)

    private val _isLoad = _userDetail.toLoadFlow()
    val isLoad = _isLoad.asLiveData(viewModelScope.coroutineContext)

    private val _isError = _userDetail.toErrorFlow()
    val isError = _isError.asLiveData(viewModelScope.coroutineContext)

    init {
        viewModelScope.launch {
            favChange.collect { user ->
                GlobalScope.launch {
                    try {
                        favUseCase.execution(Triple(user.id, user.isFavorite, user.userName))
                    }catch (e:Exception){

                    }
                }
            }
        }
    }

    fun getUserDetail(userName: String) {
        viewModelScope.launch {
            _userName.emit(userName)
        }
    }

    fun favChange(isCheck: Boolean) {
        viewModelScope.launch {
            _favChange.emit(isCheck)
        }
    }
}