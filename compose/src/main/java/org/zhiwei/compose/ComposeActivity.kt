package org.zhiwei.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.zhiwei.compose.screen.HomeScreen

/**
 * Compose功能学习演示模块的主入口界面
 */
class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //此界面使用androidx的enableEdgeToEdge 实现侵入式状态栏
        enableEdgeToEdge()
        setContent {
            Scaffold { padding ->
                HomeScreen(
                    Modifier
                        .fillMaxSize()
                        .safeContentPadding()
                        .padding(padding)
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewComposeActivity() {
    HomeScreen()
}