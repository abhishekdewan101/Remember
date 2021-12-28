package com.abhishekdewan.common_ui.widgets.richtextfield

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun RichTextField(modifier: Modifier = Modifier) {
    val viewModel = remember { RickTextFieldViewModel() }
    val textFieldValue = viewModel.textFieldValue.collectAsState()
    val isSelectionEnabled = viewModel.isSelectionEnabled.collectAsState()

    Column(modifier = modifier) {
        TextField(
            value = textFieldValue.value,
            onValueChange = viewModel::updateTextFieldValue,
            modifier = Modifier
                .weight(7f)
                .verticalScroll(rememberScrollState()),
            colors = textFieldColors(textColor = Color.White)
        )
        if (isSelectionEnabled.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.DarkGray)
            ) {
                IconButton(onClick = { viewModel.processEditType(Style.BOLD) }) {
                    Text(text = "B", style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold))
                }
                IconButton(onClick = { viewModel.processEditType(Style.ITALICS) }) {
                    Text(text = "I", style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic))
                }
                IconButton(onClick = { viewModel.processEditType(Style.HEADING) }, modifier = Modifier.size(64.dp)) {
                    Text(text = "HEADING", style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
@Preview
fun PreviewRichTextArea() {
    RichTextField(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    )
}