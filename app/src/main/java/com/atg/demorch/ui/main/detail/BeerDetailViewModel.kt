package com.atg.demorch.ui.main.detail

import androidx.lifecycle.MutableLiveData
import com.atg.demorch.model.Beer
import com.atg.demorch.model.Result
import com.atg.demorch.service.repository.BeerRepository
import com.atg.demorch.utils.BaseViewModel
import kotlinx.coroutines.launch

class BeerDetailViewModel(
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    val onBeerDetailResult = MutableLiveData<Beer?>()

    fun getBeerDetail(idBeer: Int) {
        job = scope.launch {
            onLoading.postValue(true)

            when (val response = beerRepository.getBeer(idBeer)) {
                is Result.Success -> {
                    val items = response.data
                    val item = items?.get(0) ?: null
                    onBeerDetailResult.postValue(item)
                    onLoading.postValue(false)
                }
                is Result.Failure -> {
                    onNetworkError.postValue(response.exception.message)
                    onLoading.postValue(false)
                }
            }
        }
    }
}