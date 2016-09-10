package com.naosim.rpg.model.mogura.map.f1

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.MoguraItem
import com.naosim.rpglib.model.script.ScriptUtil
import com.naosim.rpglib.model.value.ValueImutable

interface Scenario : ValueImutable<String>
interface ScenarioExecuter<T: Scenario> {
    fun run(scenario: T)
}


enum class MoguraF1Scenario: Scenario {
    テーブル;
    override val value = name

}

class MoguraF1ScenarioExecuter(val globalContainer: MoguraGlobalContainer): ScenarioExecuter<MoguraF1Scenario> {
    val scriptUtil = ScriptUtil(globalContainer.scriptExecutor)

    override fun run(scenario: MoguraF1Scenario) {
        when(scenario) {
            MoguraF1Scenario.テーブル -> {
                if(!globalContainer.itemSet.has(MoguraItem.letter)) {
                    scriptUtil.script(
                            """
                            テーブルに手紙ある
                            """,
                            { globalContainer.itemSet.add(MoguraItem.letter) }
                    )
                } else {
                    scriptUtil.script(
                            """
                            テーブルはからっぽだ
                            """
                    )
                }

            }
        }
    }

}