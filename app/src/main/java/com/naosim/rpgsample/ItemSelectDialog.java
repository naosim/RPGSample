package com.naosim.rpgsample;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.naosim.rpgmodel.lib.value.Item;

import java.util.List;

public class ItemSelectDialog {
    static void showItemListDialog(Context context, List<Item> itemList, final OnItemSelectedListener onItemSelectedListener) {
        if(itemList.size() == 0) {
            Toast.makeText(context, "どうぐがありません", Toast.LENGTH_SHORT).show();
            return;
        }

        createItemListDialog(context, itemList, onItemSelectedListener).show();
    }

    static Dialog createItemListDialog(Context context, List<Item> itemList, final OnItemSelectedListener onItemSelectedListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item, null);
        final AlertDialog alertDialog = new AlertDialog
                .Builder(context)
                .setView(view)
                .create();

        ListView listView = (ListView)view.findViewById(R.id.itemListView);
        listView.setAdapter(new ItemListAdapter(context, itemList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemListAdapter<Item> adapter = (ItemListAdapter<Item>)parent.getAdapter();
                Item selectedItem = adapter.getItem(position);
                onItemSelectedListener.onItemSelected(selectedItem);
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }

    interface OnItemSelectedListener {
        void onItemSelected(Item item);
    }
}
