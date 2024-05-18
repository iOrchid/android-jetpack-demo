package org.zhiwei.compose.screen.gesture

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 演示transform相关的变换操作
 */
@Composable
internal fun TransformGestures_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Text(title = "Transform转换操作")
        Title_Sub_Text(title = "1. 通过pointInput的detectTransformGestures操作符，Transform操作可执行平移、缩放、旋转，边界约束的等。")
        UI_TransformGestures()
        Title_Desc_Text(desc = "也可以通过onGesture的内部参数，根据需要，来限定平移边界，缩放边界等各类业务操作。")
        Title_Desc_Text(
            desc = "pointInput内还有一些其他感知交互手势的操作函数，以及不同交互方式还有内部的子函数。也可以多个pointInput不同key组合，多重手势交互。" +
                    "可根据业务自行定义，比如detectTapGestures，awaitEachGesture，awaitFirstDown等。可查看源码学习实现方式。"
        )

        Title_Sub_Text(title = "2.通过draggable操作符实现拖动操作。")

        UI_Draggable()
    }

}

@Composable
private fun UI_Draggable() {
    var orientation by remember { mutableStateOf(Orientation.Horizontal) }
    var offsetX by remember(orientation) { mutableFloatStateOf(0f) }
    var offsetY by remember(orientation) { mutableFloatStateOf(0f) }
    val state = if (orientation == Orientation.Horizontal) {
        rememberDraggableState { delta ->
            offsetX += delta
        }
    } else {
        rememberDraggableState { delta ->
            offsetY += delta
        }
    }
    Title_Desc_Text(desc = "切换拖拽方向")
    Switch(checked = orientation == Orientation.Horizontal, onCheckedChange = { selected ->
        orientation = if (selected) Orientation.Horizontal else Orientation.Vertical
    })
    //因为在column中可滚动的，所以有事件冲突。
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        //由于 Modifer 是链式执行，此时 offset 必需在 draggable 与 background 前面。
        Text(text = "拽我", Modifier
            //offset实际触发变动
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            //draggable感知拖拽
            .size(50.dp)
            .background(Color.Blue)
            .draggable(state, orientation))
    }
}


@Composable
private fun UI_TransformGestures() {
    //数字小数点的格式化
    val decimalFormat = remember { DecimalFormat("0.0") }
    //缩放的比例
    var zoomImage by remember { mutableFloatStateOf(1f) }
    var offsetImage by remember { mutableStateOf(Offset.Zero) }
    //操作中心点
    var centroidImage by remember { mutableStateOf(Offset.Zero) }
    var angleImage by remember { mutableFloatStateOf(0f) }
    var transformInfo by remember { mutableStateOf("显示操作类型和必要信息") }
    //修饰符
    val imageModify = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTransformGestures { centroid, pan, zoom, rotation ->
                //平移操作的计算
                val oldScale = zoomImage
                val newScale = zoomImage * zoom//需要临时标记变量，因为缩放过程zoom会时刻变动。
                offsetImage =
                    (offsetImage + centroid / oldScale).rotateBy(rotation) - (centroid / newScale + pan / oldScale)
                //限定以下缩放的范围，为了UI操作方便
                zoomImage = newScale.coerceIn(0.5f, 5f)
                //旋转
                angleImage += rotation

                //记住操作中心点，用于绘制标记
                centroidImage = centroid
                //信息
                transformInfo =
                    "缩放：${decimalFormat.format(zoomImage)},操控点：$centroidImage ， Pan:$pan ,rotation: $rotation"
            }
        }
        .drawWithContent {
            //通过drawWithContent，来同步在内部绘制一个操作点的标记
            drawContent()
            //平移转换点，绘制一个小圆
            drawCircle(Color.Red, 20f, centroidImage)
        }
        .graphicsLayer {
            //平移
            translationX = -offsetImage.x * zoomImage
            translationY = -offsetImage.y * zoomImage
            //对x，y方向都缩放操作
            scaleX = zoomImage
            scaleY = zoomImage
            //旋转
            rotationZ = angleImage
            TransformOrigin(0f, 0f).also { transformOrigin = it }
        }
    ImageTextBox(
        boxModifier = boxModify,
        imageModifier = imageModify,
        imgRes = R.drawable.sexy_girl,
        text = transformInfo
    )
}

private val boxModify =
    Modifier
        .fillMaxWidth()
        .height(400.dp)
        .clipToBounds()
        .background(Color.LightGray)

/**
 * 简单封装的一个可以用来变换图片以及显示操作信息的Compose元素UI
 */
@Composable
private fun ImageTextBox(
    boxModifier: Modifier,
    imageModifier: Modifier,
    imgRes: Int,
    text: String,
) {
    Box(modifier = boxModifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = imgRes),
            contentDescription = null,
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xCC000000))
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            color = Color.White
        )
    }
}

/**
 * 根据旋转角度来执行旋转操作
 * Rotates the given offset around the origin by the given angle in degrees.
 *
 * A positive angle indicates a counterclockwise rotation around the right-handed 2D Cartesian
 * coordinate system.
 *
 * See: [Rotation matrix](https://en.wikipedia.org/wiki/Rotation_matrix)
 */
fun Offset.rotateBy(angle: Float): Offset {
    val angleInRadians = angle * PI / 180
    return Offset(
        (x * cos(angleInRadians) - y * sin(angleInRadians)).toFloat(),
        (x * sin(angleInRadians) + y * cos(angleInRadians)).toFloat()
    )
}

private const val PI = Math.PI

@Preview
@Composable
private fun PreviewTransform() {
    TransformGestures_Screen()
}