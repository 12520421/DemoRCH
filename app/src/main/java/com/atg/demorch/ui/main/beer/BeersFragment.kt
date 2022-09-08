package com.atg.demorch.ui.main.beer

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
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
            showDatePickerDialog(tvBrewedAfter)
        }

        tvBrewedBefore.setOnClickListener {
            showDatePickerDialog(tvBrewedBefore)
        }

        btnClearFilterTime.setOnClickListener {
            tvBrewedAfter.text = ""
            tvBrewedBefore.text = ""
        }

        btnFilterTime.setOnClickListener {
            setEndlessRecyclerOnScrollListener()
            adapter.clearData()
            getBeers(1)
        }
    }

    private fun loadFirstData() {
        getBeers(1)
    }

    override fun observer() = with(viewModel) {
        onBeersResult.observe(viewLifecycleOwner) {
            if (it?.isEmpty() == false) {
                adapter.addData(it)
            }
        }

        onLoading.observe(viewLifecycleOwner) {
            if (it) showLoading() else hideLoading()
        }

        onNetworkError.observe(viewLifecycleOwner) {
            showAlert(null, it)
        }
    }

    private fun initRV() {
        lifecycleScope.launch(Dispatchers.Main) {
            adapter = BeerAdapter(
                itemsCells = arrayListOf(),
                onSelectedItem = { beer ->

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
            adapter.clearData()
            setEndlessRecyclerOnScrollListener()
        }
    }

    private fun setEndlessRecyclerOnScrollListener() {
        if (mLayoutManager == null)
            return
        binding.rvBeer.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(mLayoutManager) {
            override fun onLoadMore(current_page: Int) {
                loadMoreData(current_page)
            }
        })
    }

    private fun loadMoreData(current_page: Int) {
        if (viewLifecycleOwner.lifecycle.currentState ==
            Lifecycle.State.RESUMED
        )
            getBeers(current_page)
    }

    private fun getBeers(current_page: Int) {
        val brewBefore = binding.tvBrewedBefore.text.toString()
        val brewAfter = binding.tvBrewedAfter.text.toString()
        viewModel.getBeers(brewBefore.ifEmpty { null }, brewAfter.ifEmpty { null }, current_page)
    }

    //should custom from to filter
    private fun showDatePickerDialog(textView: TextView) {
        MonthYearPickerDialog.Builder(
            context = requireContext(),
            themeResId = R.style.Style_MonthYearPickerDialog_Orange,
            onDateSetListener = { year, month ->
                textView.text = "${(month + 1)}-$year"
            },
        )
            .build()
            .show()
    }
}