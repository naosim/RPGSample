package com.naosim.rpg.android.sirokuro

import android.content.SharedPreferences
import com.naosim.rpglib.model.value.ItemId
import com.naosim.rpglib.model.value.ItemSet
import com.naosim.rpglib.model.value.field.FieldNameImpl
import com.naosim.rpglib.model.value.field.Position
import com.naosim.rpglib.model.value.field.X
import com.naosim.rpglib.model.value.field.Y
import com.naosim.rpg.model.sirokuro.charactor.GameItem
import com.naosim.rpg.model.sirokuro.charactor.getGameItem
import com.naosim.rpglib.model.gametool.DataSaveContainer
import com.naosim.rpglib.model.gametool.DataSaveRepository
import com.naosim.rpg.model.sirokuro.global.Status
import com.naosim.rpg.model.sirokuro.global.Turn
import com.naosim.rpg.model.sirokuro.map.YagiFieldName

class DataSaveRepositoryAndroidImpl(val sharedPreferences: SharedPreferences): DataSaveRepository<Status, GameItem> {
    override fun load(): DataSaveContainer<Status, GameItem> {
        val itemSet = ItemSet<GameItem>()
        val itemCsv = sharedPreferences.getString("itemCsv", "${GameItem.やくそう.itemId.value}")

        itemCsv
                .split(",")
                .filter { it.trim().length > 0 }
                .map { getGameItem(ItemId(it)) }
                .forEach { itemSet.add(it) }
        val status = Status()
        val turnString = sharedPreferences.getString("turn", "kuro_eat")
        status.turnValue.setValue(Turn.valueOf(turnString))

        val position = Position(
                FieldNameImpl(sharedPreferences.getString("fieldName", YagiFieldName.main.value)),
                X(sharedPreferences.getInt("x", 0)),
                Y(sharedPreferences.getInt("y", 0))
        )

        return DataSaveContainer(status, itemSet, position)
    }

    override fun save(dataSaveContainer: DataSaveContainer<Status, GameItem>) {
        val editor = sharedPreferences.edit()
        editor.putString("turn", dataSaveContainer.status.turnValue.getValueString())
        val itemCsv = dataSaveContainer
                .itemSet
                .list
                .map({ it.itemId.value })
                .joinToString(",")
        editor.putString("itemCsv", itemCsv)

        editor.putString("fieldName", dataSaveContainer.position.fieldName.value)
        editor.putInt("x", dataSaveContainer.position.x.value)
        editor.putInt("y", dataSaveContainer.position.y.value)

        editor.commit()

    }
}
