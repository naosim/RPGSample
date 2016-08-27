package com.naosim.rpgmodel.lib.android

import android.view.View
import android.widget.Button
import com.naosim.rpgmodel.lib.model.value.ItemSet
import com.naosim.rpgmodel.lib.model.viewmodel.ItemSelectorViewModel
import com.naosim.rpgmodel.sirokuro.charactor.GameItem

class ItemSelectorViewModelImpl(
        val yakusouButton: Button,
        override val itemSet: ItemSet<GameItem>,
        override var onSelectedItem: (GameItem) -> Unit): ItemSelectorViewModel<GameItem> {

    init {
        yakusouButton.setOnClickListener {
            onSelectedItem.invoke(itemSet.list.get(0))
        }

        itemSet.register(this){ update() }
        update()
    }

    private fun update() {
        yakusouButton.visibility = if(itemSet.list.isEmpty()) View.INVISIBLE else View.VISIBLE
        yakusouButton.text = String.format("やくそう x %d", itemSet.list.size)
    }
}