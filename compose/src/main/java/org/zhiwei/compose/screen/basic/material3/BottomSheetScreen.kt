package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun BottomSheet_Screen(modifier: Modifier = Modifier) {
    //逐一注释，单个运行看效果
//    Material3SheetUI(modifier)
    //material的控件，这里还有floatActionButton的插槽
//    MaterialSheetUI(modifier)
//    Material3ModalBottomSheetUI()
//    MaterialModalBottomSheetUI()
//    MaterialBottomDrawerUI()
    MaterialBackdropUI()
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Material3SheetUI(
    modifier: Modifier,
    sheetValue: SheetValue = SheetValue.PartiallyExpanded,
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        //这里自定义state值，是为了演示IDE预览效果
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = sheetValue,
            skipHiddenState = false
        )
    )
    //BottomSheetScaffold类似于scaffold就是一个可以有bottomSheet插槽的UI脚手架，其还有topBar和snackBar
    BottomSheetScaffold(
        sheetContent = {
            //todo ⚠️这里设置sheet内容的高度，使用heightIn，min最小值是对sheet无效，但是max值，会决定sheet展开后的高度值
            //但是作用于lazyList（lazyColumn/LazyRow）是无效的，
            Column(Modifier.heightIn(50.dp, 500.dp)) {
                ListItem_Screen()
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 50.dp,//默认显示的sheet的高度
    ) {
        Column(modifier) {
            Title_Text(title = "Material3Sheet显示当前sheet的状态")
            Title_Sub_Text(title = "1、是否可见isVisible：${scaffoldState.bottomSheetState.isVisible}")
            Title_Sub_Text(title = "2、hasExpandedState：${scaffoldState.bottomSheetState.hasExpandedState}")
            Title_Sub_Text(title = "3、当前状态值currentValue：${scaffoldState.bottomSheetState.currentValue}")
            Title_Sub_Text(title = "4、hasPartiallyExpandedState：${scaffoldState.bottomSheetState.hasPartiallyExpandedState}")
            Title_Sub_Text(title = "5、展开进度requireOffset：${scaffoldState.bottomSheetState.requireOffset()}")
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun MaterialSheetUI(modifier: Modifier) {
    val scaffoldState2 = androidx.compose.material.rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val showSheet = { coroutineScope.launch { scaffoldState2.bottomSheetState.expand() } }
    val closeSheet = { coroutineScope.launch { scaffoldState2.bottomSheetState.collapse() } }
    androidx.compose.material.BottomSheetScaffold(
        sheetContent = { ListItem_Screen() },
        scaffoldState = scaffoldState2,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (scaffoldState2.bottomSheetState.isCollapsed) {
                    showSheet.invoke()
                } else {
                    closeSheet.invoke()
                }
            }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(modifier) {
            Title_Text(title = "显示当前sheet的状态")
            Title_Sub_Text(title = "1、isCollapsed：${scaffoldState2.bottomSheetState.isCollapsed}")
            Title_Sub_Text(title = "2、isExpanded：${scaffoldState2.bottomSheetState.isExpanded}")
            Title_Sub_Text(title = "3、当前状态值currentValue：${scaffoldState2.bottomSheetState.currentValue}")
            Title_Sub_Text(title = "4、requireOffset：${scaffoldState2.bottomSheetState.requireOffset()}")
            Title_Sub_Text(title = "5、展开进度progress：${scaffoldState2.bottomSheetState.progress}")
        }
    }
}


//todo ⚠️，可以参照SideDrawerScreen.kt中，根据需要，
// 自定义某些控件的一些状态的provider，添加到预览函数内，就可以在IDE中查看不同的状态预览；
// 这里可以定义sheet展开与关闭的状态
@OptIn(ExperimentalMaterial3Api::class)
private class SheetStateProvider : PreviewParameterProvider<SheetValue> {
    override val values: Sequence<SheetValue>
        get() = sequenceOf(SheetValue.Hidden, SheetValue.PartiallyExpanded, SheetValue.Expanded)

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun Material3SheetPreview(
    @PreviewParameter(SheetStateProvider::class)
    sheetValue: SheetValue,
) {
    Material3SheetUI(Modifier, sheetValue)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun BottomSheetPreview() {
    BottomSheet_Screen()
}