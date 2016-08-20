package com.naosim.rpgsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.naosim.rpgmodel.lib.value.Item;

import java.util.List;

public class ItemListAdapter<T extends Item> extends ArrayAdapter<T> {

    public ItemListAdapter(Context context, List<T> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_item_row, null);
        }

        T item = getItem(position);
        ((TextView) convertView.findViewById(R.id.itemTextView)).setText(item.getItemName().getValue());

        return convertView;
    }
}
