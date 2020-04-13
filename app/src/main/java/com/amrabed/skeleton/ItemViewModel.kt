package com.amrabed.skeleton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amrabed.skeleton.core.Item
import com.amrabed.skeleton.data.Repository

class ItemViewModel : ViewModel() {
    internal val itemList: LiveData<ArrayList<Item>> by lazy { Repository.loadItems() }

    private var selectedItem = MutableLiveData<Item>()

    fun add(item: Item) {
        Repository.add(item)
    }

    fun update(item: Item) {
        Repository.update(item)
    }

    fun delete(position: Int) {
        Repository.delete(position)
    }

    fun select(item: Item) {
        selectedItem.value = item
    }

    fun getSelected(): LiveData<Item> {
        return selectedItem
    }
}