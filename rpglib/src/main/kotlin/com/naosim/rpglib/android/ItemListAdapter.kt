package com.naosim.rpglib.android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.naosim.rpglib.R
import com.naosim.rpglib.model.value.Item

class ItemListAdapter<T : Item>(context: Context, objects: List<T>) : ArrayAdapter<T>(context, 0, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_item_row, null)
        }

        val item = getItem(position)
        (convertView!!.findViewById(R.id.itemTextView) as TextView).text = item.itemName.value

        return convertView
    }
}
