package org.zhiwei.compose.screen.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
internal fun CanvasPath_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Text(title = "Draw Path")
        Title_Sub_Text(title = "canvas也可以根据path路径来绘制，使用绝对坐标或相对坐标都可以，绘制点、线、圆，矩形或者其他形状。")
        DrawPath()
        DrawArcToPath()
        DrawTicketPathWithArc()
        DrawRoundedRectangleWithArc()
        DrawPathProgress()
        DrawPathProgress2()
        DrawPolygonPath()
        DrawPolygonPathWithProgress()
        DrawQuad()
        DrawCubic()
    }
}

@Composable
private fun DrawPath() {
    Title_Desc_Text(desc = "使用绝对/相对坐标")
    val path1 = remember { Path() }
    val path2 = remember { Path() }
    Canvas(modifier = canvasModifier) {
        //复用了path对象，在重组的时候，reset一下
        path1.reset()
        path2.reset()

        //path1 先绘制一个正方形，下面path2的正方形，它俩同一大小和位置。
        path1.moveTo(100f, 100f)
        path1.lineTo(100f, 300f)
        path1.lineTo(300f, 300f)
        path1.lineTo(300f, 100f)
        path1.lineTo(100f, 100f)
        //relative是相对之前的path的坐标而言。这里path2使用 相对 坐标的变动方式，绘制一个正方形
        path2.relativeMoveTo(100f, 100f)
        path2.relativeLineTo(0f, 200f)
        path2.relativeLineTo(200f, 0f)
        path2.relativeLineTo(0f, -200f)
        path2.relativeLineTo(-200f, 0f)


        //path1 绘制一个圆角矩形，Rect的坐标和圆角，指定不同角的圆角
        path1.addRoundRect(
            RoundRect(
                left = 400f,
                top = 200f,
                right = 600f,
                bottom = 400f,
                topLeftCornerRadius = CornerRadius(10f, 10f),
                topRightCornerRadius = CornerRadius(30f, 30f),
                bottomLeftCornerRadius = CornerRadius(50f, 20f),
                bottomRightCornerRadius = CornerRadius(0f, 0f)
            )
        )
        // path2也同样绘制一个圆角矩形
        path2.addRoundRect(
            RoundRect(
                left = 700f,
                top = 200f,
                right = 900f,
                bottom = 400f,
                radiusX = 20f,
                radiusY = 20f
            )
        )

        //path1绘制一个圆，下面path2的扇形，它们重叠部分
        path1.addOval(Rect(left = 400f, top = 50f, right = 500f, bottom = 150f))
        //path2绘制一个扇形区域，坐标恰好也是path1的圆的下半部分
        path2.addArc(
            Rect(400f, top = 50f, right = 500f, bottom = 150f),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f
        )

        //使用canvas的绘制path的函数，绘制两个path，绘制的线条样式使用的是虚实线风格
        drawPath(
            color = Color.Red,
            path = path1,
            style = Stroke(
                width = 2.dp.toPx(),
                //虚实线
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )
        drawPath(
            color = Color.Cyan,
            path = path2,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f))
            )
        )
    }
}

@Composable
private fun DrawArcToPath() {
    Title_Desc_Text(desc = "绘制圆弧，arcTo函数path")
    val path1 = remember { Path() }
    val path2 = remember { Path() }
    //在圆弧上，起始角度位置，扇区覆盖弧度
    var startAngle by remember { mutableFloatStateOf(0f) }
    var sweepAngle by remember { mutableFloatStateOf(90f) }

    Canvas(modifier = canvasModifier) {
        path1.reset()
        path2.reset()
        //整个canvas画布，描个边
        val rect = Rect(0f, 0f, size.width, size.height)
        path1.addRect(rect)
        //绘制一个扇形，起始位置角度和覆盖扇形弧度，可动态变化
        path2.arcTo(
            rect,
            startAngleDegrees = startAngle,
            sweepAngleDegrees = sweepAngle,
            forceMoveTo = false
        )
        //描边
        drawPath(
            color = Color.Red,
            path = path1,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )
        //绘制圆弧
        drawPath(
            color = Color.Blue,
            path = path2,
            style = Stroke(width = 2.dp.toPx())
        )
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(text = "起始角度 ${startAngle.roundToInt()}")
        Slider(
            value = startAngle,
            onValueChange = { startAngle = it },
            valueRange = -360f..360f,
        )

        Text(text = "扇区弧度范围 ${sweepAngle.roundToInt()}")
        Slider(
            value = sweepAngle,
            onValueChange = { sweepAngle = it },
            valueRange = -360f..360f,
        )
    }
}

@Preview(heightDp = 200)
@Composable
private fun DrawTicketPathWithArc() {
    Title_Desc_Text(desc = "简单绘制一个优惠券的样式，虚实线裁剪描边")
    Canvas(modifier = canvasModifier) {

        val canvasWidth = size.width
        val canvasHeight = size.height

        // 背景
        val ticketBackgroundWidth = canvasWidth * .8f
        val horizontalSpace = (canvasWidth - ticketBackgroundWidth) / 2

        val ticketBackgroundHeight = canvasHeight * .8f
        val verticalSpace = (canvasHeight - ticketBackgroundHeight) / 2

        //虚实线优惠券的裁剪描边的path
        val path1 = ticketPath(
            topLeft = Offset(horizontalSpace, verticalSpace),
            size = Size(ticketBackgroundWidth, ticketBackgroundHeight),
            cornerRadius = 20.dp.toPx()
        )
        //绘制
        drawPath(path1, color = Color.Cyan)

        //上层绘制虚线的canvas的画布尺寸
        val ticketForegroundWidth = ticketBackgroundWidth * .95f
        val horizontalSpace2 = (canvasWidth - ticketForegroundWidth) / 2

        val ticketForegroundHeight = ticketBackgroundHeight * .9f
        val verticalSpace2 = (canvasHeight - ticketForegroundHeight) / 2

        //背景的路径
        val path2 = ticketPath(
            topLeft = Offset(horizontalSpace2, verticalSpace2),
            size = Size(ticketForegroundWidth, ticketForegroundHeight),
            cornerRadius = 20.dp.toPx()
        )
        //绘制虚实线
        drawPath(
            path2,
            color = Color.Red,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(20f, 20f)
                )
            )
        )
    }
}

@Preview(heightDp = 300)
@Composable
private fun DrawRoundedRectangleWithArc() {
    Title_Desc_Text(desc = "通过path绘制圆角矩形")
    Canvas(modifier = canvasModifier) {
        //也是pathUtils中的圆角矩形的路径path
        val path1 = roundedRectanglePath(
            topLeft = Offset(100f, 100f),
            size = Size(400f, 300f),
            cornerRadius = 20.dp.toPx()
        )
        //填充的圆角矩形
        drawPath(path1, color = Color.Red)

        val path2 = roundedRectanglePath(
            topLeft = Offset(600f, 200f),
            size = Size(200f, 200f),
            cornerRadius = 8.dp.toPx()
        )
        //虚线描边的圆角矩形
        drawPath(
            path2, color = Color.Blue,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(20f, 20f)
                )
            )
        )
    }
    Title_Desc_Text(desc = "可调节圆角的绘制")
    DrawRoundedRectangleWithArc2()
}

@Composable
private fun DrawRoundedRectangleWithArc2() {
    val bound = 150f
    var topLeft by remember {
        mutableStateOf(30f)
    }
    var topRight by remember { mutableFloatStateOf(80f) }
    var bottomLeft by remember { mutableFloatStateOf(50f) }
    var bottomRight by remember { mutableFloatStateOf(150f) }

    Canvas(modifier = canvasModifier) {

        val path = roundedRectanglePath(
            topLeft = Offset(100f, 100f),
            size = Size(size.width - 200f, size.height - 200f),
            radiusTopLeft = topLeft,
            radiusTopRight = topRight,
            radiusBottomLeft = bottomLeft,
            radiusBottomRight = bottomRight,
        )
        drawPath(path, color = Color.Red)
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(text = "Top Left ${topLeft.roundToInt()}px")
        Slider(
            value = topLeft,
            onValueChange = { topLeft = it },
            valueRange = 0f..bound,
        )
        Text(text = "Top Right ${topRight.roundToInt()}px")
        Slider(
            value = topRight,
            onValueChange = { topRight = it },
            valueRange = 0f..bound,
        )
        Text(text = "Bottom Left ${bottomLeft.roundToInt()}px")
        Slider(
            value = bottomLeft,
            onValueChange = { bottomLeft = it },
            valueRange = 0f..bound,
        )
        Text(text = "Bottom Right ${bottomRight.roundToInt()}px")
        Slider(
            value = bottomRight,
            onValueChange = { bottomRight = it },
            valueRange = 0f..bound,
        )

    }
}

@Preview(heightDp = 300)
@Composable
private fun DrawPathProgress() {

    Title_Desc_Text(desc = "绘制sin/cos曲线，根据路径path来绘制部分线段")
    var progressStart by remember { mutableFloatStateOf(20f) }
    var progressEnd by remember { mutableFloatStateOf(80f) }


    // This is the progress path which wis changed using path measure
    val pathWithProgress by remember { mutableStateOf(Path()) }
    // using path
    val pathMeasure by remember { mutableStateOf(PathMeasure()) }


    Canvas(modifier = canvasModifier) {

        /*
            Draw  function with progress like sinus wave
         */
        val canvasHeight = size.height

        val points = getSinusoidalPoints(size)
        //完整的sin曲线的路径path
        val fullPath = Path()
        fullPath.moveTo(0f, canvasHeight / 2f)
        points.forEach { offset: Offset ->
            fullPath.lineTo(offset.x, offset.y)
        }

        //覆盖线条的path，也就是图中 实线的线段路径path
        pathWithProgress.reset()
        //pathMeasure是为了在fullPath上计算出实线的线段path
        pathMeasure.setPath(fullPath, forceClosed = false)
        pathMeasure.getSegment(
            startDistance = pathMeasure.length * progressStart / 100f,
            stopDistance = pathMeasure.length * progressEnd / 100f,
            pathWithProgress,
            startWithMoveTo = true
        )

        //绘制完整sin的path线条
        drawPath(
            color = Color.Red,
            path = fullPath,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f))
            )
        )
        //根据测量计算出来的实线，绘制
        drawPath(
            color = Color.Blue,
            path = pathWithProgress,
            style = Stroke(
                width = 2.dp.toPx(),
            )
        )
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {

        Text(text = "Progress Start ${progressStart.roundToInt()}%")
        Slider(
            value = progressStart,
            onValueChange = { progressStart = it },
            valueRange = 0f..100f,
        )

        Text(text = "Progress End ${progressEnd.roundToInt()}%")
        Slider(
            value = progressEnd,
            onValueChange = { progressEnd = it },
            valueRange = 0f..100f,
        )
    }
}

//生成sin曲线的点
private fun getSinusoidalPoints(size: Size, horizontalOffset: Float = 0f): MutableList<Offset> {
    val points = mutableListOf<Offset>()
    val verticalCenter = size.height / 2

    for (x in 0 until size.width.toInt() step 20) {
        val y = (sin(x * (2f * Math.PI / size.width)) * verticalCenter + verticalCenter).toFloat()
        points.add(Offset(x.toFloat() + horizontalOffset, y))
    }
    return points
}

//绘制一个圆角矩形，在边框绘制片段path
@Composable
private fun DrawPathProgress2() {
    Title_Desc_Text(desc = "绘制圆角矩形，根据路径path来绘制部分线段")
    var progressStart by remember { mutableFloatStateOf(20f) }
    var progressEnd by remember { mutableFloatStateOf(80f) }


    // This is the progress path which wis changed using path measure
    val pathWithProgress by remember {
        mutableStateOf(Path())
    }

    // using path
    val pathMeasure by remember { mutableStateOf(PathMeasure()) }

    val fullPath = remember {
        roundedRectanglePath(
            topLeft = Offset(100f, 100f),
            size = Size(400f, 300f),
            cornerRadius = 20f
        )
    }

    Canvas(modifier = canvasModifier) {


        pathWithProgress.reset()

        pathMeasure.setPath(fullPath, forceClosed = false)
        pathMeasure.getSegment(
            startDistance = pathMeasure.length * progressStart / 100f,
            stopDistance = pathMeasure.length * progressEnd / 100f,
            pathWithProgress,
            startWithMoveTo = true
        )

        drawPath(
            color = Color.Red,
            path = fullPath,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f))
            )
        )

        drawPath(
            color = Color.Blue,
            path = pathWithProgress,
            style = Stroke(
                width = 2.dp.toPx(),
            )
        )
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {

        Text(text = "Progress Start ${progressStart.roundToInt()}%")
        Slider(
            value = progressStart,
            onValueChange = { progressStart = it },
            valueRange = 0f..100f,
        )

        Text(text = "Progress End ${progressEnd.roundToInt()}%")
        Slider(
            value = progressEnd,
            onValueChange = { progressEnd = it },
            valueRange = 0f..100f,
        )
    }
}

/**
 * 绘制多边形
 */
@Composable
private fun DrawPolygonPath() {

    Title_Desc_Text(desc = "绘制多边形，动态修改边角点数，以及是否圆角")
    var sides by remember { mutableFloatStateOf(3f) }
    var cornerRadius by remember { mutableFloatStateOf(1f) }

    Canvas(modifier = canvasModifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val cx = canvasWidth / 2
        val cy = canvasHeight / 2
        val radius = (canvasHeight - 20.dp.toPx()) / 2
        val path = createPolygonPath(cx, cy, sides.roundToInt(), radius)

        drawPath(
            color = Color.Red,
            path = path,
            style = Stroke(
                width = 4.dp.toPx(),
                pathEffect = PathEffect.cornerPathEffect(cornerRadius)
            )
        )
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(text = "Sides ${sides.roundToInt()}")
        Slider(
            value = sides,
            onValueChange = { sides = it },
            valueRange = 3f..12f,
            steps = 10
        )

        Text(text = "CornerRadius ${cornerRadius.roundToInt()}")

        Slider(
            value = cornerRadius,
            onValueChange = { cornerRadius = it },
            valueRange = 0f..50f,
        )
    }
}

/**
 * [PathMeasure.getSegment] returns a new path segment from original path it's set with.
 * Start and stop distances determine which sections are set to new path.
 */
@Composable
private fun DrawPolygonPathWithProgress() {
    Title_Desc_Text(desc = "在多边形的边上，绘制片段")
    var sides by remember { mutableFloatStateOf(3f) }
    var cornerRadius by remember { mutableFloatStateOf(1f) }
    val pathMeasure by remember { mutableStateOf(PathMeasure()) }
    var progress by remember { mutableFloatStateOf(50f) }

    val pathWithProgress by remember { mutableStateOf(Path()) }

    Canvas(modifier = canvasModifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val cx = canvasWidth / 2
        val cy = canvasHeight / 2
        val radius = (canvasHeight - 20.dp.toPx()) / 2

        val fullPath = createPolygonPath(cx, cy, sides.roundToInt(), radius)
        pathWithProgress.reset()
        if (progress >= 100f) {
            pathWithProgress.addPath(fullPath)
        } else {
            pathMeasure.setPath(fullPath, forceClosed = false)
            pathMeasure.getSegment(
                startDistance = 0f,
                stopDistance = pathMeasure.length * progress / 100f,
                pathWithProgress,
                startWithMoveTo = true
            )
        }

        drawPath(
            color = Color.Red,
            path = pathWithProgress,
            style = Stroke(
                width = 4.dp.toPx(),
                pathEffect = PathEffect.cornerPathEffect(cornerRadius)
            )
        )
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {

        Text(text = "Progress ${progress.roundToInt()}%")
        Slider(
            value = progress,
            onValueChange = { progress = it },
            valueRange = 0f..100f,
        )

        Text(text = "Sides ${sides.roundToInt()}")
        Slider(
            value = sides,
            onValueChange = { sides = it },
            valueRange = 3f..12f,
            steps = 10
        )

        Text(text = "CornerRadius ${cornerRadius.roundToInt()}")
        Slider(
            value = cornerRadius,
            onValueChange = { cornerRadius = it },
            valueRange = 0f..50f,
        )
    }
}

//二次贝塞尔曲线
@Composable
private fun DrawQuad() {
    Title_Desc_Text(desc = "绘制二次贝塞尔曲线")
    val density = LocalDensity.current.density

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val screenWidthInPx = screenWidth.value * density

    // (x0, y0) is initial coordinate where path is moved with path.moveTo(x0,y0)
    var x0 by remember { mutableFloatStateOf(0f) }
    var y0 by remember { mutableFloatStateOf(0f) }

    /*
        Adds a quadratic bezier segment that curves from the current point(x0,y0) to the
        given point (x2, y2), using the control point (x1, y1).
     */
    var x1 by remember { mutableFloatStateOf(0f) }
    var y1 by remember { mutableFloatStateOf(screenWidthInPx) }
    var x2 by remember { mutableFloatStateOf(screenWidthInPx) }
    var y2 by remember { mutableFloatStateOf(screenWidthInPx) }

    val path1 = remember { Path() }
    val path2 = remember { Path() }
    Canvas(
        modifier = Modifier
            .padding(8.dp)
            .shadow(1.dp)
            .background(Color.White)
            .size(screenWidth, screenWidth)
    ) {
        path1.reset()
        path1.moveTo(x0, y0)
        path1.quadraticBezierTo(x1 = x1, y1 = y1, x2 = x2, y2 = y2)

        // relativeQuadraticBezierTo draws quadraticBezierTo by adding offset
        // instead of setting absolute position
        path2.reset()
        path2.moveTo(x0, y0)
        path2.relativeQuadraticBezierTo(dx1 = x1 - x0, dy1 = y1 - y0, dx2 = x2 - x0, dy2 = y2 - y0)


        drawPath(
            color = Color.Red,
            path = path1,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )

        drawPath(
            color = Color.Blue,
            path = path2,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f))
            )
        )

        // Draw Control Point on screen
        drawPoints(
            listOf(Offset(x1, y1)),
            color = Color.Green,
            pointMode = PointMode.Points,
            cap = StrokeCap.Round,
            strokeWidth = 40f
        )
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {

        Text(text = "X0: ${x0.roundToInt()}")
        Slider(
            value = x0,
            onValueChange = { x0 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "Y0: ${y0.roundToInt()}")
        Slider(
            value = y0,
            onValueChange = { y0 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "X1: ${x1.roundToInt()}")
        Slider(
            value = x1,
            onValueChange = { x1 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "Y1: ${y1.roundToInt()}")
        Slider(
            value = y1,
            onValueChange = { y1 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "X2: ${x2.roundToInt()}")
        Slider(
            value = x2,
            onValueChange = { x2 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "Y2: ${y2.roundToInt()}")
        Slider(
            value = y2,
            onValueChange = { y2 = it },
            valueRange = 0f..screenWidthInPx,
        )
    }
}

@Composable
private fun DrawCubic() {
    Title_Desc_Text(desc = "绘制三次方贝塞尔曲线")
    val density = LocalDensity.current.density

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val screenWidthInPx = screenWidth.value * density

    // (x0, y0) is initial coordinate where path is moved with path.moveTo(x0,y0)
    var x0 by remember { mutableFloatStateOf(0f) }
    var y0 by remember { mutableFloatStateOf(0f) }

    /*
        Adds a cubic bezier segment that curves from the current point(x0,y0) to the
        given point (x3, y3), using the control points (x1, y1) and (x2, y2).
     */
    var x1 by remember { mutableFloatStateOf(0f) }
    var y1 by remember { mutableFloatStateOf(screenWidthInPx) }
    var x2 by remember { mutableFloatStateOf(screenWidthInPx) }
    var y2 by remember { mutableFloatStateOf(0f) }

    var x3 by remember { mutableFloatStateOf(screenWidthInPx) }
    var y3 by remember { mutableFloatStateOf(screenWidthInPx) }

    val path1 = remember { Path() }
    val path2 = remember { Path() }
    Canvas(
        modifier = Modifier
            .padding(8.dp)
            .shadow(1.dp)
            .background(Color.White)
            .size(screenWidth, screenWidth)
    ) {
        path1.reset()
        path1.moveTo(x0, y0)
        path1.cubicTo(x1 = x1, y1 = y1, x2 = x2, y2 = y2, x3 = x3, y3 = y3)

        // relativeQuadraticBezierTo draws quadraticBezierTo by adding offset
        // instead of setting absolute position
        path2.reset()
        path2.moveTo(x0, y0)

        // TODO offsets are not correct
        path2.relativeCubicTo(
            dx1 = x1 - x0,
            dy1 = y1 - y0,
            dx2 = x2 - x0,
            dy2 = y2 - y0,
            dx3 = y3 - y0,
            dy3 = y3 - y0
        )

        drawPath(
            color = Color.Red,
            path = path1,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )

        drawPath(
            color = Color.Blue,
            path = path2,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f))
            )
        )

        // Draw Control Points on screen
        drawPoints(
            listOf(Offset(x1, y1), Offset(x2, y2)),
            color = Color.Green,
            pointMode = PointMode.Points,
            cap = StrokeCap.Round,
            strokeWidth = 40f
        )
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {

        Text(text = "X0: ${x0.roundToInt()}")
        Slider(
            value = x0,
            onValueChange = { x0 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "Y0: ${y0.roundToInt()}")
        Slider(
            value = y0,
            onValueChange = { y0 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "X1: ${x1.roundToInt()}")
        Slider(
            value = x1,
            onValueChange = { x1 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "Y1: ${y1.roundToInt()}")
        Slider(
            value = y1,
            onValueChange = { y1 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "X2: ${x2.roundToInt()}")
        Slider(
            value = x2,
            onValueChange = { x2 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "Y2: ${y2.roundToInt()}")
        Slider(
            value = y2,
            onValueChange = { y2 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "X3: ${x3.roundToInt()}")
        Slider(
            value = x3,
            onValueChange = { x3 = it },
            valueRange = 0f..screenWidthInPx,
        )

        Text(text = "Y3: ${y3.roundToInt()}")
        Slider(
            value = y3,
            onValueChange = { y3 = it },
            valueRange = 0f..screenWidthInPx,
        )
    }
}


//一个可复用的modifier
private val canvasModifier = Modifier
    .padding(8.dp)
    .shadow(1.dp)
    .background(Color.White)
    .fillMaxSize()
    .height(200.dp)

@Preview
@Composable
private fun PreviewCanvasPath() {
    CanvasPath_Screen()
}