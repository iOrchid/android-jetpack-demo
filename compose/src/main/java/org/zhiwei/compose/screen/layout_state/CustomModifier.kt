package org.zhiwei.compose.screen.layout_state

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.random.Random

@Composable
internal fun CustomModifier_Screen(modifier: Modifier = Modifier) {
    LazyColumn(modifier.fillMaxSize()) {
        item {
            UI_CustomModifier()
            UI_GlobalPositionMark()
        }
    }
}


@Composable
private fun UI_CustomModifier() {
    Title_Text("Custom Modifier")
    Title_Sub_Text(title = "1、使用compose的layout库中的Modifier的扩展函数，来获取一个可用于测量尺寸，宽高及布局相关的Modifier对象")
    Title_Desc_Text(desc = "custom Align modifier")
    //下面使用了自定义的扩展函数customAlign，给内部的控件一个定植边距，以及添加对齐方式
    val columnModifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .background(Color.LightGray)
    Column(columnModifier.wrapContentHeight()) {
        Text(
            text = "Align Start with space",
            modifier = Modifier
                .background(Color(0xFF8BC34A))
                .customAlign(align = HorizontalAlign.START)
        )

        Text(
            text = "Align Center with space",
            modifier = Modifier
                .background(Color(0xFF8BC34A))
                .customAlign(align = HorizontalAlign.CENTER)
        )

        Text(
            text = "Align End with space",
            modifier = Modifier
                .background(Color(0xFF8BC34A))
                .customAlign(align = HorizontalAlign.END)
        )
    }
    Title_Desc_Text(desc = "firstBaselineToTop Modifier")
    //对比两个text，因为属性不同，padding 32dp和baseLine top 32dp的效果；
    //padding 是从Text文本的top开始计算。而baseLine是绘制基线
    Row(columnModifier.wrapContentHeight()) {
        Text(
            text = "Padding 32dp",
            modifier = Modifier
                .background(Color(0xFF8BC34A))
                .padding(top = 32.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Baseline 32dp",
            modifier = Modifier
                .background(Color(0xFF8BC34A))
                .firstBaselineToTop(32.dp)
        )
    }
    Title_Sub_Text(title = "2、LayoutModifier对可测量对象布局，添加内边距")
    Title_Desc_Text(desc = "custom padding modifier")
    //实现LayoutModifier来自定义内边距函数
    Text(
        text = "Custom Padding",
        modifier = Modifier
            .background(Color(0xFF8BC34A))
            .paddingNoOffsetNoConstrain(all = 10.dp)
    )
    //组合compose的modifier，可以有状态保存
    Title_Sub_Text(title = "3、Modifier.composed 允许Modifier拥有remember或sideEffects来对自身操作符缓存状态。")
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
    ) {
        //用于演示的计数
        val counter = remember { mutableIntStateOf(0) }
        Button(onClick = { counter.intValue++ }) {
            Text(text = "累加: ${counter.intValue}")
        }
        Title_Desc_Text(desc = "modifier composed的自定义操作符")
        //使用composed操作符，则会记录Modifier的状态
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Box(
                modifier = Modifier
                    //自定义的扩展函数，内部使用composed，并绘制随机颜色的背景，来演示效果
                    .composedBackground(160.dp, 30.dp, 0)
                    .width(150.dp)
            ) {
                Text(text = "重组Recomposed:${counter.intValue}", color = Color.White)
            }
            Box(
                modifier = Modifier
                    .composedBackground(160.dp, 30.dp, 1)
                    .width(150.dp)
            ) {
                Text(text = "重组Recomposed:${counter.intValue}", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Title_Desc_Text(desc = "modifier没有使用组合操作符")
        //没有使用composed，则其颜色会每次都变动
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Box(
                modifier = Modifier
                    //没有使用composed，同样是绘制背景色，
                    .nonComposedBackground(160.dp, 30.dp)
                    .width(150.dp)
            ) {
                //注意⚠️：compose的好多组件可能会有自己作用域内的扩展函数，比如Box内就有Modifier的matchParentSize
                Text(text = "重组Recomposed:${counter.intValue}", color = Color.White)
            }
            Box(
                modifier = Modifier
                    .nonComposedBackground(160.dp, 30.dp)
                    .width(150.dp)
            ) {
                Text(text = "重组Recomposed:${counter.intValue}", color = Color.White)
            }
        }
    }

    Spacer(modifier = Modifier.height(50.dp))
}

@Composable
private fun UI_GlobalPositionMark() {
    val textMeasurer = rememberTextMeasurer()
    Column(modifier = Modifier
        .fillMaxSize()
        //参与到content的画布绘制
        .drawWithContent {
            //必修调用drawContent，否则内部的控件，都不会显示
            drawContent()
            drawHeightMarks(textMeasurer)
        }) {
        Box(modifier = Modifier.height(150.dp)) {
            Column {
                Title_Text(title = "onGloballyPositioned")
                Title_Sub_Text(title = "通过使用drawWithContent来添加额外的需要标记绘制的数据，以此来获取容器的刻度数据")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color(0xFFEEA2A4))
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 4.dp, color = Color(0xFF41B349), shape = RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFFFFF143))
            )
            //可以演示看得出，onGlobalPosition获取到的compose控件在当前屏幕的各层级的坐标位置数据
            Title_Desc_Text(desc = "获取到的compose控件在当前屏幕的各层级的坐标位置数据")
            UI_ShowPosition()
        }
    }
}

@Composable
private fun UI_ShowPosition() {
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth()
        .height(300.dp)
        .verticalScroll(rememberScrollState())
        .border(2.dp, Color(0xFFF0ADA0))
        //内容布局信息的函数
        .onGloballyPositioned {
            val positionInParent: Offset = it.positionInParent()
            val positionInRoot: Offset = it.positionInRoot()
            val positionInWindow: Offset = it.positionInWindow()
            val boundsInParent: Rect = it.boundsInParent()
            val boundsInRoot: Rect = it.boundsInRoot()
            val boundsInWindow: Rect = it.boundsInWindow()
            val parentCoordinates = it.parentCoordinates
            val parentLayoutCoordinates = it.parentLayoutCoordinates
            text = buildString {
                appendLine("在父布局的位置:$positionInParent")
                appendLine("在根布局中位置:$positionInRoot")
                appendLine("在window中位置:$positionInWindow")
                appendLine("在父布局中区域:$boundsInParent")
                appendLine("在根布局中区域:$boundsInRoot")
                appendLine("在window中区域:$boundsInWindow")
                //父布局的坐标
                parentCoordinates?.let { parent ->
                    appendLine("坐标,在父容器中:${parent.positionInParent()}")
                    appendLine("坐标,在根布局中:${parent.positionInRoot()}")
                    appendLine("坐标,在window中:${parent.positionInWindow()}")
                }
                parentLayoutCoordinates?.let { parent ->
                    appendLine("布局,在父容器中:${parent.positionInParent()}")
                    appendLine("布局,在根布局中:${parent.positionInRoot()}")
                    appendLine("布局,在window中:${parent.positionInWindow()}")
                }
            }
        }) {
        //显示所有文本,todo 注意⚠️，在方框内滑动，看数值变化，以及滑动外部的LazyColumn，看数值变化；
        Text(text = text, color = Color(0xFF50616D))
    }
}


//region 通过layout的自定义扩展函数


//自定义的水平对其方式
enum class HorizontalAlign {
    START, CENTER, END
}

/**
 * 自定义的Modifier的扩展函数，基于原modifier对象，继续作用效果；
 * 给内部可测量[Measurable]的对象两边都加上一定值的空白空间，并实现自定义布局对齐方式
 * 尤其注意，是作用于[Measurable]对象，Text是。
 */
private fun Modifier.customAlign(
    space: Int = 60,
    align: HorizontalAlign = HorizontalAlign.CENTER,
    //todo 注意⚠️这里的then函数，暂时未详细学习，简单理解就是字面意思，基于上个modifier对象的作用效果，继续后续操作
) = this.then(

    layout { measurable: Measurable, constraints: Constraints ->

        val placeable = measurable.measure(constraints)
        val width = placeable.measuredWidth + 2 * space

        layout(width, placeable.measuredHeight) {
            when (align) {
                HorizontalAlign.START -> {
                    placeable.placeRelative(0, 0)
                }

                HorizontalAlign.CENTER -> {
                    placeable.placeRelative(space, 0)
                }

                HorizontalAlign.END -> {
                    placeable.placeRelative(2 * space, 0)
                }
            }
        }
    }
)


/**
 * 自定义的modifier扩展函数，实现对layout中的首行text的绘制基线baseline添加上边距
 */
private fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        //判断 composable 控件 是否有首行基线FirstBaseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]
        // 高度计算
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // 布局摆放
            placeable.placeRelative(0, placeableY)
        }
    }
)

//endregion

//region 使用composed和不使用 扩展函数

private fun Modifier.composedBackground(width: Dp, height: Dp, index: Int) = this.composed(
    // 用于在debug模式下，IDE预览等配置的数据信息，release时候则会去掉
    inspectorInfo = debugInspectorInfo {
        //用于匹配modifier的名字
        name = "myModifier"
        //解析参数
        properties["width"] = width
        properties["height"] = height
        properties["index"] = index
    },

    factory = {
        val density = LocalDensity.current
        val color: Color = remember(index) {
            Color(
                red = Random.nextInt(256),
                green = Random.nextInt(256),
                blue = Random.nextInt(256),
                alpha = 255
            )
        }
        //绘制指定颜色的背景
        Modifier.drawBehind {
            val widthInPx = with(density) { width.toPx() }
            val heightInPx = with(density) { height.toPx() }
            drawRect(color = color, topLeft = Offset.Zero, size = Size(widthInPx, heightInPx))
        }
    }
)

private fun Modifier.nonComposedBackground(width: Dp, height: Dp) = this.then(
    //绘制背景区域块和颜色
    Modifier.drawBehind {
        val color: Color = Color(
            red = Random.nextInt(256),
            green = Random.nextInt(256),
            blue = Random.nextInt(256),
            alpha = 255
        )
        val widthInPx = width.toPx()
        val heightInPx = height.toPx()
        drawRect(color = color, topLeft = Offset.Zero, size = Size(widthInPx, heightInPx))
    }
)

//endregion


//扩展函数，用来绘制图层高度刻度
private fun DrawScope.drawHeightMarks(textMeasurer: TextMeasurer) {
    //DrawScope对应的compose控件的size尺寸的高度值
    val height = size.height.toInt()
    //100px为单位，绘制刻度
    for (i in 0..height step 100) {
        drawLine(
            color = Color(0xFFAAC2C4),//刻度颜色
            start = Offset(0f, i.toFloat()),//刻度起始位置
            end = Offset(20f, i.toFloat()),//刻度线20长度，
            strokeWidth = 3.dp.toPx()//刻度宽
        )
        //绘制刻度数
        drawText(
            textMeasurer = textMeasurer,//
            text = "$i",//刻度值
            topLeft = Offset(20f, i - 30f),//绘制刻度值的位置，左侧有刻度线，上下与刻度线位置偏30
            style = TextStyle(color = Color(0xFFCAD0D3), fontWeight = FontWeight.Light)//刻度值的字体样式
        )

    }
}


//region 预览效果

@Preview(showBackground = true, backgroundColor = 0XFFFFFFFF)
@Composable
private fun CustomModifierPreview() {
    CustomModifier_Screen()
}

//endregion