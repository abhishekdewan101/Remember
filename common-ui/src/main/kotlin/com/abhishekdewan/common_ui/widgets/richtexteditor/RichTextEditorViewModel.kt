package com.abhishekdewan.common_ui.widgets.richtexteditor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class EditType {
    BOLD
}

enum class Style {
    HASHTAG,
    BOLD
}

data class Edit(val range: IntRange, val type: EditType)

val HASHTAG_REGEX_PATTERN = Regex(pattern = "(#[A-Za-z0-9-_]+)(?:#[A-Za-z0-9-_]+)*")

class RichTextEditorViewModel {
    private val edits = mutableListOf<Edit>()
    private var _textFieldValue = MutableStateFlow(
        TextFieldValue(
            annotatedString = createAnnotatedString(text = "")
        )
    )
    val textFieldValue: StateFlow<TextFieldValue> = _textFieldValue

    fun updateTextFieldValue(newValue: TextFieldValue) {
        _textFieldValue.value = newValue.copy(annotatedString = createAnnotatedString(text = newValue.text))
    }

    fun processEditType(type: EditType) {
        val selection = _textFieldValue.value.selection
        val edit = Edit(range = IntRange(start = selection.start, endInclusive = selection.end), type = type)
        if (edits.contains(edit)) {
            edits.remove(edit)
        } else {
            edits.add(edit)
        }
        _textFieldValue.value = _textFieldValue.value.copy(annotatedString = createAnnotatedString(text = _textFieldValue.value.text))
    }

    private fun createAnnotatedString(text: String): AnnotatedString = when (edits.size) {
        0 -> processVisualAnnotationsOnString(text = text)
        else -> processEditsOnString(text = processVisualAnnotationsOnString(text = text))
    }

    private fun processEditsOnString(text: AnnotatedString): AnnotatedString {
        val builder = AnnotatedString.Builder()
        var startIndex = 0
        for (edit in edits) {
            println("Process Edit - $edit")
            // append everything before match
            builder.append(text.subSequence(startIndex = startIndex, endIndex = edit.range.first))

            builder.withStyle(style = getStyle(style = Style.BOLD)) {
                append(
                    text.substring(
                        startIndex = edit.range.first,
                        endIndex = edit.range.last
                    )
                )
            }
            startIndex = edit.range.last
        }
        if (startIndex <= text.length - 1) {
            builder.append(text.subSequence(startIndex = startIndex, endIndex = text.length))
        }
        return builder.toAnnotatedString()
    }

    private fun processVisualAnnotationsOnString(text: String): AnnotatedString {
        val builder = AnnotatedString.Builder()
        val matches = HASHTAG_REGEX_PATTERN.findAll(text)
        var startIndex = 0
        for (match in matches) {
            // append everything before match
            builder.append(text.substring(startIndex = startIndex, endIndex = match.range.first))

            // append hashtag style
            builder.withStyle(style = getStyle(style = Style.HASHTAG)) {
                append(text.substring(range = match.range))
            }
            startIndex = match.range.last + 1
        }
        if (startIndex <= text.length - 1) {
            builder.append(text.substring(startIndex = startIndex, endIndex = text.length))
        }
        return builder.toAnnotatedString()
    }

    private fun getStyle(style: Style): SpanStyle = when (style) {
        Style.HASHTAG -> SpanStyle(color = Color.Yellow, fontWeight = FontWeight.Bold)
        Style.BOLD -> SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)
    }
}