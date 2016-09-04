package com.naosim.rpg.android.mogura

import android.content.SharedPreferences
import com.naosim.rpg.model.mogura.B1Switch
import com.naosim.rpg.model.mogura.MoguraItem
import com.naosim.rpg.model.mogura.MoguraStatus
import com.naosim.rpg.model.sirokuro.charactor.GameItem
import com.naosim.rpg.model.sirokuro.map.YagiFieldName
import com.naosim.rpglib.model.gametool.DataSaveContainer
import com.naosim.rpglib.model.gametool.DataSaveRepository
import com.naosim.rpglib.model.value.ItemSet
import com.naosim.rpglib.model.value.field.FieldNameImpl
import com.naosim.rpglib.model.value.field.Position
import com.naosim.rpglib.model.value.field.X
import com.naosim.rpglib.model.value.field.Y

class MoguraDataSaveRepositoryAndroidImpl(val sharedPreferences: SharedPreferences): DataSaveRepository<MoguraStatus, MoguraItem> {
    override fun load(): DataSaveContainer<MoguraStatus, MoguraItem> {
        val itemSet = ItemSet<MoguraItem>()
        val itemCsv = sharedPreferences.getString("itemCsv", "${GameItem.やくそう.itemId.value}")

//        itemCsv
//                .split(",")
//                .filter { it.trim().length > 0 }
//                .map { getGameItem(ItemId(it)) }
//                .forEach { itemSet.add(it) }
        val status = MoguraStatus(B1Switch(false))
//        val turnString = sharedPreferences.getString("turn", "kuro_eat")
//        status.turnValue.setValue(Turn.valueOf(turnString))

        val position = Position(
                FieldNameImpl(sharedPreferences.getString("fieldName", YagiFieldName.main.value)),
                X(sharedPreferences.getInt("x", 0)),
                Y(sharedPreferences.getInt("y", 0))
        )

        return DataSaveContainer<MoguraStatus, MoguraItem>(status, itemSet, position)
    }

    override fun save(dataSaveContainer: DataSaveContainer<MoguraStatus, MoguraItem>) {
        val editor = sharedPreferences.edit()
//        editor.putString("turn", dataSaveContainer.status.turnValue.getValueString())
//        val itemCsv = dataSaveContainer
//                .itemSet
//                .list
//                .map({ it.itemId.value })
//                .joinToString(",")
//        editor.putString("itemCsv", itemCsv)

        editor.putString("fieldName", dataSaveContainer.position.fieldName.value)
        editor.putInt("x", dataSaveContainer.position.x.value)
        editor.putInt("y", dataSaveContainer.position.y.value)

        editor.commit()

    }
}