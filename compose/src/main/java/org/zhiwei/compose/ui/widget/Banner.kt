package org.zhiwei.compose.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import org.zhiwei.compose.R
import kotlin.math.absoluteValue

//region 普通的图片式的pager banner
/**
 * 照片banner的组件，水平方式的，竖直的类似
 * @param modifier 修饰符
 * @param photos 照片列表list
 * @param pagerState 对外提供banner内部的一些必要状态，比如获取当前页面，之类
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoBanner(
    modifier: Modifier = Modifier,
    photos: List<PhotoBannerItemData>,
    pagerState: PagerState = rememberPagerState { photos.count() },
) {

    Box(modifier) {
        //pager
        HorizontalPager(
            //2. 在需要的控件上，关联到定义的id
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
        ) { page ->
            //便与预览添加判断效果
            if (isInPreview) {
                Image(
                    painter = painterResource(id = R.drawable.picture_banner_center),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                //此处使用的是coil的库提供的image，可以支持url加载
                AsyncImage(
                    model = photos[page].photo,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F7FB))
                )
            }
        }
        //指示器
        Row(
            Modifier
                .align(Alignment.BottomStart)
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(photos.count()) { index ->
                val currentIndicator = Modifier
                    .padding(1.dp)
                    .width(10.dp)
                    .height(4.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(2.dp))
                val unSelectedIndicator = Modifier
                    .size(4.dp)
                    .background(color = Color(0x66FFFFFF), shape = RoundedCornerShape(2.dp))
                Box(modifier = if (pagerState.currentPage == index) currentIndicator else unSelectedIndicator)
                Spacer(modifier = Modifier.size(4.dp))
            }
        }
    }
}

/**
 * PhotoBanner的item的数据
 * @param photo 图片的资源，可能是drawable的id，可以是url或者path路径
 * 因为内部使用了coil的compose的图片组件来加载
 */
data class PhotoBannerItemData(val photo: Any?)

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewPictureBanner() {
    PhotoBanner(
        photos = listOf(
            PhotoBannerItemData(R.drawable.banner_001),
            PhotoBannerItemData(R.drawable.banner_002),
            PhotoBannerItemData(R.drawable.banner_003),
            PhotoBannerItemData(R.drawable.banner_004),
            PhotoBannerItemData(R.drawable.banner_005),
            PhotoBannerItemData(R.drawable.banner_006),
            PhotoBannerItemData(R.drawable.banner_007),
        )
    )
}

//endregion


//region 照片banner，类似于旋转木马的效果

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoCarrousel(
    modifier: Modifier = Modifier,
    photos: List<PhotoBannerItemData>,
    pagerState: PagerState = if (isInPreview) rememberPagerState(photos.count() / 2) { photos.count() } else rememberPagerState(
        photos.count() / 2
    ) { photos.count() },
    onPageClick: (Int) -> Unit,
) {

    //region 添加触感震动效果
    var currentPageIndex by remember { mutableIntStateOf(0) }
    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { currentPage ->
            if (currentPageIndex != currentPage) {
                hapticFeedback.performHapticFeedback(
                    hapticFeedbackType = HapticFeedbackType.LongPress,
                )
                currentPageIndex = currentPage
            }
        }
    }
    //endregion

    Box(modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 50.dp, vertical = 15.dp),
            pageSize = PageSize.Fill,
            beyondBoundsPageCount = PagerDefaults.BeyondBoundsPageCount,
            pageSpacing = (-90).dp,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState)
        ) { page ->

            /**
             *卡片切换效果见[url](https://github.com/google/accompanist/blob/main/sample/src/main/java/com/google/accompanist/sample/pager/HorizontalPagerTransitionSample.kt)
             */
            Box(
                Modifier
                    .width(282.dp)
                    .height(376.dp)
                    .graphicsLayer {
                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerpFloat(pagerState, page, .85f).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        // We animate the alpha, between 50% and 100%
                        alpha = lerpFloat(pagerState, page, .5f)
                    }
                    .shadow(5.dp, RoundedCornerShape(15.dp))
                    .zIndex(lerpFloat(pagerState, page, .5f))
            ) {
                //便与预览添加判断效果
                if (isInPreview) {
                    Image(
                        painter = painterResource(id = R.drawable.banner_007),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(5.dp, RoundedCornerShape(15.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                } else {
                    //此处使用的是coil的库提供的image，可以支持url加载
                    AsyncImage(
                        model = photos[page].photo,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F7FB))
                            .shadow(5.dp, RoundedCornerShape(15.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                }

                IconButton(
                    onClick = { onPageClick(page) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Image(
                        imageVector = Icons.Filled.Fullscreen,
                        contentDescription = null,
                    )
                }

            }
        }

        //指示器
        Row(
            Modifier
                .height(5.dp)
                .align(Alignment.BottomCenter)
        ) {
            repeat(pagerState.pageCount) {
                Box(
                    Modifier
                        .size(5.dp)
                        .background(
                            color = if (pagerState.currentPage == it) Color.Gray else Color(
                                0xFFE3E3E3
                            ),
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.size(5.dp))
            }
        }

    }
}

/**
 * 使用勒普函数计算出变化值
 */
@OptIn(ExperimentalFoundationApi::class)
private fun lerpFloat(
    pagerState: PagerState,
    page: Int,
    lerpStart: Float,
    lerpEnd: Float = 1f,
): Float {
    //根据滑动，计算当前页面的偏移量绝对值
    val pageOffset =
        calculateCurrentOffsetForPage(pagerState, page).absoluteValue
    return lerp(
        start = lerpStart,
        stop = lerpEnd,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
}

@OptIn(ExperimentalFoundationApi::class)
private fun calculateCurrentOffsetForPage(state: PagerState, page: Int): Float {
    return (state.currentPage - page) + state.currentPageOffsetFraction
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewPictureCarrousel() {
    PhotoCarrousel(
        photos = listOf(
            PhotoBannerItemData(R.drawable.banner_001),
            PhotoBannerItemData(R.drawable.banner_002),
            PhotoBannerItemData(R.drawable.banner_003),
            PhotoBannerItemData(R.drawable.banner_004),
            PhotoBannerItemData(R.drawable.banner_005),
            PhotoBannerItemData(R.drawable.banner_006),
            PhotoBannerItemData(R.drawable.banner_007),
        ), onPageClick = {}
    )
}

//endregion