package org.zhiwei.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.zhiwei.compose.screen.Home_Screen
import org.zhiwei.compose.ui.theme.ComposeFeatureTheme

/**
 * Compose功能学习演示模块的主入口界面
 */
class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //此界面使用androidx的enableEdgeToEdge 实现侵入式状态栏
        enableEdgeToEdge()
        setContent {
            ComposeFeatureTheme {
                Scaffold { padding ->
                    Home_Screen(
                        Modifier
                            .fillMaxSize()
                            .background(color = Color(0XFFF2FDFF))
                            .padding(padding)
                    )
                }
            }
        }
    }
}

/**
 * 预览界面，仅作用域AndroidStudio的编辑预览，配置参数可与实际不同，所以具体运行效果要看实际配置
 */
@Preview
@Composable
fun PreviewComposeActivity() {
    ComposeFeatureTheme {
        Scaffold { padding ->
            Home_Screen(
                Modifier
                    .fillMaxSize()
                    .background(color = Color(0XFFF2FDFF))
                    .padding(padding)
            )
        }
    }
}