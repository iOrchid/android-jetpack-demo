package org.zhiwei.compose.screen.layout

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import kotlinx.coroutines.launch
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.math.roundToInt

@Composable
internal fun OnPlaceLayoutId_Screen(modifier: Modifier = Modifier) {

    UI_onPlaced(modifier)

}

//region onPlaced
@Composable
private fun UI_onPlaced(modifier: Modifier) {
    var alignment by remember {
        mutableStateOf(Alignment.Center)
    }

    var alignmentValue by remember {
        mutableFloatStateOf(0f)
    }

    alignment = when (alignmentValue.roundToInt()) {
        0 -> Alignment.TopStart
        1 -> Alignment.TopCenter
        2 -> Alignment.TopEnd
        3 -> Alignment.CenterStart
        4 -> Alignment.Center
        5 -> Alignment.CenterEnd
        6 -> Alignment.BottomStart
        7 -> Alignment.BottomCenter
        else -> Alignment.BottomEnd
    }

    val text = when (alignmentValue.roundToInt()) {
        0 -> "Alignment.TopStart"
        1 -> "Alignment.TopCenter"
        2 -> "Alignment.TopEnd"
        3 -> "Alignment.CenterStart"
        4 -> "Alignment.Center"
        5 -> "Alignment.CenterEnd"
        6 -> "Alignment.BottomStart"
        7 -> "Alignment.BottomCenter"
        else -> "Alignment.BottomEnd"
    }

    Column(
        modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(10.dp)
    ) {
        Title_Text(title = "onPlace Modifier")
        Title_Sub_Text(title = "使用Modifier.onPlace操作符来控制子元素的布局摆放方式")
        Title_Desc_Text(desc = "对齐方式：$text")
        //steps的值，表示分steps+1个分段
        Slider(
            value = alignmentValue,
            onValueChange = { alignmentValue = it },
            valueRange = 0f..8f,
            steps = 7
        )
        Box(
            Modifier
                .fillMaxSize()
                .border(2.dp, Color.Green)
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .animatePlacement()
                    .align(alignment)
                    .size(100.dp)
                    .background(Color.Red)
            )
        }
    }
}

/**
 * 自定义扩展的一个用于alignment动画效果的Modifier操作符
 * 这使用了composed来组合modifier的其他操作符
 */
private fun Modifier.animatePlacement(): Modifier = composed {
    //协程scope
    val scope = rememberCoroutineScope()
    var targetOffset by remember { mutableStateOf(IntOffset.Zero) }
    var animatable by remember { mutableStateOf<Animatable<IntOffset, AnimationVector2D>?>(null) }
    this
        //onPlaced要放在Offset之前
        .onPlaced {
            // 这里计算在父容器中的位置
            targetOffset = it
                .positionInParent()
                .round()
        }
        //动画偏移
        .offset {
            val anim =
                animatable ?: Animatable(targetOffset, IntOffset.VectorConverter).also {
                    animatable = it
                }
            if (anim.targetValue != targetOffset) {
                scope.launch {
                    anim.animateTo(
                        targetOffset,
                        spring(stiffness = Spring.StiffnessMediumLow)
                    )
                }
            }
            animatable?.let { it.value - targetOffset } ?: IntOffset.Zero
        }
}
//endregion

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun OnPlacePreview() {
    OnPlaceLayoutId_Screen()
}
