package com.abhishekdewan.common_ui.widgets.richtexteditor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class Style {
    HASHTAG,
    BOLD,
    HEADING,
}

data class Edit(val range: IntRange, val style: Style)

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

    fun processEditType(style: Style) {
        val selection = _textFieldValue.value.selection
        val edit = Edit(range = IntRange(start = selection.start, endInclusive = selection.end), style = style)
        if (edits.contains(edit)) {
            edits.remove(edit)
        } else {
            // TODO: We shouldn't just add everything. We need to figure out if the new edit is overlapping ranges and type and then break them out.
            edits.add(edit)
        }
        _textFieldValue.value = _textFieldValue.value.copy(annotatedString = createAnnotatedString(text = _textFieldValue.value.text))
    }

    private fun createAnnotatedString(text: String): AnnotatedString = when (edits.size) {
        0 -> processVisualAnnotationsOnString(text = text)
        else -> processEditsOnString(text = processVisualAnnotationsOnString(text = text))
    }

    private fun processEditsOnString(text: AnnotatedString): AnnotatedString {

        val spanStyles = text.spanStyles.toMutableList()
        for (edit in edits) {
            println("Processing Edit - $edit")
            spanStyles.add(AnnotatedString.Range(getStyle(style = edit.style), start = edit.range.first, end = edit.range.last))
        }

        return AnnotatedString(text = text.text, spanStyles = spanStyles)
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
        Style.HEADING -> SpanStyle(fontSize = 32.sp)
    }
}