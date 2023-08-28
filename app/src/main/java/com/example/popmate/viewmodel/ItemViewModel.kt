package com.example.popmate.viewmodel

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.R
import com.example.popmate.model.data.local.Item

class ItemViewModel : ViewModel() {
    private val _itemList = MutableLiveData<ArrayList<Item>>()

    val itemList: LiveData<ArrayList<Item>> get() = _itemList

    init {
        val Items: ArrayList<Item> = ArrayList()
        Items.add(Item( "title1"))
        Items.add(Item("title2"))
        Items.add(Item( "title3"))
        Items.add(Item( "title4"))
        Items.add(Item( "title5"))

        _itemList.value = Items
    }

}
