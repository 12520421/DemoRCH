package com.atg.demorch.ui.main.beer

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.dzmitry_lakisau.month_year_picker_dialog.MonthYearPickerDialog
import com.atg.demorch.R
import com.atg.demorch.databinding.FragmentBeersBinding
import com.atg.demorch.ext.viewBinding
import com.atg.demorch.ui.main.beer.adapter.BeerAdapter
import com.atg.demorch.utils.BaseFragment
import com.atg.demorch.utils.EndlessRecyclerOnScrollListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class BeersFragment : BaseFragment(R.layout.fragment_beers) {

    private val binding by viewBinding(FragmentBeersBinding::bind)
    private val viewModel: BeersViewModel by viewModel()

    private lateinit var adapter: BeerAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFirstData()
    }

    override fun bindView() = with(binding) {
        initRV()

        tvBrewedAfter.setOnClickListener {
            showDatePickerDialog(isBrewedAfter = true)
        }

        tvBrewedBefore.setOnClickListener {
            showDatePickerDialog(isBrewedAfter = false)
        }

        btnClearFilterTime.setOnClickListener {
            viewModel.cleanLimitTime()
            setEndlessRecyclerOnScrollListener()
            adapter.clearData()
            getBeers(current_page = 1)
        }

        btnFilterTime.setOnClickListener {
            setEndlessRecyclerOnScrollListener()
            adapter.clearData()
            getBeers(current_page = 1)
        }
    }

    private fun loadFirstData() {
        getBeers(current_page = 1)
    }

    override fun observer() = with(viewModel) {
        onBeersResult.observe(viewLifecycleOwner) {
            if (it?.isEmpty() == false) {
                adapter.addData(dataViews = it)
            }
        }

        onAfterBrewed.observe(viewLifecycleOwner) {
            binding.tvBrewedAfter.text = it
        }

        onBeforeBrewed.observe(viewLifecycleOwner) {
            binding.tvBrewedBefore.text = it
        }

        onLoading.observe(viewLifecycleOwner) {
            if (it) showLoading() else hideLoading()
        }

        onNetworkError.observe(viewLifecycleOwner) {
            showAlert(
                title = null,
                message = it
            )
        }
    }

    private fun initRV() {
        lifecycleScope.launch(Dispatchers.Main) {
            adapter = BeerAdapter(
                items = arrayListOf(),
                onSelectedItem = { beer ->
                    viewModel.cleanLimitTime()

                    val action =
                        BeersFragmentDirections.actionListFragmentToDetailsFragment(beer.id)
                    findNavController().navigate(action)
                }
            )
            adapter.notifyDataSetChanged()
            binding.rvBeer.adapter = adapter
            mLayoutManager = LinearLayoutManager(requireContext())
            binding.rvBeer.layoutManager = mLayoutManager
            binding.rvBeer.setHasFixedSize(true)
            setEndlessRecyclerOnScrollListener()
        }
    }

    private fun setEndlessRecyclerOnScrollListener() {
        if (mLayoutManager == null)
            return
        binding.rvBeer.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(mLayoutManager) {
            override fun onLoadMore(current_page: Int) {
                loadMoreData(current_page = current_page)
            }
        })
    }

    private fun loadMoreData(current_page: Int) {
        getBeers(current_page = current_page)
    }

    private fun getBeers(current_page: Int) {
        val brewBefore = binding.tvBrewedBefore.text.toString()
        val brewAfter = binding.tvBrewedAfter.text.toString()
        viewModel.getBeers(
            brewedBefore = brewBefore.ifEmpty { null },
            brewedAfter = brewAfter.ifEmpty { null },
            currentPage = current_page
        )
    }

    private fun showDatePickerDialog(isBrewedAfter: Boolean) {

        val limitTimePicker: Pair<Int, Int>? = viewModel.getLimitTimePicker(isBrewedAfter)
        val curTime = Calendar.getInstance()

        val builder = MonthYearPickerDialog.Builder(
            context = requireContext(),
            themeResId = R.style.Style_MonthYearPickerDialog_Orange,
            onDateSetListener = { year, month ->
                viewModel.handleTimePicker(year, month, isBrewedAfter)
            },
            selectedMonth = limitTimePicker?.first ?: curTime[Calendar.MONTH],
            selectedYear = limitTimePicker?.second ?: curTime[Calendar.YEAR]
        )

        limitTimePicker?.let { limitTimePicker ->
            if (isBrewedAfter) {
                builder.setMaxMonth(limitTimePicker.first)
                builder.setMaxYear(limitTimePicker.second)
            } else {
                builder.setMinMonth(limitTimePicker.first)
                builder.setMinYear(limitTimePicker.second)
            }
        }

        builder.build()
            .show()
    }
}