package com.abhishekdewan.common_ui.widgets.utils

fun getStartingText(numberOfLines: Int): String {
    var startText = ""
    repeat(numberOfLines) {
        startText += "\n"
    }
    return startText
}