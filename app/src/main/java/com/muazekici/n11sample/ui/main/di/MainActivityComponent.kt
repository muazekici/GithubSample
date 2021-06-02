package com.muazekici.n11sample.ui.main.di

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muazekici.n11sample.di.qualifiers.ActivityContext
import com.muazekici.n11sample.di.scopes.ActivityScope
import com.muazekici.n11sample.ui.main.MainActivity
import com.muazekici.n11sample.ui.main.fragment_detail.FragmentUserDetail
import com.muazekici.n11sample.ui.main.fragment_search.FragmentSearchDI
import com.muazekici.n11sample.ui.utils.N11SampleViewModelFactory
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Provider

@ActivityScope
@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent : FragmentSearchDI {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: Activity): MainActivityComponent
    }

    fun injectActivity(mainActivity: MainActivity)

    fun inject(fragment: FragmentUserDetail)

}

@Module(subcomponents = [MainActivityComponent::class])
class MainActivitySubComponent

@Module(includes = [MainActivityViewModelModule::class])
class MainActivityModule {

    @Provides
    @ActivityContext
    @ActivityScope
    fun bindActivityContext(activity: Activity): Context {
        return activity
    }

    @ActivityScope
    @Provides
    fun provideViewModelFactory(map: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {
        return N11SampleViewModelFactory(map)
    }

}