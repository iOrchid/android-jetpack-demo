package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.DrawerState
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun SideDrawer_Screen(modifier: Modifier = Modifier) {

    SideDrawerDemo(modifier)
}


//region sideDrawer
@Composable
private fun SideDrawerDemo(modifier: Modifier) {
    //整个面板管理状态的对象
    val scaffoldState = rememberScaffoldState()
    //有些UI操作是suspend的，需要协程环境
    val coroutineScope = rememberCoroutineScope()
    //这里是用material库的scaffold，而非Material3的；scaffold可理解为App界面的脚手架，UI框架或者插槽面板。
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            TopBarUI(coroutineScope, scaffoldState)
        },
        bottomBar = {
            BottomBarUI()
        },
        snackbarHost = {
            //默认有snackBar的实现，也可以自定义
            SnackbarHost(hostState = it)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }, shape = CircleShape) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,//是否显示在bottomBar本体上，而不是Top上，
        drawerContent = { DrawerContent(scaffoldState.drawerState, navController) },
        drawerGesturesEnabled = true,//是否启用手势可显示侧边栏
        drawerShape = RoundedCornerShape(topEnd = 30.dp),//侧边栏的边角形状
        drawerElevation = 2.dp,
        drawerBackgroundColor = Color.White,
        drawerContentColor = Color.Cyan,
        backgroundColor = Color.White,
        contentColor = Color.Yellow,
    ) {
        //随便写个使用paddingValues的函数，如果不使用，IDE会提示；因为按照设计理念，是要使用这个padding值的
        check(it.calculateBottomPadding() > 0.dp)
        NavHost(navController = navController, startDestination = motorPage) {
            composable(motorPage) { LazyGrid_Screen() }
            composable(listPage) { ListItem_Screen() }
        }
    }
}

@Composable
private fun TopBarUI(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
) {
    TopAppBar(backgroundColor = Color.White) {
        IconButton(onClick = {
            coroutineScope.launch {
                if (scaffoldState.drawerState.isOpen) {
                    scaffoldState.drawerState.close()
                } else {
                    scaffoldState.drawerState.open()
                }
            }
        }) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "点击显示drawer")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
        IconButton(onClick = {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("显示snackBar的信息")
            }
        }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "显示snackbar")
        }
    }
}

@Composable
private fun BottomBarUI() {
    BottomAppBar(
        backgroundColor = Color.White,
        cutoutShape = CircleShape,//结合floatingActionButton和Scaffold来实现挖孔效果
        elevation = 2.dp
    ) {
        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
    }
}

private val motorPage = "MotoPage"
private val listPage = "listPage"

@Composable
private fun DrawerContent(drawerState: DrawerState, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                //不是当前页面，点击后才切换页面
                if (navController.currentDestination?.route != motorPage) {
                    //navigation默认每次navigate都是创建新的fragment的栈，所以这里popBack，避免过多页面；demo而已
                    navController.popBackStack()
                    navController.navigate(motorPage)
                }
                coroutineScope.launch { drawerState.close() }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(Color(0xFFFF9900)),
            contentPadding = PaddingValues(8.dp),
        ) {
            Text(text = "摩托Grid网格列表")
        }
        Button(
            onClick = {
                if (navController.currentDestination?.route != listPage) {
                    navController.popBackStack()
                    navController.navigate(listPage)
                }
                coroutineScope.launch { drawerState.close() }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(Color(0xFF1BA784)),
            contentPadding = PaddingValues(8.dp),
        ) {
            Text(text = "ListItem的页面")
        }

    }
}

//endregion


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun SideDrawerPreview() {
    SideDrawer_Screen()
}