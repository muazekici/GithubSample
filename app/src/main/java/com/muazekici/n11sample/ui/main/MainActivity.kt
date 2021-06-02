package com.muazekici.n11sample.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.muazekici.n11sample.N11Application
import com.muazekici.n11sample.R
import com.muazekici.n11sample.databinding.ActivityMainBinding
import com.muazekici.n11sample.ui.main.di.MainActivityComponent
import com.muazekici.n11sample.ui.main.fragment_search.FragmentUserSearch
import com.muazekici.n11sample.ui.navigation.NavigationHostActivity
import com.muazekici.n11sample.ui.utils.DaggerComponentOwner

class MainActivity : NavigationHostActivity(), DaggerComponentOwner<MainActivityComponent> {

    private lateinit var activityBinding: ActivityMainBinding

    override lateinit var component: MainActivityComponent

    override val fragmentContainer: Int = R.id.ContainerMain

    override fun onCreate(savedInstanceState: Bundle?) {
        component =
            (application as N11Application).appComponent.mainActivityComponent()
                .create(this)
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initNavigation(FragmentUserSearch.newInstance(),savedInstanceState)
    }


}