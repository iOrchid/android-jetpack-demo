package org.zhiwei.compose.screen.gesture

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.PhotoBanner
import org.zhiwei.compose.ui.widget.PhotoBannerItemData
import org.zhiwei.compose.ui.widget.PhotoCarrousel
import org.zhiwei.compose.ui.widget.PicFlowWithAnimation

/**
 * 简单的演示一些自定义的compose的UI组件的效果
 */
@Composable
internal fun PhotoFlow_Screen(modifier: Modifier = Modifier) {
    //卡片流效果
    PicFlowWithAnimation(
        Modifier.fillMaxSize(),
        iconUrl = "https://img.keaitupian.cn/uploads/2020/07/27/vtba42j0ldr.jpg"
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PhotoBanner_Screen(modifier: Modifier = Modifier) {
    //水平照片banner的效果，这里可以传url，也可以是drawable
    PhotoBanner(
        photos = listOf(
            PhotoBannerItemData(R.drawable.banner_001),
            PhotoBannerItemData("https://img.keaitupian.cn/uploads/2020/07/27/vtba42j0ldr.jpg"),
            PhotoBannerItemData(R.drawable.banner_003),
            PhotoBannerItemData(R.drawable.banner_004),
            PhotoBannerItemData("https://img.keaitupian.cn/uploads/2020/07/27/vtba42j0ldr.jpg"),
            PhotoBannerItemData(R.drawable.banner_006),
            PhotoBannerItemData(R.drawable.banner_007),
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PhotoCarrousel_Screen(modifier: Modifier = Modifier) {
    //水平照片banner的效果，这里可以传url，也可以是drawable
    PhotoCarrousel(
        photos = listOf(
            PhotoBannerItemData(R.drawable.banner_001),
            PhotoBannerItemData("https://img.keaitupian.cn/uploads/2020/07/27/vtba42j0ldr.jpg"),
            PhotoBannerItemData(R.drawable.banner_003),
            PhotoBannerItemData(R.drawable.banner_004),
            PhotoBannerItemData("https://img.keaitupian.cn/uploads/2020/07/27/vtba42j0ldr.jpg"),
            PhotoBannerItemData(R.drawable.banner_006),
            PhotoBannerItemData(R.drawable.banner_007),
        )
    ) { index ->
        Log.d("Compose", "PhotoCarrousel_Screen: 点击了某个item $index")
    }
}