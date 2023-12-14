package com.enciyo.navigationcomponentlint.model

import com.enciyo.navigationcomponentlint.utils.localNode
import com.gitlab.mvysny.konsumexml.Konsumer
import java.lang.IllegalStateException

data class Navigation(
    val id: String?,
    val startDestination: String,
    val fragment: List<Direction>,
    val dialog: List<Direction>,
    val activity: List<Direction>,
    val action: List<Action>,
) {

    val attrs
        get() = listOf(
            AndroidAttr.Id.value to id,
            AppAttr.StartDestination.value to startDestination,
            "xmlns:android" to  Schemas.ANDROID.namespace,
            "xmlns:app" to  Schemas.APP.namespace,
            "xmlns:tools" to  Schemas.TOOLS.namespace,
        )

    companion object {
        fun xml(k: Konsumer): Navigation {
            k.checkCurrent("navigation")

            val fragments = arrayListOf<Direction>()
            val dialogs = arrayListOf<Direction>()
            val activities = arrayListOf<Direction>()
            val actions = arrayListOf<Action>()
            val id = k.attributes.getValueOrNull("id", Schemas.ANDROID.namespace)
            val startDestination = k.attributes.getValue("startDestination", Schemas.APP.namespace)

            k.children(Nodes.of(Nodes.Dialog, Nodes.Fragment, Nodes.Activity, Nodes.Action)) {
                when(localNode){
                    Nodes.Activity -> activities.add(Direction.xml(this))
                    Nodes.Fragment -> fragments.add(Direction.xml(this))
                    Nodes.Dialog -> dialogs.add(Direction.xml(this))
                    Nodes.Action -> actions.add(Action.xml(this))
                    else -> throw IllegalStateException("Doesn't handled $localName")
                }

            }

            return Navigation(
                id = id,
                startDestination = startDestination,
                dialog = dialogs,
                fragment = fragments,
                activity = activities,
                action = actions,
            )
        }
    }
}