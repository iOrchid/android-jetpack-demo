package org.zhiwei.compose.screen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ConstraintsScreenPreview() {
    Constraints_Screen()
}