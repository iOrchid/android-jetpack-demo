package org.zhiwei.compose.screen.layout_state

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.R
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
        UI_WrapContentSize()
        UI_LayoutModifier()
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

//region wrapContent

@Composable
private fun UI_WrapContentSize() {
    Title_Text(title = "wrapContentSize Modifier")
    Title_Sub_Text(title = "1、使用wrapContentSize操作符可用于覆盖父容器的最小约束设定。下面示例自定义控件最小宽度的容器布局")
    val textMeasurer = rememberTextMeasurer()
    Column(
        Modifier
            .fillMaxWidth()
            .drawWithContent {
                drawContent()
                drawWidthMarks(textMeasurer)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title_Desc_Text(desc = "不使用外部Modifier约束的时候")
        MinimumWidthLayout(
            modifier = Modifier.border(2.dp, Color.Green),
            minSizeDp = 100.dp,//这里限定了最小尺寸的dp值，会直接作用于内部子控件（一级子控件）
        ) {
            val density = LocalDensity.current
            with(density) {
                Log.d(
                    "ConstraintsScreen",
                    "density:${density.density} , 300px相当于 ${300.toDp()}"
                )
            }
            //此box容器，外容器是MinimumWidthLayout自定义的限定最小宽度的layout布局，指定的100.dp的最小宽度，所以该box的size，如果小于100，那么就会取100的。
            //如果大于100，就会取 不大于外部约束的宽度的 size值。
            Box(
                modifier = Modifier
                    .size(50.dp)//可尝试修改为200，和500的效果。直接在IDE即可看到效果
                    .background(Color.Red)
            ) {
                //此情形，该compose会收到外部box容器的边界限制，自身的size大于外容器是背限定边界的。
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Yellow)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Title_Desc_Text(desc = "Modifier.wrapContentSize()")
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        MinimumWidthLayout(
            modifier = Modifier.border(2.dp, Color.Green),
            minSizeDp = 100.dp,
        ) {
            //MinimumWidthLayout的直接子容器使用的size是wrapContentSize，而此时demo演示的是竖向滑动，所以maxWidth就是屏幕宽。
            //上面的minSizeDp就不起作用（因为内部走到else逻辑了）
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .background(Color.Red)
                    //加padding以及内部box设置yellow是为了看效果
                    .padding(5.dp)
            ) {
                //外层是wrapContent，所以这里的size就是外层的size（直到再外层的边界约束）
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Yellow)
                )
            }
        }
        MinimumWidthLayout(
            modifier = Modifier.border(2.dp, Color.Green),
            minSizeDp = 100.dp,
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.BottomStart)
                    .background(Color.Red)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }
        MinimumWidthLayout(
            modifier = Modifier.border(2.dp, Color.Green),
            minSizeDp = 100.dp,
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.BottomEnd)
                    .background(Color.Red)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }
    }
    Title_Sub_Text(title = "2、Surface控件会强制直接子控件的最小约束尺寸。可使用wrapContentSize实现最小约束。")
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Surface(
            modifier = Modifier
                .size(100.dp)//这个size就是确定值，如果是widthIn之类的，就会分开min和max的约束
                .border(2.dp, Color.Yellow)
        ) {
            //surface指定了size就会对直接子控件有了minSize的约束。这里column设定size小于surface的约束，所以生效的是surface的设定值。
            Column(
                Modifier
                    .size(50.dp)//所以这里的50，并不能让自身只有50的大小，因为小于surface的100约束。
                    .background(Color.Red, RoundedCornerShape(6.dp))
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Green, RoundedCornerShape(6.dp))
                )
            }
        }
        Surface(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Yellow)
        ) {
            Column(
                Modifier
                    //⚠️，但是使用wrapContent就是没有确切值的约束，此时surface的size约定，就对内部min约束无效了。
                    .wrapContentWidth(Alignment.End)
                    .background(Color.Red, RoundedCornerShape(6.dp))
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Green, RoundedCornerShape(6.dp))
                )
            }
        }
        Surface(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Yellow)
        ) {
            Column(
                Modifier
                    //对齐方式，是指该组件在surface中的位置
                    .wrapContentHeight(Alignment.Top)
                    .background(Color.Red, RoundedCornerShape(6.dp))
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Green, RoundedCornerShape(6.dp))
                )
            }
        }
    }

    //unBounded
    CP_unBounded()
    CP_unBoundedImage()
}

@Composable
private fun CP_unBounded() {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title_Sub_Text(title = "2、wrapContentSize中有个参数unBounded默认false，就是时候约束内部空间的最大尺寸。")
        Title_Desc_Text(desc = "unBounded默认false的时候")
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Green)
        ) {
            Column(
                Modifier
                    .border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                    .wrapContentSize(unbounded = false)
                    .background(Color.Cyan)
                    //上边有了wrapContentSize，此处有第一次的确切值设定，则会生效。
                    .size(90.dp)//可以看出此size值，大于和小于上面Box的100的size时候，不同的效果
            ) {
                Text(
                    text = "青青河边草，五一放假了",
                    Modifier.background(Color(0xFFC8ADC4)),
                    color = Color.White
                )
            }
        }
        Title_Desc_Text(desc = "unBounded=true，并且里边size更大")
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Green)
        ) {
            Column(
                Modifier
                    .border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                    .wrapContentSize(unbounded = true)
                    .background(Color.Cyan)
                    .size(150.dp)//可以看出此size值，大于Box的100的size时候,因为unbounded=true，所以可以超出约束边界
            ) {
                Text(
                    text = "野火烧不尽，春风春来了，悠悠睡不着...",
                    Modifier.background(Color(0xFFC8ADC4)),
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Title_Desc_Text(desc = "unBounded=true，先有确定size再wrapContent")
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Green)
        ) {
            Column(
                Modifier
                    .border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                    .size(150.dp)//现有确定的size框高，再出现wrapContent的时候，
                    //⚠️wrapContentSize就包含了wrap宽高，前面有讲解到，超出约束的时候，会居中的方式来双向延伸突破约束边界
                    .wrapContentSize(unbounded = true)
                    .background(Color.Cyan)
            ) {
                Text(
                    text = "西湖的雨，你的泪，啦啦啦，啊啊啊，来来来，许仙爱吃蛇🐍肉羹。。。。",
                    Modifier.background(Color(0xFFC8ADC4)),
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Preview
@Composable
private fun CP_unBoundedImage() {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title_Sub_Text(title = "2、使用unBounded来实现对图片Image的按需显示区域，而不用scaling缩放图片来适配。")
        Title_Desc_Text(desc = "unBounded默认false的时候")
        //请注意，绿色是box边框，cyan是图片边框，red也是，只是顺序不同。作用区域也就不同
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Green)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sexy_girl),
                contentDescription = null,
                Modifier
                    .wrapContentSize(unbounded = false)
                    .size(150.dp),
                contentScale = ContentScale.FillBounds
            )
        }
        Title_Desc_Text(desc = "unBounded=true，并且里边size更大")
        Spacer(modifier = Modifier.height(80.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Green)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sexy_girl),
                contentDescription = null,
                Modifier
                    .border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                    .wrapContentSize(unbounded = true)
                    .border(2.dp, Color.Cyan)
                    .size(250.dp),
            )
        }
        Spacer(modifier = Modifier.height(80.dp))
        Title_Desc_Text(desc = "unBounded=true，先有确定size再wrapContent")
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Green)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sexy_girl),
                contentDescription = null,
                Modifier
                    .size(250.dp)
                    .border(3.dp, Color.Red, RoundedCornerShape(8.dp))
                    .wrapContentSize(unbounded = true)
                    .border(2.dp, Color.Cyan),
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

//此处自定义的布局控件，演示对width最小宽度约束的方式；因为本页面Screen是竖直滑动的，屏幕宽度是有边界的。如果是横向滑动，对应height的方式，是类似的。
@Composable
private fun MinimumWidthLayout(modifier: Modifier, minSizeDp: Dp, content: @Composable () -> Unit) {

    val measurePolicy = MeasurePolicy { measurables, constraints ->
        //注意⚠️这里是该composable组件的核心原理，就是设定了最小尺寸值，下面再去宽度的时候，会判断
        val placeables = measurables.map { measurable ->
            measurable.measure(
                constraints.copy(
                    minWidth = minSizeDp.roundToPx(),
                    minHeight = minSizeDp.roundToPx()
                )
            )
        }
        val hasBoundedWidth = constraints.hasBoundedWidth
        val hasFixedWidth = constraints.hasFixedWidth
        //⚠️，核心原理。根据约束条件，如果有外部边界约束及确切的宽度值，那么就取值确切值（此时约束的maxWidth就是确切值）；如果没有确切约束，那么就将内部所有可测量的控件的宽度最大值，并且在整个容器的约束值范围内，取值。
        val width =
            if (hasBoundedWidth && hasFixedWidth) constraints.maxWidth
            //此处，如果是wrapContent的时候，constraints.maxWidth屏幕宽度（因为此时demo容器是竖向滑动，如果是横向的，就是无限大）
            else placeables.maxOf { it.width }.coerceIn(constraints.minWidth, constraints.maxWidth)
        val height = placeables.maxOf { it.height }
        var yPos = 0//布局所有子控件的UI元素，初始高度是0，而后各元素高度递增向下排
        layout(width, height) {
            placeables.forEach {
                //布局每个元素，竖向，起始x坐标都是0，y坐标就依次向下，增加元素的高度。
                it.placeRelative(0, yPos)
                yPos += it.height
            }
        }
    }
    Layout(measurePolicy = measurePolicy, modifier = modifier, content = content)
}


//扩展函数，用来绘制图层宽度刻度，单位px像素
private fun DrawScope.drawWidthMarks(textMeasurer: TextMeasurer) {
    //DrawScope对应的compose控件的size尺寸的高度值
    val width = size.width.toInt()
    //100px为单位，绘制刻度
    for (i in 0..width step 50) {
        drawLine(
            color = Color(0xFFAAC2C4),//刻度颜色
            start = Offset(i.toFloat(), size.height),//刻度起始位置
            end = Offset(i.toFloat(), size.height + 20f),//刻度线20长度，
            strokeWidth = 3.dp.toPx()//刻度宽
        )
        //绘制刻度数
        drawText(
            textMeasurer = textMeasurer,//
            text = "$i",//刻度值
            topLeft = Offset(i - 30f, size.height - 30f),//绘制刻度值的位置，左侧有刻度线，左右与刻度线位置偏30
            style = TextStyle(
                color = Color(0xFF685E48),
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            ),//刻度值的字体样式
        )
    }
}
//endregion

@Preview
@Composable
private fun UI_LayoutModifier() {
    Title_Text(title = "layout Modifier")
    Title_Sub_Text(title = "通过Modifier.layout{}可创建一个layoutModifier实现对子元素的布局与测量相关的要素影响。")
    //演示
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        //使用wrapContentSize的效果
        Column {
            Title_Desc_Text(desc = "使用Modifier.wrapContentSize")
            Box(
                Modifier
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                    .background(Color(0xFFEB507E))
                    .size(140.dp)
                    .wrapContentSize()
                    .size(100.dp)
            ) {
                BoxWithConstraints(
                    Modifier
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .background(Color(0xFF5CB3CC))
                ) {
                    Text(text = "最小宽度:$minWidth,最大宽度:$maxWidth", Modifier.fillMaxWidth())
                }
            }
        }
        //使用Modifier.layout的效果
        Column {
            Title_Desc_Text(desc = "使用Modifier.layout")
            Box(
                Modifier
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                    .background(Color(0xFFEB507E))
                    .size(140.dp)
                    .layout { measurable, constraints ->
                        val placeable =
                            measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
                        layout(constraints.maxWidth, constraints.maxHeight) {
                            val xPos = (constraints.maxWidth - placeable.width) / 2
                            val yPos = (constraints.maxHeight - placeable.height) / 2
                            placeable.placeRelative(xPos, yPos)
                        }
                    }
                    .size(100.dp)
            ) {
                BoxWithConstraints(
                    Modifier
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .background(Color(0xFF5CB3CC))
                ) {
                    Text(text = "最小宽度:$minWidth,最大宽度:$maxWidth", Modifier.fillMaxWidth())
                }
            }
        }
    }

    Title_Sub_Text(title = "通过Modifier.layout{}可以让子布局元素的尺寸突破父容器的尺寸限定；下面演示子元素增加额外的40.dp的宽度，来突破父容器边界。")
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp), clip = false)//clip=false才能不裁剪子元素的越界
            .background(Color(0xFFF1441D))
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .shadow(4.dp, RoundedCornerShape(8.dp))
                .background(Color(0xFF4A4035))
        )
        Spacer(modifier = Modifier.height(20.dp))
        //使用layout来突破父容器的约束,增加40.dp的额外宽度，
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .border(2.dp, Color.Yellow)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(
                        constraints.copy(
                            minWidth = constraints.maxWidth + 40.dp.roundToPx(),
                            maxWidth = constraints.maxWidth + 40.dp.roundToPx()
                        )
                    )

                    val layoutWidth =
                        placeable.width.coerceIn(constraints.maxWidth, constraints.maxWidth)
                    val layoutHeight =
                        placeable.height.coerceIn(constraints.minHeight, constraints.maxHeight)

                    layout(layoutWidth, layoutHeight) {
                        val xPos = (layoutWidth - placeable.width) / 2
                        placeable.placeRelative(xPos, 0)
                    }
                }
                .shadow(4.dp, RoundedCornerShape(8.dp))
                .background(Color(0xFF83CBAC))
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .shadow(4.dp, RoundedCornerShape(8.dp))
                .background(Color(0xFF4A4035))
        )
        Spacer(modifier = Modifier.height(20.dp))
    }

    Title_Sub_Text(title = "layout布局顺序是从下到上，但是约束是从上而下的，且约束会在超出范围时，自动调整为范围内的接近值。")

    /*
        logcat的输出可以看到:
        I  🍎 Bottom Measurement phase  minWidth: 180.0.dp, maxWidth: 180.0.dp, placeable width: 180.0.dp
        I  🍏 Middle Measurement phase minWidth: 100.0.dp, maxWidth: 300.0.dp, placeable width: 180.0.dp
        I  🌻Top Measurement phase minWidth: 0.0.dp, maxWidth: 392.72726.dp, placeable width: 300.0.dp
        I  🌻🌻 Top Placement Phase
        I  🍏🍏 Middle Placement Phase
        I  🍎🍎 Bottom Placement Phase

     */
    BoxWithConstraints(
        Modifier
            .height(300.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp), clip = false)
            .background(Color(0xFFEF6F48))
            // 这layout的约束效果是，宽高都是0到父容器给定Content的边界尺寸
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                println(
                    "🌻Top Measurement phase " +
                            "minWidth: ${constraints.minWidth.toDp()}, " +
                            "maxWidth: ${constraints.maxWidth.toDp()}, " +
                            "placeable width: ${placeable.width.toDp()}"
                )

                layout(constraints.maxWidth, constraints.maxHeight) {
                    println("🌻🌻 Top Placement Phase")
                    placeable.placeRelative(50, 0)
                }
            }
            // 🔥 SizeIn（包括widthIn/heightIn）会影响到layout内的约束计算
            .widthIn(min = 100.dp, max = 300.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(Color(0xFF20894D))
            .layout { measurable, constraints ->
                // 🔥此处计算的约束布局数据，会影响到后续操作符的layout计算
                val placeable = measurable.measure(
                    constraints
                        .copy(
                            minWidth = 180.dp.roundToPx(),
                            maxWidth = 250.dp.roundToPx(),
                            minHeight = 180.dp.roundToPx(),
                            maxHeight = 250.dp.roundToPx()
                        )
                )
                println(
                    "🍏 Middle Measurement phase " +
                            "minWidth: ${constraints.minWidth.toDp()}, " +
                            "maxWidth: ${constraints.maxWidth.toDp()}, " +
                            "placeable width: ${placeable.width.toDp()}"
                )

                layout(constraints.maxWidth, constraints.maxHeight) {
                    println("🍏🍏 Middle Placement Phase")
                    placeable.placeRelative(0, 50)
                }
            }
            // Uncomment size modifiers to see how Constraints change
            // 🔥🔥 This Constraints minWidth = 100.dp, maxWidth = 100.dp is not
            // in bounds of Constraints that placeable measured above
            // Because it's smaller than minWidth, minWidth and maxWidth
            // is changed to 180.dp from layout above
//            .width(100.dp)
            // This Constraints minWidth = 240.dp, maxWidth = 240.dp is valid
            // for 180.dp-250.dp range
//                .size(240.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(Color(0xFFF97D1C))
            .layout { measurable, constraints ->

                val placeable = measurable.measure(constraints)
                println(
                    "🍎 Bottom Measurement phase  " +
                            "minWidth: ${constraints.minWidth.toDp()}, " +
                            "maxWidth: ${constraints.maxWidth.toDp()}, " +
                            "placeable width: ${placeable.width.toDp()}"
                )
                layout(placeable.width, placeable.height) {
                    println("🍎🍎 Bottom Placement Phase")
                    placeable.placeRelative(150, 150)
                }
            }
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(Color(0XFFD294D3))
        // 🔥 This width modifier also narrows range for the last
        // Constraints passed from BoxWithConstraints to Text
//            .width(50.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "最小宽度 $minWidth\n最大宽度: $maxWidth",
            modifier = Modifier
                .border(2.dp, Color.Red)
                .padding(5.dp),
            color = Color.White
        )

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ConstraintsScreenPreview() {
    Constraints_Screen(Modifier.fillMaxSize())
}