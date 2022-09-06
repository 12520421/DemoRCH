package com.atg.demorch.di

import com.atg.demorch.service.repository.BeerRepository
import com.atg.demorch.service.repository.BeerRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single<BeerRepository> {
        BeerRepositoryImpl(get())
    }
}