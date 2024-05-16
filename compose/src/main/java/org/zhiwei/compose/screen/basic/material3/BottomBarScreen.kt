package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FabPosition
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

//主要演示bottomNavigation，BottomAppBar的控件

@Composable
fun BottomBar_Screen(modifier: Modifier = Modifier) {
    MaterialShow(modifier)
}

//region material3的控件演示
@Composable
private fun BottomBarShow() {
    //这里面演示了几种BottomNavigationView和BottomAppBar的使用，material的库控件，非material3的
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        //material3中，没有了BottomNavigationView；这里是material的库中的
        Title_Text(title = "BottomBar的演示")
        Title_Sub_Text(title = "1、material库中的BottomNavigationView")
        Title_Desc_Text(desc = "1.1, 仅显示icon的效果")
        IconOnlyBottomNavigationView()
        Title_Desc_Text(desc = "1.2, 仅显示Text文本的效果")
        TextOnlyBottomNavigationView()
        Title_Desc_Text(desc = "1.3, 图标Icon，选中则显示文本")
        BottomNavigationComponent()
        Title_Desc_Text(desc = "1.4, 图标Icon，选中则显示文本")
        BottomNavigationComponent(true)
        Title_Sub_Text(title = "2、material库中的BottomAppBar")
        MaterialBottomAppBar()
        //material3的BottomAppBar
        Title_Sub_Text(title = "3、material3库中的BottomAppBar")
        BottomBarOne()
        BottomBarTwo()
    }
}

@Composable
private fun BottomBarOne() {
    BottomAppBar(
        containerColor = Color.White,
//                contentColor = Color.Yellow,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets(0.dp),
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
        }
        Text(text = "Material3的BottomAppBar")
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(20.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
        }
    }
}

@Composable
private fun BottomBarTwo() {
    BottomAppBar(actions = {
        IconButton(onClick = { /**/ }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
        }
        Text(text = "Material3的BottomAppBar")
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /**/ }) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = null)
        }
        IconButton(onClick = { /**/ }) {
            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = { }, shape = CircleShape) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }
    })
}

//endregion

@Composable
private fun MaterialShow(modifier: Modifier) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            HoleBottomAppBar()
        },
        floatingActionButton = {
            androidx.compose.material.FloatingActionButton(
                onClick = { },
                contentColor = Color.Yellow,
                backgroundColor = Color.Cyan,
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { _ ->
        BottomBarShow()
    }
}

//region material库的演示
@Composable
private fun MaterialBottomAppBar() {
    androidx.compose.material.BottomAppBar(
        backgroundColor = Color.White,
        elevation = 5.dp,
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
        }
        Text(text = "BottomAppBar")
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(20.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
        }
    }
}

@Composable
private fun TextOnlyBottomNavigationView() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val list = listOf("Home", "Map", "Settings")
    BottomNavigation(
        elevation = 2.dp,
        backgroundColor = Color(0xff212121)
    ) {
        list.forEachIndexed { index: Int, text: String ->
            BottomNavigationItem(
                unselectedContentColor = Color(0xffFF6F00),
                selectedContentColor = Color(0xffFFE082),
                icon = {},
                label = { androidx.compose.material.Text(text) },
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                }
            )
        }
    }
}

@Composable
private fun IconOnlyBottomNavigationView() {

    var selectedIndex by remember { mutableIntStateOf(0) }
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Map, Icons.Filled.Settings)

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 1.dp
    ) {
        icons.forEachIndexed { index, imageVector: ImageVector ->
            BottomNavigationItem(
                unselectedContentColor = Color(0xffEF9A9A),
                selectedContentColor = Color(0xffD32F2F),
                icon = { androidx.compose.material.Icon(imageVector, contentDescription = null) },
                label = null,
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                }
            )
        }
    }
}

@Composable
private fun BottomNavigationComponent(alwaysShowLabel: Boolean = false) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val tabContents = listOf(
        "Home" to Icons.Filled.Home,
        "Map" to Icons.Filled.Map,
        "Settings" to Icons.Filled.Settings
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 2.dp
    ) {
        tabContents.forEachIndexed { index, pair: Pair<String, ImageVector> ->
            BottomNavigationItem(
                icon = { androidx.compose.material.Icon(pair.second, contentDescription = null) },
                label = { androidx.compose.material.Text(pair.first) },
                selected = selectedIndex == index,
                alwaysShowLabel = alwaysShowLabel, // 标记是否一直显示label，false的话，只有选中的才显示
                onClick = {
                    selectedIndex = index
                }
            )
        }
    }
}

@Composable
private fun HoleBottomAppBar() {
    androidx.compose.material.BottomAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 4.dp,
        cutoutShape = CircleShape//该属性，只有在BottomAppBar和FloatingActionButton同时在Scaffold中时候的时候，才生效。就是挖孔效果
    ) {
        // 这个就是提高一下图标的亮度;注意⚠️：CompositionLocalProvider提供一个CompositionLocal数据值
        // ，简单理解其作用特点就是，提供的数据用于将数据变化控制在特定的compose作用域内
        // ，从而在多层嵌套的场景下，避免触发其他不必要的compose函数的重组绘制，提升性能。
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            androidx.compose.material.IconButton(
                onClick = { }) {
                androidx.compose.material.Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        androidx.compose.material.IconButton(onClick = { }) {
            androidx.compose.material.Icon(Icons.Filled.Search, contentDescription = null)
        }

        androidx.compose.material.IconButton(onClick = { }) {
            androidx.compose.material.Icon(Icons.Filled.MoreVert, contentDescription = null)
        }
    }
}

//endregion

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun BottomBarPreview() {
    BottomBar_Screen()
}