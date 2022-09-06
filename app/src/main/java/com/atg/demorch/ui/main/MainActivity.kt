package com.atg.demorch.ui.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.atg.dcard.utils.BaseActivity
import com.atg.demorch.R
import com.atg.demorch.databinding.ActivityMainBinding
import com.atg.demorch.ext.viewBinding

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
    }

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(navHostFragment.navController.graph)
    }

    override fun bindView() = with(binding) {
        setSupportActionBar(toolbar)

        // setup toolbar with navigation component
        toolbar.setupWithNavController(navHostFragment.navController, appBarConfiguration)
    }

    override fun observer() {

    }

}