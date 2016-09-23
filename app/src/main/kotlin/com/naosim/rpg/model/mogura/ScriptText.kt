package com.naosim.rpg.model.mogura

import com.naosim.rpglib.model.script.HasKirikiriScript
import com.naosim.rpglib.model.script.KirikiriScript

enum class ScriptText(val value: String): HasKirikiriScript {
    item_use_letter(
            """
            父親のメモを読んだ[r]
            右上から
            左に3歩
            下に3歩
            """
    ),
    f1_看板(
            """
            張り紙がある[r]
            張り紙には
            「とぉさんは出かけてきます。」
            と書かれている
            """
    ),
    f1_table_find_letter(
            """
            テーブルにメモをみつけた[r]
            「とぉさんメモ」を手に入れた
            """
    ),

    f1_table_nothing(
            """
            テーブルには何もない
            """
    ),

    b1_switch_before(
            """
            床にスイッチを見つけた[r]
            押してみた
            """
    ),
    b1_switch_after(
            """
            壁に通り道ができた
            """
    ),
    b2_neko_before_eathquake(
            """
            おや
            君が来るなんてめずらしいね
            """
    ),

    ;

    override val kirikiriScript = KirikiriScript(value)
}
