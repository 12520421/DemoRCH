package com.atg.demorch.di

import com.atg.demorch.ui.main.beer.BeersViewModel
import com.atg.demorch.ui.main.detail.BeerDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { BeersViewModel(get()) }
    viewModel { BeerDetailViewModel(get()) }
}