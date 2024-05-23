package com.alikhan.projecttrial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CustomAdapter(private val itemList: List<Any>, function: () -> Unit) : RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val removeButton: ImageView = itemView.findViewById(R.id.removeButton)
        val addButton: ImageView = itemView.findViewById(R.id.addButton)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        if (item is Item) {
            // Regular item
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.img) // Placeholder image while loading
                .into(holder.imageView)
            holder.descriptionTextView.text = item.description
            holder.priceTextView.text = item.price
            holder.quantityTextView.text = item.quantity.toString()

            // Decrease quantity when remove button is clicked
            holder.removeButton.setOnClickListener {
                var quantity = item.quantity
                if (quantity > 0) {
                    quantity--
                    item.quantity = quantity
                    holder.quantityTextView.text = quantity.toString()
                }
            }

            // Increase quantity when add button is clicked
            holder.addButton.setOnClickListener {
                var quantity = item.quantity
                quantity++
                item.quantity = quantity
                holder.quantityTextView.text = quantity.toString()

                // Calculate total price and show as toast
                val totalPrice = quantity * item.price.toDouble() // Calculate total price
                val toastMessage = "Total Price: $$totalPrice" // Construct toast message
                Toast.makeText(holder.itemView.context, toastMessage, Toast.LENGTH_SHORT).show() // Show toast
            }
        } else if (item is Itemrec) {
            // Clicked item (handled differently or can be ignored)
        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }
}
