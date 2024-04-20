package org.zhiwei.compose.model

import androidx.compose.ui.Modifier
import org.zhiwei.compose.screen.basic.Box_Column_Row_Screen
import org.zhiwei.compose.screen.basic.Surface_Shape_Clickable_Screen
import org.zhiwei.compose.screen.basic.material3.BottomBar_Screen
import org.zhiwei.compose.screen.basic.material3.BottomSheet_Screen
import org.zhiwei.compose.screen.basic.material3.Button_Screen
import org.zhiwei.compose.screen.basic.material3.Image_Screen
import org.zhiwei.compose.screen.basic.material3.LazyGrid_Screen
import org.zhiwei.compose.screen.basic.material3.LazyList_Screen
import org.zhiwei.compose.screen.basic.material3.ListItem_Screen
import org.zhiwei.compose.screen.basic.material3.SideDrawer_Screen
import org.zhiwei.compose.screen.basic.material3.SwipeDialog_Screen
import org.zhiwei.compose.screen.basic.material3.TextField_Screen
import org.zhiwei.compose.screen.basic.material3.Text_Screen
import org.zhiwei.compose.screen.basic.material3.TopAppbarTabs_Screen
import org.zhiwei.compose.screen.basic.material3.Widget_Screen
import org.zhiwei.compose.screen.layout.UI_CustomModifier

/**
 * 用于配置整个Compose模块内所有可跳转的页面UI，用于Navigation导航
 */
internal fun configPageRoute(modifier: Modifier, onBack: (() -> Unit) = {}): List<CourseItemModel> {
    val list = mutableListOf<CourseItemModel>()
    list.addAll(BasicScreenUIs.basicCourses(modifier, onBack))
    list.addAll(LayoutScreenUIs.layoutCourses(modifier))
    return list
}


//region basicScreen基础控件

internal object BasicScreenUIs {
    //所有基础内容的list，⚠️todo 除了要用于填充页面，还要在上面添加到list中，注册页面导航route
    internal fun basicCourses(modifier: Modifier = Modifier, onBack: (() -> Unit) = {}) = listOf(
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
        ) { Image_Screen(modifier) },
        CourseItemModel(
            "LazyColumn/LazyRow",
            "可以理解为简版的类似于传统RecyclerView的compose的，可加载多个列表的滑动式组件，演示滑动控制，数据变更，悬浮标题等。"
        ) { LazyList_Screen(modifier) },
        CourseItemModel(
            "LazyGrid",
            "网格布局容器，水平/竖直，还有瀑布流方式的，主要看属性和用法的演示。"
        ) { LazyGrid_Screen(modifier) },
        CourseItemModel(
            "ListItem",
            "Compose直接提供的，可用于简便设置条目的实现，有图标，单行，多行，副标题，按钮等。"
        ) { ListItem_Screen(modifier) },
        CourseItemModel(
            "TopAppbar&Tabs",
            "material3库中的top AppBar和Tabs相关控件的演示。"
        ) { TopAppbarTabs_Screen(modifier, onBack) },
        CourseItemModel(
            "BottomBar",
            "BottomNavigationView和BottomAppBar的compose控件的演示。"
        ) { BottomBar_Screen(modifier) },
        CourseItemModel(
            "Side Navigation Vs ModalDrawer",
            "侧边栏SideDrawer，还有ModelDrawer的控件演示。"
        ) { SideDrawer_Screen(modifier) },
        CourseItemModel(
            "BottomSheet",
            "BottomSheet，底部sheet控件,还有BottomDrawer的演示。"
        ) { BottomSheet_Screen(modifier) },
        CourseItemModel(
            "OtherWidget",
            "提示浮窗SnackBar，进度条Progress，勾选框CheckBox，开关Switch，Slider的使用演示。"
        ) { Widget_Screen(modifier) },
        CourseItemModel(
            "SwipeDialog",
            "列表中的swipe滑动操作，还有结合checkBox记录状态，以及Dialog的弹窗的使用。"
        ) { SwipeDialog_Screen(modifier) },
    )
}

//endregion

//region LayoutScreen布局相关
internal object LayoutScreenUIs {
    //所有基础内容的list ⚠️todo 除了要用于填充页面，还要在上面添加到list中，注册页面导航route
    internal fun layoutCourses(modifier: Modifier = Modifier) = listOf(
        CourseItemModel(
            "Custom Modifier",
            "创建自定义的modifier，来处理布局layout，测量measurable，约束constraint，占位等。"
        ) { UI_CustomModifier(modifier) },
    )
}
//endregion