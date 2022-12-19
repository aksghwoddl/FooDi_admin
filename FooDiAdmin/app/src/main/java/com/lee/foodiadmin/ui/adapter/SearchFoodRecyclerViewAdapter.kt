package com.lee.foodiadmin.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.PopupMenu.OnMenuItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.lee.foodiadmin.R
import com.lee.foodiadmin.data.model.FoodData
import com.lee.foodiadmin.databinding.SearchItemBinding

class SearchFoodRecyclerViewAdapter : RecyclerView.Adapter<SearchFoodRecyclerViewAdapter.SearchFoodRecyclerViewHolder>() {
    private var mSearchFoodList = mutableListOf<FoodData>()
    private var mOnItemClickListener : OnItemClickListener? = null
    private var mOnMenuItemClickListener : OnMenuItemClickListener? = null

    private lateinit var mSelectedData : FoodData

    interface OnItemClickListener {
        fun onClick(view : View , model : FoodData , position: Int){ }
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mOnItemClickListener = listener
    }

    fun setOnMenuItemClickListener(listener: OnMenuItemClickListener){
        mOnMenuItemClickListener = listener
    }

    fun getSelectedData() = mSelectedData

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
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                mSelectedData = data
                itemView.setOnClickListener {
                    mOnItemClickListener?.onClick(it , data , position)
                }
                itemView.setOnLongClickListener {
                    val popupMenu = PopupMenu(binding.root.context , it)
                    popupMenu.menuInflater.inflate(R.menu.delete_menu , popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener(mOnMenuItemClickListener)
                    popupMenu.show()
                    true
                }
            }
        }
    }
}