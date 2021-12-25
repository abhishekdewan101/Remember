package com.abhishekdewan.common_ui.widgets.richtexteditor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.abhishekdewan.common_ui.widgets.richtexteditor.EditorAction.BOLD

@Composable
fun RichTextEditor(modifier: Modifier = Modifier) {
    var textFieldState by remember { mutableStateOf(TextFieldValue(text = "")) }
    var selectionRange by remember { mutableStateOf(IntRange(start = 0, endInclusive = 0)) }
    var editorAction by remember { mutableStateOf(EditorAction.NONE) }
    Column(modifier = Modifier.fillMaxSize()) {
        if (textFieldState.selection.length > 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Button(onClick = {
                    val start = textFieldState.selection.start
                    val end = textFieldState.selection.start + textFieldState.selection.length
                    selectionRange = IntRange(start = start, endInclusive = end)
                    editorAction = BOLD
                }) {
                    Text("Bold")
                }
            }
        }
        TextField(
            value = textFieldState,
            onValueChange = { textFieldState = it },
            modifier = modifier.verticalScroll(
                rememberScrollState()
            ),
            colors = textFieldColors(textColor = Color.White),
            visualTransformation = RichTextVisualTransformation(
                selectedRange = selectionRange,
                editorAction = editorAction
            )
        )
    }
}