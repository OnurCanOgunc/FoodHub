package com.decode.foodhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decode.foodhub.databinding.CategoryMealsCardBinding
import com.decode.foodhub.models.MealX

class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    var onItemClick: ((MealX) -> Unit)? = null
    class FavoritesViewHolder(private val binding: CategoryMealsCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(mealX: MealX,onItemClick:(MealX)->Unit) {
                binding.mealX = mealX
                binding.bool = false

                binding.cardMeal.setOnClickListener {
                    onItemClick.invoke(mealX)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryMealsCardBinding.inflate(layoutInflater,parent,false)
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(differ.currentList[position],onItemClick!!)
    }

    private val defferCalBack = object : DiffUtil.ItemCallback<MealX>() {
        override fun areItemsTheSame(oldItem: MealX, newItem: MealX): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealX, newItem: MealX): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,defferCalBack)
}