package com.example.examengapsi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.examengapsi.R
import com.example.examengapsi.databinding.ItemProductBinding
import com.example.examengapsi.domain.model.Product
import com.example.examengapsi.utils.toPrice

class ProductAdapter :
    ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(
        val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val product = getItem(position)




        holder.binding.apply {

            tvProduct.text = product.name

            tvPrice.text = product.price.toPrice()

            Glide.with(imgProduct.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_error)
                .into(imgProduct)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }
}