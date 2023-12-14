package com.enciyo.navigationcomponentlint.model

import com.gitlab.mvysny.konsumexml.Konsumer


data class Argument(
    val name: String,
    val argType: String,
    val defaultValue: String?,
    val nullable: String?
) {

    val tag = "argument"
    val attrs
        get() = listOf(
            AndroidAttr.Name to name,
            AppAttr.ArgType to argType,
            AppAttr.Nullable to nullable,
            AndroidAttr.DefaultValue to defaultValue
        )

    companion object {
        fun xml(k: Konsumer): Argument {
            k.checkCurrent("argument")
            return Argument(
                name = k.attributes.getValue("name", Schemas.ANDROID.namespace),
                defaultValue = k.attributes.getValueOrNull("defaultValue", Schemas.ANDROID.namespace),
                argType = k.attributes.getValue("argType", Schemas.APP.namespace),
                nullable = k.attributes.getValueOrNull("nullable", Schemas.APP.namespace)
            )
        }
    }
}