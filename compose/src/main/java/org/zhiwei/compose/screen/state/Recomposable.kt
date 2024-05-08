package org.zhiwei.compose.screen.state

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

/**
 * 演示state的使用，以及composable组件的重组现象；
 * 1. compose默认最小化重组区域原则；
 * 2. 一般仅在scope内或lambda，非内联的compose函数内作用；
 * 3. state使用remember结合mutableStateOf的时候，有policy参数可配置变化。
 * 4. 抽取非内联compose函数可改变 重组 影响范围。
 * 5. @stable @Immutable @NonRestartComposable注解会影响compose函数的重组响应。
 */
@Composable
internal fun StateReComposable_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier.verticalScroll(rememberScrollState())
    ) {
        UI_State()
    }
}

//region state的演示
private data class CounterNum(var num: Int)

/**
 * 演示state的使用
 */
@Composable
private fun UI_State() {
    Title_Text(title = "State的使用")
    Title_Sub_Text(title = "compose的重组变化，是通过state感知数据变化来触发重组的。")
    var number = 0//普通类型的数据方式，不会因为compose重组而更新读取数值
    //使用remember的，才可以让compose函数感知到变化的数据
    val counter = remember { mutableIntStateOf(0) }
    val cNum = remember { CounterNum(0) }
    Button(onClick = {
        number++
        counter.intValue += 1
        cNum.num++
    }) {
        Text(text = "点击变化数字", color = Color.White)
    }
    Text(text = "普通Num：$number ,remember的Counter：${counter.intValue},自定义数据：${cNum.num}")
}


//endregion


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun StateRecPreview() {
    StateReComposable_Screen()
}