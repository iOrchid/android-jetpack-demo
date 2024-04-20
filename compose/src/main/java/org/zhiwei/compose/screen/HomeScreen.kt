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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.zhiwei.compose.model.TabPagerModel
import org.zhiwei.compose.model.configPageRoute
import org.zhiwei.compose.screen.basic.Basic_Screen
import org.zhiwei.compose.screen.gesture.GestureScreen
import org.zhiwei.compose.screen.graphics.GraphicsScreen
import org.zhiwei.compose.screen.layout.LayoutScreen
import org.zhiwei.compose.screen.state.StateScreen
import org.zhiwei.compose.screen.theme.ThemeScreen

/**
 * Compose的主页面UI的screen,
 * Compose的一个重要设计理念就是modifier修饰符的可复用，可传递
 * Compose的命名约定无返回值的函数，大写字母开头；有返回值的，小写字母开头；
 * 函数名可以是驼峰规则，也可以是_下划线风格；建议一般将自定义的compose界面UI使用_下划线风格命名，
 * 如此便于和系统的compose组件区分，方便使用.
 * ⚠️注，本项目演示代码，一般都会限定权限符，比如internal，private等。
 * 如果是自身业务项目需要public，那时候可能会出现多个重名的compose组件，所以才有如上建议。
 */
@Composable
internal fun Home_Screen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    //需要使用NavHost来创建管理导航页面的管理其配置
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable(route = "HomeScreen") {
            HomeScreenContent(modifier, navController = navController)
        }
        //配置所有pager下的页面，每个item对应页面 可导航的页面，需要使用composable来设置
        configPageRoute(modifier) { navController.navigateUp() }.forEach { model ->
            composable(route = model.title) {
                //model中ui的属性字段是个函数，需要invoke来调用
                model.ui()
            }
        }
    }
}

/**
 * 像此类private权限限制的compose的UI，命名可以不用_下划线风格，因为不太会在其他地方出现干扰
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(modifier: Modifier, navController: NavController) {
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
                TabPagerModel("基础组件") { Basic_Screen(navController = navController) },
                TabPagerModel("布局Layout") { LayoutScreen(navController) },
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
    Home_Screen(modifier = Modifier.background(Color(0XFFF2FDFF)))
}