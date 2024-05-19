package org.zhiwei.compose.screen.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 作者： iOrchid
 * 主页： [Github](https://github.com/iOrchid)
 * 日期： 2024年05月19日 10:59
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 */
@Composable
internal fun CanvasBasic_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Text(title = "CanvasBasic")
        Title_Sub_Text(title = "1. 使用canvas绘制基础的点、线、面，以及相关的描边，渐变色，虚实间隔等效果。")
        UI_Line()
        Title_Desc_Text(desc = "绘制圆和椭圆，及其渐变色，空心与叠加效果。")
        UI_Circle()
        Title_Desc_Text(desc = "绘制矩形，类似绘制圆和线，也有描边，变色的相关属性。")
        UI_Rectangle()
        Title_Desc_Text(desc = "点的绘制，主要的是点数据。也会有渐变色的设置。PointMode.Points是关键。")
        UI_Points()
        UI_Arc()
        UI_Image()
    }
}

@Composable
private fun UI_Line() {
    Title_Desc_Text(desc = "线条的绘制，包含粗细、虚实、渐变色的效果")
    Canvas(modifier = canvasModifier) {
        //绘制线条，参数中指定颜色，起始和结束的点位。线宽有默认值
        drawLine(Color.Red, start = Offset(100f, 30f), end = Offset(size.width - 100f, 30f))
        drawLine(
            Color.Red,
            start = Offset(100f, 60f),
            end = Offset(size.width - 100f, 60f),
            strokeWidth = 5f
        )
        //cap是指绘制线条两头的形状处理方式
        drawLine(
            Color.Red,
            start = Offset(100f, 90f),
            end = Offset(size.width - 100f, 90f),
            strokeWidth = 15f,//粗一点，好看效果
            cap = StrokeCap.Round
        )
    }
    Canvas(modifier = canvasModifier) {

        //指定方式的线效果,菱形
        val path = Path().apply {
            moveTo(10f, 0f)
            lineTo(20f, 10f)
            lineTo(10f, 20f)
            lineTo(0f, 10f)
        }
        drawLine(
            Color.Red,
            start = Offset(100f, 20f),
            end = Offset(size.width - 100f, 20f),
            strokeWidth = 15f,
            cap = StrokeCap.Round,
            pathEffect = PathEffect.stampedPathEffect(
                shape = path,
                advance = 50f,
                phase = 0f,//起始偏移
                style = StampedPathEffectStyle.Rotate
            )
        )
        //pathEffect 虚实效果
        drawLine(
            Color.Red,
            start = Offset(100f, 60f),
            end = Offset(size.width - 100f, 60f),
            strokeWidth = 15f,
            cap = StrokeCap.Round,
            //dashPathEffect两个参数，第一个是，控制 实线，虚线的长度;第二个参数，是开始虚实的偏移量，可修改看效果。
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 20f), 25f)
        )
        //线条颜色可以是渐变色，渐变色效果作用于整条线，而不关心它是否虚实，点化
        drawLine(
            brush = Brush.horizontalGradient(
                listOf(Color.Red, Color.Green, Color.Blue)
            ),
            start = Offset(100f, 90f),
            end = Offset(size.width - 100f, 90f),
            strokeWidth = 15f,
            cap = StrokeCap.Round,
            //dashPathEffect两个参数，第一个是，控制 实线，虚线的长度;第二个参数，是开始虚实的偏移量，可修改看效果。
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 20f), 25f)
        )
    }
}

@Composable
private fun UI_Circle() {
    Canvas(modifier = canvasModifier2) {
        //绘制椭圆
        val canvasHeight = size.height
        val canvasWidth = size.width
        val radius = canvasHeight / 2f
        drawOval(
            color = Color.Blue,
            topLeft = Offset.Zero,
            size = Size(1.2f * canvasHeight, canvasHeight)
        )
        drawOval(
            color = Color.Green,
            topLeft = Offset(1.3f * canvasHeight, 0f),
            size = Size(canvasHeight / 1.5f, canvasHeight)
        )
        drawCircle(
            Color.Red,
            center = Offset(canvasWidth - 2.5f * radius, canvasHeight / 2),
            radius = radius * 0.8f,
            style = Fill//默认就是Fill
        )
        //绘制描边的圆
        drawCircle(
            Color.Cyan,
            center = Offset(canvasWidth - radius, canvasHeight / 2),
            radius = radius * 0.5f,
            style = Stroke(
                width = 5f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(30f, 20f)
                )
            )
        )
    }
    Canvas(modifier = canvasModifier2) {
        //绘制椭圆
        val canvasHeight = size.height
        val canvasWidth = size.width
        val radius = canvasHeight / 2f
        val space = (canvasWidth - 6 * radius) / 4
        drawCircle(
            brush = Brush.linearGradient(
                colors = listOf(Color.Red, Color.Green),
                start = Offset(radius * .3f, radius * .1f),
                end = Offset(radius * 2f, radius * 2f)
            ),
            radius = radius,
            center = Offset(space + radius, canvasHeight / 2),
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.Red, Color.Green)
            ),
            radius = radius,
            center = Offset(2 * space + 3 * radius, canvasHeight / 2),
        )

        drawCircle(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Red,
                    Color.Green,
                    Color.Yellow,
                    Color.Blue,
                    Color.Cyan,
                    Color.Magenta
                ),
            ),
            radius = radius,
            center = Offset(canvasWidth - space - radius, canvasHeight / 2)
        )

    }
    Canvas(modifier = canvasModifier2) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = canvasHeight / 2
        val space = (canvasWidth - 6 * radius) / 4

        drawCircle(
            brush = Brush.sweepGradient(
                colors = listOf(
                    Color.Green,
                    Color.Red,
                    Color.Blue
                ),
                center = Offset(space + radius, canvasHeight / 2),
            ),
            radius = radius,
            center = Offset(space + radius, canvasHeight / 2),
        )

        drawCircle(
            brush = Brush.sweepGradient(
                colors = listOf(
                    Color.Green,
                    Color.Cyan,
                    Color.Red,
                    Color.Blue,
                    Color.Yellow,
                    Color.Magenta,
                ),
                // Offset for this gradient is not at center, a little bit left of center
                center = Offset(2 * space + 2.7f * radius, canvasHeight / 2),
            ),
            radius = radius,
            center = Offset(2 * space + 3 * radius, canvasHeight / 2),
        )

        drawCircle(
            brush = Brush.sweepGradient(
                colors = listOf(
                    Color.Green,
                    Color.Cyan,
                    Color.Red,
                    Color.Blue,
                    Color.Yellow,
                    Color.Magenta,
                ),
                center = Offset(canvasWidth - space - radius, canvasHeight / 2),
            ),
            radius = radius,
            center = Offset(canvasWidth - space - radius, canvasHeight / 2)
        )
    }
    Title_Desc_Text(desc = "简单演示canvas的BlendMode")
    Canvas(modifier = canvasModifier2) {

        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = canvasHeight / 2
        val space = (canvasWidth - 4 * radius) / 2

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)
            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(space, canvasHeight / 2),
            )
            drawCircle(
                blendMode = BlendMode.DstOut,
                color = Color.Blue,
                radius = radius,
                center = Offset(space + radius, canvasHeight / 2),
            )
            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(space + 3 * radius, canvasHeight / 2),
            )
            drawCircle(
                blendMode = BlendMode.Xor,
                color = Color.Blue,
                radius = radius,
                center = Offset(space + 4 * radius, canvasHeight / 2),
            )
            restoreToCount(checkPoint)
        }
    }
}

@Composable
private fun UI_Rectangle() {
    Canvas(modifier = canvasModifier2) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val space = 60f
        val rectHeight = canvasHeight / 2
        val rectWidth = (canvasWidth - 4 * space) / 3

        drawRect(
            color = Color.Blue,
            topLeft = Offset(space, rectHeight / 2),
            size = Size(rectWidth, rectHeight)
        )

        drawRect(
            color = Color.Green,
            topLeft = Offset(2 * space + rectWidth, rectHeight / 2),
            size = Size(rectWidth, rectHeight),
            style = Stroke(width = 12.dp.toPx())
        )

        drawRect(
            color = Color.Red,
            topLeft = Offset(3 * space + 2 * rectWidth, rectHeight / 2),
            size = Size(rectWidth, rectHeight),
            style = Stroke(width = 2.dp.toPx())
        )
    }
    Canvas(modifier = canvasModifier2) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val space = 60f
        val rectHeight = canvasHeight / 2
        val rectWidth = (canvasWidth - 4 * space) / 3

        drawRoundRect(
            color = Color.Blue,
            topLeft = Offset(space, rectHeight / 2),
            size = Size(rectWidth, rectHeight),
            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
        )

        drawRoundRect(
            color = Color.Green,
            topLeft = Offset(2 * space + rectWidth, rectHeight / 2),
            size = Size(rectWidth, rectHeight),
            cornerRadius = CornerRadius(70f, 70f)

        )

        drawRoundRect(
            color = Color.Red,
            topLeft = Offset(3 * space + 2 * rectWidth, rectHeight / 2),
            size = Size(rectWidth, rectHeight),
            cornerRadius = CornerRadius(50f, 25f)
        )
    }
    Canvas(modifier = canvasModifier2) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val space = 30f
        val rectHeight = canvasHeight / 2
        val rectWidth = (canvasWidth - 4 * space) / 3

        drawRect(
            color = Color.Blue,
            topLeft = Offset(space, rectHeight / 2),
            size = Size(rectWidth, rectHeight),
            style = Stroke(
                width = 2.dp.toPx(),
                join = StrokeJoin.Miter,
                cap = StrokeCap.Butt,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f))
            )
        )

        drawRect(
            color = Color.Green,
            topLeft = Offset(2 * space + rectWidth, rectHeight / 2),
            size = Size(rectWidth, rectHeight),
            style = Stroke(
                width = 2.dp.toPx(),
                join = StrokeJoin.Bevel,
                cap = StrokeCap.Square,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f))
            )
        )

        drawRect(
            color = Color.Red,
            topLeft = Offset(3 * space + 2 * rectWidth, rectHeight / 2),
            size = Size(rectWidth, rectHeight),
            style = Stroke(
                width = 2.dp.toPx(),
                join = StrokeJoin.Round,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f))
            )
        )
    }
    Canvas(modifier = canvasModifier2) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val space = 30f
        val rectHeight = canvasHeight / 2
        val rectWidth = (canvasWidth - 4 * space) / 3

        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Green,
                    Color.Red,
                    Color.Blue,
                    Color.Yellow,
                    Color.Magenta
                ),
                center = Offset(space + .5f * rectWidth, rectHeight),
                tileMode = TileMode.Mirror,//渐变色的重复模式
                radius = 20f
            ),
            topLeft = Offset(space, rectHeight / 2),
            size = Size(rectWidth, rectHeight)
        )

        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Green,
                    Color.Red,
                    Color.Blue,
                    Color.Yellow,
                    Color.Magenta
                ),
                center = Offset(2 * space + 1.5f * rectWidth, rectHeight),
                tileMode = TileMode.Repeated,
                radius = 20f
            ),
            topLeft = Offset(2 * space + rectWidth, rectHeight / 2),
            size = Size(rectWidth, rectHeight)
        )

        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Green,
                    Color.Red,
                    Color.Blue,
                    Color.Yellow,
                    Color.Magenta
                ),
                center = Offset(3 * space + 2.5f * rectWidth, rectHeight),
                tileMode = TileMode.Decal,
                radius = rectHeight / 2
            ),
            topLeft = Offset(3 * space + 2 * rectWidth, rectHeight / 2),
            size = Size(rectWidth, rectHeight)
        )
    }
}

@Composable
private fun UI_Points() {
    Canvas(modifier = canvasModifier2) {
        val middleW = size.width / 2
        val middleH = size.height / 2
        drawLine(Color.Gray, Offset(0f, middleH), Offset(size.width - 1, middleH))
        drawLine(Color.Gray, Offset(middleW, 0f), Offset(middleW, size.height - 1))
        //sin曲线的点数据
        val points1 = getSinusoidalPoints(size)

        drawPoints(
            points = points1,
            pointMode = PointMode.Points,
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
        )

        val points2 = getSinusoidalPoints(size, 100f)
        drawPoints(
            points = points2,
            pointMode = PointMode.Points,
            color = Color.Green,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
        )

        val points3 = getSinusoidalPoints(size, 200f)
        drawPoints(
            points = points3,
            pointMode = PointMode.Points,
            color = Color.Yellow,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
        )
    }
    Canvas(modifier = canvasModifier2) {

        val middleW = size.width / 2
        val middleH = size.height / 2
        drawLine(Color.Gray, Offset(0f, middleH), Offset(size.width - 1, middleH))
        drawLine(Color.Gray, Offset(middleW, 0f), Offset(middleW, size.height - 1))


        val points1 = getSinusoidalPoints(size)

        drawPoints(
            brush = Brush.linearGradient(
                colors = listOf(Color.Red, Color.Green)
            ),
            points = points1,
            cap = StrokeCap.Round,
            pointMode = PointMode.Points,
            strokeWidth = 10f
        )

        val points2 = getSinusoidalPoints(size, 100f)
        drawPoints(
            brush = Brush.linearGradient(
                colors = listOf(Color.Green, Color.Magenta)
            ),
            points = points2,
            cap = StrokeCap.Round,
            pointMode = PointMode.Lines,
            strokeWidth = 10f
        )

        val points3 = getSinusoidalPoints(size, 200f)
        drawPoints(
            brush = Brush.linearGradient(
                colors = listOf(Color.Red, Color.Yellow)
            ),
            points = points3,
            cap = StrokeCap.Round,
            pointMode = PointMode.Polygon,
            strokeWidth = 10f
        )
    }
}

@Composable
private fun UI_Arc() {
    Title_Sub_Text("2. Draw Arc 绘制扇形，弦切弧，圆环，以及扇形扫览。")
    Title_Desc_Text(desc = "绘制扇形，圆弧，切面")
    var startAngle by remember { mutableFloatStateOf(0f) }
    var sweepAngle by remember { mutableFloatStateOf(60f) }
    var useCenter by remember { mutableStateOf(true) }
    //canvas绘制区域
    Canvas(modifier = canvasModifier2) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawArc(
            //同样，渐变色或颜色是作用于整个图形
            brush = Brush.sweepGradient(
                colors = listOf(
                    Color.Green,
                    Color.Red,
                    Color.Blue,
                    Color.Yellow,
                    Color.Magenta
                ),
                center = Offset(canvasWidth / 2, canvasHeight / 2)
            ),
            startAngle,//开始绘制的点所在的圆角的角度值
            sweepAngle,//绘制终点的所在的圆角角度值
            useCenter,//标记是否从圆心出发的扇形，true包含圆心点，false的话，就是弦切弧的图形。
            topLeft = Offset((canvasWidth - canvasHeight) / 2, 0f),
            size = Size(canvasHeight, canvasHeight)
        )
    }
    Canvas(modifier = canvasModifier2) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawArc(
            //同样，渐变色或颜色是作用于整个图形
            brush = Brush.sweepGradient(
                colors = listOf(
                    Color.Green,
                    Color.Red,
                    Color.Blue,
                    Color.Yellow,
                    Color.Magenta
                ),
                center = Offset(canvasWidth / 2, canvasHeight / 2)
            ),
            startAngle,//开始绘制的点所在的圆角的角度值
            sweepAngle,//绘制终点的所在的圆角角度值
            useCenter,//标记是否从圆心出发的扇形，true包含圆心点，false的话，就是弦切弧的图形。
            topLeft = Offset((canvasWidth - canvasHeight) / 2, 0f),
            size = Size(canvasHeight, canvasHeight),
            style = Stroke(10f),//这里就类似上面了，可以配置不同的虚实点线样式
        )
    }
    //动态控制起始角，和扇形的覆盖范围。
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(text = "扇形开始位置角度 ${startAngle.roundToInt()}")
        Slider(
            value = startAngle,
            onValueChange = { startAngle = it },
            valueRange = 0f..360f,
        )

        Text(text = "扇面覆盖角度 ${sweepAngle.roundToInt()}")
        Slider(
            value = sweepAngle,
            onValueChange = { sweepAngle = it },
            valueRange = 0f..360f,
        )
        //是否包含圆心点
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = useCenter, onCheckedChange = { useCenter = it })
            Text(text = "是否包含圆心")
        }
    }
}

@Composable
private fun UI_Image() {
    Title_Sub_Text(title = "3. DrawImage")
    Title_Desc_Text(desc = "canvas的绘制image操作函数。")

    val canvasModifier = Modifier
        .padding(8.dp)
        .shadow(1.dp)
        .background(Color.White)
        .fillMaxWidth()
        .height(200.dp)

    val bitmap = ImageBitmap.imageResource(R.drawable.sexy_girl)
    Canvas(modifier = canvasModifier) {
        drawImage(bitmap)
    }

    var srcOffsetX by remember { mutableIntStateOf(0) }
    var srcOffsetY by remember { mutableIntStateOf(0) }
    var srcWidth by remember { mutableIntStateOf(1080) }
    var srcHeight by remember { mutableIntStateOf(1080) }

    var dstOffsetX by remember { mutableIntStateOf(0) }
    var dstOffsetY by remember { mutableIntStateOf(0) }
    var dstWidth by remember { mutableIntStateOf(1080) }
    var dstHeight by remember { mutableIntStateOf(1080) }

    Canvas(modifier = canvasModifier) {
        drawImage(
            image = bitmap,
            srcOffset = IntOffset(srcOffsetX, srcOffsetY),
            srcSize = IntSize(srcWidth, srcHeight),
            dstOffset = IntOffset(dstOffsetX, dstOffsetY),
            dstSize = IntSize(dstWidth, dstHeight),
            filterQuality = FilterQuality.High
        )
    }
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(text = "srcOffsetX $srcOffsetX")
        Slider(
            value = srcOffsetX.toFloat(),
            onValueChange = { srcOffsetX = it.toInt() },
            valueRange = -540f..540f,
        )

        Text(text = "srcOffsetY $srcOffsetY")
        Slider(
            value = srcOffsetY.toFloat(),
            onValueChange = { srcOffsetY = it.toInt() },
            valueRange = -540f..540f,
        )
        Text(text = "srcWidth $srcWidth")
        Slider(
            value = srcWidth.toFloat(),
            onValueChange = { srcWidth = it.toInt() },
            valueRange = 0f..1080f,
        )

        Text(text = "srcHeight $srcHeight")
        Slider(
            value = srcHeight.toFloat(),
            onValueChange = { srcHeight = it.toInt() },
            valueRange = 0f..1080f,
        )


        Text(text = "dstOffsetX $dstOffsetX")
        Slider(
            value = dstOffsetX.toFloat(),
            onValueChange = { dstOffsetX = it.toInt() },
            valueRange = -540f..540f,
        )

        Text(text = "dstOffsetY $dstOffsetY")
        Slider(
            value = dstOffsetY.toFloat(),
            onValueChange = { dstOffsetY = it.toInt() },
            valueRange = -540f..540f,
        )
        Text(text = "dstWidth $dstWidth")
        Slider(
            value = dstWidth.toFloat(),
            onValueChange = { dstWidth = it.toInt() },
            valueRange = 0f..1080f,
        )

        Text(text = "dstHeight $dstHeight")
        Slider(
            value = dstHeight.toFloat(),
            onValueChange = { dstHeight = it.toInt() },
            valueRange = 0f..1080f,
        )
    }
}


//定义一个函数，用于创建一堆点数据，这里是按照sin曲线的路径生成的点数据
private fun getSinusoidalPoints(size: Size, horizontalOffset: Float = 0f): MutableList<Offset> {
    val points = mutableListOf<Offset>()
    val verticalCenter = size.height / 2

    for (x in 0 until size.width.toInt() step 20) {
        val y =
            (sin(x * (2f * Math.PI / size.width)) * verticalCenter + verticalCenter).toFloat()
        points.add(Offset(x.toFloat() + horizontalOffset, y))
    }
    return points
}

private val canvasModifier = Modifier
    .padding(8.dp)
    .shadow(1.dp)
    .background(Color.White)
    .fillMaxSize()
    .height(60.dp)

private val canvasModifier2 = Modifier
    .padding(8.dp)
    .shadow(1.dp)
    .background(Color.White)
    .fillMaxSize()
    .height(100.dp)


@Preview
@Composable
private fun PreviewCanvasBasic() {
    CanvasBasic_Screen(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}