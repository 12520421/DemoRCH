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
    val onAfterBrewed = MutableLiveData<String>()
    val onBeforeBrewed = MutableLiveData<String>()

    var monthBrewedAfter = 0
    var monthBrewedBefore = 0
    var yearBrewedAfter = 0
    var yearBrewedBefore = 0

    fun getBeers(brewedBefore: String?, brewedAfter: String?, currentPage: Int) {
        job = scope.launch {
            onLoading.postValue(true)

            delay(timeMillis = 200)

            when (val response =
                beerRepository.getBeers(
                    brewedBefore,
                    brewedAfter,
                    Constants.PAGE_SIZE,
                    currentPage
                )) {
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

    fun handleTimePicker(year: Int, month: Int, brewedAfter: Boolean) {
        if (brewedAfter) {
            monthBrewedAfter = month
            yearBrewedAfter = year

            onAfterBrewed.value = "${(month + 1)}-$year"
        } else {
            monthBrewedBefore = month
            yearBrewedBefore = year

            onBeforeBrewed.value = "${(month + 1)}-$year"
        }
    }

    fun getLimitTimePicker(brewedAfter: Boolean): Pair<Int, Int>? {
        return if (brewedAfter) limitTimeAfterBrewed() else limitTimeBeforeBrewed()
    }

    private fun limitTimeAfterBrewed(): Pair<Int, Int>? {
        if (monthBrewedBefore <= 0 || yearBrewedBefore <= 0)
            return null
        return Pair(monthBrewedBefore, yearBrewedBefore)
    }

    private fun limitTimeBeforeBrewed(): Pair<Int, Int>? {
        if (monthBrewedAfter <= 0 || yearBrewedAfter <= 0)
            return null
        return Pair(monthBrewedAfter, yearBrewedAfter)
    }

    fun cleanLimitTime() {
        monthBrewedAfter = 0
        monthBrewedBefore = 0
        yearBrewedAfter = 0
        yearBrewedBefore = 0

        onBeforeBrewed.value = ""
        onAfterBrewed.value = ""
    }

}