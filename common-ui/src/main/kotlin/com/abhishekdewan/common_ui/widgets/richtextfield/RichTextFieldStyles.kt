package com.abhishekdewan.common_ui.widgets.richtextfield

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

sealed class RichTextFieldStyles(
    private val fontSize: TextUnit = 16.sp,
    private val fontWeight: FontWeight = FontWeight.Normal,
    private val fontColor: Color = Color.White,
    private val fontStyle: FontStyle = FontStyle.Normal
) {
    object Title : RichTextFieldStyles(fontSize = 32.sp, fontWeight = FontWeight.Bold)
    object Heading : RichTextFieldStyles(fontSize = 28.sp, fontWeight = FontWeight.Bold)
    object SubHeading : RichTextFieldStyles(fontSize = 24.sp, fontWeight = FontWeight.Bold)
    object Body : RichTextFieldStyles(fontWeight = FontWeight.Normal)

    object Bold : RichTextFieldStyles(fontWeight = FontWeight.Bold)
    object Italics : RichTextFieldStyles(fontStyle = FontStyle.Italic)
    object HashTags : RichTextFieldStyles(fontColor = Color.Yellow, fontWeight = FontWeight.Bold)

    fun toSpanStyle() = SpanStyle(fontSize = fontSize, fontWeight = fontWeight, color = fontColor, fontStyle = fontStyle)
}