package com.atg.demorch.ui.main.beer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atg.demorch.R
import com.atg.demorch.databinding.ItemRowBeerBinding
import com.atg.demorch.ext.loadImage
import com.atg.demorch.model.Beer

class BeerAdapter(
    private var itemsCells: ArrayList<Beer?>,
    val onSelectedItem: ((Beer) -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: ArrayList<Beer?>) {
        this.itemsCells.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun clearData() {
        val size = this.itemsCells.size
        this.itemsCells.clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_beer, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsCells.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = ItemRowBeerBinding.bind(holder.itemView)
        val item = itemsCells[position]

        binding.tvBeerName.text = item?.name
        binding.tvBeerDescription.text = item?.description
        binding.beerImg.loadImage(item?.image_url)

        holder.itemView.setOnClickListener {
            item?.let {
                onSelectedItem?.invoke(item)
            }
        }
    }
}