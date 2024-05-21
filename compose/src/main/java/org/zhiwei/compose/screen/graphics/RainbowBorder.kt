package org.zhiwei.compose.screen.graphics

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Sub_Text

@Composable
internal fun RainbowBorder_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Title_Sub_Text(
            "Draw animated rainbow color border using **BlendMode.SrcIn** " +
                    "and Modifier.drawWithCache",
        )

        Box(
            modifier = Modifier
                .size(140.dp, 100.dp)
                .drawRainbowBorder(
                    strokeWidth = 4.dp,
                    durationMillis = 3000
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Hello World", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .drawAnimatedBorder(
                    strokeWidth = 4.dp,
                    durationMillis = 2000,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Hello World", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Box(
                modifier = Modifier
                    .size(120.dp)
//                .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
                    .drawAnimatedBorder(
                        strokeWidth = 6.dp,
                        durationMillis = 3000,
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .matchParentSize(),
                    painter = painterResource(id = R.drawable.avatar02),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .drawAnimatedBorder(
                        strokeWidth = 6.dp,
                        durationMillis = 3000,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .matchParentSize(),
                    painter = painterResource(id = R.drawable.avatar01),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .size(80.dp)
                .drawAnimatedBorder(
                    strokeWidth = 4.dp,
                    durationMillis = 2000,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .drawAnimatedBorder(
                    strokeWidth = 4.dp,
                    durationMillis = 2000,
                    shape = CutCornerShape(8.dp)
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Hello World", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .drawAnimatedBorder(
                    strokeWidth = 4.dp,
                    durationMillis = 2000,
                    shape = createBubbleShape(
                        arrowWidth = 20f,
                        arrowHeight = 20f,
                        arrowOffset = 20f,
                        arrowDirection = ArrowDirection.Left
                    )
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Hello World", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .drawAnimatedBorder(
                    brush = {
                        Brush.sweepGradient(
                            colors = listOf(
                                Color.Gray,
                                Color.White,
                                Color.Gray,
                                Color.White,
                                Color.Gray
                            )
                        )
                    },
                    strokeWidth = 4.dp,
                    durationMillis = 2000,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Hello World", fontSize = 20.sp)
        }
    }
}


fun Modifier.drawRainbowBorder(
    strokeWidth: Dp,
    durationMillis: Int,
) = composed {

    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "rotation"
    )

    val brush = Brush.sweepGradient(gradientColors)

    this.drawWithContent {

        val strokeWidthPx = strokeWidth.toPx()
        val width = size.width
        val height = size.height

        drawContent()

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            // Destination
            drawRect(
                color = Color.Gray,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(width - strokeWidthPx, height - strokeWidthPx),
                style = Stroke(strokeWidthPx)
            )

            // Source
            rotate(angle) {

                drawCircle(
                    brush = brush,
                    radius = size.width,
                    blendMode = BlendMode.SrcIn,
                )
            }

            restoreToCount(checkPoint)
        }
    }
}

fun Modifier.drawAnimatedBorder(
    strokeWidth: Dp,
    shape: Shape,
    brush: (Size) -> Brush = {
        Brush.sweepGradient(gradientColors)
    },
    durationMillis: Int,
) = composed {

    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "rotation"
    )

    Modifier
        .clip(shape)
        .drawWithCache {
            val strokeWidthPx = strokeWidth.toPx()

            val outline: Outline = shape.createOutline(size, layoutDirection, this)

            val pathBounds = outline.bounds

            onDrawWithContent {
                // This is actual content of the Composable that this modifier is assigned to
                drawContent()

                with(drawContext.canvas.nativeCanvas) {
                    val checkPoint = saveLayer(null, null)

                    // Destination

                    // We draw 2 times of the stroke with since we want actual size to be inside
                    // bounds while the outer stroke with is clipped with Modifier.clip

                    // 🔥 Using a maskPath with op(this, outline.path, PathOperation.Difference)
                    // And GenericShape can be used as Modifier.border does instead of clip
                    drawOutline(
                        outline = outline,
                        color = Color.Gray,
                        style = Stroke(strokeWidthPx * 2)
                    )

                    // Source
                    rotate(angle) {

                        drawCircle(
                            brush = brush(size),
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    restoreToCount(checkPoint)
                }
            }
        }
}


fun createBubbleShape(
    arrowWidth: Float,
    arrowHeight: Float,
    arrowOffset: Float,
    arrowDirection: ArrowDirection,
): GenericShape {

    return GenericShape { size: Size, layoutDirection: LayoutDirection ->

        val width = size.width
        val height = size.height

        when (arrowDirection) {
            ArrowDirection.Left -> {
                moveTo(arrowWidth, arrowOffset)
                lineTo(0f, arrowOffset)
                lineTo(arrowWidth, arrowHeight + arrowOffset)
                addRoundRect(
                    RoundRect(
                        rect = Rect(left = arrowWidth, top = 0f, right = width, bottom = height),
                        cornerRadius = CornerRadius(x = 20f, y = 20f)
                    )
                )
            }

            ArrowDirection.Right -> {
                moveTo(width - arrowWidth, arrowOffset)
                lineTo(width, arrowOffset)
                lineTo(width - arrowWidth, arrowHeight + arrowOffset)
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            left = 0f,
                            top = 0f,
                            right = width - arrowWidth,
                            bottom = height
                        ),
                        cornerRadius = CornerRadius(x = 20f, y = 20f)
                    )
                )
            }

            ArrowDirection.Top -> {
                moveTo(arrowOffset, arrowHeight)
                lineTo(arrowOffset + arrowWidth / 2, 0f)
                lineTo(arrowOffset + arrowWidth, arrowHeight)

                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            left = 0f,
                            top = arrowHeight,
                            right = width,
                            bottom = height
                        ),
                        cornerRadius = CornerRadius(x = 20f, y = 20f)
                    )
                )
            }

            else -> {
                moveTo(arrowOffset, height - arrowHeight)
                lineTo(arrowOffset + arrowWidth / 2, height)
                lineTo(arrowOffset + arrowWidth, height - arrowHeight)

                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            left = 0f,
                            top = 0f,
                            right = width,
                            bottom = height - arrowHeight
                        ),
                        cornerRadius = CornerRadius(x = 20f, y = 20f)
                    )
                )
            }
        }
    }

}

enum class ArrowDirection {
    Left, Right, Top, Bottom
}
