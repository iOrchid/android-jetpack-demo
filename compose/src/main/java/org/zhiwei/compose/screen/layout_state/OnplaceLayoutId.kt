package org.zhiwei.compose.screen.layout_state

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.intermediateLayout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import kotlinx.coroutines.launch
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.math.roundToInt

@Composable
internal fun OnPlaceLayoutId_Screen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        UI_onPlaced()
        UI_LayoutId()
    }
    //单独演示，可注释掉上面Column部分
//    UI_LookAheadScope()

}

//region onPlaced
@Composable
private fun UI_onPlaced() {
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
        Modifier
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

//region layoutId

@Composable
private fun UI_LayoutId() {
    Title_Text(title = "layoutId的使用")
    Title_Sub_Text(title = "在自定义的layout中，根据指定layoutId来确定布局方式")
    MyLayout(
        Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Green)
    ) {
        //这里确定的两个layoutId
        Text(
            text = "立夏·春光万象中，夏日初长成",
            Modifier
                .layoutId("text")
                .border(2.dp, Color.Red)
        )
        Image(
            painter = painterResource(id = R.drawable.m005),
            contentDescription = null,
            Modifier
                .layoutId("image")
                .size(100.dp)
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    MyLayout(
        Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Green)
    ) {
        Image(
            painter = painterResource(id = R.drawable.m005),
            contentDescription = null,
            Modifier
                .layoutId("image")
                .size(150.dp)
        )
        //调换顺序，也不影响布局方式，因为layoutId在MyLayout中确定了布局方式
        Text(
            text = "立夏·春光万象中，夏日初长成",
            Modifier
                .layoutId("text")
                .border(2.dp, Color.Red)
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    //指定layoutId的icon设置透明度不同
    PlaceWithLayerLayout(
        modifier = Modifier
            .fillMaxWidth()
            .drawChecker(),
        alpha = .4f//都是0.4f的都明度
    ) {
        Icon(
            imageVector = Icons.Default.NotificationsActive,
            contentDescription = null,
            modifier = Modifier
                .background(
                    Color.Red,
                    CircleShape
                )
                .size(80.dp)
                .padding(10.dp),
            tint = Color.White,
        )
        Icon(
            imageVector = Icons.Default.NotificationsActive,
            contentDescription = null,
            modifier = Modifier
                .background(
                    Color.Red,
                    CircleShape
                )
                .size(80.dp)
                .padding(10.dp),
            tint = Color.White,
        )
        //指定的layoutId，在布局中，会特别处理
        Icon(
            imageVector = Icons.Default.NotificationsActive,
            contentDescription = null,
            modifier = Modifier
                .background(
                    Color.Red,
                    CircleShape
                )
                .layoutId("full_alpha")
                .size(80.dp)
                .padding(10.dp),
            tint = Color.White,
        )
        Icon(
            imageVector = Icons.Default.NotificationsActive,
            contentDescription = null,
            modifier = Modifier
                .background(
                    Color.Red,
                    CircleShape
                )
                .size(80.dp)
                .padding(10.dp),
            tint = Color.White,
        )
    }
}


/**
 * 自定义的，图片在上文字在下的布局，且总体依据图片控件的宽度约束
 */
@Composable
private fun MyLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(content = content, modifier, measurePolicy = { measurables, constraints ->
        //演示用的，确定的两个layout的id
        val imageMeasurable: Measurable = measurables.find { it.layoutId == "image" }!!
        val textMeasurable: Measurable = measurables.find { it.layoutId == "text" }!!
        // 图片控件设定minSize，因为如果外部传入modifier用的是fillMax，那么min可能就是宽高屏幕
        val imagePlaceable = imageMeasurable.measure(constraints.copy(minWidth = 0, minHeight = 0))

        // Limit text width to image width by setting min and max width at image width
        val textPlaceable = textMeasurable.measure(
            constraints.copy(
                minWidth = imagePlaceable.width,
                maxWidth = imagePlaceable.width
            )
        )
        val width = imagePlaceable.width
        val imagePlaceableHeight = imagePlaceable.height
        val height = imagePlaceableHeight + textPlaceable.height
        //这里确定了两个元素的布局顺序和方式
        layout(width, height) {
            imagePlaceable.placeRelative(0, 0)
            textPlaceable.placeRelative(0, imagePlaceableHeight)
        }
    })
}

/**
 * 自定义的，指定layoutId的图片，设置透明度
 */
@Composable
private fun PlaceWithLayerLayout(
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
    content: @Composable () -> Unit,
) {
    val measurePolicy = MeasurePolicy { measurables, constraints ->

        val fullAlphaIndex = measurables.indexOfFirst {
            it.layoutId == "full_alpha"
        }
        val placeablesWidth = measurables.map { measurable ->
            measurable.measure(
                constraints.copy(
                    minWidth = 0,
                    maxWidth = Constraints.Infinity,
                    minHeight = 0,
                    maxHeight = Constraints.Infinity
                )
            )
        }

        val hasBoundedWidth = constraints.hasBoundedWidth
        val hasFixedWidth = constraints.hasFixedWidth

        val hasBoundedHeight = constraints.hasBoundedHeight
        val hasFixedHeight = constraints.hasFixedHeight

        val width =
            if (hasBoundedWidth && hasFixedWidth) constraints.maxWidth
            else placeablesWidth.sumOf { it.width }.coerceAtMost(constraints.maxWidth)

        val height =
            if (hasBoundedHeight && hasFixedHeight) constraints.maxHeight
            else placeablesWidth.maxOf { it.height }.coerceAtMost(constraints.maxHeight)


        var posX = 0

        layout(width, height) {
            placeablesWidth.forEachIndexed { index, placeable ->
                placeable.placeRelativeWithLayer(posX, 0) {
                    if (index == fullAlphaIndex) {
                        this.alpha = 1f
                    } else {
                        this.alpha = alpha
                    }
                }

                posX += placeable.width
            }
        }

    }

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = measurePolicy
    )
}

/**
 * 绘制背景格子背景的扩展操作符
 */
fun Modifier.drawChecker() = this.then(
    drawBehind {
        val width = this.size.width
        val height = this.size.height

        val checkerWidth = 10.dp.toPx()
        val checkerHeight = 10.dp.toPx()

        val horizontalSteps = (width / checkerWidth).toInt()
        val verticalSteps = (height / checkerHeight).toInt()

        for (y in 0..verticalSteps) {
            for (x in 0..horizontalSteps) {
                val isGrayTile = ((x + y) % 2 == 1)
                drawRect(
                    color = if (isGrayTile) Color.LightGray else Color.White,
                    topLeft = Offset(x * checkerWidth, y * checkerHeight),
                    size = Size(checkerWidth, checkerHeight)
                )
            }
        }
    }
)
//endregion


//region lookaHeadScope

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.animatePlacementInScope(lookaheadScope: LookaheadScope) = composed {
    // Creates an offset animation
    var offsetAnimation: Animatable<IntOffset, AnimationVector2D>? by mutableStateOf(
        null
    )
    var targetOffset: IntOffset? by mutableStateOf(null)

    this.intermediateLayout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            // Converts coordinates of the current layout to LookaheadCoordinates
            val coordinates = coordinates
            if (coordinates != null) {
                // Calculates the target offset within the lookaheadScope
                val target = with(lookaheadScope) {
                    lookaheadScopeCoordinates
                        .localLookaheadPositionOf(coordinates)
                        .round().also { targetOffset = it }
                }

                // Uses the target offset to start an offset animation
                if (target != offsetAnimation?.targetValue) {
                    offsetAnimation?.run {
                        launch { animateTo(target) }
                    } ?: Animatable(target, IntOffset.VectorConverter).let {
                        offsetAnimation = it
                    }
                }
                // Calculates the *current* offset within the given LookaheadScope
                val placementOffset =
                    lookaheadScopeCoordinates.localPositionOf(
                        coordinates,
                        Offset.Zero
                    ).round()
                // Calculates the delta between animated position in scope and current
                // position in scope, and places the child at the delta offset. This puts
                // the child layout at the animated position.
                val (x, y) = requireNotNull(offsetAnimation).run { value - placementOffset }
                placeable.place(x, y)
            } else {
                placeable.place(0, 0)
            }
        }
    }
}

val colors = listOf(
    Color(0xffff6f69), Color(0xffffcc5c), Color(0xff264653), Color(0xff2a9d84)
)

/**
 * 演示动态切换布局方式，使用LookAheadScope
 */
@Preview(showBackground = true)
@Composable
private fun UI_LookAheadScope() {

    var isInColumn by remember { mutableStateOf(true) }
    LookaheadScope {
        // Creates movable content containing 4 boxes. They will be put either in a [Row] or in a
        // [Column] depending on the state
        val items = remember {
            movableContentOf {
                colors.forEach { color ->
                    Box(
                        Modifier
                            .padding(15.dp)
                            .size(100.dp, 80.dp)
                            .animatePlacementInScope(this)
                            .background(color, RoundedCornerShape(20))
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { isInColumn = !isInColumn }
        ) {
            // As the items get moved between Column and Row, their positions in LookaheadScope
            // will change. The `animatePlacementInScope` modifier created above will
            // observe that final position change via `localLookaheadPositionOf`, and create
            // a position animation.
            if (isInColumn) {
                Column(Modifier.fillMaxSize()) {
                    items()
                }
            } else {
                Row { items() }
            }
        }
    }
}

//endregion

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun OnPlacePreview() {
    OnPlaceLayoutId_Screen()
}
