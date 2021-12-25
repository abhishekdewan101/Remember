package com.abhishekdewan.common_ui.widgets.richtexteditor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import com.abhishekdewan.common_ui.widgets.richtexteditor.EditorAction.BOLD

enum class EditorAction {
    NONE,
    BOLD
}

class RichTextVisualTransformation(
    private val selectedRange: IntRange,
    private val editorAction: EditorAction
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var transformedText = text
        transformedText = processHashTags(transformedText)
        when (editorAction) {
            BOLD -> transformedText = processBoldSelection(transformedText, selectedRange)
            else -> {}
        }
        return TransformedText(text = transformedText, offsetMapping = OffsetMapping.Identity)
    }

    private fun processBoldSelection(
        text: AnnotatedString,
        selectedRange: IntRange
    ): AnnotatedString {
        val builder = AnnotatedString.Builder()
        var startIndex = 0
        builder.append(text.subSequence(startIndex = startIndex, endIndex = selectedRange.first))
        builder.withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
            append(text.substring(startIndex = selectedRange.first, endIndex = selectedRange.last))
        }
        startIndex = selectedRange.last
        if (startIndex < text.length) {
            builder.append(text.subSequence(startIndex = startIndex, endIndex = text.length))
        }

        return builder.toAnnotatedString()
    }

    private fun processHashTags(text: AnnotatedString): AnnotatedString {
        val builder = AnnotatedString.Builder()
        val hashTagRegex = Regex(pattern = "(#[A-Za-z0-9-_]+)(?:#[A-Za-z0-9-_]+)*")
        val matches = hashTagRegex.findAll(text)
        var startIndex = 0
        matches.forEach {
            builder.append(text.subSequence(startIndex = startIndex, endIndex = it.range.first))
            builder.withStyle(
                style = SpanStyle(
                    color = Color.Yellow,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(text.substring(it.range))
            }
            startIndex = it.range.last + 1
        }
        builder.append(text.subSequence(startIndex = startIndex, endIndex = text.length))
        return builder.toAnnotatedString()
    }
}