package com.atg.demorch.ui.main.beer

import androidx.lifecycle.MutableLiveData
import com.atg.demorch.model.Beer
import com.atg.demorch.model.Result
import com.atg.demorch.service.repository.BeerRepository
import com.atg.demorch.utils.BaseViewModel
import com.atg.demorch.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BeersViewModel(
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    val onBeersResult = MutableLiveData<ArrayList<Beer?>?>()

    fun getBeers(brewedBefore: String?, brewedAfter: String?, currentPage: Int) {
        job = scope.launch {
            onLoading.postValue(true)

            delay(200)

            when (val response =
                beerRepository.getBeers(brewedBefore,brewedAfter, Constants.PAGE_SIZE, currentPage)) {
                is Result.Success -> {
                    onBeersResult.postValue(response.data)
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