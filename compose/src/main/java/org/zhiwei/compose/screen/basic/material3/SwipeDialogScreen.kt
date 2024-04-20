package org.zhiwei.compose.screen.basic.material3

import android.text.format.DateUtils
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ModeFanOff
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.zhiwei.compose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SwipeDialog_Screen(modifier: Modifier = Modifier) {
    //compose的viewModel依赖库支持这么创建viewModel实例
//    val vm:MockViewModel = viewModel()//目前该项目 compose是dynamicfeature的形式，暂时使用有问题。如果是普通module可能没问题
    val vm = MockViewModel()
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { vm.createUserList() }) {
            Text(text = "生成列表数据")
        }
        //flow接收数据流，并且关联生命周期，
        val userList = vm.listFlow.collectAsStateWithLifecycle()
        LazyColumn {
            //因为要增删数据，在列表中操作，会引起item的显示位置的变化，所以需要配置唯一的key在作为item的标记
            items(items = userList.value, key = { user -> user.id }) { user ->
                //记录当前操作的item
                val currentItem by rememberUpdatedState(newValue = user)
                //滑动操作，这里左右滑动，都是删除
                val dismissState =
                    rememberSwipeToDismissBoxState(confirmValueChange = { dismissValue ->
                        when (dismissValue) {
                            SwipeToDismissBoxValue.Settled -> false
                            SwipeToDismissBoxValue.EndToStart -> {
                                vm.removeItem(currentItem)
                                true
                            }

                            SwipeToDismissBoxValue.StartToEnd -> {
                                vm.removeItem(currentItem)
                                true
                            }
                        }
                    })
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        //这里是滑动item后，item背后显示的操作层
                        val direction = dismissState.dismissDirection
                        //不同滑动方向和状态，显示不同的背景色
                        val color by animateColorAsState(
                            targetValue = when (dismissState.targetValue) {
                                SwipeToDismissBoxValue.StartToEnd -> Color.Green
                                SwipeToDismissBoxValue.EndToStart -> Color.Red
                                SwipeToDismissBoxValue.Settled -> Color.LightGray
                            }, label = ""
                        )
                        //在不同的滑动方向时候，背层操作控件，使用不同的对齐方式
                        val alignment = when (direction) {
                            SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                            SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                            SwipeToDismissBoxValue.Settled -> Alignment.Center
                        }
                        //不同滑动方向，配置不同的操作按钮图标
                        val icon = when (direction) {
                            SwipeToDismissBoxValue.StartToEnd -> Icons.Filled.Done
                            SwipeToDismissBoxValue.EndToStart -> Icons.Filled.DeleteSweep
                            SwipeToDismissBoxValue.Settled -> Icons.Filled.ModeFanOff
                        }
                        //提升交互体验，不同的滑动状态下，icon的大小适当的缩放
                        val scale by animateFloatAsState(
                            targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.75f else 1f,
                            label = ""
                        )
                        //滑动swipe后，出现的操作icon
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp), contentAlignment = alignment
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    }) {
                    //实际的item卡片
                    Card(
                        elevation = CardDefaults.cardElevation(
                            //配置卡片的高度，只要是滑动动作，就有
                            defaultElevation = animateDpAsState(
                                targetValue = if (dismissState.dismissDirection != SwipeToDismissBoxValue.Settled) 4.dp else 0.dp,
                                label = ""
                            ).value
                        )
                    ) {
                        ListItem(
                            headlineContent = {
                                val showDialog = remember { mutableStateOf(false) }
                                val context = LocalContext.current
                                Box(Modifier.clickable { showDialog.value = !showDialog.value }) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = user.name,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Checkbox(
                                            checked = user.isChecked.value,
                                            onCheckedChange = { user.isChecked.value = it })
                                    }
                                    if (showDialog.value) {
                                        UI_DatePickerDialog(showDialog) { state ->
                                            Toast.makeText(
                                                context,
                                                "选择的日期是: ${
                                                    DateUtils.formatDateTime(
                                                        context,
                                                        state.selectedDateMillis ?: 0L,
                                                        DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
                                                    )
                                                }",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }

                            },
                            supportingContent = { Text(text = "向左👈或向右👉滑动") },
                            leadingContent = {
                                val showDialog = remember { mutableStateOf(false) }
                                IconButton(onClick = { showDialog.value = !showDialog.value }) {
                                    Image(
                                        painter = painterResource(id = R.drawable.img_moto_girl),
                                        contentDescription = null
                                    )
                                    if (showDialog.value) {
                                        UI_Dialog(showDialog)
                                    }
                                }
                            },
                            trailingContent = {
                                val showDialog = remember { mutableStateOf(false) }
                                IconButton(onClick = { showDialog.value = !showDialog.value }) {
                                    Icon(
                                        imageVector = Icons.Sharp.FavoriteBorder,
                                        contentDescription = null
                                    )
                                    if (showDialog.value) {
                                        UI_AlertDialog(showDialog)
                                    }
                                }
                            })
                    }
                }
            }
        }
    }
}

//region dialog演示
@Composable
private fun UI_AlertDialog(showDialog: MutableState<Boolean>) {
    AlertDialog(
        //点击dialog外部，或者系统返回键的时候，走这里，dialog自身的取消按钮逻辑不是这里
        onDismissRequest = { showDialog.value = !showDialog.value },
        confirmButton = {
            OutlinedButton(onClick = { showDialog.value = !showDialog.value }) {
                Text(text = "确认按钮")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = { showDialog.value = !showDialog.value }) {
                Text(text = "取消")
            }
        },
        icon = {
            Image(
                painter = painterResource(id = R.drawable.img_motocycle),
                contentDescription = null
            )
        },
        title = { Text(text = "常规AlertDialog") },
        text = { Text(text = "谷雨如丝，绿意浓厚，万物生长，欣欣向荣。") },
        //其他配置属性color之类的，不一一演示，compose内的控件，可自由组合，样式，设计。
        shape = CutCornerShape(20.dp),//切角样式
        //配置一些dialog的属性设置，比如是否可取消，是否外部点击消失，是否使用默认宽度，安全策略，就是在home预览recent的时候，是否显示
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            securePolicy = SecureFlagPolicy.SecureOff,//如果是on，就在recent不显示
            usePlatformDefaultWidth = true,
        )
    )
}

@Composable
private fun UI_Dialog(showDialog: MutableState<Boolean>) {
    //基类dialog，可更自由的定制一下dialog的内容
    Dialog(
        //点击dialog外部，或者系统返回键的时候，走这里，dialog自身的取消按钮逻辑不是这里
        onDismissRequest = { showDialog.value = !showDialog.value },
        //配置一些dialog的属性设置，比如是否可取消，是否外部点击消失，是否使用默认宽度，安全策略，就是在home预览recent的时候，是否显示
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.SecureOff,//如果是on，就在recent不显示
            usePlatformDefaultWidth = true,
        )
    ) {
        Text(text = "高自由度的Dialog")
        Image(painter = painterResource(id = R.drawable.img_moto_girl), contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UI_DatePickerDialog(
    showDialog: MutableState<Boolean>,
    //演示带数据回调的操作
    callback: (DatePickerState) -> Unit,
) {
    //另还有baseDialog，其他控件dialog，都是触类旁通的使用方式
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        //点击dialog外部，或者系统返回键的时候，走这里，dialog自身的取消按钮逻辑不是这里
        onDismissRequest = { showDialog.value = !showDialog.value },
        confirmButton = {
            OutlinedButton(onClick = {
                showDialog.value = !showDialog.value
                callback(datePickerState)
            }) {
                Text(text = "确认按钮")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = { showDialog.value = !showDialog.value }) {
                Text(text = "取消")
            }
        },
    ) {
        LazyColumn {
            item {
                DatePicker(state = datePickerState)
                datePickerState.selectedDateMillis
            }
        }
    }
}
//endregion

//region 模拟业务逻辑

private data class MockUser(
    val id: Int,
    val name: String,
    //需要保存compose的状态的，就要用State的类型，而非直接的数据类型
    var isChecked: MutableState<Boolean>,
)

private class MockViewModel : ViewModel() {
    private val userList = mutableStateListOf<MockUser>()
    val listFlow = MutableStateFlow(userList)
    fun createUserList() {
        val list = mutableStateListOf<MockUser>()
        repeat(36) {
            list.add(MockUser(it, "用户$it,号", mutableStateOf(false)))
        }
        userList.addAll(list)
        listFlow.update { userList }
    }

    fun removeItem(item: MockUser) {
        userList.remove(item)
    }

}

//endregion

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun SwipeDialogPreview() {
    SwipeDialog_Screen()
}