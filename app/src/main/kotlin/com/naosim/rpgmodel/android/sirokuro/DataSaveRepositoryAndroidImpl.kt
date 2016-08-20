package com.naosim.rpgmodel.android.sirokuro

import android.content.SharedPreferences
import com.naosim.rpgmodel.lib.value.ItemSet
import com.naosim.rpgmodel.lib.value.field.FieldNameImpl
import com.naosim.rpgmodel.lib.value.field.Position
import com.naosim.rpgmodel.lib.value.field.X
import com.naosim.rpgmodel.lib.value.field.Y
import com.naosim.rpgmodel.sirokuro.charactor.GameItem
import com.naosim.rpgmodel.sirokuro.global.DataSaveContainer
import com.naosim.rpgmodel.sirokuro.global.DataSaveRepository
import com.naosim.rpgmodel.sirokuro.global.Status
import com.naosim.rpgmodel.sirokuro.global.Turn
import com.naosim.rpgmodel.sirokuro.map.YagiFieldName
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

        val position = Position(
                FieldNameImpl(sharedPreferences.getString("fieldName", YagiFieldName.main.value)),
                X(sharedPreferences.getInt("x", 0)),
                Y(sharedPreferences.getInt("y", 0))
        )

        return DataSaveContainer(status, itemSet, position)
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

        editor.putString("fieldName", dataSaveContainer.position.fieldName.value)
        editor.putInt("x", dataSaveContainer.position.x.value)
        editor.putInt("y", dataSaveContainer.position.y.value)

        editor.commit()

    }

}
