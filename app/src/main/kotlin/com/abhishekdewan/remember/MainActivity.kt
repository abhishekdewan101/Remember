package com.abhishekdewan.remember

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.abhishekdewan.common_ui.widgets.text.RichTextEditor
import com.abhishekdewan.remember.ui.theme.RememberTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RememberTheme {
                RichTextEditor(modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize())
            }
        }
    }
}