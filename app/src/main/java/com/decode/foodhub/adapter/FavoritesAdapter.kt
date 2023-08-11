package com.decode.foodhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decode.foodhub.databinding.CategoryMealsCardBinding
import com.decode.foodhub.models.MealX

class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    class FavoritesViewHolder(private val binding: CategoryMealsCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(mealX: MealX) {
                binding.mealX = mealX
                binding.bool = false
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryMealsCardBinding.inflate(layoutInflater,parent,false)
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount() = deffer.currentList.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(deffer.currentList[position])
    }

    private val defferCalBack = object : DiffUtil.ItemCallback<MealX>() {
        override fun areItemsTheSame(oldItem: MealX, newItem: MealX): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealX, newItem: MealX): Boolean {
            return oldItem == newItem
        }

    }

    val deffer = AsyncListDiffer(this,defferCalBack)
}