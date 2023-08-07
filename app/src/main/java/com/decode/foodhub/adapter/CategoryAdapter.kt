package com.decode.foodhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decode.foodhub.databinding.CategoryCardBinding
import com.decode.foodhub.models.Category
import javax.inject.Inject

class CategoryAdapter @Inject constructor() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHodler>() {

    var onItemClick: ((Category) -> Unit)? = null

    class CategoryViewHodler(private val binding: CategoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(category: Category , onItemClick:(Category)->Unit) {
                binding.category = category

                binding.layout.setOnClickListener {
                    onItemClick.invoke(category)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHodler {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryCardBinding.inflate(layoutInflater,parent,false)
        return CategoryViewHodler(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CategoryViewHodler, position: Int) {
        onItemClick?.let { holder.bind(differ.currentList[position], it) }
    }

    private val differCalBack = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCalBack)


}