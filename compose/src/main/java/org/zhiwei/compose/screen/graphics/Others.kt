package org.zhiwei.compose.screen.graphics

import android.graphics.ComposePathEffect
import android.graphics.CornerPathEffect
import android.graphics.DiscretePathEffect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toComposePathEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.R
import org.zhiwei.compose.screen.graphics.editbox.ScaleEditBox
import org.zhiwei.compose.ui.widget.Title_Sub_Text

@Composable
internal fun Others_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Sub_Text(
            "Gooey Effect is combination of paths with path operation. **Blue** path " +
                    "is the original path without any operation. Other paths are drawn using" +
                    "**Path.combine** and **PathEffect**s to create gooey effect.",
        )
        GooeyEffectSample()
        Title_Sub_Text(
            "Gooey Effect with touch. Touch on Canvas to move " +
                    "one of the circles"
        )
        GooeyEffectSample2()
        //为了显示出可绘制的水滴圆
        Spacer(modifier = Modifier.height(100.dp))

        Title_Sub_Text(title = "编辑图片的缩放效果")
        EditScaleDemo()
        Title_Sub_Text(title = "挖空效果")
        CustomArcShapeSample()
    }
}

//region GooeyEffect

@Composable
private fun GooeyEffectSample() {

    val pathLeft = remember { Path() }
    val pathRight = remember { Path() }

    val segmentCount = 20
    val pathMeasure = PathMeasure()
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(4 / 3f)
    ) {
        val center = size.center

        if (pathLeft.isEmpty) {
            pathLeft.addOval(
                Rect(
                    center = Offset(center.x - 90f, center.y),
                    radius = 200f
                )
            )
        }

        if (pathRight.isEmpty) {
            pathRight.addOval(
                Rect(
                    center = Offset(center.x + 90f, center.y),
                    radius = 200f
                )
            )
        }

        val path = Path.combine(PathOperation.Union, pathLeft, pathRight)
        pathMeasure.setPath(pathLeft, true)

        val discretePathEffect = DiscretePathEffect(pathMeasure.length / segmentCount, 0f)
        val cornerPathEffect = CornerPathEffect(50f)

        val composePathEffect = ComposePathEffect(cornerPathEffect, discretePathEffect)


        val chainPathEffect = PathEffect.chainPathEffect(
            outer = cornerPathEffect.toComposePathEffect(),
            inner = discretePathEffect.toComposePathEffect()
        )
        drawPath(path, Color.Blue, style = Stroke(4.dp.toPx()))

        translate(top = -50f) {
            drawPath(
                path = path,
                color = Color.Red,
                style = Stroke(
                    4.dp.toPx(),
                    pathEffect = chainPathEffect
                )
            )
        }

        translate(top = -100f) {
            drawPath(
                path = path,
                color = Color.Cyan,
                style = Stroke(
                    4.dp.toPx(),
                    pathEffect = composePathEffect.toComposePathEffect()
                )
            )
        }
    }
}


@Composable
private fun GooeyEffectSample2() {

    val pathDynamic = remember { Path() }
    val pathStatic = remember { Path() }

    /**
     * Current position of the pointer that is pressed or being moved
     */
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }

    val segmentCount = 20
    val pathMeasure = remember {
        PathMeasure()
    }

    val modifier = Modifier
        .pointerInput(Unit) {
            detectDragGestures { change, _ ->
                currentPosition = change.position
            }
        }
        .fillMaxSize()

    val paint = remember {
        Paint()
    }

    var isPaintSetUp by remember {
        mutableStateOf(false)
    }

    Canvas(modifier = modifier) {
        val center = size.center

        val position = if (currentPosition == Offset.Unspecified) {
            center
        } else currentPosition

        pathDynamic.reset()
        pathDynamic.addOval(
            Rect(
                center = position,
                radius = 100f
            )
        )

        pathStatic.reset()
        pathStatic.addOval(
            Rect(
                center = Offset(center.x, center.y),
                radius = 150f
            )
        )

        pathMeasure.setPath(pathDynamic, true)

        val discretePathEffect = DiscretePathEffect(pathMeasure.length / segmentCount, 0f)
        val cornerPathEffect = CornerPathEffect(50f)


        val chainPathEffect = PathEffect.chainPathEffect(
            outer = cornerPathEffect.toComposePathEffect(),
            inner = discretePathEffect.toComposePathEffect()
        )

        if (!isPaintSetUp) {

            paint.shader = LinearGradientShader(
                from = Offset.Zero,
                to = Offset(size.width, size.height),
                colors = listOf(
                    Color(0xffFFEB3B),
                    Color(0xffE91E63)
                ),
                tileMode = TileMode.Clamp
            )
            isPaintSetUp = true
            paint.pathEffect = chainPathEffect
        }

        val newPath = Path.combine(PathOperation.Union, pathDynamic, pathStatic)

        with(drawContext.canvas) {
            this.drawPath(
                newPath,
                paint
            )
        }
    }
}
//endregion

//region 图片编辑缩放效果

@Composable
private fun EditScaleDemo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xff424242))
            .padding(8.dp)
    ) {


        Spacer(modifier = Modifier.height(40.dp))


        val density = LocalDensity.current
        val size = (1000 / density.density).dp

        ScaleEditBox(
            modifier = Modifier.size(size),
            enabled = true
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.moto_run),
                contentScale = ContentScale.FillBounds,
                contentDescription = "",
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        ScaleEditBox(
            handleRadius = 20.dp,
            enabled = true
        ) {
            Image(
                painter = painterResource(id = R.drawable.sexy_girl),
                contentScale = ContentScale.FillBounds,
                contentDescription = ""
            )
        }


    }
}
//endregion

//region 挖洞效果

@Composable
private fun CustomArcShapeSample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        val content = @Composable {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Payment Failed",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("Sorry !", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(14.dp))
                Text("Your transfer to bank failed", color = Color.Gray)
            }
        }

        val content2 = @Composable {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, Color.Green),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Payment Failed",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("Sorry !", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(14.dp))
                Text("Your transfer to bank failed", color = Color.Gray)
            }
        }

        CustomArcShape(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(250.dp)
        ) {
            content()
        }

        Spacer(modifier = Modifier.height(40.dp))

        CustomArcShape(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(250.dp)
        ) {
            content2()
        }
    }
}

@Composable
private fun CustomArcShape(
    modifier: Modifier,
    elevation: Dp = 4.dp,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    content: @Composable () -> Unit,
) {

    Column {
        val diameter = 60.dp
        val radiusDp = diameter / 2

        val cornerRadiusDp = 10.dp

        val density = LocalDensity.current
        val cutoutRadius = density.run { radiusDp.toPx() }
        val cornerRadius = density.run { cornerRadiusDp.toPx() }

        val shape = remember {
            GenericShape { size: Size, layoutDirection: LayoutDirection ->
                this.roundedRectanglePath(
                    size = size,
                    cornerRadius = cornerRadius,
                    fabRadius = cutoutRadius * 2
                )
            }
        }

        Spacer(modifier = Modifier.height(diameter / 2))

        Box(contentAlignment = Alignment.TopCenter) {

            Icon(
                modifier = Modifier
                    .offset(y = -diameter / 5)
                    .background(Color(0xffD32F2F), CircleShape)
                    .size(diameter)
                    .drawBehind {
                        drawCircle(
                            Color.Red.copy(.5f),
                            radius = 1.3f * size.width / 2
                        )

                        drawCircle(
                            Color.Red.copy(.3f),
                            radius = 1.5f * size.width / 2
                        )
                    }
                    .align(Alignment.TopCenter)
                    .padding(8.dp),
                tint = Color.White,
                imageVector = Icons.Filled.Close,
                contentDescription = "Close"
            )

            Surface(
                modifier = modifier,
                shape = shape,
                tonalElevation = elevation,
                color = color,
                contentColor = contentColor
            ) {
                Column {
                    Spacer(modifier = Modifier.height(diameter))
                    content()
                }
            }
        }
    }
}

fun Path.roundedRectanglePath(
    size: Size,
    cornerRadius: Float,
    fabRadius: Float,
) {

    val centerX = size.width / 2
    val x0 = centerX - fabRadius * 1.15f
    val y0 = 0f

    // offset of the first control point (top part)
    val topControlX = x0 + fabRadius * .5f
    val topControlY = y0

    // offset of the second control point (bottom part)
    val bottomControlX = x0
    val bottomControlY = y0 + fabRadius

    // first curve
    // set the starting point of the curve (P2)
    val firstCurveStart = Offset(x0, y0)

    // set the end point for the first curve (P3)
    val firstCurveEnd = Offset(centerX, fabRadius * 1f)

    // set the first control point (C1)
    val firstCurveControlPoint1 = Offset(
        x = topControlX,
        y = topControlY
    )

    // set the second control point (C2)
    val firstCurveControlPoint2 = Offset(
        x = bottomControlX,
        y = bottomControlY
    )

    // second curve
    // end of first curve and start of second curve is the same (P3)
    val secondCurveStart = Offset(
        x = firstCurveEnd.x,
        y = firstCurveEnd.y
    )

    // end of the second curve (P4)
    val secondCurveEnd = Offset(
        x = centerX + fabRadius * 1.15f,
        y = 0f
    )

    // set the first control point of second curve (C4)
    val secondCurveControlPoint1 = Offset(
        x = secondCurveStart.x + fabRadius,
        y = bottomControlY
    )

    // set the second control point (C3)
    val secondCurveControlPoint2 = Offset(
        x = secondCurveEnd.x - fabRadius / 2,
        y = topControlY
    )

    // Top left arc
    val radius = cornerRadius * 2

    arcTo(
        rect = Rect(
            left = 0f,
            top = 0f,
            right = radius,
            bottom = radius
        ),
        startAngleDegrees = 180.0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = firstCurveStart.x, y = firstCurveStart.y)

    // bezier curve with (P2, C1, C2, P3)
    cubicTo(
        x1 = firstCurveControlPoint1.x,
        y1 = firstCurveControlPoint1.y,
        x2 = firstCurveControlPoint2.x,
        y2 = firstCurveControlPoint2.y,
        x3 = firstCurveEnd.x,
        y3 = firstCurveEnd.y
    )

    // bezier curve with (P3, C4, C3, P4)
    cubicTo(
        x1 = secondCurveControlPoint1.x,
        y1 = secondCurveControlPoint1.y,
        x2 = secondCurveControlPoint2.x,
        y2 = secondCurveControlPoint2.y,
        x3 = secondCurveEnd.x,
        y3 = secondCurveEnd.y
    )

    lineTo(x = size.width - cornerRadius, y = 0f)

    // Top right arc
    arcTo(
        rect = Rect(
            left = size.width - radius,
            top = 0f,
            right = size.width,
            bottom = radius
        ),
        startAngleDegrees = -90.0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = 0f + size.width, y = size.height - cornerRadius)

    // Bottom right arc
    arcTo(
        rect = Rect(
            left = size.width - radius,
            top = size.height - radius,
            right = size.width,
            bottom = size.height
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = cornerRadius, y = size.height)

    // Bottom left arc
    arcTo(
        rect = Rect(
            left = 0f,
            top = size.height - radius,
            right = radius,
            bottom = size.height
        ),
        startAngleDegrees = 90.0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = 0f, y = cornerRadius)
    close()
}


//endregion

@Preview
@Composable
private fun PreviewOthers() {
    Others_Screen()
}