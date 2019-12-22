package am.mandiri.movie.base

import am.mandiri.movie.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerViewAdapter<T>.ItemViewHolder>() {

    var items = listOf<T>()
        set(value) {
            val old = field
            field = value
            val diff = DiffUtil.calculateDiff(ItemDiffUtil(old, value))
            diff.dispatchUpdatesTo(this)
        }

    var footerLayout = R.layout.nothing
        set(value) {
            field = value
            notifyItemChanged(itemCount - 1)
        }

    var onItemClickListener: OnItemClickListener<T>? = null

    var onItemLongClickListener: OnItemLongClickListener<T>? = null

    @LayoutRes
    abstract fun getItemLayout(): Int

    override fun getItemCount(): Int {
        return items.count() + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position < items.count()) {
            return getItemLayout()
        }
        return footerLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (position in 0 until items.count()) {
            holder.setupItem(position, items[position])
        } else {
            holder.setupFooter()
        }
    }

    interface OnItemClickListener<in T> {
        fun onItemClick(view: View, item: T)
        fun onFooterClick(view: View)
    }

    interface OnItemLongClickListener<in T> {
        fun onItemLongClick(view: View, item: T)
        fun onFooterLongClick(view: View)
    }

    abstract fun onBindItem(itemView: View, position: Int)

    abstract fun onBindFooter(itemView: View)

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setupItem(position: Int, item: T) {
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(itemView, item)
            }
            itemView.setOnLongClickListener {
                onItemLongClickListener?.onItemLongClick(itemView, item)
                return@setOnLongClickListener true
            }
            onBindItem(itemView, position)
        }

        fun setupFooter() {
            itemView.setOnClickListener {
                onItemClickListener?.onFooterClick(itemView)
            }
            itemView.setOnLongClickListener {
                onItemLongClickListener?.onFooterLongClick(itemView)
                return@setOnLongClickListener true
            }
            onBindFooter(itemView)
        }

    }


    inner class ItemDiffUtil(private val old: List<T>, private val new: List<T>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return old.count()
        }

        override fun getNewListSize(): Int {
            return new.count()
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return this@RecyclerViewAdapter.areItemsTheSame(old[oldItemPosition], new[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return this@RecyclerViewAdapter.areContentsTheSame(old[oldItemPosition], new[newItemPosition])
        }

    }


}