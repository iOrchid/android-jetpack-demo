package org.zhiwei.compose.model

import androidx.compose.ui.Modifier
import org.zhiwei.compose.screen.basic.Box_Column_Row_Screen
import org.zhiwei.compose.screen.basic.Surface_Shape_Clickable_Screen
import org.zhiwei.compose.screen.basic.material3.Button_Screen
import org.zhiwei.compose.screen.basic.material3.TextField_Screen
import org.zhiwei.compose.screen.basic.material3.Text_Screen

/**
 * 用于配置整个Compose模块内所有可跳转的页面UI，用于Navigation导航
 */

//region basicScreen内
internal object BasicScreenUIs {
    //所有基础内容的list
    internal fun basicCourses(modifier: Modifier = Modifier) = listOf(
        CourseItemModel(
            "Column,Rom,Box,Modifiers",
            "列，行，箱，都是容器，顾名思义就是成列，成行和层叠摆放内部子控件；及修饰符Modifier内外边距等基本使用。"
        ) { Box_Column_Row_Screen(modifier) },
        CourseItemModel(
            "Surface，Shape，Clickable",
            "面板，图形，绘制背景，点击交互，偏移，权重等。"
        ) { Surface_Shape_Clickable_Screen(modifier) },
        //下面主要是Material3提供的控件功能，material的也有类似，不做更多比较。
        CourseItemModel(
            "Text",
            "Material3的text文本控件，以及字号、颜色、字体、字重、样式等文本相关属性的设置。"
        ) { Text_Screen(modifier) },
        CourseItemModel(
            "Button",
            "主要演示文本按钮，图标按钮，悬浮按钮或标签🏷️按钮的使用，及其属性设置。"
        ) { Button_Screen(modifier) },
        CourseItemModel(
            "TextField",
            "演示文本输入框的样式配置，颜色，状态，错误提示和输入显示和输入法联动等设置。"
        ) { TextField_Screen(modifier) },
        CourseItemModel(
            "Image",
            "创建图片展示控件，演示显示方式，裁剪图形和颜色过滤等属性用法。"
        ) { Box_Column_Row_Screen(modifier) },
        CourseItemModel(
            "LazyColumn",
            "可以理解为简版的类似于传统RecyclerView的compose的，可加载多个列表的滑动式组件，演示滑动控制，数据变更，悬浮标题等。"
        ) { Box_Column_Row_Screen(modifier) },
        CourseItemModel(
            "LazyRow",
            "类似于上面的LazyColumn，这个是水平的行排列的容器控件。"
        ) { Box_Column_Row_Screen(modifier) },
        CourseItemModel(
            "LazyVirtualGrid",
            "竖直方向的网格布局容器，水平的也是类似，主要看属性和用法的演示。"
        ) { Box_Column_Row_Screen(modifier) },
        CourseItemModel(
            "ListItem",
            "Compose直接提供的，可用于简便设置条目的实现，有图标，单行，多行，副标题，按钮等。"
        ) { Box_Column_Row_Screen(modifier) },
        CourseItemModel(
            "LazyListLayoutInfo",
            "使用LazyLayoutInfo来获取LazyColumn/LazyRow的一些元数据。"
        ) { Box_Column_Row_Screen(modifier) },
    )
}

//endregion
