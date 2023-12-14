package com.enciyo.navigationcomponentlint.model

import com.enciyo.navigationcomponentlint.utils.localNode
import com.gitlab.mvysny.konsumexml.Konsumer

data class Direction(
    val id: String,
    val name: String,
    val label: String?,
    val arguments: List<Argument>,
    val action: List<Action>,
    val navigatorName:String
) {

    val attrs
        get() = listOf(
            AndroidAttr.Id to id,
            AndroidAttr.Name to name,
            AndroidAttr.Label to label
        )
    companion object {
        fun xml(k: Konsumer): Direction {
            val id = k.attributes.getValue("id", Schemas.ANDROID.namespace)
            val name = k.attributes.getValue("name", Schemas.ANDROID.namespace)
            val label = k.attributes.getValueOrNull("label", Schemas.ANDROID.namespace)
            val navigatorName = k.tagName

            val arguments = arrayListOf<Argument>()
            val actions = arrayListOf<Action>()

            k.children(Nodes.of(Nodes.Argument, Nodes.Action)) {
                when (localNode) {
                    Nodes.Argument -> arguments.add(Argument.xml(this))
                    Nodes.Action -> actions.add(Action.xml(this))
                    else -> throw IllegalStateException("Not handled $localName")
                }
            }

            return Direction(
                id = id,
                name = name,
                label = label,
                arguments = arguments,
                action = actions,
                navigatorName = navigatorName
            )
        }
    }
}


