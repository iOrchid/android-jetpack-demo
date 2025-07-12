package org.zhiwei.compose.screen.graphics

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Text

@Composable
internal fun BeforeAfterScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Title_Text("BeforeAfter Layout")
        BeforeAfterSample()
    }
}

@Preview
@Composable
private fun BeforeAfterSample() {

    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BeforeAfterLayoutWithBlendMode(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .size(240.dp),
            progress = { progress },
            beforeLayout = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.avatar01),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            },
            afterLayout = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.avatar02),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        BeforeAfterLayoutWithBlendMode(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(.8f)
                .aspectRatio(16 / 9f),
            progress = { progress },
            beforeLayout = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.avatar02),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            },
            afterLayout = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.avatar01),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        BeforeAfterLayoutWithBlendMode(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(.6f)
                .aspectRatio(1f),
            progress = { progress },
            beforeLayout = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.avatar01),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            },
            afterLayout = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.avatar02),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
        )
    }
}

@Composable
fun BeforeAfterLayoutWithBlendMode(
    modifier: Modifier = Modifier,
    progress: () -> Float,
    beforeLayout: @Composable BoxScope.() -> Unit,
    afterLayout: @Composable BoxScope.() -> Unit,
) {
    Box(modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
        ) {
            beforeLayout()
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                    alpha = progress()
                }
        ) {
            afterLayout()
        }
    }
}

@Preview
@Composable
private fun PreviewBeforeAfter() {
    BeforeAfterScreen()
}