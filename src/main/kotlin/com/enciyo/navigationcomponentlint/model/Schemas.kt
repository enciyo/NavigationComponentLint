package com.enciyo.navigationcomponentlint.model

enum class Schemas(val namespace: String, val value: String) {
    ANDROID("http://schemas.android.com/apk/res/android", "android"),
    APP("http://schemas.android.com/apk/res-auto", "app"),
    TOOLS("http://schemas.android.com/tools", "tools")
}