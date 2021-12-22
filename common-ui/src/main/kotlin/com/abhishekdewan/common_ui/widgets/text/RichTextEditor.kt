package com.abhishekdewan.common_ui.widgets.text

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.abhishekdewan.common_ui.widgets.utils.getStartingText

@Composable
fun RichTextEditor(modifier: Modifier = Modifier, startingLineCount: Int = 50) {
    var text by remember { mutableStateOf(getStartingText(startingLineCount)) }
    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = modifier.verticalScroll(
            rememberScrollState()
        )
    )
}