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
import org.zhiwei.compose.screen.gesture.Clickable_Screen
import org.zhiwei.compose.screen.gesture.SwipeScroll_Screen
import org.zhiwei.compose.screen.gesture.TapDragGestures_Screen
import org.zhiwei.compose.screen.gesture.TouchImage_Screen
import org.zhiwei.compose.screen.gesture.TransformGestures_Screen
import org.zhiwei.compose.screen.graphics.BeforeAfterScreen
import org.zhiwei.compose.screen.graphics.BlendMode_Screen
import org.zhiwei.compose.screen.graphics.CanvasBasic_Screen
import org.zhiwei.compose.screen.graphics.CanvasPathEffect_Screen
import org.zhiwei.compose.screen.graphics.CanvasPathOperations_Screen
import org.zhiwei.compose.screen.graphics.CanvasPath_Screen
import org.zhiwei.compose.screen.graphics.Chart_Screen
import org.zhiwei.compose.screen.graphics.MultiColorDrawable_Screen
import org.zhiwei.compose.screen.graphics.NeonGlowEffect_Screen
import org.zhiwei.compose.screen.graphics.Others_Screen
import org.zhiwei.compose.screen.graphics.RainbowBorder_Screen
import org.zhiwei.compose.screen.graphics.ShimmerEffect_Screen
import org.zhiwei.compose.screen.layout_state.ConstraintLayout_Screen
import org.zhiwei.compose.screen.layout_state.Constraints_Screen
import org.zhiwei.compose.screen.layout_state.CustomModifier_Screen
import org.zhiwei.compose.screen.layout_state.Effect_Screen
import org.zhiwei.compose.screen.layout_state.GraphicsLayerModifier_Screen
import org.zhiwei.compose.screen.layout_state.LazyListRc_Screen
import org.zhiwei.compose.screen.layout_state.ListDirection_Screen
import org.zhiwei.compose.screen.layout_state.OnPlaceLayoutId_Screen
import org.zhiwei.compose.screen.layout_state.StateReComposable_Screen

/**
 * 用于配置整个Compose模块内所有可跳转的页面UI，用于Navigation导航
 */
internal fun configPageRoute(modifier: Modifier, onBack: (() -> Unit) = {}): List<CourseItemModel> {
    val list = mutableListOf<CourseItemModel>()
    list.addAll(BasicScreenUIs.basicCourses(modifier, onBack))
    list.addAll(LayoutStateScreenUIs.layoutCourses(modifier))
    list.addAll(GestureScreenUIs.layoutCourses(modifier))
    list.addAll(GraphicsScreenUIs.layoutCourses(modifier))
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

//region LayoutStateScreen布局相关
internal object LayoutStateScreenUIs {
    //所有基础内容的list ⚠️todo 除了要用于填充页面，还要在上面添加到list中，注册页面导航route
    internal fun layoutCourses(modifier: Modifier = Modifier) = listOf(
        CourseItemModel(
            "Custom Modifier",
            "创建自定义的modifier，来处理布局layout，测量measurable，约束constraint，占位等。"
        ) { CustomModifier_Screen(modifier) },
        CourseItemModel(
            "graphicsLayer Modifier",
            "Modifier的graphicsLayer操作符，可对内部的compose控件做图层操作处理，如平移/缩放/阴影/剪切等。"
        ) { GraphicsLayerModifier_Screen(modifier) },
        CourseItemModel(
            "Constraints&Size Modifier",
            "BoxWithConstraints，演示一些compose组件内的约束相关的使用。"
        ) { Constraints_Screen(modifier) },
        CourseItemModel(
            "Constraints Layout",
            "补充一个ConstraintLayout的约束布局的使用。"
        ) { ConstraintLayout_Screen(modifier) },
        CourseItemModel(
            "onPlace和layoutId",
            "Modifier的onPlace和layoutId操作符的使用。"
        ) { OnPlaceLayoutId_Screen(modifier) },
        CourseItemModel(
            "State ReComposable",
            "compose的重组和状态变化作用域感知的相关使用与演示。"
        ) { StateReComposable_Screen(modifier) },
        CourseItemModel(
            "Effect效应",
            "compose生命周期不同阶段的感知，effect使用方式。"
        ) { Effect_Screen(modifier) },
        CourseItemModel(
            "LazyList重组",
            "类似recyclerView的LazyList组件，在compose中，多层绘制重组。"
        ) { LazyListRc_Screen(modifier) },
        CourseItemModel(
            "List滑动方向",
            "根据LazyList的firstItem可见行和偏移量的计算，来获取整个列表的滑动方向的分析。"
        ) { ListDirection_Screen(modifier) },
    )
}
//endregion

//region 手势事件相关的布局

internal object GestureScreenUIs {

    internal fun layoutCourses(modifier: Modifier = Modifier) = listOf(
        CourseItemModel(
            "Clickable",
            "点击相关，水波纹ripple和交互效果的简单演示。"
        ) { Clickable_Screen(modifier) },
        CourseItemModel(
            "Tap&Drag Gestures",
            "点击与拖拽事件的监测与状态感知。"
        ) { TapDragGestures_Screen(modifier) },
        CourseItemModel(
            "Transforms",
            "对控件元素的转换操作，包括平移、缩放、旋转等。"
        ) { TransformGestures_Screen(modifier) },
        CourseItemModel(
            "SwipeScrollable",
            "Modifier的一些其他操作手势，侧滑，滚动等。"
        ) { SwipeScroll_Screen(modifier) },
        CourseItemModel(
            "TouchOnImage",
            "从图片的点击位置，获取触控点的颜色。"
        ) { TouchImage_Screen(modifier) },
    )
}

//endregion

//region canvas 图像图形相关

internal object GraphicsScreenUIs {

    internal fun layoutCourses(modifier: Modifier = Modifier) = listOf(
        CourseItemModel(
            "CanvasBasic",
            "图像图形中最重要的概念，canvas画布，此处演示最基本的使用，绘制点线面和图片。"
        ) { CanvasBasic_Screen(modifier) },
        CourseItemModel(
            "CanvasPath",
            "canvas也可以用于绘制不规则图形，根据path路径的设置，还可以应用不同的线条风格。"
        ) { CanvasPath_Screen(modifier) },
        CourseItemModel(
            "CanvasPathOps",
            "canvas绘制path，不同的图形使用交互方式不同，表现层叠交集效果。"
        ) { CanvasPathOperations_Screen(modifier) },
        CourseItemModel(
            "CanvasPathEffect",
            "canvas绘制path的时候，可以设置不同的pathEffect效果。"
        ) { CanvasPathEffect_Screen(modifier) },
        CourseItemModel(
            "BlendMode",
            "blendMode是用于图形/图像层叠交互的时候，确定融合与剪切后的显示效果。"
        ) { BlendMode_Screen(modifier) },
        CourseItemModel(
            "MultiColorDrawable",
            "演示图形blendMode多色混合的模式效果。"
        ) { MultiColorDrawable_Screen(modifier) },
        CourseItemModel(
            "ChartDemos",
            "演示图表相关的绘制。"
        ) { Chart_Screen(modifier) },
        CourseItemModel(
            "BeforeAfter",
            "一个自动切换上下层变化的控件演示demo，变化前后是不同的控件。"
        ) { BeforeAfterScreen(modifier) },
        CourseItemModel(
            "ShimmerEffect",
            "演示shimmer加载效果的使用，区分同步加载和变化加载。"
        ) { ShimmerEffect_Screen(modifier) },
        CourseItemModel(
            "RainbowBorder",
            "演示七彩虹的渐变色的描边效果，并且可以动态滚动。"
        ) { RainbowBorder_Screen(modifier) },
        CourseItemModel(
            "NeonGlowEffect",
            "一种高斯模糊效果blur的实线方式。"
        ) { NeonGlowEffect_Screen(modifier) },
        CourseItemModel(
            "OthersScreen",
            "零散的其他一些效果演示，比如挖空、图片缩放、水滴融合效果等。"
        ) { Others_Screen(modifier) },
    )
}

//endregion
