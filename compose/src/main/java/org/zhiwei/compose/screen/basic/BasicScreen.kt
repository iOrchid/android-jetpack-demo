package org.zhiwei.compose.screen.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.zhiwei.compose.model.CourseItemModel

/**
 * 基础学习笔记
 */
@Composable
internal fun BasicScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    //需要使用NavHost来创建管理导航页面的管理其配置
    NavHost(navController = navController, startDestination = "BasicScreenContent") {
        composable(route = "BasicScreenContent") {
            BasicScreenContent(navController = navController)
        }

        //每个可导航的页面，需要使用composable来设置
        basicCourses.forEach { model ->
            composable(route = model.title) {
                //model中ui的属性字段是个函数，需要invoke来调用
                model.ui()
            }
        }
    }

}

//所有基础内容的list
private val basicCourses = listOf(
    CourseItemModel(
        "Column,Rom,Box,Modifiers",
        "列，行，箱，都是容器，顾名思义就是成列，成行和层叠摆放内部子控件；及修饰符Modifier内外边距等基本使用。"
    ) { BoxColumnRowScreen() },
    CourseItemModel(
        "Surface，Shape，Clickable",
        "面板，图形，绘制背景，点击交互，偏移，权重等。"
    ) { SurfaceShapeClickableScreen() },
    //下面主要是Material3提供的控件功能，material的也有类似，不做更多比较。
    CourseItemModel(
        "Text",
        "Material3的text文本控件，以及字号、颜色、字体、字重、样式等文本相关属性的设置。"
    ) { BoxColumnRowScreen() },
    CourseItemModel(
        "Button",
        "主要演示文本按钮，图标按钮，悬浮按钮或标签🏷️按钮的使用，及其属性设置。"
    ) { BoxColumnRowScreen() },
    CourseItemModel(
        "TextField",
        "演示文本输入框的样式配置，颜色，状态，错误提示和输入显示和输入法联动等设置。"
    ) { BoxColumnRowScreen() },
    CourseItemModel(
        "Image",
        "创建图片展示控件，演示显示方式，裁剪图形和颜色过滤等属性用法。"
    ) { BoxColumnRowScreen() },
    CourseItemModel(
        "LazyColumn",
        "可以理解为简版的类似于传统RecyclerView的compose的，可加载多个列表的滑动式组件，演示滑动控制，数据变更，悬浮标题等。"
    ) { BoxColumnRowScreen() },
    CourseItemModel(
        "LazyRow",
        "类似于上面的LazyColumn，这个是水平的行排列的容器控件。"
    ) { BoxColumnRowScreen() },
    CourseItemModel(
        "LazyVirtualGrid",
        "竖直方向的网格布局容器，水平的也是类似，主要看属性和用法的演示。"
    ) { BoxColumnRowScreen() },
    CourseItemModel(
        "ListItem",
        "Compose直接提供的，可用于简便设置条目的实现，有图标，单行，多行，副标题，按钮等。"
    ) { BoxColumnRowScreen() },
    CourseItemModel(
        "LazyListLayoutInfo",
        "使用LazyLayoutInfo来获取LazyColumn/LazyRow的一些元数据。"
    ) { BoxColumnRowScreen() },
)

/**
 * BasicScreen的主页面
 */
@Composable
private fun BasicScreenContent(navController: NavController) {

    //外部使用列容器LazyColumn，里面就是代码形式便捷创建多个列;LazyColumn就是类似与传统RecyclerView的列容器 ，
    // 如果多个item超出了屏幕，可实现滑动，而且不用代码手动些什么ViewHolder之类的性能管理缓存。
    LazyColumn {
        //后续会详细学习，LazyColumn内部排列多个item的时候，接收items的数据源，而后是每个item的Compose布局实现
        items(basicCourses) { model ->
            //每个item这里使用了ListItem，框架提供的简便的一个控件
            ListItem(
                modifier = Modifier
                    .wrapContentHeight()
                    //modifier的点击属性，使用navController通过route跳转
                    .clickable { navController.navigate(model.title) },
                headlineContent = { Text(text = model.title, fontSize = 14.sp) },
                supportingContent = { Text(text = model.description, fontSize = 12.sp) },
                colors = ListItemDefaults.colors(containerColor = Color(0XFFF9E8D0))
            )
            HorizontalDivider()
        }
    }
}


/**
 * 预览界面，仅作用域AndroidStudio的编辑预览，配置参数可与实际不同，所以具体运行效果要看实际配置
 */
@Preview
@Composable
private fun BasicScreenPreview() {
    BasicScreen(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}