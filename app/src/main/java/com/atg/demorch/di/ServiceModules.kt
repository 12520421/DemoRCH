package com.atg.demorch.di

import com.atg.demorch.service.client.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit


val serviceModule = module {
    single { provideService(retrofit = get()) }
}

private fun provideService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

