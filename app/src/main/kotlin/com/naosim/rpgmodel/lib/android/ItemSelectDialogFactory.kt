package com.naosim.rpgmodel.lib.android

import android.app.Dialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.naosim.rpgmodel.lib.model.value.Item
import com.naosim.rpgsample.R

class ItemSelectDialogFactory {
    fun showItemListDialog(context: Context, itemList: List<Item>, onItemSelectedListener: (Item) -> Unit) {
        if (itemList.size == 0) {
            Toast.makeText(context, "どうぐがありません", Toast.LENGTH_SHORT).show()
            return
        }

        createItemListDialog(context, itemList, onItemSelectedListener).show()
    }

    fun createItemListDialog(context: Context, itemList: List<Item>, onItemSelectedListener: (Item) -> Unit): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item, null)
        val alertDialog = AlertDialog.Builder(context).setView(view).create()

        val listView = view.findViewById(R.id.itemListView) as ListView
        listView.adapter = ItemListAdapter(context, itemList)
        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val selectedItem = adapterView.adapter.getItem(i) as Item
            onItemSelectedListener.invoke(selectedItem)
            alertDialog.dismiss()
        }

        view.findViewById(R.id.cancelButton).setOnClickListener { alertDialog.dismiss() }

        return alertDialog
    }
}



