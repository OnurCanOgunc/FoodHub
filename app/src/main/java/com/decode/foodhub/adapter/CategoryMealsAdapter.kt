package com.decode.foodhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decode.foodhub.databinding.CategoryMealsCardBinding
import com.decode.foodhub.models.Meal

class CategoryMealsAdapter: RecyclerView.Adapter<CategoryMealsAdapter.CategoryMeals>() {

    var onItemClick:((Meal)->Unit)? = null
    class CategoryMeals(private val binding: CategoryMealsCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(meal: Meal,onItemClick:(Meal) -> Unit) {
                binding.meal = meal

                binding.cardMeal.setOnClickListener {
                    onItemClick.invoke(meal)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMeals {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoryMeals(CategoryMealsCardBinding.inflate(layoutInflater,parent,false))
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CategoryMeals, position: Int) {
        holder.bind(differ.currentList[position],onItemClick!!)

    }

    private val differCalBack = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCalBack)
}