package org.zhiwei.compose.screen.graphics

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

@Composable
fun MultiColorDrawable_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
    ) {

        Title_Text("Blend (PorterDuff) Modes")
        Title_Sub_Text(title = "Use BlendMode to create partially filled VectorDrawable")
        ClipIconSample()
        Title_Sub_Text(title = "Gradient colored VectorDrawable")

        GradientClipIconSample()
        Title_Sub_Text(title = "Fill/empty animation when VectorDrawable is touched.")

        FillIconSample()
        Title_Sub_Text(title = "Shimmer effect.")
        ShimmerIconSample()
        Title_Sub_Text(title = "Clip image with polygon.")
        ImageShapeClipSample()
        Title_Sub_Text(title = "Clip part(circle from) of image.")
        ImageShapeClipSample2()
    }
}


@Composable
private fun ClipIconSample() {
    val vectorRes2: Painter = painterResource(id = R.drawable.img_moto_girl)
    Icon(
        vectorRes2,
        modifier = Modifier
            .drawWithContent {
                val height = size.height

                with(drawContext.canvas.nativeCanvas) {
                    val checkPoint = saveLayer(null, null)

                    // Destination
                    drawContent()

                    // Source
                    drawRect(
                        Color(0xffEC407A),
                        topLeft = Offset(0f, height / 2),
                        size = Size(size.width, size.height / 2),
                        blendMode = BlendMode.SrcIn

                    )

                    restoreToCount(checkPoint)

                }
            }
            .size(100.dp),
        contentDescription = null
    )
}

@Composable
private fun GradientClipIconSample() {
    val vectorRes2: Painter = painterResource(id = R.drawable.sexy_girl)
    Icon(
        vectorRes2,
        modifier = Modifier
            .drawWithContent {
                with(drawContext.canvas.nativeCanvas) {
                    val checkPoint = saveLayer(null, null)

                    // Destination
                    drawContent()

                    // Source
                    drawRect(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color.Green,
                                Color.Cyan,
                                Color.Red,
                                Color.Blue,
                                Color.Yellow,
                                Color.Magenta,
                            )
                        ),
                        blendMode = BlendMode.SrcIn
                    )

                    restoreToCount(checkPoint)

                }
            }
            .size(100.dp),
        contentDescription = null
    )
}

@Composable
private fun FillIconSample() {
    val vectorRes2: Painter = painterResource(id = R.drawable.sexy_girl)
    var targetValue by remember { mutableFloatStateOf(0f) }
    val progress by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(1000), label = ""
    )
    Icon(
        vectorRes2,
        modifier = Modifier
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
            ) {
                targetValue = if (targetValue == 0f) {
                    1f
                } else {
                    0f
                }
            }
            .drawWithContent {
                val height = size.height * progress

                with(drawContext.canvas.nativeCanvas) {
                    val checkPoint = saveLayer(null, null)

                    val totalHeight = size.height
                    val filledHeight = totalHeight * progress

                    // Destination
                    drawContent()

                    // Source
                    drawRect(
                        Color(0xffEC407A),
                        topLeft = Offset(0f, totalHeight - height),
                        size = Size(size.width, height),
                        blendMode = BlendMode.SrcIn

                    )

                    restoreToCount(checkPoint)

                }
            }
            .size(100.dp),
        contentDescription = null
    )
}

@Composable
private fun ShimmerIconSample() {

    val vectorRes2: Painter = painterResource(id = R.drawable.sexy_girl)

    val transition = rememberInfiniteTransition(label = "")

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Icon(
        vectorRes2,
        modifier = Modifier
            .drawWithContent {
                with(drawContext.canvas.nativeCanvas) {
                    val checkPoint = saveLayer(null, null)
                    val canvasWidth = size.width

                    val brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xff616161),
                            Color.White,
                            Color(0xff9E9E9E),
                            Color(0xff616161),
                        ),
                        startX = canvasWidth * progress,
                        endX = canvasWidth * progress * 2f
                    )

                    // Destination
                    drawContent()

                    // Source
                    drawRect(
                        brush = brush,
                        blendMode = BlendMode.SrcIn
                    )

                    restoreToCount(checkPoint)

                }
            }
            .size(100.dp),
        contentDescription = null
    )
}


@Preview
@Composable
fun ImageShapeClipSample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        val imageBitmap = ImageBitmap.imageResource(R.drawable.sexy_girl)

        Box(
            Modifier
                .size(120.dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val cx = canvasWidth / 2
                    val cy = canvasHeight / 2
                    val radius = canvasHeight / 4
                    val path = createPolygonPath(cx, cy, 6, radius)

                    onDrawWithContent {

                        // Source
                        drawPath(
                            path = path,
                            color = Color.Red,
                            style = Stroke(
                                width = canvasHeight / 2,
                                pathEffect = PathEffect.cornerPathEffect(15f)
                            )
                        )

                        drawCircle(
                            color = Color.Red,
                            radius = 10f
                        )

                        // Destination
                        drawImage(
                            image = imageBitmap,
                            dstSize = IntSize(size.width.toInt(), size.height.toInt()),
                            blendMode = BlendMode.SrcIn
                        )
                    }
                }
        )
    }
}


@Preview
@Composable
fun ImageShapeClipSample2() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    val radius = 24.dp.toPx()
                    val badgeRadius = 16.dp.toPx()

                    val center = Offset(
                        size.width - radius,
                        size.height - radius
                    )

                    // Destination
                    drawContent()

                    // Source
                    drawCircle(
                        color = Color.Transparent,
                        radius = radius,
                        center = center,
                        blendMode = BlendMode.Clear
                    )

                    drawCircle(
                        color = Color.Red,
                        radius = badgeRadius,
                        center = center
                    )
                },
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun PreviewMultiColorDrawable() {
    MultiColorDrawable_Screen()
}