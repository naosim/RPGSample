package com.naosim.rpgmodel.lib.model.value

import java.util.*

interface Item {
    val itemId: ItemId
    val itemName: ItemName
}

class ItemName(override val value: String): ValueImutable<String> {
}

class ItemId(override val value: String): ValueImutable<String> {
}

class ItemSet<I: Item>(): RegisterManagerPublic<ItemSet<I>> {
    val list = ArrayList<I>()
    val registerManager = RegisterManager<ItemSet<I>>()

    fun add(item: I) {
        list.add(item)
        registerManager.invoke(this)
    }

    fun remove(targetItem: I) {
        var sameItem: Item? = null
        var sameTypeItem: Item? = null
        for(item in list) {
            if(item.itemId == targetItem.itemId) {
                sameTypeItem = item
            } else if(item == targetItem) {
                sameItem = item
            }
        }

        if(sameItem != null) {
            list.remove(sameItem)
            registerManager.invoke(this)
        } else if(sameTypeItem != null) {
            list.remove(sameTypeItem)
            registerManager.invoke(this)
        }
    }

    override fun register(thiz: Any, callback: (ItemSet<I>) -> Unit) {
        registerManager.register(thiz, callback)
    }

    override fun unregister(thiz: Any) {
        registerManager.unregister(thiz)
    }
}

