package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun SideDrawer_Screen(modifier: Modifier = Modifier) {
    val scaffoldState = rememberScaffoldState()
    //这里是用material库的scaffold，而非Material3的；scaffold可理解为App界面的脚手架，UI框架或者插槽面板。
    androidx.compose.material.Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {},
        bottomBar = {},
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }, shape = CircleShape) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,//是否显示在bottomBar本体上，而不是Top上，
        drawerContent = {},
        drawerGesturesEnabled = true,//是否启用手势可显示侧边栏
        drawerShape = RoundedCornerShape(5.dp),
        drawerElevation = 2.dp,
        drawerBackgroundColor = Color.White,
        drawerContentColor = Color.Cyan,
        backgroundColor = Color.White,
        contentColor = Color.Yellow,
    ) {
        //随便写个使用paddingValues的函数，如果不使用，IDE会提示；因为按照设计理念，是要使用这个padding值的
        check(it.calculateBottomPadding() > 0.dp)

    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun SideDrawerPreview() {
    SideDrawer_Screen()
}