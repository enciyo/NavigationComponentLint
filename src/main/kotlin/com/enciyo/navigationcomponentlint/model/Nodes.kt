package com.enciyo.navigationcomponentlint.model

import com.gitlab.mvysny.konsumexml.Names

enum class Nodes(val localName: String) {
    Activity("activity"),
    Fragment("fragment"),
    Dialog("dialog"),
    Action("action"),
    Argument("argument");

    companion object {
        fun of(vararg nodes: Nodes) = Names.of(*nodes.map { it.localName }.toTypedArray())
    }
}

