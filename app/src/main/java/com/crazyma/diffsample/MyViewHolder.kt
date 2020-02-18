package com.crazyma.diffsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_test.view.*

/**
 * @author Batu
 */
class MyViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup) = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_test,
                parent,
                false
            )
        )
    }

    fun bind(floor: Int, message: String){
        itemView.apply {
            titleTextView.text = floor.toString()
            messageTextView.text = message
        }
    }
}