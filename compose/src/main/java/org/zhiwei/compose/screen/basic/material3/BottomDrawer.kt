package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Text

//类似于BottomSheet的，Drawer，从底部滑出的抽屉控件

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MaterialBottomDrawerUI(drawerValue: BottomDrawerValue = BottomDrawerValue.Closed) {
    //material3没有这个控件,此处也是单独演示BottomDrawer的使用，
    // 可以结合到必要的业务容器,比如bottomBar，或者其他控件；
    //todo ⚠️，还有其他material 或material3的类似控件，触类旁通，不再一一演示。
    val drawSate = rememberBottomDrawerState(initialValue = drawerValue)
    BottomDrawer(
        drawerContent = { Button_Screen() },
        drawerState = drawSate,
        gesturesEnabled = drawSate.isClosed//todo ⚠️ 手势的启用与否，这里在drawer隐藏时候，启用，那么显示出来就不能滑动关闭了。该属性可用于处理包含滑动冲突的场景
    ) {
        Column(Modifier.safeContentPadding()) {
            Title_Text(title = "演示BottomDrawer使用")
            Title_Desc_Text(desc = "向上👆滑动，会出现底部的drawer")
        }
    }
}

//可以理解为前后面板的一个面板控件，类似与scaffold
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MaterialBackdropUI(value: BackdropValue = BackdropValue.Concealed) {
    val scaffoldState = rememberBackdropScaffoldState(initialValue = value)
    //此控件也是material，而非material3
    BackdropScaffold(
        appBar = {
            TopAppBar {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
            }
        },
        backLayerContent = {
            Column(verticalArrangement = Arrangement.Center) {
                Title_Text(title = "演示BackDropScaffold的使用")
                Title_Desc_Text(desc = "BackDropScaffold也是Scaffold的一种，里面也有appbar和snackHost")
            }
        },
        frontLayerContent = {
            //前面板
            Column {
                Button_Screen()
            }
        },
        scaffoldState = scaffoldState,
        peekHeight = 300.dp,//可理解为前面板最高的时候，到顶部的距离
        headerHeight = 80.dp//前面板最小高度
    )
}


@OptIn(ExperimentalMaterialApi::class)
private class BackDropValueProvider : PreviewParameterProvider<BackdropValue> {
    override val values: Sequence<BackdropValue>
        get() = sequenceOf(BackdropValue.Revealed, BackdropValue.Concealed)

}

//配置了不同的状态，所以IDE预览可以看到不同的效果
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun BackDropPreview(@PreviewParameter(BackDropValueProvider::class) value: BackdropValue) {
    MaterialBackdropUI(value)
}

@OptIn(ExperimentalMaterialApi::class)
private class ThisDrawerValue : PreviewParameterProvider<BottomDrawerValue> {
    override val values: Sequence<BottomDrawerValue>
        get() = sequenceOf(
            BottomDrawerValue.Open,
            BottomDrawerValue.Closed,
            BottomDrawerValue.Expanded,
        )

}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun MaterialBottomDrawerPreview(
    @PreviewParameter(ThisDrawerValue::class)
    drawerValue: BottomDrawerValue,
) {
    MaterialBottomDrawerUI(drawerValue)
}