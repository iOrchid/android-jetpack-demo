package org.zhiwei.compose.screen.basic.material3

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrueltyFree
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.rounded.ColorLens
import androidx.compose.material.icons.sharp.BluetoothAudio
import androidx.compose.material.icons.twotone.DeviceHub
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Text

/**
 * 演示compose的Material3的Button按钮的使用，compose自身UI的库里面的button也是类似的
 */
@Composable
internal fun Button_Screen(modifier: Modifier = Modifier) {

    LazyColumn(modifier.fillMaxSize()) {

        item {
            Title_Text(title = "Button")
            //1、普通的常规属性
            Title_Desc_Text(desc = "1、button 常用属性如圆角，禁用，图标，背景色阴影边框等")
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {/*点击事件是在这里处理*/ }) {
                    //Compose中Button也是个容器内部可以是文本，icon或者image都行，或者其他compose的
                    Text(text = "Button")
                }
                TextButton(onClick = { }) {
                    Text(text = "TextButton")
                }
                OutlinedButton(onClick = { }) {
                    Text(text = "OutlinedButton")
                }
            }
            //按钮被禁用，enable=false的时候，默认的效果样式
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {}, enabled = false) {
                    //Compose中Button也是个容器内部可以是文本，icon或者image都行，或者其他compose的
                    Text(text = "Button")
                }
                TextButton(onClick = { }, enabled = false) {
                    Text(text = "TextButton")
                }
                OutlinedButton(onClick = { }, enabled = false) {
                    Text(text = "OutlinedButton")
                }
            }
            //shape属性
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {}, shape = RectangleShape) {
                    //Compose中Button也是个容器内部可以是文本，icon或者image都行，或者其他compose的
                    Text(text = "矩形")
                }
                //圆角/切角 都可以指定单独每个角的尺寸
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(topStart = 8.dp, bottomEnd = 12.dp)
                ) {
                    Text(text = "圆角按钮")
                }
                OutlinedButton(onClick = { }, shape = CutCornerShape(10.dp)) {
                    Text(text = "切角外框按钮")
                }
            }
            // 带icon的按钮,
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {}) {
                    //Button内部是个row的行容器布局,可以自由的组合喜欢的compose组件:单文字，单图标，文字图标组合等
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "爱心")
                    Text(text = "按钮")
                    //Icons是compose material icons 库提供的，一些通用icon图标
                    Icon(imageVector = Icons.TwoTone.DeviceHub, contentDescription = "Hub")
                }
                TextButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "爱心")
                    Icon(imageVector = Icons.TwoTone.DeviceHub, contentDescription = "Hub")
                }
                OutlinedButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "爱心")
                    Icon(imageVector = Icons.TwoTone.DeviceHub, contentDescription = "Hub")
                }
                ElevatedButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "爱心")
                    Icon(imageVector = Icons.TwoTone.DeviceHub, contentDescription = "Hub")
                }
            }
            //其他一些button，可以留意其内部布局有的是行布局，有的就是帧布局
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilledIconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "爱心")
                    Icon(imageVector = Icons.Default.DeviceHub, contentDescription = "爱心")
                }
                FilledIconToggleButton(checked = false, onCheckedChange = {}) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "爱心")
                }
                FilledTonalButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "爱心")
                }
                OutlinedIconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "爱心")
                    Icon(imageVector = Icons.TwoTone.DeviceHub, contentDescription = "Hub")
                }

                FloatingActionButton(
                    onClick = { },
                    shape = FloatingActionButtonDefaults.smallShape
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "爱心")
                }
                ExtendedFloatingActionButton(onClick = { }, shape = CircleShape) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "爱心")
                }
            }
            //背景色，字体色
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {},
                    //注意使用colors的方式可以多样，ButtonColors构建，
                    // 或者ButtonDefaults对应按钮colors的copy或者构建
//                    colors = ButtonDefaults.buttonColors().copy(),
//                    colors = ButtonDefaults.buttonColors(),
                    colors = ButtonColors(
                        containerColor = Color(0xFFD2D97A),
                        contentColor = Color(0xFFFFFFFF),
                        disabledContainerColor = Color(0xFFD2D97A),
                        disabledContentColor = Color(0xFFD2D97A)
                    ),
                    //悬浮高度，可细分为默认，按压下的，禁用时的等
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    border = BorderStroke(2.dp, Color.Red),//加边框，可用纯色，也可以用brush多色
                ) {
                    Text(text = "槐花黄绿")
                }
                //其他按钮的elevation、border等属性也是触类旁通，不再逐一演示
                TextButton(
                    onClick = {},
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF41AE3C))
                ) {
                    Text(text = "宝石绿")
                }
                //似乎只能通过modifier来实现渐变色背景的按钮；fixme 后续学完graphic等应该会有其他方案。
                Button(
                    onClick = { },
                    colors = ButtonDefaults.textButtonColors(),
                    modifier = Modifier.background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFFF97D1C),
                                Color(0xFF41AE3C),
                                Color(0xFFBEC936)
                            )
                        )
                    )
                ) {
                    Text(text = "多色毛刷")
                }
            }
        }

        item {
            Title_Desc_Text(desc = "2、IconButton")
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                //普通的icon button
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = null
                    )
                }
                //选择状态的,这里使用了remember和mutableState两个暂时为学习到的知识点，
                // 简单理解就是在该compose组件生命周期内，记录一个状态变量在内存里，便于使用
                var checked by remember { mutableStateOf(false) }
                val ctx = LocalContext.current
                IconToggleButton(checked = checked, onCheckedChange = {
                    checked = it
                    Toast.makeText(ctx, "当前Icon开关是 $checked", Toast.LENGTH_SHORT).show()
                }) {
                    //简单理解就是会根据checked的状态，按钮的颜色会不同，而且这里还设置了切换动画效果
                    val tint by animateColorAsState(
                        targetValue = if (checked) Color.Red else Color.Green,
                        animationSpec = tween(400),//动画效果，是转换
                        label = ""
                    )
                    Icon(
                        Icons.Filled.Favorite, tint = tint,
                        contentDescription = null
                    )
                }
                IconButton(onClick = {
                    Toast.makeText(ctx, "点我干嘛？", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        Icons.Sharp.BluetoothAudio,
                        contentDescription = null
                    )
                }
            }

        }

        item {
            Title_Text(title = "Chip")
            Title_Desc_Text(desc = "Chip 可以理解为标签🏷️按钮")
            //chip也是个变样的button，具有compose组合空间的通用特性，
            //所以可以在compose的函数内，自由组合其子布局的控件，modifier实现必要的颜色，边框，点击，背景 ，图形等
            //Chip内的icon可以定义为compose的任意控件，有自带点击事件的，可自行处理；
            //无自带点击事件的compose组件，可通过modifier的onClick处理单的的点击，如果有需要的话。
            val context = LocalContext.current
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AssistChip(onClick = {
                    Toast.makeText(context, "点击整个AssistChip", Toast.LENGTH_SHORT).show()
                },
                    label = { Text(text = "AssistChip") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.clickable(
                                //通过interactionSource配合indication实现自定义点击ripple的效果
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = false,//标记ripple是否约束在控件尺寸内
                                    radius = 20.dp,//ripple的半径
                                    Color.Green//ripple的颜色
                                )
                            ) {
                                /*这里可以单独处理chip内部的icon的点击事件*/
                                Toast.makeText(
                                    context,
                                    "FavoriteBorder图标被点击",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Hub,
                            contentDescription = null
                        )
                    }
                )
                ElevatedAssistChip(onClick = { }, label = { Text(text = "ElevatedAssistChip") })

            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                ElevatedFilterChip(
                    selected = true,
                    onClick = { },
                    enabled = false,
                    label = { Text(text = "ElevatedFilterChip") })

                ElevatedSuggestionChip(
                    onClick = { },
                    enabled = false,
                    label = { Text(text = "ElevatedSuggestionChip") })
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val ctx = LocalContext.current
                FilterChip(
                    selected = true,
                    onClick = {
                        Toast.makeText(
                            ctx,
                            "注意看这个点击效果，悬浮高度是随意定义的",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    label = { Text(text = "FilterChip") },
                    elevation = FilterChipDefaults.filterChipElevation(
                        5.dp,
                        2.dp,
                        3.dp,
                        1.dp,
                        10.dp
                    )
                )

                InputChip(
                    selected = false,
                    onClick = { },
                    label = { Text(text = "InputChip") },
                    avatar = {
                        Icon(imageVector = Icons.Rounded.ColorLens, contentDescription = null)
                    },
                    shape = CutCornerShape(10.dp),
                    border = BorderStroke(2.dp, Color.Red)
                )

                SuggestionChip(onClick = { },
                    label = {
                        //todo label虽说字面意思是文字标题，但实际上内部可以是任何compose的组件
                        Text(text = "SuggestionChip", fontSize = 8.sp)
                        Icon(
                            imageVector = Icons.Filled.CrueltyFree,
                            contentDescription = null,
                        )
                    },
                    //整个chip的modifier，限定了总的尺寸，所以上面的label内的组件，尺寸的设置就会被影响
                    modifier = Modifier.size(120.dp, 30.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun ButtonScreenPreview() {
    Button_Screen()
}