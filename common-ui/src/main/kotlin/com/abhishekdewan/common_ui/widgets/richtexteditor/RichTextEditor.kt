package com.abhishekdewan.common_ui.widgets.richtexteditor

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RichTextEditor(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        maxLines = 50,
        modifier = modifier.verticalScroll(
            rememberScrollState()
        ),
        colors = textFieldColors(textColor = Color.White),
        visualTransformation = HashTagVisualTransformation()
    )
}