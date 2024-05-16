package org.zhiwei.compose.screen.gesture

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

/**
 * 点击，拖拽手势
 */
@Composable
internal fun TapDragGestures_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Text(title = "Tap和Drag手势")
        Title_Sub_Text(title = "1. 使用pointerInput的detectTapGestures来感知触摸事件")
        Title_Desc_Text(desc = "感知Press、Tap单击、DoubleClick双击、LongClick双击。")
        var gestureText by remember { mutableStateOf("Press,Tap,Drag,DoubleClick,LongClick") }
        var gestureColor by remember { mutableStateOf(Color(0xFFFCA104)) }
        val gestureModifier = commonModifier
            .background(gestureColor)
            .pointerInput(Unit) {
                detectTapGestures(onDoubleTap = {
                    gestureText = "双击DoubleClick"
                    gestureColor = Color(0xFF41B349)
                }, onLongPress = {
                    gestureText = "长按LongPress"
                    gestureColor = Color(0xFFF43E06)
                }, onPress = {
                    gestureText = "按下Press"
                    gestureColor = Color(0xFFCADDD5)
                }, onTap = {
                    gestureText = "点击Tap"
                    gestureColor = Color(0xFF3EEDE7)
                })
            }
        GestureDetectButton(modifier = gestureModifier, text = gestureText)
        Title_Desc_Text(desc = "通过tryAwaitRelease来等待⌛️释放按压事件。")
        //如上可值，默认的触摸事件，会分为按压，单击，双击，长按，是通过触摸down，up，等事件判断的。
        var gestureText2 by remember { mutableStateOf("Press,Tap,Drag,DoubleClick,LongClick") }
        var gestureColor2 by remember { mutableStateOf(Color(0xFFFCA104)) }
        // 这里可以单独处理按压down，而后等待up，而不去触发其他事件类型分析。
        val awaitModifier = commonModifier
            .background(gestureColor2)
            .pointerInput(Unit) {
                detectTapGestures(onPress = {
                    gestureText2 = "按下Press"
                    gestureColor2 = Color(0xFFCADDD5)
                    val released = runCatching { tryAwaitRelease() }.getOrElse { false }
                    if (released) {
                        gestureText2 = "Release释放了"
                        gestureColor2 = Color(0xFF41B349)
                    } else {
                        gestureText2 = "Cancel取消了"
                        gestureColor2 = Color(0xFFF43E06)
                    }
                })
            }
        GestureDetectButton(modifier = awaitModifier, text = gestureText2)
    }
}

//本页面内通用的部分modifier
private val commonModifier = Modifier
    .padding(8.dp)
    .fillMaxWidth()
    .height(50.dp)
    .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp), clip = true)

@Composable
private fun GestureDetectButton(modifier: Modifier, text: String) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(text = text, color = Color.White, fontSize = 12.sp)
    }
}


@Preview
@Composable
private fun PreviewTapDrag() {
    TapDragGestures_Screen()
}