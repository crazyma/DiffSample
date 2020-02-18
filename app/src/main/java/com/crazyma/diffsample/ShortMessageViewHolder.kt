package com.crazyma.diffsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_test_short.view.*

/**
 * @author Batu
 */
class ShortMessageViewHolder(
    itemView: View
) : MyViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup) = ShortMessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_test_short,
                parent,
                false
            )
        )
    }

    fun bind(floor: Int, message: String) {
        itemView.apply {
            titleTextView.text = floor.toString()
            messageTextView.text = message
        }
    }
}