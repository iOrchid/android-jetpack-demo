package org.zhiwei.compose.screen.gesture

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

@Composable
internal fun Clickable_Screen(modifier: Modifier = Modifier) {

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Text(title = "Clickable")
        Title_Sub_Text(title = "1. 演示常规点击的交互、ripple水波纹效果以及边界和样式。")
        //在基础篇，有说明，modifier的许多操作符，有作用顺序而不同效果。
        BasicClickable()
        Title_Sub_Text(title = "2. interactionSource相关，即compose控件的交互，比如点击/双击/长按/拖拽等。")
        UI_Interaction()

    }

}

//region 基础使用
@Composable
private inline fun BasicClickable() {
    Title_Desc_Text(desc = "clip的作用对比，裁剪的形状和点击作用的水波ripple返回，会因clickable的位置而不同。")
    val modifierWithClip = Modifier
        .fillMaxWidth()
        .height(40.dp)
        //裁剪形状clip的作用，用于裁剪形状区域rect
        .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp), clip = true)
        .background(Color(0xffBDBDBD))
    UI_Clip2Clickable(modifierWithClip)
    Title_Desc_Text(desc = "水波ripple的范围限定，通过interactionSource来设置。")
    UI_RippleBounded(modifierWithClip)
    Title_Desc_Text(desc = "通过主题theme来设置控件的点击效果的水波ripple")
    UI_ThemeRipple(modifierWithClip)
    Title_Desc_Text(desc = "自定义的点击交互，这里简单演示的是ripple")
    UI_Indication()
}

//clickable点击效果与shape的clip作用前后的对比
@Composable
private fun UI_Clip2Clickable(modifierWithClip: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifierWithClip
                .clickable {}
        ) {
            Text(
                text = "点击在clip前",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                //clickable而后再应用clip的modifier。这里用到了then操作符，就是将另外的modifier作用于当前
                .clickable {}
                .then(modifierWithClip)
        ) {
            Text(
                text = "Clip后写clickable",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

//ripple范围的限定
@Composable
private fun UI_RippleBounded(modifierWithClip: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        //如果多个控件使用同一个MutableInteractionSource，交互效果会同步
        val mutableInteractionSource = remember { MutableInteractionSource() }
        val mutableInteractionSource2 = remember { MutableInteractionSource() }
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifierWithClip
                .clickable(
                    interactionSource = mutableInteractionSource,
                    //ripple水波的自定义
                    indication = rememberRipple(
                        bounded = true,//限定作用范围
                        radius = 260.dp,//水波半径
                        color = Color.Yellow,//水波颜色
                    )
                ) {}
        ) {
            Text(
                text = "限定ripple范围",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clickable(
                    interactionSource = mutableInteractionSource2,
                    //ripple水波的自定义
                    indication = rememberRipple(
                        bounded = false,//不限定作用范围
                        radius = 260.dp,//水波半径
                        color = Color.Green,//水波颜色
                    )
                ) {}
                .then(modifierWithClip)
        ) {
            Text(
                text = "不限定水波ripple",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

//通过theme主题设置ripple样式
@Composable
private fun UI_ThemeRipple(modifierWithClip: Modifier) {
    //自定义的ripple theme，也可以封装成类，接收color和alpha等需要的参数。
    val customRippleTheme = object : RippleTheme {
        @Composable
        override fun defaultColor(): Color {
            return Color.Red
        }

        @Composable
        override fun rippleAlpha(): RippleAlpha {
            return RippleTheme.defaultRippleAlpha(
                Color.Red,
                lightTheme = !isSystemInDarkTheme()
            )
        }
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        //简单理解CompositionLocalProvider就是提供一个限定数据作用域的方式，减少触发compose重组的影响范围，以提升性能。
        CompositionLocalProvider(value = LocalRippleTheme provides customRippleTheme) {
            Box(modifier = modifierWithClip.clickable { }, contentAlignment = Alignment.Center) {
                Text(text = "自定义RippleTheme的演示", color = Color.White)
            }
        }
    }
}

//自定义的indication,简单演示按压效果
@Composable
private fun UI_Indication() {
    val indicationA = CustomIndication(Color.Yellow, .4f)
    val indicationB = CustomIndication(Color.Cyan, .4f, drawRoundedShape = false)
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp), clip = true)
                .background(Color(0xffBDBDBD))
                .clickable(
                    indication = indicationA,
                    interactionSource = MutableInteractionSource()
                ) { }, contentAlignment = Alignment.Center
        ) {
            Text(text = "自定义indication的演示", color = Color.White)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color(0xffBDBDBD), shape = RoundedCornerShape(16.dp))
                .clickable(
                    indication = indicationB,
                    interactionSource = MutableInteractionSource()
                ) { }, contentAlignment = Alignment.Center
        ) {
            Text(text = "自定义indication无边界", color = Color.Black)
        }
    }
}

//自定义的indication
private class CustomIndication(
    private val pressColor: Color,//按压后的颜色
    private val alpha: Float,//透明度
    private val drawRoundedShape: Boolean = true,//是否绘制ripple在区域内
    private val cornerRadius: CornerRadius = CornerRadius(16f, 16f),//圆角
) : Indication {
    private inner class DefaultIndicationInstance(private val isPressed: State<Boolean>) :
        IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            //作用对象的compose容器，内容要绘制
            drawContent()
            when {
                //表示 有触压动作时候才会有的逻辑
                isPressed.value -> {
                    if (drawRoundedShape) {
                        //根据设置，绘制
                        drawRoundRect(
                            cornerRadius = cornerRadius,
                            color = pressColor.copy(
                                alpha = alpha
                            ), size = size
                        )
                    } else {
                        //绘制一个大大的圆，根据size的宽度，作为半径的圆
                        drawCircle(
                            radius = size.width,
                            color = pressColor.copy(
                                alpha = alpha
                            )
                        )
                    }
                }
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        //collectIsPressedAsState 按钮的交互状态，
        val isPressed = interactionSource.collectIsPressedAsState()
        return remember(interactionSource) {
            DefaultIndicationInstance(isPressed)
        }
    }
}

//endregion

//region 交互相关

@Composable
private fun UI_Interaction() {
    Title_Desc_Text(desc = "collectIsPressedAsState,感知按钮的触压事件")
    val btnInteraction = remember { MutableInteractionSource() }
    //感知按压相关事件，类似的还有hover，dragged，focus
    val isPressed by btnInteraction.collectIsPressedAsState()
    Button(onClick = { }, modifier = Modifier.fillMaxWidth(), interactionSource = btnInteraction) {
        Text(text = if (isPressed) "按下⛰️" else "未🈚️🍐按压")
    }
    //通过clickable的interactionSource感知交互事件
    Spacer(modifier = Modifier.height(8.dp))
    val boxInteraction = remember { MutableInteractionSource() }
    LaunchedEffect(key1 = null) {
        //这里启动一个协程，来搜集box的交互事件,interactions是个flow
        boxInteraction.interactions
            //这里可以看到，搜集到三种状态，press，release，和cancel；
            .onEach { println("📦：boxInteraction，$it") }
            .launchIn(this)
    }
    //注意点击，按压 按钮时候，看log输出事件
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 8.dp)
            .background(
                Color.LightGray,
                RoundedCornerShape(8.dp)
            )
            .clickable(
                interactionSource = boxInteraction,
                indication = rememberRipple(),
                onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "点击并搜集交互状态")
    }
    Title_Desc_Text(desc = "按压状态实现动画交互的按钮")
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        PressIconButton(
            onClick = { },
            icon = { Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = null) },
            text = {
                Text(
                    text = "点击添加"
                )
            })
        ElasticButton(modifier = Modifier.shadow(
            8.dp, RoundedCornerShape(10.dp)
        ), onClick = { }) {
            Surface(shape = RoundedCornerShape(10.dp)) {
                Text(
                    text = "缩放效果的按钮",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .background(Color(0xffFFA726))
                        .padding(10.dp),
                    color = Color.White
                )
            }
        }
    }
    Title_Desc_Text(desc = "多控件UI共用交互interactionSource")
    ShareInteractionSource()
}

/**
 * 简单封装的一个按钮，按下状态下，会显示icon，松开后，仅有文字。
 */
@Composable
private fun PressIconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    //按压状态
    val isPressed by interactionSource.collectIsPressedAsState()
    Button(onClick = onClick, modifier, interactionSource = interactionSource) {
        //compose的动画,这里会根据按压状态来控制显示隐藏
        AnimatedVisibility(visible = isPressed) {
            if (isPressed) {
                Row {
                    icon()
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                }
            }
        }
        text()
    }

}

/**
 * 简单封装的缩放按压效果的按钮
 */
@Composable
private fun ElasticButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    indication: Indication? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    Box(
        modifier = Modifier
            .graphicsLayer {
                //根据按压状态，缩放按钮
                scaleX = if (isPressed) .95f else 1f
                scaleY = if (isPressed) .95f else 1f
            }
            .then(
                modifier.clickable(
                    interactionSource = interactionSource,
                    indication = indication
                ) { onClick() })
    ) { content() }
}

@Composable
private fun ShareInteractionSource() {
    val context = LocalContext.current

    // 外面的交互interactionSource和内部的共用，然后外部点击会触发UI交互；⚠️注意：共享的是UI交互，而不是click事件。
    val interactionSource = remember { MutableInteractionSource() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(8.dp), clip = true)
                .background(color = Color(0xffFFA726))
                .clickable {
                    //这段协程代码，为了演示 同步交互UI效果，这里故意延迟了按钮的释放
                    coroutineScope.launch {
                        val press = PressInteraction.Press(Offset.Zero)
                        interactionSource.emit(
                            press
                        )
                        delay(300)
                        interactionSource.emit(
                            PressInteraction.Release(press)
                        )
                    }
                    Toast
                        .makeText(context, "点击了外部", Toast.LENGTH_SHORT)
                        .show()

                }
                .padding(8.dp)

        ) {

            Text("Outer Composable", color = Color.White)

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(8.dp), clip = true)
                    .background(color = Color.Cyan)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple(),
                        onClick = {
                            Toast
                                .makeText(context, "内部click点击", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                    .padding(8.dp)
            ) {
                Text(
                    "Inner Composable",
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

//endregion

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewClickable() {
    Clickable_Screen()
}