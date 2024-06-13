package org.zhiwei.compose.ui.widget

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import org.zhiwei.compose.R
import kotlin.random.Random

@Preview("组合式的照片流", heightDp = 600)
@Composable
fun PicFlowWithAnimation(
    modifier: Modifier = Modifier,
    iconUrl: String = "http://pic.predream.org/2014/0920/20140920102816869.jpg",
) {

    Box(modifier.fillMaxWidth()) {

        PicFlow(Modifier.matchParentSize())

        Box(
            modifier = Modifier
                .fillMaxWidth(.656f)
                .aspectRatio(0.75f)
                .shadow(
                    10.dp,
                    RoundedCornerShape(10.dp),
                    spotColor = Color.Gray
                )
                .padding(5.dp)
                .align(Alignment.Center)
        ) {

            Image(
                painter = if (isInPreview) painterResource(id = R.drawable.banner_002) else rememberAsyncImagePainter(
                    model = iconUrl
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(10.dp))
            )
            AsyncImage(
                model = R.drawable.anim_shimmer,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }

}

/**
 * 此处演示的，结合上面作为背景使用，其实也可以单独使用
 */
@Preview("照片流")
@Composable
private fun PicFlow(modifier: Modifier = Modifier) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        val list = listOf(
            R.drawable.banner_001 to Modifier.height(200.dp),
            R.drawable.banner_007 to Modifier.height(120.dp),
            R.drawable.banner_008 to Modifier.height(120.dp),
            R.drawable.banner_003 to Modifier.height(130.dp),
            R.drawable.banner_006 to Modifier.height(130.dp),
            R.drawable.picture_banner_center to Modifier.height(260.dp),
            R.drawable.banner_002 to Modifier.height(200.dp),
            R.drawable.banner_009 to Modifier.height(168.dp),
            R.drawable.banner_004 to Modifier.height(135.dp),
            R.drawable.banner_005 to Modifier.height(125.dp),
            R.drawable.picture_banner_center to Modifier.height(165.dp),
        )
        //随机的流动方向，中间与两边相反
        val reverse = Random.nextBoolean()
        PicFlowColumn(
            Modifier.weight(1f),
            list.shuffled(),
            Random.nextInt(5, 12) to Random.nextInt(15, 25),
            reverse,
            Random.nextInt(400, 2000)
        )
        PicFlowColumn(
            Modifier.weight(1f),
            list.shuffled(),
            Random.nextInt(5, 12) to Random.nextInt(15, 25),
            !reverse,
            Random.nextInt(500, 2000)
        )
        PicFlowColumn(
            Modifier.weight(1f),
            list.shuffled(),
            Random.nextInt(5, 12) to Random.nextInt(15, 25),
            reverse,
            Random.nextInt(400, 2000)
        )
    }
}

/**
 * 每一列的照片流
 */
@Composable
private fun PicFlowColumn(
    modifier: Modifier = Modifier,
    list: List<Pair<Int, Modifier>>,
    speedPair: Pair<Int, Int>,
    reverse: Boolean = false,
    durationMillis: Int,
) {
    val state = rememberLazyListState()

    val animateFloat by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = speedPair.first.toFloat(),
        targetValue = speedPair.second.toFloat(),
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    LaunchedEffect(key1 = animateFloat) {
        state.scrollBy(animateFloat)
    }

    LazyColumn(modifier, state, userScrollEnabled = false, reverseLayout = reverse) {
        items(Int.MAX_VALUE) { index ->
            val (drawableRes, imgModifier) = list[index.floorMod(list.size)]
            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(imgModifier)
                    .clip(RoundedCornerShape(10.dp)),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

/**
 * 官方demo的一个计算取模的优化写法
 */
private fun Int.floorMod(other: Int) = when {
    other < 0 -> this
    else -> this - floorDiv(other) * other
}
