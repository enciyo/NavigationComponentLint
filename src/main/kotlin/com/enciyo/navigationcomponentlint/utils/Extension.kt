package com.enciyo.navigationcomponentlint.utils

import com.enciyo.navigationcomponentlint.model.Direction
import com.enciyo.navigationcomponentlint.model.Navigation
import com.enciyo.navigationcomponentlint.model.Nodes
import com.enciyo.navigationcomponentlint.model.ResAttr
import com.enciyo.processor.TagFather
import com.enciyo.processor.tag
import com.gitlab.mvysny.konsumexml.Konsumer
import java.lang.IllegalStateException


val Konsumer.localNode
    get() = Nodes.values().find { it.localName == this.localName }
        ?: throw IllegalStateException("Not handled this")


fun TagFather.attr(pair: Pair<ResAttr, String?>) = pair.second?.let { attr(pair.first.value, it) }
fun TagFather.attrS(pair: Pair<String, String?>) = pair.second?.let { attr(pair.first, it) }



fun TagFather.createDirection(direction: Direction) = with(this) {
    tag(direction.navigatorName) {
        direction.attrs.forEach(::attr)
        direction.arguments.forEach { argument ->
            tag(argument.tag) {
                argument.attrs.forEach(::attr)
            }
        }
    }
}

fun createNav(navigation: Navigation): TagFather {
    return tag("navigation") {
        navigation.attrs.forEach(::attrS)
        navigation.fragment.forEach(::createDirection)
        navigation.dialog.forEach(::createDirection)
        navigation.activity.forEach(::createDirection)
    }
}