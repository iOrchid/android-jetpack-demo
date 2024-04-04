package org.zhiwei.compose.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.zhiwei.compose.model.TabPagerModel
import org.zhiwei.compose.screen.basic.BasicScreen
import org.zhiwei.compose.screen.gesture.GestureScreen
import org.zhiwei.compose.screen.graphics.GraphicsScreen
import org.zhiwei.compose.screen.layout.LayoutScreen
import org.zhiwei.compose.screen.state.StateScreen
import org.zhiwei.compose.screen.theme.ThemeScreen

/**
 * Compose的主页面UI的screen
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    //使用Column列容器，存放本页内容
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        //Text是文本控件，标题和说明
        Text(
            text = "Compose Study Notes", modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = Color.DarkGray, fontSize = 26.sp,
            fontStyle = FontStyle.Italic, fontFamily = FontFamily.Cursive,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Compose是用于Android的新式 声明性 可组合函数、动态内容、解构重组,界面工具包。便于预览UI界面，编写和维护界面更加容易。",
            color = Color.Gray, fontSize = 14.sp,
            fontStyle = FontStyle.Italic, fontFamily = FontFamily.Serif,
        )
        val tabPagerModels =
            listOf(
                TabPagerModel("基础组件") { BasicScreen() },
                TabPagerModel("布局Layout") { LayoutScreen() },
                TabPagerModel("状态State") { StateScreen() },
                TabPagerModel("手势Gesture") { GestureScreen() },
                TabPagerModel("图像Graphics") { GraphicsScreen() },
                TabPagerModel("Theme主题") { ThemeScreen() },
            )
        //这个是用于记录tabRow和Pager的状态，用于tab和pager的联动，后续会学到。
        val pagerState: PagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) { tabPagerModels.size }
        val coroutineScope = rememberCoroutineScope()
        //tabRow，类似于传统view里的tabLayout
        // 类别的tabLayout,必须同时出现Pager且配置PagerState，才能有点击切换的效果，
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            edgePadding = 8.dp,
        ) {
            // compose很大的一个优势，就是可以代码化，高阶语法的形式来实现UI的逻辑
            tabPagerModels.forEachIndexed { index, model ->
                Tab(
                    text = { Text(model.title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
        //viewPager，用于关联上面的tabLayout
        HorizontalPager(state = pagerState) { page ->
            //注意此处model的page是个compose的函数，需要调用invoke或者函数体才能生效
            tabPagerModels[page].page()
        }
    }

}

/**
 * 预览界面，仅作用域AndroidStudio的编辑预览，配置参数可与实际不同，所以具体运行效果要看实际配置
 */
@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(modifier = Modifier.background(Color(0XFFE6D2D5)))
}