package com.example.examengapsi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examengapsi.databinding.ItemPageBinding

class PageAdapter(
    private val onPageClick: (Int) -> Unit
) : ListAdapter<Int, PageAdapter.PageViewHolder>(DiffCallback()) {

    inner class PageViewHolder(
        private val binding: ItemPageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(page: Int) {
            binding.tvPage.text = page.toString()

            binding.root.setOnClickListener {
                onPageClick(page)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageViewHolder {

        val binding = ItemPageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PageViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Int>() {

        override fun areItemsTheSame(
            oldItem: Int,
            newItem: Int
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Int,
            newItem: Int
        ) = oldItem == newItem
    }
}