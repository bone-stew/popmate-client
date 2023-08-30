package com.example.popmate.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.popmate.R
import com.example.popmate.model.data.local.PopupStore


class PopupStoreAdapterForListView(val context: Context, val popupStores: List<PopupStore>) : BaseAdapter() {

//    private lateinit var items: List<Item>
//    init {
//        var items: List<Item> = items
//    }

    override fun getCount(): Int {
        return popupStores.size
    }

    override fun getItem(position: Int): Any {
        return popupStores.get(position)
    }

    override fun getItemId(position: Int): Long {
        return  1L
    }
//    fun ItemAdapterForListView(items: List<String>?) {
//        this.items = items
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.row_popupstore, null)

        val titleView = view.findViewById<TextView>(R.id.titleTextView)
        val imageView = view.findViewById<ImageView>(R.id.itemImageView)
        val dateTextView = view.findViewById<TextView>(R.id.titleTextView)
        val locationTextView = view.findViewById<TextView>(R.id.titleTextView)
        val organizerTextView = view.findViewById<TextView>(R.id.titleTextView)

        val popupstore = popupStores[position]
        titleView.text = popupstore.title
        dateTextView.text = popupstore.openDateFormatted
        locationTextView.text = popupstore.location
        organizerTextView.text = popupstore.organizer


        view.setOnClickListener() {
            Toast.makeText(context, "클릭된 아이템 = ${it}", Toast.LENGTH_LONG).show()
        }

        return view
    }



}
