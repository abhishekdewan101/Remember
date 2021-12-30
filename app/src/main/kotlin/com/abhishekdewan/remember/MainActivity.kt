package com.abhishekdewan.remember

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.abhishekdewan.common_ui.widgets.richtextfield.RichTextField
import com.abhishekdewan.remember.ui.theme.RememberTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            RememberTheme {
                ProvideWindowInsets {
                    RichTextField(
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxSize()
                            .navigationBarsWithImePadding()
                            .statusBarsPadding()
                    )
                }
            }
        }
    }
}