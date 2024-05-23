package com.alikhan.projecttrial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class OrderAdapter(private val orderList: List<OrderDisplayy>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val deliveredTextView: TextView = itemView.findViewById(R.id.amountTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.date1TextVieew)
        val priceTextView: TextView = itemView.findViewById(R.id.price1TextView)
        val orderImageView: ImageView = itemView.findViewById(R.id.box1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.twentythree_layout, parent, false) // Replace with your item layout
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = orderList[position]
        holder.orderIdTextView.text = currentItem.orderId
        holder.deliveredTextView.text = currentItem.delivered
        holder.dateTextView.text = currentItem.date
        holder.priceTextView.text = currentItem.price

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(currentItem.imageResource)
            .into(holder.orderImageView)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}
