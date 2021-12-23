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
    val builder = AnnotatedString.Builder()
    val hashTagRegex = Regex(pattern = "(#[A-Za-z0-9-_]+)(?:#[A-Za-z0-9-_]+)*")
    val matches = hashTagRegex.findAll(text)
    var startIndex = 0
    matches.forEach {
        builder.append(
            text.substring(
                IntRange(
                    start = startIndex,
                    endInclusive = it.range.first - 1
                )
            )
        )
        builder.withStyle(style = SpanStyle(color = Color.Yellow, fontWeight = FontWeight.Bold)) {
            append(text.substring(it.range))
        }
        startIndex = it.range.last + 1
    }
    builder.append(text.substring(IntRange(start = startIndex, endInclusive = text.length - 1)))
    return builder.toAnnotatedString()
}