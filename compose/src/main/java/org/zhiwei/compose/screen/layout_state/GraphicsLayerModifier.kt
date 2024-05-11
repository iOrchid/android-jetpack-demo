package org.zhiwei.compose.screen.layout_state

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.math.roundToInt

@Composable
internal fun GraphicsLayerModifier_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            //使用此操作符，就有点类似LazyColumn差不多，可以容纳更多的content，超出就滚动。
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Title_Text(title = "graphicsLayer Modifier")
        Title_Sub_Text(title = "1. Modifier的元素会使得内部容器的compose组件绘制在一个图层之上；该图层可独立于父容器，单独处理内部UI元素的绘制可用状态；使用graphicsLayer可用于独立处理图层上的元素操作。")
        UI_OffsetTranslate()
        UI_ScaleTranslate()
        UI_WidthTranslate()
        UI_ScaleStartEnd()
        UI_Rotation()
        UI_TransformOrigin()
    }
}

@Composable
private fun UI_OffsetTranslate() {
    Title_Sub_Text(title = "graphicsLayer可对内容元素进行缩放、旋转、不透明度、阴影和裁剪等处理。")
    Title_Desc_Text(desc = "Offset和Translate 两种方式的平移对比")
    //上下文context，用于toast
    val context = LocalContext.current
    // 注意⚠️ 如下布局中，无其他额外限定，平移/偏移都会让控件超出父容器的边界
    //偏移或平移的数值
    val xNum = remember { mutableFloatStateOf(0f) }
    Row(modifier = Modifier.border(width = 2.dp, color = Color.Red)) {
        //响应滑动offset偏移的方块
        Box(modifier = Modifier
            .offset { IntOffset(xNum.floatValue.toInt(), 0) }
            .clickable {
                Toast
                    .makeText(
                        context,
                        "点击Offset偏移的方块",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
            .zIndex(2f)
            .shadow(2.dp)
            .border(2.dp, Color.Green)
            .background(Color.Yellow)
            .size(120.dp),
            contentAlignment = Alignment.Center
        ) { Text(text = "偏移Offset") }
        //固定的方块
        Box(
            modifier = Modifier
                .zIndex(1f)
                .background(Color.Blue)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(context, "Offset中固定不动的方块被点击", Toast.LENGTH_SHORT)
                        .show()
                }
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    //下面是translate平移的方块
    Row(modifier = Modifier.border(width = 2.dp, color = Color.Red)) {
        //响应滑动offset偏移的方块
        Box(modifier = Modifier
            .graphicsLayer { translationX = xNum.floatValue }
            .clickable {
                Toast
                    .makeText(
                        context,
                        "点击Translate平移的方块",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
            .zIndex(2f)
            .shadow(2.dp)
            .border(2.dp, Color.Green)
            .background(Color.Yellow)
            .size(120.dp),
            contentAlignment = Alignment.Center
        ) { Text(text = "平移Translate") }
        //固定的方块
        Box(
            modifier = Modifier
                .zIndex(1f)
                .background(Color.Blue)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "Translate中固定不动的方块被点击",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Title_Desc_Text(desc = "偏移量/平移量：${xNum.floatValue}")
    Slider(
        value = xNum.floatValue,
        onValueChange = { xNum.floatValue = it },
        valueRange = 0f..1000f
    )
}

@Composable
private fun UI_ScaleTranslate() {
    Title_Sub_Text(title = "2. 通过graphicsLayer的缩放，并不会改变compose元素的自身尺寸；但是平移会改变元素在父容器中的位置坐标。")
    Title_Desc_Text(desc = "注意观察scale控件的size数值，是不变的。")
    //上下文context，用于toast
    val context = LocalContext.current
    var offsetX by remember { mutableFloatStateOf(0f) }
    var sclX by remember { mutableFloatStateOf(1f) }

    var textSize by remember { mutableStateOf("") }
    var textPosition by remember { mutableStateOf("") }


    Row(modifier = Modifier.border(width = 2.dp, color = Color.Red)) {
        //响应滑动offset偏移的方块
        Image(
            painter = painterResource(id = R.drawable.sexy_girl),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer {
                    translationX = offsetX
                    scaleX = sclX
                }
                .zIndex(2f)
                .shadow(2.dp)
                .border(2.dp, Color.Green)
                .size(120.dp)
                .background(Color.LightGray)
//                .clickable {
//                    Toast
//                        .makeText(context, "Girl图 clickable点击", Toast.LENGTH_SHORT)
//                        .show()
//                }
                //点击的另一种实现方式
                .pointerInput(Unit) {
                    detectTapGestures {
                        Toast
                            .makeText(context, "美女图点击的位置 $it", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .onSizeChanged { textSize = "Size: $it\n" }
                .onGloballyPositioned {
                    textPosition =
                        "在父容器中的位置：${it.positionInParent()}\n在根布局中的位置:${it.positionInRoot()}\n"
                },
        )
        //固定的方块
        Box(
            modifier = Modifier
                .zIndex(1f)
                .background(Color.Blue)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "Translate中固定不动的方块被点击",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
        )
    }
    Title_Sub_Text(title = "尺寸及位置：$textSize $textPosition")
    Spacer(modifier = Modifier.height(5.dp))
    Title_Desc_Text(desc = "平移量：${offsetX}")
    Slider(value = offsetX, onValueChange = { offsetX = it }, valueRange = 0f..1000f)
    Title_Desc_Text(desc = "x轴缩放：${sclX}")
    Slider(value = sclX, onValueChange = { sclX = it }, valueRange = .3f..3f)
}

@Composable
private fun UI_WidthTranslate() {
    Title_Sub_Text(title = "3. 不同与scaleX，动态修改控件的宽度with，也会使得父容器边界发生变化。")
    val context = LocalContext.current
    var offsetX by remember { mutableFloatStateOf(0f) }
    var width by remember { mutableFloatStateOf(120f) }

    var textPosition by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .border(width = 2.dp, color = Color.Red)
//        .width(300.dp)//默认是wrapContent，如果设定了固定值，下面的子控件width变化就不会影响父容器宽度。
    ) {
        //响应滑动offset偏移的方块
        Image(
            painter = painterResource(id = R.drawable.sexy_girl),
            contentDescription = null,
            modifier = Modifier
                //宽度的变化，也会使得外部容器的宽度依次变化（如果没有固定限定的话）
                .width(width.dp)
                .graphicsLayer {
                    translationX = offsetX
                }
                .zIndex(2f)
                .shadow(2.dp)
                .border(2.dp, Color.Green)
                .size(120.dp)
                .background(Color.LightGray)
                .clickable {
                    Toast
                        .makeText(context, "Girl图 clickable点击", Toast.LENGTH_SHORT)
                        .show()
                }
                .onGloballyPositioned {
                    textPosition =
                        "在父容器中的边界：${it.boundsInParent()}\n在根布局中的边界:${it.boundsInParent()}\n"
                },
        )
        //固定的方块
        Box(
            modifier = Modifier
                .zIndex(1f)
                .background(Color.Blue)
                .size(120.dp)
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "Translate中固定不动的方块被点击",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
        )
    }
    Title_Sub_Text(title = "边界：$textPosition")
    Spacer(modifier = Modifier.height(5.dp))
    Title_Desc_Text(desc = "平移量：${offsetX}")
    Slider(value = offsetX, onValueChange = { offsetX = it }, valueRange = 0f..1000f)
    Title_Desc_Text(desc = "宽度：${width}")
    Slider(value = width, onValueChange = { width = it }, valueRange = 0f..500f)
}

@Composable
private fun UI_ScaleStartEnd() {
    Title_Sub_Text(title = "4. 默认的scaleX缩放是从对称轴双向缩放的，也可以实现缩放start/end方向，这样有点类似宽度width改变的样子。")
    var scX by remember { mutableFloatStateOf(1f) }
    var width by remember { mutableFloatStateOf(0f) }
    Title_Desc_Text(desc = "Scale End方向")
    Row(
        modifier = Modifier
            .border(width = 2.dp, color = Color.Red)
    ) {
        //响应滑动offset偏移的方块
        Image(
            painter = painterResource(id = R.drawable.sexy_girl),
            contentDescription = null,
            modifier = Modifier
                //通过平移与缩放的结合，来实现定边 缩放的效果。
                .graphicsLayer {
                    translationX = (width * scX - width) / 2
                    scaleX = scX
                }
                .onSizeChanged { width = it.width.toFloat() }
                .zIndex(2f)
                .size(120.dp)
                .background(Color.LightGray),
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Title_Desc_Text(desc = "End方向缩放量：${scX}")
    Slider(value = scX, onValueChange = { scX = it }, valueRange = .3f..3f)

    Title_Desc_Text(desc = "Scale Start方向")
    var scX2 by remember { mutableFloatStateOf(1f) }
    var width2 by remember { mutableFloatStateOf(0f) }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Row(
            modifier = Modifier
                .border(width = 2.dp, color = Color.Red)
        ) {
            //响应滑动offset偏移的方块
            Image(
                painter = painterResource(id = R.drawable.sexy_girl),
                contentDescription = null,
                modifier = Modifier
                    //通过平移与缩放的结合，来实现定边 缩放的效果。
                    .graphicsLayer {
                        translationX = -(width2 * scX2 - width2) / 2
                        scaleX = scX2
                    }
                    .onSizeChanged { width2 = it.width.toFloat() }
                    .zIndex(2f)
                    .size(120.dp)
                    .background(Color.LightGray),
            )
        }
    }

    Spacer(modifier = Modifier.height(5.dp))
    Title_Desc_Text(desc = "Start方向缩放量：${scX2}")
    Slider(value = scX2, onValueChange = { scX2 = it }, valueRange = .3f..3f)
}

@Composable
private fun UI_Rotation() {
    Title_Sub_Text(title = "5. 通过Modifier.graphicsLayer来实现对子控件的旋转rotation操作，能够实现对x、y、z轴不同的方式旋转。")
    var rx by remember { mutableFloatStateOf(0f) }
    var ry by remember { mutableFloatStateOf(0f) }
    var rz by remember { mutableFloatStateOf(0f) }
    var camDistance by remember { mutableFloatStateOf(5f) }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.border(2.dp, Color.Red)) {
            Image(
                painter = painterResource(id = R.drawable.img_moto_girl),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer {
                        rotationX = rx
                        rotationY = ry
                        rotationZ = rz
                        cameraDistance = camDistance
                    }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Title_Desc_Text(desc = "x轴旋转: ${rx.round2Digits()}")
        Slider(value = rx, onValueChange = { rx = it }, valueRange = 0f..360f)
        Title_Desc_Text(desc = "y轴旋转: ${ry.round2Digits()}")
        Slider(value = ry, onValueChange = { ry = it }, valueRange = 0f..360f)
        Title_Desc_Text(desc = "z轴旋转: ${rz.round2Digits()}")
        Slider(value = rz, onValueChange = { rz = it }, valueRange = 0f..360f)
        Title_Desc_Text(desc = "CameraDistance视距: ${camDistance.round2Digits()}")
        Slider(value = camDistance, onValueChange = { camDistance = it }, valueRange = 0f..10f)
    }
}

@Composable
private fun UI_TransformOrigin() {
    Title_Sub_Text(title = "6. 通过Modifier.graphicsLayer有transformOrigin来控制旋转轴的轴心偏移 默认是0.5F的居中。")
    var tX by remember { mutableFloatStateOf(0f) }
    var rx by remember { mutableFloatStateOf(0f) }
    var ry by remember { mutableFloatStateOf(0f) }
    var rz by remember { mutableFloatStateOf(0f) }
    var pfx by remember { mutableFloatStateOf(0.5f) }

    Box(modifier = Modifier.border(2.dp, Color.Red)) {
        Image(
            painter = painterResource(id = R.drawable.img_moto_girl),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    translationX = tX
                    rotationX = rx
                    rotationY = ry
                    rotationZ = rz
                    transformOrigin =
                        TransformOrigin(pivotFractionX = pfx, pivotFractionY = .5f)
                }
        )
    }
    Spacer(modifier = Modifier.height(30.dp))
    Title_Desc_Text(desc = "平移x方向: ${tX.round2Digits()}")
    Slider(value = tX, onValueChange = { tX = it }, valueRange = 0f..360f)
    Title_Desc_Text(desc = "x轴旋转: ${rx.round2Digits()}")
    Slider(value = rx, onValueChange = { rx = it }, valueRange = 0f..360f)
    Title_Desc_Text(desc = "y轴旋转: ${ry.round2Digits()}")
    Slider(value = ry, onValueChange = { ry = it }, valueRange = 0f..360f)
    Title_Desc_Text(desc = "z轴旋转: ${rz.round2Digits()}")
    Slider(value = rz, onValueChange = { rz = it }, valueRange = 0f..360f)
    Title_Desc_Text(desc = "pivotFractionX的值: ${pfx.round2Digits()}")
    Slider(value = pfx, onValueChange = { pfx = it })
}

//扩展函数，这样可以让显示的float值是两位小数点,因为*100而后roundInt，再/100.
private fun Float.round2Digits() = (this * 100).roundToInt() / 100f

//region 预览

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun GraphicsLayerModifierPreview() {
    GraphicsLayerModifier_Screen()
}

//endregion