package org.zhiwei.compose.screen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

@Composable
internal fun Constraints_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Text(title = "BoxWithConstraints")
        UI_BoxConstraints()
        UI_SizeModifier()
        UI_ChainsSizeModifier()
    }
}

//region box constraints
@Composable
private fun UI_BoxConstraints() {
    Title_Sub_Text(title = "1、根据约束比例分配子控件元素UI布局")
    //以下两个sample，就是不同的高度，但是内部子控件可以根据约束，依旧占有各自的比例
    Title_Desc_Text(desc = "不同高度的容器，内部子控件依旧按照约束比例布局分配空间")
    CP_BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
    CP_BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
    Title_Sub_Text(title = "2、根据某些条件判断，来适配元素UI布局")
    Title_Desc_Text(desc = "外容器参数要求>100dp的条件")
    CP_BoxWithConstraints2(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    )
    Title_Desc_Text(desc = "外容器参数要求<100dp的条件")
    CP_BoxWithConstraints2(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    )
}


@Composable
private fun CP_BoxWithConstraints(modifier: Modifier) {
    //BoxWithConstraints是提供一个带有约束条件的box容器
    BoxWithConstraints(
        modifier
            .background(Color.LightGray)
            .padding(horizontal = 4.dp)
    ) {
        //上部分 占2/3控件，不论传来的尺寸高度是多少，
        val topPartDp = maxHeight * 2 / 3
        //也可以这么计算成dp
        val density = LocalDensity.current
        val bottomPartDp = with(density) { (constraints.maxHeight * 1 / 3).toDp() }
        //todo 注意⚠️：hasBoundedHeight/Width 是指BoxWithConstraints是否有外部边界约束的高/宽，hasFixedHeight/Width是指是否给BoxWithConstraints的modifier指定了确切的数值宽高；
        Column {
            Text(
                text = """
        maxHeight:${this@BoxWithConstraints.maxHeight},maxWidth:${this@BoxWithConstraints.maxWidth}
        minHeight:${this@BoxWithConstraints.minHeight},minWidth:${this@BoxWithConstraints.minWidth}
        density:$density,Constraints内的属性「
            hasBoundedHeight：${this@BoxWithConstraints.constraints.hasBoundedHeight}
            hasBoundedWidth：${this@BoxWithConstraints.constraints.hasBoundedWidth}
            hasFixedHeight：${this@BoxWithConstraints.constraints.hasFixedHeight}
            hasFixedWidth：${this@BoxWithConstraints.constraints.hasFixedWidth}
            isZero：${this@BoxWithConstraints.constraints.isZero}」
        该部分Text占据整个控件的2/3空间
            """.trimIndent(), modifier = Modifier
                    .height(topPartDp)
                    .background(Color(0xFFFCA106)),
                fontSize = 10.sp
            )

            Text(
                text = "该部分Text占据整个控件的1/3空间",
                modifier = Modifier
                    .height(bottomPartDp)
                    .background(Color(0xFFD0DEAA)),
                fontSize = 10.sp,
            )
        }
    }
}

@Composable
private fun CP_BoxWithConstraints2(modifier: Modifier) {
    //BoxWithConstraints是提供一个带有约束条件的box容器
    BoxWithConstraints(
        modifier
            .background(Color.LightGray)
            .padding(horizontal = 4.dp)
    ) {
        val density = LocalDensity.current
        val maxHeightInDp: Dp = with(density) {
            constraints.maxHeight.toDp()
        }

        var selected by remember { mutableStateOf(true) }
        //根据外部传入的条件，来显示不同的布局
        if (maxHeight > 100.dp) {
            Row(modifier = Modifier.padding(8.dp)) {

                RadioButton(selected = selected, onClick = { selected = !selected })
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material.Text(
                    text = "该部分适用于高度大于100dp的外容器的时候，才会显示",
                    modifier = Modifier.background(Color(0xFF896C39))
                )
            }
        } else {
            Row(modifier = Modifier.padding(8.dp)) {
                Switch(checked = selected, onCheckedChange = { selected = !selected })
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material.Text(
                    text = "外容器高度小于100dp的时候，会显示此内容",
                    modifier = Modifier.background(Color(0xFFEAFF56))
                )
            }
        }
    }
}
//endregion

//region size modifier
@Composable
private fun UI_SizeModifier() {
    Title_Text(title = "SizeModifier 尺寸限定")
    Title_Sub_Text(title = "2、BoxWithConstraints的约束作用，也会因为传入的modifier设置的宽高，而有不同的取值。wrapContent和确切值的宽高，而宽度会有屏幕限定，高度可能是无限大的场景（竖直滑动时如此，横向滑动则宽高互换）。")
    //注意该演示都在竖向屏幕可滑动的列表中演示的，而不是横向滑动演示。此时，横向是有手机屏幕宽度的边界限定的。
    CP_Box_SizeDemo(
        "a. 不指定size约束的时候",
        Modifier.background(Color(0xFF9C5333))
    )
    CP_Box_SizeDemo(
        "b. 确定高度200dp和最大宽度的场景",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFED5126))
    )
    CP_Box_SizeDemo(
        "c. wrapContentSize",
        modifier = Modifier
            .wrapContentSize()
            .background(Color(0xFFFCD217))
    )
    CP_Box_SizeDemo(
        "d. 固定宽高值200dp",
        modifier = Modifier
            .size(200.dp)
            .background(Color(0xFF41B349))
    )
    CP_Box_SizeDemo(
        "e. 最小宽度widthIn和最小高度heightIn",
        modifier = Modifier
            .widthIn(200.dp)
            .heightIn(200.dp)
            .background(Color(0xFF1677B3))
    )
    //⚠️注意，requiredHeightIn/requiredWidthIn会强制要求父容器一定要满足自己的要求，如果就算父容器尺寸小于要求条件，超出绘制区域，也要显示它。
    CP_Box_SizeDemo(
        "f. require最小宽度requiredWidthIn和最小高度requiredHeightIn",
        modifier = Modifier
            .requiredWidthIn(200.dp)
            .requiredHeightIn(200.dp)
            .background(Color(0xFFEEA5D1))
    )
    CP_Box_SizeDemo(
        "g. defaultMinSize可设置默认最小宽高",
        modifier = Modifier
            .defaultMinSize(200.dp)
            .background(Color(0xFFA22076))
    )
    CP_Box_SizeDemo(
        "h. widthIn设定max值",
        modifier = Modifier
            .widthIn(max = 200.dp)
            .background(Color(0xFF704D4E))
    )
}

@Composable
private fun CP_Box_SizeDemo(desc: String, modifier: Modifier, simple: Boolean = false) {
    Title_Desc_Text(desc)
    BoxWithConstraints(modifier) {
        val hasBoundedWidth = constraints.hasBoundedWidth
        val hasFixedWidth = constraints.hasFixedWidth
        val minWidth = minWidth
        val maxWidth = maxWidth

        val hasBoundedHeight = constraints.hasBoundedHeight
        val hasFixedHeight = constraints.hasFixedHeight
        val minHeight = minHeight
        val maxHeight = maxHeight
        val textStr =
            if (simple) "最小宽高：[ w=$minWidth,h=$minHeight ]; 最大宽高：【w=$maxWidth,h=$maxHeight】"
            else "宽度：「是否有宽的边界：$hasBoundedWidth,是否有固定宽度值：$hasFixedWidth,\n 最小宽度:$minWidth,最大宽度:$maxWidth」\n 高度：「是否有高的边界：$hasBoundedHeight,是否有固定高度值：$hasFixedHeight,\n 最小高度:$minHeight,最大高度:$maxHeight」"
        Text(
            text = textStr,
            color = if (simple) Color.Black else Color.White,
            fontSize = 12.sp
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun UI_ChainsSizeModifier() {
    Title_Text(title = "Chain SizeModifier")
    Title_Sub_Text(title = "3、modifier的操作符一般是依次生效的，但在size设定方面，单值情况第一次设定有效的。")
    CP_Box_SizeDemo(
        "Ⓐ. 宽度值的设定",
        //这里先fillMaxWidth而后width具体值，会忽略后面的width设定。可以切换顺序看效果。⚠️IDE可以实时预览效果，而不一定必须运行到手机。（首次除外）
        Modifier
            .fillMaxWidth()
            .width(200.dp)
            .border(width = 2.dp, color = Color(0xFF95509F)),
        true
    )

    CP_Box_SizeDemo(
        "Ⓑ. 多个width设定",
        //同理，第一次设定才有效
        Modifier
            .width(300.dp)
            .width(200.dp)
            .border(width = 2.dp, color = Color(0xFFDE7AB1)),
        true
    )
    Title_Sub_Text(title = "4、Modifier的width/height/size都是可以接收min/max值范围的，一般允许尺寸在范围内，不允许超出范围。")
    CP_Box_SizeDemo(
        "❶、widthIn和width依次添加的演示",
        //同理，第一次设定才有效
        Modifier
            .widthIn(100.dp, 200.dp)
            //上面限定了width的范围，那么下面再出现width的时候，在范围内才有效，否则无效。
//            .widthIn(300.dp,350.dp)//如果出现的同样的width范围的，但是它依旧是上面的有效，而会根据此条来在第一个范围里选择靠近的值，例如第一个限定100-200，第二个限定220-300，那么就会是200。相反如果第二个是20-80，那么就会选择100，因为第一个限定了范围100-200
            .width(180.dp)
            .border(width = 2.dp, color = Color(0xFF57C3C2)),
        true
    )
    CP_Box_SizeDemo(
        "❷、widthIn范围与越上界",
        //同理，第一次设定才有效
        Modifier
            .widthIn(100.dp, 200.dp)
            .width(280.dp)//从上面取最靠近的
            .border(width = 2.dp, color = Color(0xFFEB261A)),
        true
    )
    CP_Box_SizeDemo(
        "❸、widthIn范围与越下界",
        Modifier
            .widthIn(100.dp, 200.dp)
            .width(80.dp)
            .border(width = 2.dp, color = Color(0xFFEB261A)),
        true
    )
    //以此类推，不再一一演示。
    CP_Box_SizeDemo(
        "❹、多个widthIn",
        Modifier
            //说白了就是第一条生效，而后尽可能取交集。没有交集，就是第一个范围内的最靠近后面期望的值
            .widthIn(100.dp, 200.dp)
            .widthIn(180.dp, 220.dp)
            .border(width = 2.dp, color = Color(0xFFEB261A)),
        true
    )
    Title_Sub_Text(title = "5、Modifier的require会突破外层限制，尽可能的在屏幕展示自己要求的尺寸。")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .border(2.dp, Color(0xFFCADDD5))
    ) {
        Title_Desc_Text(desc = "size100而require 160")
        //⚠️ 若果require的比较多，则双向突围，不受限制
        BoxWithConstraints(
            modifier = Modifier
                .border(2.dp, Color.Red)
                .size(100.dp)
                .requiredWidth(160.dp)
        ) {
            Text(
                text = "最小宽度: $minWidth, 最大宽度: $maxWidth",
                modifier = Modifier.border(3.dp, Color.Green)
            )
        }
        Title_Desc_Text(desc = "size100 但 require 80")
        //⚠️ 使用require后，在范围内，则居中处理
        BoxWithConstraints(
            modifier = Modifier
                .border(2.dp, Color.Red)
                .size(100.dp)
                .requiredWidth(80.dp)
                .requiredHeight(40.dp)
        ) {
            Text(
                text = "最小宽度: $minWidth, 最大宽度: $maxWidth",
                modifier = Modifier.border(3.dp, Color.Green)
            )
        }
    }

}
//endregion


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ConstraintsScreenPreview() {
    Constraints_Screen()
}