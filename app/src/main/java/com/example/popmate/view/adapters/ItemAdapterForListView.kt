package com.example.popmate.view.adapters

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.popmate.R
import com.example.popmate.model.data.local.Item
import com.kakao.sdk.common.KakaoSdk.init
import org.w3c.dom.Text
import java.security.AccessController.getContext




class ItemAdapterForListView(val context: Context, val items: List<Item>) : BaseAdapter() {

//    private lateinit var items: List<Item>
//    init {
//        var items: List<Item> = items
//    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items.get(position)
    }

    override fun getItemId(position: Int): Long {
        return  1L
    }
//    fun ItemAdapterForListView(items: List<String>?) {
//        this.items = items
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.main_list_item, null)

        val textView = view.findViewById<TextView>(R.id.mainItemText)
        val item = items[position]
        textView.text = item.itemName
        view.setOnClickListener() {
            Toast.makeText(context, "클릭된 아이템 = ${it}", Toast.LENGTH_LONG).show()
        }

//        var textView = TextView(context);
//        textView.text = items.get(position).itemName
        return view
    }



}
