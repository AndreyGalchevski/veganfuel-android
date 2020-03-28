package com.andreygalchevski.veganfuel.screens.foods

import android.animation.LayoutTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andreygalchevski.veganfuel.R
import com.andreygalchevski.veganfuel.network.Food


class FoodAdapter(private var data: List<Food>) :
    RecyclerView.Adapter<FoodAdapter.FoodItemViewHolder>() {

    class FoodItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodNameText: TextView = itemView.findViewById(R.id.food_name_text)
        val measureText: TextView = itemView.findViewById(R.id.measure_text)
        val contentText: TextView = itemView.findViewById(R.id.content_text)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.food_item_view, parent, false)
        return FoodItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val item = data[position]
        holder.foodNameText.text = item.name
        holder.measureText.text = item.measure
        val content = "Nutrient content: ${item.nutrients[0].value} ${item.nutrients[0].unit}"
        holder.contentText.text = content
    }

    fun filterList(filteredList: List<Food>) {
        data = filteredList
        notifyDataSetChanged()
    }
}