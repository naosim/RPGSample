package com.naosim.rpg.model.mogura.map

enum class MoguraMapMoveEvent {
    f1_move_to_b1,
    b1_move_to_f1,
    b1_switch,
    b1_move_to_b2,
    b2_move_to_b1_in_house,
    b2_move_to_b3_in_house,
    b2_move_to_b1_outof_house,
    ;
    val fieldName: MoguraFieldName

    init {
        fieldName =  MoguraFieldName.values().filter { name.contains("to_${it.value}")}.first()
    }
}