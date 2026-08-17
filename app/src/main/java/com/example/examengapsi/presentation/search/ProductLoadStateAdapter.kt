package com.example.examengapsi.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examengapsi.databinding.ActivityMainBinding

class ProductLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ProductLoadStateAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ActivityMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            binding.loading.visibility =
                if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding =
            ActivityMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}