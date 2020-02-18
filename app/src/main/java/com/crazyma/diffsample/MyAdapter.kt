package com.crazyma.diffsample

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Batu
 */
class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    var items: List<Item>? = null
        set(value) {
            diffItems(field, value).dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.create(parent)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        items!![position].run {
            holder.bind(floor, message)
        }
    }

    data class Item(val floor: Int, val message: String)

    private fun diffItems(oldItems: List<Item>?, newItems: List<Item>?) =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun getOldListSize() = oldItems?.size ?: 0

            override fun getNewListSize() = newItems?.size ?: 0

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

                val oldItem = oldItems!![oldItemPosition]
                val newItem = newItems!![newItemPosition]

                return oldItem.floor == newItem.floor
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems!![oldItemPosition]
                val newItem = newItems!![newItemPosition]

                return oldItem.message == newItem.message
            }
        })

}