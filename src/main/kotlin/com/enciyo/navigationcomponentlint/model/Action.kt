package com.enciyo.navigationcomponentlint.model

import com.gitlab.mvysny.konsumexml.Konsumer

data class Action(
    val id: String,
    val destination: String,
    val popUpTo: String?, //Support
    val popUpToInclusive: String? // Support
){
    val attrs
        get() = listOf(
            AndroidAttr.Id to id,
            AppAttr.StartDestination to destination,
        )

    companion object {
        fun xml(k: Konsumer): Action {
            k.checkCurrent("action")
            return Action(
                id = k.attributes.getValue("id", namespace = Schemas.ANDROID.namespace),
                destination = k.attributes.getValue("destination", Schemas.APP.namespace),
                popUpTo = k.attributes.getValueOrNull("popUpTo", Schemas.APP.namespace),
                popUpToInclusive = k.attributes.getValueOrNull("popUpToInclusive", Schemas.APP.namespace)
            )
        }
    }

}