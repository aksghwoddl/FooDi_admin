package com.lee.foodiadmin.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lee.foodiadmin.data.model.FoodData
import com.lee.foodiadmin.databinding.SearchItemBinding

class SearchFoodRecyclerViewAdapter : RecyclerView.Adapter<SearchFoodRecyclerViewAdapter.SearchFoodRecyclerViewHolder>() {
    private var mSearchFoodList = mutableListOf<FoodData>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchFoodRecyclerViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return SearchFoodRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchFoodRecyclerViewHolder, position: Int) {
        holder.bind(mSearchFoodList[position])
    }

    override fun getItemCount() = mSearchFoodList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list : MutableList<FoodData>){
        mSearchFoodList = list
        notifyDataSetChanged()
    }

    inner class SearchFoodRecyclerViewHolder(private val binding : SearchItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : FoodData){
            with(binding){
                foodIdTextView.text = data.id.toString()
                foodNameTextView.text = data.foodName
            }
        }
    }
}