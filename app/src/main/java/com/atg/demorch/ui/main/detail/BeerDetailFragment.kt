package com.atg.demorch.ui.main.detail

import android.os.Bundle
import android.view.View
import com.atg.demorch.R
import com.atg.demorch.databinding.FragmentBeerDetailBinding
import com.atg.demorch.ext.loadImage
import com.atg.demorch.ext.viewBinding
import com.atg.demorch.model.Beer
import com.atg.demorch.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeerDetailFragment : BaseFragment(R.layout.fragment_beer_detail) {

    private val binding by viewBinding(FragmentBeerDetailBinding::bind)
    private val viewModel: BeerDetailViewModel by viewModel()

    private var idBeer: Int? = null

    override fun bindView() {

    }

    override fun observer() = with(viewModel) {
        onBeerDetailResult.observe(viewLifecycleOwner) { beer ->
            beer?.let { loadData(it) }
        }

        onLoading.observe(viewLifecycleOwner) {
            if (it) showLoading() else hideLoading()
        }

        onNetworkError.observe(viewLifecycleOwner) {
            showAlert(null, it)
        }
    }

    private fun loadData(beer: Beer) {
        binding.apply {
            imgDetailsBear.loadImage(beer.image_url)
            tvName.text = beer.name
            tvYear.text = "${requireContext().resources.getString(R.string.txt_brewed)} : ${beer.first_brewed}"
            tvAbv.text = "${requireContext().resources.getString(R.string.txt_abv)} : ${beer.abv.toString()}"
            tvDescription.text = beer.description
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idBeer = arguments?.let { BeerDetailFragmentArgs.fromBundle(it).beer }
        idBeer?.let { viewModel.getBeerDetail(it) }
    }
}