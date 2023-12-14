package com.enciyo.navigationcomponentlint.model

sealed class AndroidAttr(attr: String) : ResAttr(attr, Schemas.ANDROID) {
    object Id : AndroidAttr("id")
    object Name : AndroidAttr("name")
    object DefaultValue : AndroidAttr("defaultValue")
    object Label : AndroidAttr("label")
}
