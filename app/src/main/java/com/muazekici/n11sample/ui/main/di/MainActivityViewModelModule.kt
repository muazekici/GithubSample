package com.muazekici.n11sample.ui.main.di

import androidx.lifecycle.ViewModel
import com.muazekici.n11sample.di.qualifiers.ViewModelKey
import com.muazekici.n11sample.ui.main.fragment_detail.FragmentDetailViewModel
import com.muazekici.n11sample.ui.main.fragment_search.FragmentSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FragmentSearchViewModel::class)
    abstract fun bindFragmentSearchViewModel(viewModel: FragmentSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FragmentDetailViewModel::class)
    abstract fun bindFragmentUserDetailViewModel(viewModel: FragmentDetailViewModel): ViewModel

}