package com.enciyo.navigationcomponentlint.model


sealed class ResAttr(private val attr: String, private val schemas: Schemas) {
    val value get() = "${schemas.value}:$attr"
    val valueNs get() = "${schemas.namespace}:$attr"
}

