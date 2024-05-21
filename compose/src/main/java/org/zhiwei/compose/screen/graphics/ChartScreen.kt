package org.zhiwei.compose.screen.graphics

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
internal fun Chart_Screen(modifier: Modifier = Modifier) {

    var dismissToolTip by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Sub_Text(title = "演示饼状图的分段可点击效果")
        Column(
            Modifier
                .fillMaxWidth()
                .clickable {
                    dismissToolTip = true
                }) {
            val data = remember {
                listOf(
                    ChartData(Color.Green, 10f, "Seeds", "4000"),
                    ChartData(Color.Red, 20f, "Labour", "10000"),
                    ChartData(Color.Cyan, 15f, "Fertilizer", "6000"),
                    ChartData(Color.Blue, 5f, "Tractor", "1000"),
                    ChartData(Color.Yellow, 35f, "Weeding", "20000"),
                    ChartData(Color.Magenta, 15f, "Sowing", "6000")
                )
            }

            PieChart(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .size(140.dp)
                    .align(Alignment.CenterHorizontally),
                data = data,
                outerRingPercent = 35,
                onClick = { chartData, index ->
                    Toast.makeText(context, "Clicked: ${chartData.data}", Toast.LENGTH_SHORT).show()

                },
                dimissToolTip = dismissToolTip
            ) {
                dismissToolTip = it
            }
        }
        Title_Sub_Text(title = "演示圆弧轨道变化的效果")
        ProjectionLerpSample()
        Title_Sub_Text(title = "演示饼块图效果")
        PieChart()
    }
}

//region 分段的圆饼 图表
@Composable
fun PieChart(
    modifier: Modifier,
    data: List<ChartData>,
    startAngle: Float = 0f,
    outerRingPercent: Int = 35,
    drawText: Boolean = true,
    onClick: ((data: ChartData, index: Int) -> Unit)? = null,
    dimissToolTip: Boolean,
    resetDismiss: (Boolean) -> Unit,
) {

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        val width = constraints.maxWidth.toFloat()

        // Outer radius of chart. This is edge of stroke width as
        val radius = (width / 2f) * .9f
        val outerStrokeWidthPx =
            (radius * outerRingPercent / 100f).coerceIn(0f, radius)

        // Inner radius of chart. Semi transparent inner ring
        val innerRadius = (radius - outerStrokeWidthPx).coerceIn(0f, radius)

        // Start angle of chart. Top center is -90, right center 0,
        // bottom center 90, left center 180
        val animatableInitialSweepAngle = remember {
            Animatable(startAngle)
        }

        val chartEndAngle = 360f + startAngle

        val sum = data.sumOf {
            it.data.toDouble()
        }.toFloat()

        val coEfficient = 360f / sum
        var currentAngle = 0f
        val currentSweepAngle = animatableInitialSweepAngle.value

        val chartDataList = remember(data) {
            data.map {

                val chartData = it.data
                val range = currentAngle..currentAngle + chartData * coEfficient
                currentAngle += chartData * coEfficient

                AnimatedChartData(
                    color = it.color,
                    data = it.data,
                    selected = false,
                    range = range,
                    type = it.type,
                    expense = it.expense
                )
            }
        }


        LaunchedEffect(key1 = animatableInitialSweepAngle) {
            animatableInitialSweepAngle.animateTo(
                targetValue = chartEndAngle,
                animationSpec = tween(
                    delayMillis = 1000,
                    durationMillis = 1500
                )
            )
        }

        val textMeasurer = rememberTextMeasurer()
        val textMeasureResults: List<Pair<TextLayoutResult, TextLayoutResult>> =
            remember(chartDataList) {
                chartDataList.map {
                    Pair(
                        textMeasurer.measure(
                            text = it.type,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ),
                        textMeasurer.measure(
                            text = it.expense,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    )
                }
            }

        val chartModifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { position: Offset ->
                        val xPos = size.center.x - position.x
                        val yPos = size.center.y - position.y
                        val length = sqrt(xPos * xPos + yPos * yPos)
                        val isTouched = length in innerRadius..radius

                        if (isTouched) {
                            var touchAngle =
                                (-startAngle + 180f + atan2(
                                    yPos,
                                    xPos
                                ) * 180 / Math.PI) % 360f

                            if (touchAngle < 0) {
                                touchAngle += 360f
                            }

                            chartDataList.forEachIndexed { index, chartData ->
                                val range = chartData.range
                                val isTouchInArcSegment = touchAngle in range
                                if (chartData.isSelected) {
                                    chartData.isSelected = false
                                    resetDismiss(false)
                                } else {
                                    chartData.isSelected = isTouchInArcSegment

                                    if (isTouchInArcSegment) {
                                        onClick?.invoke(
                                            ChartData(
                                                color = chartData.color,
                                                data = chartData.data,
                                                type = chartData.type,
                                                expense = chartData.expense
                                            ), index
                                        )

                                    }
                                }
                            }
                        }
                    }
                )
            }

        PieChartImpl(
            modifier = chartModifier,
            chartDataList = chartDataList,
            textMeasureResults = textMeasureResults,
            currentSweepAngle = currentSweepAngle,
            chartStartAngle = startAngle,
            chartEndAngle = chartEndAngle,
            outerRadius = radius,
            outerStrokeWidth = outerStrokeWidthPx,
            innerRadius = innerRadius,
            drawText = drawText,
            dimissToolTip = dimissToolTip
        )

    }
}


@Composable
private fun PieChartImpl(
    modifier: Modifier = Modifier,
    chartDataList: List<AnimatedChartData>,
    textMeasureResults: List<Pair<TextLayoutResult, TextLayoutResult>>,
    currentSweepAngle: Float,
    chartStartAngle: Float,
    chartEndAngle: Float,
    outerRadius: Float,
    outerStrokeWidth: Float,
    innerRadius: Float,
    drawText: Boolean,
    rectWidth: Int = 99,
    rectHeight: Int = 54,
    rectCornerRadius: Int = 8,
    triangleWidth: Int = 24,
    triangleHeight: Int = 8,
    dimissToolTip: Boolean,
) {

    val pointerVector = ImageVector.vectorResource(id = R.drawable.tip)
    val pointerTip = rememberVectorPainter(image = pointerVector)

    Canvas(modifier = modifier) {

        val width = size.width
        var startAngle = chartStartAngle

        var offsetX = 0f
        var offsetY = 0f
        var indexSelected = -1
        var angRad = -1f
        var arcW = -1f

        val halfRectWidth = rectWidth / 2
        val halfRectHeight = rectHeight / 2
        val halfTriangleWidth = triangleWidth / 2
        val halfTriangleHeight = triangleHeight / 2


        for (index in 0..chartDataList.lastIndex) {

            val chartData = chartDataList[index]
            val range = chartData.range
            val sweepAngle = range.endInclusive - range.start
            val angleInRadians = (startAngle + sweepAngle / 2).degreeToRadian

            val arcWidth = if (chartData.isSelected) {
                // Increase arc width for the first arc
                outerStrokeWidth + 50f // You can adjust the value as needed
            } else {
                // Keep the same arc width for other arcs
                outerStrokeWidth
            }

            if (startAngle <= currentSweepAngle) {

                val color = chartData.color
                val diff = (width / 2 - outerRadius) / outerRadius
                val fraction = (chartData.animatable.value - 1f) / diff

                val animatedColor = lerp(
                    color,
                    color.copy(alpha = .8f),
                    fraction
                )

                drawArc(
                    color = animatedColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle.coerceAtMost(
                        currentSweepAngle - startAngle
                    ),
                    useCenter = false,
                    topLeft = Offset(
                        (width - 2 * innerRadius - arcWidth) / 2,
                        (width - 2 * innerRadius - arcWidth) / 2
                    ),
                    size = Size(
                        innerRadius * 2 + arcWidth,
                        innerRadius * 2 + arcWidth
                    ),
                    style = Stroke(arcWidth)
                )
                if (drawText && currentSweepAngle == chartEndAngle && chartData.isSelected) {
                    indexSelected = index
                    offsetX = center.x + (innerRadius + arcWidth / 2) * cos(angleInRadians)
                    offsetY = center.y + (innerRadius + arcWidth / 2) * sin(angleInRadians)
                    angRad = angleInRadians
                    arcW = arcWidth
                }

                startAngle += sweepAngle
            }
        }

        // this is to make sure tool tip is drawn after all the arc segments
        // to prevent overlap
        if (indexSelected != -1) {
            val chartData = chartDataList[indexSelected]
            val textMeasureResult = textMeasureResults[indexSelected]
            val typeSize = textMeasureResult.first.size
            val expenseSize = textMeasureResult.second.size
            val typeCenter = typeSize.center
            val expenseCenter = typeSize.center

            if (!dimissToolTip && drawText && currentSweepAngle == chartEndAngle && chartData.isSelected) {

                // Draw the tip
                translate(
                    left = offsetX - halfTriangleWidth.dp.toPx(),
                    top = offsetY - halfTriangleHeight.dp.toPx()
                ) {
                    with(pointerTip) {
                        draw(
                            size = Size(triangleWidth.dp.toPx(), triangleHeight.dp.toPx())
                        )
                    }
                }

                // Draw rectangle
                drawRoundRect(
                    color = Color.Black,
                    topLeft = Offset(
                        offsetX - halfRectWidth.dp.toPx(),
                        offsetY - rectHeight.dp.toPx() - halfTriangleHeight.dp.toPx()
                    ),
                    size = Size(rectWidth.dp.toPx(), rectHeight.dp.toPx()),
                    style = Fill,
                    cornerRadius = CornerRadius(rectCornerRadius.dp.toPx())
                )

                // Draw text centered
                drawText(
                    textLayoutResult = textMeasureResult.first,
                    color = Color.White,
                    topLeft = Offset(
                        (-typeCenter.x + center.x
                                + (innerRadius + arcW / 2) * cos(angRad)),
                        (-typeCenter.y + center.y
                                + (innerRadius + arcW / 2) * sin(angRad) - halfRectHeight.dp.toPx() - halfTriangleHeight.dp.toPx() - typeSize.height / 2)
                    )
                )

                drawText(
                    textLayoutResult = textMeasureResult.second,
                    color = Color.White,
                    topLeft = Offset(
                        (-expenseCenter.x + center.x
                                + (innerRadius + arcW / 2) * cos(angRad)),
                        (-expenseSize.height / 2 + center.y
                                + (innerRadius + arcW / 2) * sin(angRad) - halfRectHeight.dp.toPx() - halfTriangleHeight.dp.toPx() + expenseSize.height / 2 + 5.dp.toPx())
                    )
                )
            }
        }
    }
}


@Immutable
data class ChartData(
    val color: Color,
    val data: Float,
    val type: String = "",
    val expense: String = "",
)

@Immutable
internal class AnimatedChartData(
    val color: Color,
    val data: Float,
    val type: String,
    val expense: String,
    selected: Boolean = false,
    val range: ClosedFloatingPointRange<Float>,
    val animatable: Animatable<Float, AnimationVector1D> = Animatable(1f),
) {
    var isSelected by mutableStateOf(selected)
}

//endregion


val Float.degreeToRadian
    get() = (this * Math.PI / 180f).toFloat()

val Float.asAngle: Float
    get() = this * 360f / 100f

//region 卫星图轨迹变化
@Preview
@Composable
private fun ProjectionLerpSample() {

    val outerCircleRadius = 450f
    val innerCircleRadius = 250f

    var isOut by remember {
        mutableStateOf(false)
    }

    val transition = rememberInfiniteTransition(label = "infinite angle transition")

    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                0.0f at 0 with LinearEasing
                360f at 3000 with LinearEasing
            },
            repeatMode = RepeatMode.Restart
        ), label = "angle"
    )

    val progress by animateFloatAsState(
        if (isOut) 1f else 0f,
        animationSpec = tween(durationMillis = 700, easing = LinearEasing),
        label = "distance"
    )

    val distance = remember(progress) {
        androidx.compose.ui.util.lerp(innerCircleRadius, outerCircleRadius, progress)
    }

    var position by remember {
        mutableStateOf(Offset.Unspecified)
    }

    Column {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {

            drawCircle(
                color = Color.Blue,
                radius = outerCircleRadius,
                style = Stroke(2.dp.toPx())
            )

            drawCircle(
                color = Color.Blue,
                radius = innerCircleRadius,
                style = Stroke(2.dp.toPx())
            )

            rotate(angle) {
                drawCircle(
                    color = Color.Red,
                    radius = 50f,
                    center = Offset(center.x + distance, center.y)

                )
            }

            val angleInRadians = angle * DEGREE_TO_RAD
            position = Offset(
                center.x + distance * cos(angleInRadians), center.y + distance * sin(angleInRadians)
            )
        }

        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onClick = {
                isOut = isOut.not()
            }
        ) {
            Text("Out $isOut")
        }

        if (position != Offset.Unspecified) {
            Text(
                text = "Position: $position",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

        }
    }
}

private const val DEGREE_TO_RAD = (Math.PI / 180f).toFloat()

//endregion

//region 饼块图

@Composable
private fun PieChart() {
    val chartDataList = remember {
        listOf(
            ChartData(Color.Cyan, 10f),
            ChartData(Color.Red, 20f),
            ChartData(Color.Green, 15f),
            ChartData(Color.Yellow, 5f),
            ChartData(Color.Blue, 50f),
        )
    }

    val textMeasurer = rememberTextMeasurer()
    val textMeasureResults = remember(chartDataList) {
        chartDataList.map {
            textMeasurer.measure(
                text = "%${it.data.toInt()}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

    val animatable = remember {
        Animatable(-90f)
    }

    val finalValue = 270f

    LaunchedEffect(key1 = animatable) {
        animatable.animateTo(
            targetValue = finalValue,
            animationSpec = tween(
                delayMillis = 1000,
                durationMillis = 2000
            )
        )
    }
    val currentSweepAngle = animatable.value


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            val width = size.width
            val radius = width / 2f
            val strokeWidth = radius * .4f
            val innerRadius = radius - strokeWidth
            val lineStrokeWidth = 3.dp.toPx()

            var startAngle = -90f

            for (index in 0..chartDataList.lastIndex) {

                val chartData = chartDataList[index]
                val sweepAngle = chartData.data.asAngle
                val angleInRadians = (startAngle + sweepAngle / 2).degreeToRadian
                val textMeasureResult = textMeasureResults[index]
                val textSize = textMeasureResult.size

                if (startAngle <= currentSweepAngle) {
                    drawArc(
                        color = chartData.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle.coerceAtMost(currentSweepAngle - startAngle),
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = Size(width - strokeWidth, width - strokeWidth),
                        style = Stroke(strokeWidth)
                    )
                }

                rotate(
                    90f + startAngle
                ) {
                    drawLine(
                        color = Color.White,
                        start = Offset(radius, strokeWidth),
                        end = Offset(radius, 0f),
                        strokeWidth = lineStrokeWidth
                    )
                }

                val textCenter = textSize.center

                if (currentSweepAngle == finalValue) {
                    drawText(
                        textLayoutResult = textMeasureResult,
                        color = Color.Black,
                        topLeft = Offset(
                            -textCenter.x + center.x + (innerRadius + strokeWidth / 2) * cos(
                                angleInRadians
                            ),
                            -textCenter.y + center.y + (innerRadius + strokeWidth / 2) * sin(
                                angleInRadians
                            )
                        )
                    )
                }

                startAngle += sweepAngle
            }
        }
    }
}

@Preview
@Composable
private fun PieChartWithRoundedStrokesAndLabels() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        val chartDataList = remember {
            listOf(
                ChartData(Color.Cyan, 10f),
                ChartData(Color.Red, 20f),
                ChartData(Color.Green, 15f),
                ChartData(Color.Yellow, 5f),
                ChartData(Color.Blue, 50f),
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .aspectRatio(1f)
        ) {
            val width = size.width
            val radius = width / 2f
            val strokeWidth = 20.dp.toPx()

            val circumference = 2 * Math.PI * (radius - strokeWidth / 2)
            // stroke width is diameter of rounded cap half circle
            val strokeAngle = 1.2f * (strokeWidth / circumference * 180f).toFloat()

            var startAngle = -90f

            for (index in 0..chartDataList.lastIndex) {

                val chartData = chartDataList[index]
                val sweepAngle = chartData.data.asAngle
                val angleInRadians = (startAngle + sweepAngle / 2).degreeToRadian

                drawArc(
                    color = chartData.color,
                    startAngle = startAngle + strokeAngle,
                    sweepAngle = (sweepAngle - strokeAngle * 2).coerceAtLeast(0f),
                    useCenter = false,
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(width - strokeWidth, width - strokeWidth),
                    style = Stroke(strokeWidth, cap = StrokeCap.Round)
                )

                startAngle += sweepAngle


                val rectWidth = 20.dp.toPx()
                drawRect(
                    color = Color.Red,
                    size = Size(rectWidth, rectWidth),
                    topLeft = Offset(
                        -rectWidth / 2 + center.x + (radius + strokeWidth) * cos(
                            angleInRadians
                        ),
                        -rectWidth / 2 + center.y + (radius + strokeWidth) * sin(
                            angleInRadians
                        )
                    )
                )
            }
        }
    }
}

//endregion

@Preview
@Composable
private fun PreviewChartScreen() {
    Chart_Screen()
}