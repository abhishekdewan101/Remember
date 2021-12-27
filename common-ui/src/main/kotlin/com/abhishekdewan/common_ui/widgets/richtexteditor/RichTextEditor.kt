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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RichTextEditor(modifier: Modifier = Modifier) {
    val viewModel = remember { RichTextEditorViewModel() }
    val textFieldValue = viewModel.textFieldValue.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        if (textFieldValue.value.selection.length > 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Button(onClick = {
                    viewModel.processEditType(EditType.BOLD)
                }) {
                    Text("Bold")
                }
            }
        }
        TextField(
            value = textFieldValue.value,
            onValueChange = viewModel::updateTextFieldValue,
            modifier = modifier.verticalScroll(
                rememberScrollState()
            ),
            colors = textFieldColors(textColor = Color.White)
        )
    }
}