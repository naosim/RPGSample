package com.naosim.rpgmodel.android.sirokuro

import android.content.SharedPreferences
import com.naosim.rpgmodel.lib.value.ItemSet
import com.naosim.rpgmodel.sirokuro.charactor.GameItem
import com.naosim.rpgmodel.sirokuro.global.DataSaveContainer
import com.naosim.rpgmodel.sirokuro.global.DataSaveRepository
import com.naosim.rpgmodel.sirokuro.global.Status
import com.naosim.rpgmodel.sirokuro.global.Turn
import java.util.*

class DataSaveRepositoryAndroidImpl(val sharedPreferences: SharedPreferences): DataSaveRepository {
    override fun load(): DataSaveContainer {
        val itemSet = ItemSet<GameItem>()
        itemSet.add(GameItem.やくそう)
        itemSet.add(GameItem.やくそう)
        itemSet.add(GameItem.やくそう)
        val status = Status()
        val turnString = sharedPreferences.getString("turn", "kuro_eat")
        status.turnValue.setValue(Turn.valueOf(turnString))
        return DataSaveContainer(status, itemSet)
    }

    override fun save(dataSaveContainer: DataSaveContainer) {
        val editor = sharedPreferences.edit()
        editor.putString("turn", dataSaveContainer.status.turnValue.getValueString())
        // TODO impl
        val itemSet = HashSet<String>()
         dataSaveContainer
                .itemSet
                .list
                .map({ arrayOf(it.itemId.value, it.itemName.value) })
                .map({ "${it[0]},${it[1]}"})
                .forEach { itemSet.add(it) }
        editor.putStringSet("itemSet", itemSet)

        editor.commit()

    }

}
