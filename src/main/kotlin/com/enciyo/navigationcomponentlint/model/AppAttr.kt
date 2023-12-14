package com.enciyo.navigationcomponentlint.model

sealed class AppAttr(attr: String) : ResAttr(attr, Schemas.APP) {
    object StartDestination : AppAttr("startDestination")
    object ArgType : AppAttr("argType")
    object Nullable : AppAttr("nullable")
}
