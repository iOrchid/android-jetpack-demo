package org.zhiwei.compose.screen.gesture

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

/**
 * 作者： iOrchid
 * 主页： [Github](https://github.com/iOrchid)
 * 日期： 2024年05月18日 10:12
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * 演示一些拖拽，侧滑和滚动相关的一些手势事件
 */
@Composable
internal fun SwipeScroll_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Text(title = "Swipeable")
        Title_Sub_Text(title = "1. 侧滑事件的简单实现，swipeable操作符，以及替代方式的演示。")
        Title_Desc_Text(desc = "swipeable的使用,设置fraction的位置不同，滑动切换的临界点就不一样。")
        UI_Swipeable()
        Title_Desc_Text(desc = "替代swipeable，使用推荐的新的api，AnchoredDraggable实现相同效果。")
        UI_AnchoredDraggable()
        Title_Sub_Text(title = "2. 使用Modifier的滑动相关的操作符。")
        Title_Desc_Text(desc = "scrollable滑动操作符的使用,简单看一下滚动状态和偏移量变化。")
        ScrollableModifierSample()
        Title_Desc_Text(desc = "scrollable操作符的scrollable的state可以控制滑动的位置。")
        ScrollExample()
        //滚动可以嵌套，还有水平，竖直滑动的单独操作符函数。

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UI_Swipeable() {
    var fraction by remember { mutableFloatStateOf(0.3f) }

    //fraction简单理解就是，滑动过渡的临界条件，在临界点之前，会返回到原点，过了临界点，就滑过去到终点
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Fraction")
        Spacer(modifier = Modifier.width(8.dp))
        Slider(value = fraction, onValueChange = { fraction = it })
    }

    // 注意：swipeable API 在 Material3 中已被废弃，建议使用 AnchoredDraggable
    // 这里保留代码结构，但实际功能已被下面的 UI_AnchoredDraggable 替代
//    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
//        val width = maxWidth
//        val squareSize = 60.dp
//
//        Box(
//            modifier = Modifier
//                .width(width)
//                .background(Color.LightGray, RoundedCornerShape(8.dp))
//        ) {
//            Box(
//                Modifier
//                    .size(squareSize)
//                    .background(Color.Blue, RoundedCornerShape(8.dp))
//            )
//        }
//    }

    Spacer(modifier = Modifier.height(20.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color.Blue)
    ) {
        Text(
            color = Color.White,
            text = "swipeable API 已被废弃，请使用 AnchoredDraggable"
        )
    }
}

//使用AnchoredDraggable代替swipeable实现侧滑效果
@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UI_AnchoredDraggable() {
    var fraction by remember { mutableFloatStateOf(0.3f) }

    //fraction简单理解就是，滑动过渡的临界条件，在临界点之前，会返回到原点，过了临界点，就滑过去到终点
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Fraction")
        Spacer(modifier = Modifier.width(8.dp))
        Slider(value = fraction, onValueChange = { fraction = it })
    }
    val squareSize = 60.dp
    val density = LocalDensity.current
//    BoxWithConstraints(Modifier.fillMaxWidth()) {
//        //end的锚点位置，要放方块末端到屏幕右边，end to end
//        val widthPx = with(LocalDensity.current) {
//            (maxWidth - squareSize).toPx()
//        }
//        val anchors = DraggableAnchors<String> {
//            //两个锚点
//            "Start" at 0f
////            "Middle" at widthPx / 2f //可以多个锚点，这里模拟加个中间锚点
//            "End" at widthPx
//        }
//        val state = remember {
//            AnchoredDraggableState(
//                initialValue = "Start",
//                anchors = anchors,
//                //位置阈值，50%的位置，或者指定点 with(density) { 56.dp.toPx() }
//                positionalThreshold = { totalDistance: Float -> totalDistance * 0.5f },
//                //速度阈值
//                velocityThreshold = { with(density) { 125.dp.toPx() } },
//                animationSpec = TweenSpec()
//            )
//        }
//        SideEffect {
//            state.updateAnchors(anchors)
//        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.LightGray, RoundedCornerShape(8.dp))
//        ) {
//            Box(
//                Modifier
//                    .offset {
//                        IntOffset(
//                            state
//                                .requireOffset()
//                                .roundToInt(), 0
//                        )
//                    }
//                    .anchoredDraggable(state = state, orientation = Orientation.Horizontal)
//                    .size(squareSize)
//                    .background(Color.Blue, RoundedCornerShape(8.dp))
//            )
//        }
//    }
}

@Composable
private fun ScrollableModifierSample() {
    var offset by remember { mutableFloatStateOf(0f) }
    Box(
        Modifier
            .fillMaxWidth()
            .height(200.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                }
            )
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(offset.toString())
    }
}

@Composable
private fun ScrollExample() {

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)) {
        val coroutineScope = rememberCoroutineScope()

        // Smoothly scroll 100px on first composition
        val state = rememberScrollState()

        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp)
                .verticalScroll(state)
        ) {
            repeat(30) {
                Text("Item $it", modifier = Modifier.padding(2.dp), fontSize = 20.sp)
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                coroutineScope.launch {
                    state.animateScrollTo(100)
                }
            }
        ) {
            Text(text = "Smooth Scroll to top")
        }


        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                coroutineScope.launch {
                    state.scrollTo(100)
                }
            }) {
            Text(text = "Scroll to top")
        }
    }
}



@Preview
@Composable
private fun PreviewSwipeScroll() {
    SwipeScroll_Screen()
}