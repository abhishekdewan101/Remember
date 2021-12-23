package com.abhishekdewan.common_ui.widgets.richtexteditor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle

class HashTagVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(text.annotateHashTags(), OffsetMapping.Identity)
    }
}

private fun AnnotatedString.annotateHashTags(): AnnotatedString {
    val words: List<String> = text.split("\\s+".toRegex())
    val hashTagRegex = Regex(pattern = "(#[A-Za-z0-9-_]+)(?:#[A-Za-z0-9-_]+)*")
    val builder = AnnotatedString.Builder()
    words.forEach {
        if (hashTagRegex.matches(it)) {
            builder.withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                append("$it ")
            }
        } else {
            builder.withStyle(style = SpanStyle(color = Color.Black)) {
                append("$it ")
            }
        }
    }
    return builder.toAnnotatedString()
}