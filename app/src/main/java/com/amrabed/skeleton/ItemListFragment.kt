package com.amrabed.skeleton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amrabed.skeleton.core.Item
import com.amrabed.skeleton.swipe.DragListener
import com.amrabed.skeleton.swipe.SwipeHandler
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_list_element.view.*
import kotlinx.android.synthetic.main.item_list_fragment.*
import kotlinx.android.synthetic.main.item_list_fragment.view.*

/**
 * List Fragment using RecyclerView to display list of Items loaded using ViewModel.
 * List supports the following operations:
 *  - Add new item
 *  - Swipe to refresh
 *  - Swipe items left and right to delete and hide
 *  - Drag items to move in list
 */
class ItemListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private val viewModel: ItemViewModel by lazy {
        ViewModelProvider(activity as ViewModelStoreOwner).get(ItemViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_list_fragment, container, false)
        loadItems()
        view.swipeToRefresh.setOnRefreshListener(this)
        view.addFab.setOnClickListener(this)
        return view
    }

    override fun onRefresh() {
        view?.swipeToRefresh?.isRefreshing = true
        loadItems()
        view?.swipeToRefresh?.isRefreshing = false
    }

    /**
     * Called when the FAB is clicked -> Add new item
     */
    override fun onClick(view: View?) {
        val item = Item()
        viewModel.select(item)
        parentFragmentManager.beginTransaction().replace(R.id.fragment, ItemEditFragment())
            .addToBackStack(null).commit()
        viewModel.add(item)
    }

    private fun loadItems() {
        viewModel.itemList.observe(viewLifecycleOwner, Observer { items ->
            listView.adapter = ItemListAdapter(items)
        })
    }

    inner class ItemListAdapter(private val items: ArrayList<Item>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), DragListener {
        private val touchHelper: ItemTouchHelper = ItemTouchHelper(SwipeHandler(context!!, this))

        init {
            touchHelper.attachToRecyclerView(listView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_element, parent, false)
            return ItemViewHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item = items[position]
            holder.itemView.title.text = item.title
            holder.itemView.summary.text = item.summary

            Glide.with(activity!!).load(item.image).placeholder(R.drawable.placeholder)
                .circleCrop()
                .into(holder.itemView.icon)

            holder.itemView.setOnClickListener {
                selectItem(item)
            }

            holder.itemView.setOnLongClickListener {
                onDrag(holder)
                false
            }
        }

        override fun onDrag(holder: RecyclerView.ViewHolder) {
            touchHelper.startDrag(holder)
        }

        override fun onItemMoved(
            source: RecyclerView.ViewHolder,
            destination: RecyclerView.ViewHolder
        ) {
            notifyItemMoved(source.adapterPosition, destination.adapterPosition)
        }

        override fun onItemRemoved(holder: RecyclerView.ViewHolder) {
            val position = holder.adapterPosition
            val item = items[position]
            items.removeAt(position)
//            viewModel.delete(position)
            notifyItemRemoved(position)
            // Show undo message to allow user to undo deletion
            Snackbar.make(view!!, R.string.itemRemoved, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo) {
                    items.add(position, item)
//                    viewModel.add(item)
                    notifyItemInserted(position)
                }
                .show()
        }

        override fun onItemHidden(holder: RecyclerView.ViewHolder) {
            val position = holder.adapterPosition
            items.removeAt(position)
            notifyItemRemoved(position)
        }

        private fun selectItem(item: Item) {
            viewModel.select(item)
            parentFragmentManager.beginTransaction().replace(R.id.fragment, ItemDetailsFragment())
                .addToBackStack(null).commit()
        }

        inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }
}
