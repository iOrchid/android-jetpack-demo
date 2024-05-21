package org.zhiwei.compose.screen.graphics

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Sub_Text

@Composable
fun NeonGlowEffect_Screen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        Title_Sub_Text(
            title = "Use **paint.asFrameworkPaint()** to create blur effect to mimic neon glow" +
                    "and infinite animation to dim and glow infinitely"
        )
        NeonSample()
        Title_Sub_Text(title = "Use touch events to draw path with neon glow.")
        Column(Modifier.fillMaxWidth()) {
            NeonDrawingSample()
        }
    }
}


@Composable
private fun NeonSample() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color.Black)
    ) {

        val paint = remember {
            Paint().apply {
                style = PaintingStyle.Stroke
                strokeWidth = 30f
            }
        }

        val frameworkPaint = remember {
            paint.asFrameworkPaint()
        }

        val color = Color.Red

        val transition: InfiniteTransition = rememberInfiniteTransition(label = "")

        // Infinite phase animation for PathEffect
        val phase by transition.animateFloat(
            initialValue = .9f,
            targetValue = .3f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1500,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )


        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            this.drawIntoCanvas {

                val transparent = color
                    .copy(alpha = 0f)
                    .toArgb()

                frameworkPaint.color = transparent

                paint.asFrameworkPaint().setShadowLayer(
                    30f * phase,
                    0f,
                    0f,
                    color
                        .copy(alpha = phase)
                        .toArgb()
                )

                it.drawRoundRect(
                    left = 100f,
                    top = 100f,
                    right = 500f,
                    bottom = 500f,
                    radiusX = 5.dp.toPx(),
                    5.dp.toPx(),
                    paint = paint
                )

                drawRoundRect(
                    Color.White,
                    topLeft = Offset(100f, 100f),
                    size = Size(400f, 400f),
                    cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )


                frameworkPaint.setShadowLayer(
                    30f,
                    0f,
                    0f,
                    color
                        .copy(alpha = .5f)
                        .toArgb()
                )


                it.drawRoundRect(
                    left = 600f,
                    top = 100f,
                    right = 1000f,
                    bottom = 500f,
                    radiusX = 5.dp.toPx(),
                    5.dp.toPx(),
                    paint = paint
                )

                drawRoundRect(
                    Color.White,
                    topLeft = Offset(600f, 100f),
                    size = Size(400f, 400f),
                    cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}

@Composable
private fun NeonDrawingSample() {

    var motionEvent by remember { mutableStateOf(NeonMotionEvent.Idle) }
    // This is our motion event we get from touch motion
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    // This is previous motion event before next touch is saved into this current position
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }


    val transition: InfiniteTransition = rememberInfiniteTransition(label = "")

    // Infinite phase animation for PathEffect
    val phase by transition.animateFloat(
        initialValue = .9f,
        targetValue = .6f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val color = Color.Magenta

    val paint = remember {
        Paint().apply {
            style = PaintingStyle.Stroke
            strokeWidth = 20f
            strokeCap = StrokeCap.Round

            this.asFrameworkPaint().apply {
                val transparent = color
                    .copy(alpha = 0f)
                    .toArgb()

                this.color = transparent
            }
        }
    }

    paint.asFrameworkPaint().setShadowLayer(
        35f * phase,
        0f,
        0f,
        color
            .copy(alpha = phase)
            .toArgb()
    )

    // Path is what is used for drawing line on Canvas
    val path = remember { Path() }

    val drawModifier = Modifier
        .background(Color.Black)
        .fillMaxSize()
        .clipToBounds()
        .pointerMotionEvents(
            onDown = { pointerInputChange: PointerInputChange ->
                currentPosition = pointerInputChange.position
                motionEvent = NeonMotionEvent.Down
                pointerInputChange.consume()
            },
            onMove = { pointerInputChange: PointerInputChange ->
                currentPosition = pointerInputChange.position
                motionEvent = NeonMotionEvent.Move
                pointerInputChange.consume()
            },
            onUp = { pointerInputChange: PointerInputChange ->
                motionEvent = NeonMotionEvent.Up
                pointerInputChange.consume()
            },
            delayAfterDownInMillis = 25L
        )

    Canvas(modifier = drawModifier) {
        when (motionEvent) {
            NeonMotionEvent.Down -> {
                path.moveTo(currentPosition.x, currentPosition.y)
                previousPosition = currentPosition
            }

            NeonMotionEvent.Move -> {
                path.quadraticBezierTo(
                    previousPosition.x,
                    previousPosition.y,
                    (previousPosition.x + currentPosition.x) / 2,
                    (previousPosition.y + currentPosition.y) / 2

                )

                previousPosition = currentPosition
            }

            NeonMotionEvent.Up -> {
                path.lineTo(currentPosition.x, currentPosition.y)
                currentPosition = Offset.Unspecified
                previousPosition = currentPosition
                motionEvent = NeonMotionEvent.Idle
            }

            else -> Unit
        }

        this.drawIntoCanvas {

            it.drawPath(path, paint)

            drawPath(
                color = Color.White.copy((0.7f + phase).coerceAtMost(1f)),
                path = path,
                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}

enum class NeonMotionEvent {
    Idle, Down, Move, Up
}

@Preview
@Composable
private fun PreviewNeonGlowEffect() {
    NeonGlowEffect_Screen()
}