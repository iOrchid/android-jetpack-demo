package org.zhiwei.compose.screen.gesture

import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.math.roundToInt

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
        UI_DetectTapGestures()
        Title_Sub_Text(title = "2. 通过modifier的pointerInteropFilter操作符来感知compose的MotionEvents，有Down，UP，MOVE。")
        UI_PointerInteropFilter()
        Title_Sub_Text(title = "3. 通过pointerInput的detectDragGestures，可以处理Drag拖拽事件，。")
        UI_DetectDragGestures()
        UI_DragBoxGestures()
    }
}

//本页面内通用的部分modifier
private val commonModifier = Modifier
    .padding(8.dp)
    .fillMaxWidth()
    .height(50.dp)
    .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp), clip = true)

//通过pointInput感知tap事件
@Composable
private fun UI_DetectTapGestures() {
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

//通过PointerInterop感知MotionEvent
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun UI_PointerInteropFilter() {
    Title_Desc_Text(desc = "pointerInputModifier可以提供对compose的触摸事件MotionEvents的分发阶段的感知。")
    var gestureText by remember { mutableStateOf("MotionEvents，Down/UP/Move") }
    var gestureColor by remember { mutableStateOf(Color(0xFFCADDD5)) }
    val gestureModifier = commonModifier
        .background(gestureColor)
        .pointerInteropFilter { event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    gestureText = "触摸Down"
                    gestureColor = Color(0xFFFCA104)
                }

                MotionEvent.ACTION_MOVE -> {
                    gestureText = "移动Move"
                    gestureColor = Color(0xFF3EEDE7)
                }

                MotionEvent.ACTION_UP -> {
                    gestureText = "抬起UP"
                    gestureColor = Color(0xFF41B349)
                }
                //触摸点超出控件范围就会cancel
                MotionEvent.ACTION_CANCEL -> {
                    gestureText = "取消Cancel"
                    gestureColor = Color(0xFFF43E06)
                }

                else -> {}
            }
            true
        }
    GestureDetectButton(modifier = gestureModifier, text = gestureText)
}

//通过Drag感知拖拽事件
@Composable
private fun UI_DetectDragGestures() {
    Title_Desc_Text(desc = "pointerInputModifier可以提供对compose的触摸事件MotionEvents的分发阶段的感知。")
    var gestureText by remember { mutableStateOf("MotionEvents，Down/UP/Move") }
    var gestureColor by remember { mutableStateOf(Color(0xFFCADDD5)) }

    var gestureDetailText by remember { mutableStateOf("Drag拖拽的变化细节") }

    val gestureModifier = commonModifier
        .background(gestureColor)
        .pointerInput(null) {
            detectDragGestures(
                onDragStart = {
                    gestureText = "onDragStart"
                    gestureColor = Color(0xFFFCA104)
                },
                onDragEnd = {
                    gestureText = "onDragEnd"
                    gestureColor = Color(0xFF41B349)
                },
                onDragCancel = {
                    gestureText = "onDragCancel"
                    gestureColor = Color(0xFFF43E06)
                },
                onDrag = { change: PointerInputChange, dragAmount: Offset ->
                    gestureText = "onDrag"
                    gestureColor = Color(0xFF3EEDE7)
                    gestureDetailText = """
                        拖拽事件的部分详细信息：dragAmount：$dragAmount
                        change.id是PointId：${change.id}
                        change.type：${change.type}
                        change.uptimeMillis：${change.uptimeMillis}
                        change.scrollDelta：${change.scrollDelta}
                        change.previousUptimeMillis：${change.previousUptimeMillis}
                        change.previousPressed：${change.previousPressed}
                        change.previousPosition：${change.previousPosition}
                        change.pressure：${change.pressure}
                        change.position：${change.position}
                    """.trimIndent()
                })
        }
    GestureDetectButton(modifier = gestureModifier, text = gestureText)
    Text(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.LightGray)
            .padding(8.dp),
        color = Color.White,
        text = gestureDetailText,
        textAlign = TextAlign.Center
    )
}

//drag移动控件元素，这里演示长按后自由拖拽，类似的水平，竖直的，触类旁通。不再演示。
@Composable
private fun UI_DragBoxGestures() {
    Title_Desc_Text(desc = "pointerInput内有detectDragGestures,detectDragGesturesAfterLongPress,detectHorizontalDragGestures,detectVerticalDragGestures等多个拖拽事件相关的闭包函数。")
    var gestureText by remember { mutableStateOf("MotionEvents，Down/UP/Move") }
    var gestureColor by remember { mutableStateOf(Color(0xFFCADDD5)) }

    var offX by remember { mutableFloatStateOf(0f) }
    var offY by remember { mutableFloatStateOf(0f) }
    val outBoxSize = remember { mutableStateOf(IntSize.Zero) }
    var gestureDetailText by remember { mutableStateOf("Drag拖拽的变化细节") }

    val gestureModifier = Modifier
        //2.这里会触发位置变化的实际执行。
        .offset { IntOffset(offX.roundToInt(), offY.roundToInt()) }
        .size(50.dp)
        .background(gestureColor)
        .pointerInput(null) {
            //这里挑一个触发长按之后，才能拖动控件的操作符函数。
            detectDragGesturesAfterLongPress(
                onDragStart = {
                    gestureText = "onDragStart"
                    gestureColor = Color(0xFFFCA104)
                },
                onDragEnd = {
                    gestureText = "onDragEnd"
                    gestureColor = Color(0xFF41B349)
                },
                onDragCancel = {
                    gestureText = "onDragCancel"
                    gestureColor = Color(0xFFF43E06)
                },
                //1.这里是根据拖拽事件，更新位置数据，需要上面的offset来执行变化。
                onDrag = { _: PointerInputChange, dragAmount: Offset ->
                    gestureText = "onDrag"
                    gestureColor = Color(0xFF3EEDE7)
                    //上次的控件位置坐标
                    val origin = Offset(offX, offY)
                    //拖拽后的坐标，注意，此时位置可能越界
                    val afterDrag = origin + dragAmount
                    val nowOff = Offset(
                        //使用coerceIn限定以下拖拽后 innerBox的位置，避免越出容器边界
                        x = afterDrag.x.coerceIn(0f, (outBoxSize.value.width - 50).toFloat()),
                        y = afterDrag.y.coerceIn(0f, (outBoxSize.value.height - 50).toFloat())
                    )
                    //更新位置赋值
                    offX = nowOff.x
                    offY = nowOff.y

                    gestureDetailText = """
                        拖拽偏移：$dragAmount
                        原位置：$origin
                        现位置：$nowOff
                    """.trimIndent()
                })
        }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.LightGray)
            .onSizeChanged { outBoxSize.value = it }
    ) {
        //显示信息的
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp),
            color = Color.White,
            text = gestureDetailText,
            textAlign = TextAlign.Center
        )
        Box(gestureModifier)
    }
}

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