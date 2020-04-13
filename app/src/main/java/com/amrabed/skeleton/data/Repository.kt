package com.amrabed.skeleton.data

import androidx.lifecycle.MutableLiveData
import com.amrabed.skeleton.core.Item

object Repository {
    var itemList = arrayListOf(
        Item(
            title = "Relaxing",
            summary = "Hammock on the beach",
            image = "https://unsplash.com/photos/lRq0kAQ_qr4/download?w=640"
        ),
        Item(
            title = "Joyful",
            summary = "Swing on the beach",
            image = "https://unsplash.com/photos/67sVPjK6Q7I/download?w=640"
        ),
        Item(
            title = "Peaceful",
            summary = "Cabins on the beach",
            image = "https://unsplash.com/photos/Csk0z5DRb1M/download?w=640"
        )
    )

    fun loadItems(): MutableLiveData<ArrayList<Item>> {
        return MutableLiveData(itemList)
    }

    fun add(item: Item) {
        itemList.add(item)
    }

    fun delete(position: Int) {
        itemList.removeAt(position)
    }

    fun update(item: Item) {
    }
}