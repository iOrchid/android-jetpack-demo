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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import kotlin.math.sqrt


@Composable
internal fun ShimmerEffect_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Title_Sub_Text(
            "Items that control their animation State. " +
                    "When a new item enters composition its progress is restarted."
        )
        LazyColumn(
            modifier = Modifier.height(300.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(50) {
                ShimmerRow()
            }
        }

        val transition = rememberInfiniteTransition(label = "shimmer")
        val progress by transition.animateFloat(
            initialValue = -2f,
            targetValue = 2f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3000,
                )
            ), label = "shimmer"
        )

        Title_Sub_Text(
            "Items that get animation progress from one controller. Since " +
                    "these items get progress from single Controller they are always synced",
        )

        LazyColumn(
            modifier = Modifier.height(300.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(50) {
                ShimmerRow(progress)
            }
        }
        Title_Sub_Text(title = "演示折扣标签label")
        Image(
            painter = painterResource(id = R.drawable.moto_run),
            contentDescription = null,
            Modifier
                .fillMaxWidth()
                .aspectRatio(4 / 3f)
                .then(Modifier.drawDiagonalShimmerLabel("40% OFF", Color.Green)),
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(id = R.drawable.moto_run),
            contentDescription = null,
            Modifier
                .fillMaxWidth()
                .aspectRatio(4 / 3f)
                .then(Modifier.drawDiagonalLabel("50%", Color.Red)),
            contentScale = ContentScale.FillBounds
        )
    }
}


@Composable
private fun ShimmerRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmer()
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth(.3f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer()

            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer()

            )

        }
    }
}

@Composable
private fun ShimmerRow(progress: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .drawShimmer(progress)
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth(.3f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .drawShimmer(progress)

            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .drawShimmer(progress)
            )
        }
    }
}

fun Modifier.shimmer(
    durationMillis: Int = 2500,
) = composed {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val progress by transition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
            )
        ), label = "shimmer"
    )
    drawShimmer(progress)
}

fun Modifier.drawShimmer(progress: Float) = this.then(

    Modifier
        .drawWithContent {

            val width = size.width
            val height = size.height
            val offset = progress * width

            drawContent()
            val brush = Brush.linearGradient(
                colors = listOf(
                    Color.LightGray,
                    Color.LightGray,
                    Color.White,
                    Color.LightGray,
                    Color.LightGray
                ),
                start = Offset(offset, 0f),
                end = Offset(offset + width, height)
            )
            drawRect(brush)
        }
)


fun Modifier.drawDiagonalLabel(
    text: String,
    color: Color,
    style: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    ),
    labelTextRatio: Float = 7f,
) = composed(
    factory = {

        val textMeasurer = rememberTextMeasurer()
        val textLayoutResult: TextLayoutResult = remember {
            textMeasurer.measure(text = AnnotatedString(text), style = style)
        }


        this
            .clipToBounds()
            .drawWithContent {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val textSize = textLayoutResult.size
                val textWidth = textSize.width
                val textHeight = textSize.height

                val rectWidth = textWidth * labelTextRatio
                val rectHeight = textHeight * 1.1f

                val rect = Rect(
                    offset = Offset(canvasWidth - rectWidth, 0f),
                    size = Size(rectWidth, rectHeight)
                )

                val sqrt = sqrt(rectWidth / 2f)
                val translatePos = sqrt * sqrt

                drawContent()
                withTransform(
                    {
                        rotate(
                            degrees = 45f,
                            pivot = Offset(
                                canvasWidth - rectWidth / 2,
                                translatePos
                            )
                        )
                    }
                ) {
                    drawRect(
                        color = color,
                        topLeft = rect.topLeft,
                        size = rect.size
                    )
                    drawText(
                        textMeasurer = textMeasurer,
                        text = text,
                        style = style,
                        topLeft = Offset(
                            rect.left + (rectWidth - textWidth) / 2f,
                            rect.top + (rect.bottom - textHeight) / 2f
                        )
                    )
                }

            }
    }
)


fun Modifier.drawDiagonalShimmerLabel(
    text: String,
    color: Color,
    style: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    ),
    labelTextRatio: Float = 7f,
) = composed(
    factory = {

        val textMeasurer = rememberTextMeasurer()
        val textLayoutResult: TextLayoutResult = remember {
            textMeasurer.measure(text = AnnotatedString(text), style = style)
        }

        val transition = rememberInfiniteTransition(label = "")

        val progress by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        this
            .clipToBounds()
            .drawWithContent {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val textSize = textLayoutResult.size
                val textWidth = textSize.width
                val textHeight = textSize.height

                val rectWidth = textWidth * labelTextRatio
                val rectHeight = textHeight * 1.1f

                val rect = Rect(
                    offset = Offset(canvasWidth - rectWidth, 0f),
                    size = Size(rectWidth, rectHeight)
                )

                val sqrt = sqrt(rectWidth / 2f)
                val translatePos = sqrt * sqrt

                val brush = Brush.linearGradient(
                    colors = listOf(
                        color,
                        style.color,
                        color,
                    ),
                    start = Offset(progress * canvasWidth, progress * canvasHeight),
                    end = Offset(
                        x = progress * canvasWidth + rectHeight,
                        y = progress * canvasHeight + rectHeight
                    ),
                )

                drawContent()
                withTransform(
                    {
                        rotate(
                            degrees = 45f,
                            pivot = Offset(
                                canvasWidth - rectWidth / 2,
                                translatePos
                            )
                        )
                    }
                ) {
                    drawRect(
                        brush = brush,
                        topLeft = rect.topLeft,
                        size = rect.size
                    )
                    drawText(
                        textMeasurer = textMeasurer,
                        text = text,
                        style = style,
                        topLeft = Offset(
                            rect.left + (rectWidth - textWidth) / 2f,
                            rect.top + (rect.bottom - textHeight) / 2f
                        )
                    )
                }

            }
    }
)

