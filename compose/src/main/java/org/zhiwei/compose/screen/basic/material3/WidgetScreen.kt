package org.zhiwei.compose.screen.basic.material3

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

@Composable
internal fun Widget_Screen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
            //原本column默认是不可滑动的，使用此修饰符，则内容过多的时候，可滑动
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center
    ) {
        UI_SnackBar()
        UI_ProgressIndicator()
        UI_CheckBox()
        UI_Switch()
        UI_Radio()
        UI_Slider()
    }
}

@Composable
private fun UI_SnackBar() {
    Title_Text(title = "SnackBar")
    Title_Sub_Text(title = "1、snackbar用于提供短暂的消息提示，类似于toast")
    Title_Desc_Text(desc = "基础使用")
    Snackbar {
        //这里是个box容器布局类型，只显示最后一个compose的控件
        Text(text = "基本的snack，显示消息文本")
        Text(text = "基本的snack，显示消息文本，，，，")
    }
    Title_Desc_Text(desc = "带有交互Action操作")
    val context = LocalContext.current
    Snackbar(
        action = {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "点击snackbar的action叻",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(imageVector = Icons.Filled.Share, contentDescription = null)
            }
        },
    ) {
        //这里是个box容器布局类型，只显示最后一个compose的控件
        Text(text = "带Action的snackBar")
    }
    Title_Desc_Text(desc = "另起一行 带有交互Action操作")
    Snackbar(
        action = {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "点击snackbar的action叻",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(imageVector = Icons.Filled.Send, contentDescription = null)
            }
        },
        actionOnNewLine = true,
    ) {
        //这里是个box容器布局类型，只显示最后一个compose的控件
        Text(text = "带Action的snackBar")
    }
    Title_Desc_Text(desc = "自定义样式的snackbar")
    Snackbar(
        action = { Text(text = "Action") },
        dismissAction = { Text(text = "取消") },
        shape = RoundedCornerShape(10.dp),
        containerColor = Color(0XFFF8D86A),
        contentColor = Color(0XFF3D3B4F),
        actionContentColor = Color(0XFF758A99),
        dismissActionContentColor = Color(0XFFF43E06),
    ) {
        //这里是个box容器布局类型，只显示最后一个compose的控件
        Text(text = "自定义样式style的snackbar")
    }
    Spacer(Modifier.height(8.dp))
    Snackbar(
        action = { Text(text = "Action") },
        shape = CutCornerShape(topStart = 10.dp, topEnd = 8.dp),
        containerColor = Color(0XFFF19790),
        actionContentColor = Color.Green,
    ) {
        //这里是个box容器布局类型，只显示最后一个compose的控件
        Text(text = "切角snackbar")
    }
}

@Composable
private fun UI_ProgressIndicator() {
    Title_Text(title = "ProgressIndicator")
    Title_Sub_Text(title = "1、ProgressIndicator就是传统view中的progressBar，进度提示条/圈")
    Title_Desc_Text(desc = "Indeterminate progress 默认进度progress是0的时候")
    CircularProgressIndicator()
    Spacer(modifier = Modifier.height(8.dp))
    LinearProgressIndicator()
    Spacer(modifier = Modifier.height(8.dp))
    Title_Desc_Text(desc = "Determinate 有确切的进度")
    //在compose中，数据变化都是state的，
    val progress = progressFlow.collectAsState(initial = 0)
    CircularProgressIndicator(
        progress = { progress.value / 100f },
        color = Color(0XFFFA7E23),
        strokeWidth = 4.dp,
        trackColor = Color(0XFF1BA784),
    )
    Spacer(modifier = Modifier.height(8.dp))
    Title_Desc_Text(desc = "注意观察进度条的变化，使用flow数据流")
    LinearProgressIndicator(
        progress = { progress.value / 100f },
        color = Color(0XFFF6AD8F),
        trackColor = Color(0XFF9BAE86),
    )
    Spacer(modifier = Modifier.height(8.dp))
    Title_Desc_Text(desc = "进度条变化动画形式")

    var progressAnimated by remember { mutableFloatStateOf(0.1f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progressAnimated,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = ""
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedButton(
            onClick = { if (progressAnimated < 1f) progressAnimated += 0.1f }
        ) {
            Text("变长～")
        }
        Spacer(Modifier.requiredWidth(30.dp))
        LinearProgressIndicator(progress = { animatedProgress })
    }
}

@Composable
private fun UI_CheckBox() {
    Title_Text(title = "CheckBox")
    Title_Sub_Text(title = "1、勾选框")
    var checkBoxState by remember { mutableStateOf(false) }
    Title_Desc_Text(desc = "常规使用")
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checkBoxState,
            onCheckedChange = { checkBoxState = it },
        )
        //禁用不可点击
        Checkbox(
            checked = true,
            onCheckedChange = null,
            enabled = false
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
    Title_Desc_Text(desc = "结合text文本")
    //自定义组合
    var checkBoxState2 by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable(
                interactionSource = interactionSource,
                // 这里的话，点击Row就不会有ripple效果
                indication = null,
                role = Role.Checkbox,
                onClick = {
                    checkBoxState2 = !checkBoxState2
                }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Checkbox(
            checked = checkBoxState2,
            onCheckedChange = null
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Text(text = "你喜欢Compose么？")
    }
    //带有ripple
    Title_Desc_Text(desc = "带有Ripple效果的 结合text文本")
    var checkBoxState3 by remember { mutableStateOf(false) }
    WithTextCheckbox("你喜欢谁呀？", checkBoxState3) { checkBoxState3 = it }
    //复合勾选框
    Title_Sub_Text(title = "2、复合勾选框TriStateCheckbox")
    Title_Desc_Text(desc = "外层勾选状态会根据内部子框的选择状态而确定")
    Column(modifier = Modifier.padding(8.dp)) {
        //定义子checkbox的选择状态;⚠️注意：remember的另一种生命方式，可以声明变量及其变化
        val (state, onStateChange) = remember { mutableStateOf(false) }
        val (state2, onStateChange2) = remember { mutableStateOf(false) }
        //记录外层checkbox的选择状态，根据子控件的逻辑
        val parentState = remember(key1 = state, key2 = state2) {
            //根据逻辑，处理显示状态
            if (state && state2) ToggleableState.On else if (!state && !state2) ToggleableState.Off else ToggleableState.Indeterminate
        }
        //外层的check变化，要同步控制内部所有的子控件选择状态
        val onParentClick = {
            val change = parentState != ToggleableState.On
            onStateChange(change)
            onStateChange2(change)
        }

        Spacer(modifier = Modifier.width(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            //复合勾选框
            TriStateCheckbox(
                state = parentState,
                onClick = onParentClick,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "起飞🛫")
        }
        Spacer(Modifier.height(8.dp))
        Column(Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
            WithTextCheckbox(label = "夹板已清空", state = state, onStateChange = onStateChange)
            Spacer(Modifier.height(8.dp))
            WithTextCheckbox(label = "飞机启动正常", state = state2, onStateChange = onStateChange2)
        }
    }
}

@Composable
private fun UI_Switch() {
    Title_Text(title = "Switch")
    Title_Sub_Text(title = "开关，左边演示正常可用的，右边是enable为false的状态")
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val checked = remember { mutableStateOf(false) }
        Switch(checked = checked.value, onCheckedChange = { checked.value = it })
        Switch(
            checked = !checked.value,
            onCheckedChange = { checked.value = it },
            //这个就是改变开关的按钮的🔘的内容
            thumbContent = { Icon(imageVector = Icons.Filled.Share, contentDescription = null) },
        )
        Switch(
            checked = checked.value,
            onCheckedChange = { checked.value = it },
            thumbContent = { Text(text = "戳") },
            //可根据UI需要，配置不同颜色
            colors = SwitchDefaults.colors().copy(checkedTrackColor = Color.Green)
        )
        Switch(
            checked = true,
            onCheckedChange = null,
            enabled = false,
        )
        Switch(
            checked = false,
            onCheckedChange = null,
            enabled = false,
        )
    }

}

@Composable
private fun UI_Radio() {
    Title_Text(title = "RadioButton")
    Title_Sub_Text(title = "RadioButton可单个使用，一般也是用在radioGroup中组合使用")
    //在compose中，每个基础控件都是细颗粒度的，通过多样化组合来实现想要的效果
    var isRadioSelected by remember { mutableStateOf(true) }
    Title_Desc_Text(desc = "常规使用，启用与禁用的状态，及点击选中，颜色配置等")
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        // 可用状态
        RadioButton(selected = isRadioSelected, onClick = { isRadioSelected = !isRadioSelected })
        RadioButton(
            selected = !isRadioSelected,
            onClick = { isRadioSelected = !isRadioSelected },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xffE91E63),
                unselectedColor = Color(0xffFFEB3B),
                disabledSelectedColor = Color(0XFF607D8B),
                disabledUnselectedColor = Color(0XFF997D8B),
            )
        )
        // 禁用状态
        RadioButton(
            enabled = false,
            selected = false,
            onClick = {},
            colors = RadioButtonDefaults.colors(
                disabledSelectedColor = Color(0XFF607D8B)
            )
        )
        RadioButton(
            enabled = false,
            selected = true,
            onClick = {},
            colors = RadioButtonDefaults.colors(
                disabledSelectedColor = Color(0xff607D8B)
            )
        )
    }
    Title_Sub_Text(title = "二选一效果RadioGroup")
    Title_Desc_Text(desc = "点击切换选择")
    Spacer(Modifier.height(8.dp))
    //演示 二选一
    var state by remember { mutableStateOf(true) }
    Row(
        Modifier
            .selectableGroup()
            .padding(8.dp)
    ) {
        RadioButton(
            selected = state,
            onClick = { state = true }
        )
        Spacer(modifier = Modifier.width(24.dp))
        RadioButton(
            selected = !state,
            onClick = { state = false }
        )
    }
    Title_Sub_Text(title = "2. 多选一的效果")
    Title_Desc_Text(desc = "选择你最喜欢的AV女优")
    Spacer(Modifier.height(8.dp))
    val radioOptions = listOf("吉根柚莉爱", "月乃露娜", "北野未奈", "水原美园")
    val (selectedOption: String, onOptionSelected: (String) -> Unit) = remember {
        mutableStateOf(
            radioOptions[0]
        )
    }
    //组合多个selectable的控件，最好selectableGroup设置
    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null //这里null，因为点击事件交给了外层row的modifier处理
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun UI_Slider() {
    Title_Text(title = "Slider")
    Title_Sub_Text(title = "类似于旧view中的seekbar，滑动选择进度条")
    Title_Desc_Text(desc = "常规slider")
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Spacer(Modifier.height(8.dp))
    Slider(value = sliderPosition, onValueChange = { sliderPosition = it })
    val colors = SliderDefaults.colors(
        thumbColor = Color(0xffF44336),
        disabledThumbColor = Color(0xff795548),
        activeTrackColor = Color(0xff009688),
        inactiveTrackColor = Color(0xffFFEA00),
        disabledActiveTrackColor = Color(0xffFF9800),
        disabledInactiveTrackColor = Color(0xff616161),
        activeTickColor = Color(0xff673AB7),
        inactiveTickColor = Color(0xff2196F3),
        disabledActiveTickColor = Color(0xffE0E0E0),
        disabledInactiveTickColor = Color(0xff607D8B)
    )
    Spacer(Modifier.height(8.dp))
    var sliderPosition2 by remember { mutableFloatStateOf(.3f) }
    Title_Desc_Text(desc = "自定义配色")
    Slider(
        value = sliderPosition2,
        onValueChange = { sliderPosition2 = it },
        colors = colors
    )
    Spacer(Modifier.height(8.dp))
    Title_Desc_Text(desc = "禁用状态enable=false")
    var sliderPosition3 by remember { mutableFloatStateOf(.4f) }
    Slider(
        value = sliderPosition3,
        onValueChange = { sliderPosition3 = it },
        enabled = false,//todo ⚠️ 禁用状态
        colors = colors
    )
    Spacer(Modifier.height(8.dp))
    var sliderPosition4 by remember { mutableFloatStateOf(36f) }
    Title_Desc_Text(desc = "当前progress值：${sliderPosition4}")
    //变动值是float的
    Slider(
        value = sliderPosition4,
        onValueChange = { sliderPosition4 = it },
        valueRange = 0f..100f,
        onValueChangeFinished = {},
        steps = 10,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.secondary,
            activeTrackColor = MaterialTheme.colorScheme.secondary
        )
    )
    Title_Sub_Text(title = "RangeSlider")
    var sliderPosition5 by remember { mutableStateOf(.1f..(.3f)) }
    RangeSlider(
        value = sliderPosition5,
        onValueChange = { sliderPosition5 = it },
        colors = colors
    )

}

@Composable
private fun WithTextCheckbox(label: String, state: Boolean, onStateChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable(
                role = Role.Checkbox,
                onClick = {
                    onStateChange(!state)
                }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Checkbox(
            checked = state,
            //整个row感知了点击事件，所以这里不用了。
            onCheckedChange = null
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Text(text = label)
    }

}


//模拟进度增加
private val progressFlow: Flow<Int> by lazy {
    flow<Int> {
        repeat(100) {
            emit(it + 1)//进度从1--100
            delay(50)
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun WidgetPreview() {
    Widget_Screen()
}