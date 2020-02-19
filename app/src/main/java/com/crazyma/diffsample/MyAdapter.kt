package com.crazyma.diffsample

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Batu
 */
class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    companion object {
        const val TYPE_BIG_MESSAGE = 0
        const val TYPE_SHORT_MESSAGE = 1
    }

    var items: MutableList<Item>? = null
        set(value) {
            diffItems(field, value).dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return when (viewType) {
            TYPE_BIG_MESSAGE -> BigMessageViewHolder.create(parent)
            TYPE_SHORT_MESSAGE -> ShortMessageViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown ViewHolder for view type: $viewType.")
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun getItemViewType(position: Int) = when (items?.get(position)) {
        is BigItem -> TYPE_BIG_MESSAGE
        is ShortItem -> TYPE_SHORT_MESSAGE
        else -> {
            throw RuntimeException("Unable to determine view type")
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items?.get(position) ?: return

        when (getItemViewType(position)) {
            TYPE_BIG_MESSAGE -> {
                item as BigItem
                (holder as BigMessageViewHolder).bind(item.floor, item.message)
            }
            TYPE_SHORT_MESSAGE -> {
                Log.d("badu", "position $position")
                item as ShortItem
                (holder as ShortMessageViewHolder).bind(item.floor, item.message)
            }
        }
    }

    fun insertToFirstItem(){
        items?.let{
            it.add(
                0,
                ShortItem(1000 + it.size, "item inserted by adapter")
            )
        }
        notifyItemInserted(0)
    }

    abstract class Item(val floor: Int)

    abstract class MessageItem(floor: Int, val message: String) : Item(floor)

    class BigItem(floor: Int, message: String) : MessageItem(floor, message)

    class ShortItem(floor: Int, message: String) : MessageItem(floor, message)

    private fun diffItems(oldItems: List<Item>?, newItems: List<Item>?) =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun getOldListSize() = oldItems?.size ?: 0

            override fun getNewListSize() = newItems?.size ?: 0

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

                val oldItem = oldItems!![oldItemPosition]
                val newItem = newItems!![newItemPosition]
                if (oldItem::class != newItem::class) {
                    return false
                }

                return oldItem.floor == newItem.floor
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems!![oldItemPosition]
                val newItem = newItems!![newItemPosition]
                if (oldItem::class != newItem::class) {
                    return false
                }
                return when (oldItem) {
                    is BigItem -> (newItem as BigItem).message === oldItem.message
                    is ShortItem -> (newItem as ShortItem).message === oldItem.message
                    else -> false
                }
            }
        })

}