package org.zhiwei.compose.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Compose的主页面UI的screen
 */
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Text(text = "Compose的HomeScreen页面")
}


@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}