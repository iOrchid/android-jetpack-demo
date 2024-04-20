package org.zhiwei.compose.screen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

@Composable
internal fun UI_CustomModifier(modifier: Modifier = Modifier) {
    LazyColumn(modifier.fillMaxSize()) {
        item {
            Title_Text("Custom Modifier")
            Title_Sub_Text(title = "1、使用compose的layout库中的Modifier的扩展函数，来获取一个可用于测量尺寸，宽高及布局相关的Modifier对象")
            Title_Desc_Text(desc = "custom Align modifier")
            //下面使用了自定义的扩展函数customAlign，给内部的控件一个定植边距，以及添加对齐方式
            val modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
            Column(modifier.wrapContentHeight()) {
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
            Row(modifier.wrapContentHeight()) {
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
                    .paddingNoOffsetNoConstrain(all = 4.dp)
            )
        }
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

//region 预览效果

@Preview(showBackground = true, backgroundColor = 0XFFFFFFFF)
@Composable
private fun CustomModifierPreview() {
    UI_CustomModifier()
}

//endregion