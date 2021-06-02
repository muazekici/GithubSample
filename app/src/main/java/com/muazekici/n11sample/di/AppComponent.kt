package com.muazekici.n11sample.di

import android.app.Application
import com.muazekici.n11sample.N11Application
import com.muazekici.n11sample.di.scopes.AppScope
import com.muazekici.n11sample.data.di.DataSourcesModule
import com.muazekici.n11sample.data.di.RepositoriesModule
import com.muazekici.n11sample.ui.main.di.MainActivityComponent
import com.muazekici.n11sample.ui.main.di.MainActivitySubComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@AppScope
@Component(
    modules = [
        AppModule::class,
        RepositoriesModule::class,
        DataSourcesModule::class,
        MainActivitySubComponent::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance @Named(BASE_URL_TAG) baseUrl: String
        ): AppComponent
    }

    fun injectApplication(application: N11Application)

    fun mainActivityComponent(): MainActivityComponent.Factory
}

const val BASE_URL_TAG = "BASE_URL"
