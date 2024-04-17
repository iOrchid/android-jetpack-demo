package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.LastPage
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.ShortText
import androidx.compose.material.icons.rounded.Webhook
import androidx.compose.material.icons.sharp.DirectionsTransit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

/**
 * Material3库中的topAppbar和Tabs的相关使用演示
 */
@Composable
internal fun TopAppbarTabs_Screen(modifier: Modifier = Modifier, onBack: (() -> Unit) = { }) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Title_Text(title = "TopAppbar")
            WithIcons(onBack)
        }
        item {
            WithMenus()
        }
        item {
            TabsShow()
        }
        item {
            CustomTabsShow()
        }
        item {
            CustomTabs()
        }
        item {
            ScrollTabsShow()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun WithIcons(onBack: () -> Unit) {
    Title_Sub_Text(title = "1、TopAppbar 添加iconButton用作toolbar")
    TopAppBar(
        title = { Text(text = "TopAppbar") },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        },
        actions = {
            //就是理解为菜单
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }

            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Refresh, contentDescription = null)
            }

            IconButton(
                onClick = { /* doSomething() */ }) {
                //此处，如果不设置颜色tint，就会使用topAppbar设定的content的color
                Icon(Icons.Filled.Call, contentDescription = null, tint = Color.Red)
            }
        },
        //可以配置topAppbar内的一些颜色设置
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color.Green,
            containerColor = Color.White,
            actionIconContentColor = Color.Cyan,
            navigationIconContentColor = Color.Yellow,
            scrolledContainerColor = Color.Blue
        )
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun WithMenus() {
    Title_Sub_Text(title = "2、TopAppbar 添加更多菜单")
    //标记是否显示更多菜单
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = "Overflow") },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        },
        actions = {
            //就是理解为菜单
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }

            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
            //更多菜单
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                //item还有一些其他属性，不一一演示
                DropdownMenuItem(
                    text = { Text(text = "打开链接") },
                    onClick = { showMenu = false },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Webhook,
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.LastPage,
                            contentDescription = ""
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "分享App") },
                    onClick = { showMenu = false },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.ShortText,
                            contentDescription = ""
                        )
                    }
                )
            }

        },
        //默认的topAppbar的windowInsets是有一些内边距的。适配手机状态栏等
        windowInsets = WindowInsets(top = 0.dp)
    )
}

@Composable
private fun TabsShow() {

    Title_Sub_Text(title = "3、TabRow和Tab 控件")
    var selectedIndex by remember { mutableIntStateOf(0) }
    TabRow(
        selectedTabIndex = selectedIndex,
//        contentColor = Color.Magenta,
        containerColor = Color.White,
        indicator = { tabPositions ->
            //配置自定义的indicator指示器
            if (selectedIndex < tabPositions.size) {
                TabRowDefaults.PrimaryIndicator(Modifier.tabIndicatorOffset(tabPositions[selectedIndex]))
            }
        },
        //整个tabRow底部的一个分割线
        divider = {
            HorizontalDivider()
        }
    ) {
        //单个tab都可以根据需要，配置不同的样式,单文字/单图标/文字与图标组合
        Tab(
            selected = selectedIndex == 0, onClick = { selectedIndex = 0 },
            text = {
                //这内部是个box容器布局，
                Text(text = "消息")
            },
            icon = {
                //这内部是个box容器布局，
                Icon(
                    imageVector = Icons.Filled.Message,
                    contentDescription = null
                )
            },
        )
        Tab(
            selected = selectedIndex == 1, onClick = { selectedIndex = 1 },
            text = {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Contacts,
                        contentDescription = null
                    )
                    Text(text = "好友")
                }
            },
        )
        Tab(
            selected = selectedIndex == 2, onClick = { selectedIndex = 2 },
            text = { Text(text = "朋友圈") },
        )
        Tab(
            selected = selectedIndex == 2, onClick = { selectedIndex = 2 },
            icon = { Icon(imageVector = Icons.Sharp.DirectionsTransit, contentDescription = null) },
        )
    }

}

@Composable
private fun CustomTabsShow() {

    Title_Sub_Text(title = "4、Tabs")
    var selectedIndex by remember { mutableIntStateOf(0) }
    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Color.White,
    ) {
        //单个tab都可以根据需要，配置不同的样式,单文字/单图标/文字与图标组合
        Tab(
            selected = selectedIndex == 0, onClick = { selectedIndex = 0 },
            text = {
                //这内部是个box容器布局，
                Text(text = "消息")
            },
            icon = {
                //这内部是个box容器布局，
                Icon(
                    imageVector = Icons.Filled.Message,
                    contentDescription = null
                )
            },
            selectedContentColor = Color.Black,
            unselectedContentColor = Color.LightGray
        )

        Text(
            text = "任意控件作为tab",
            modifier = Modifier
                .background(Color.Cyan)
                .clickable { selectedIndex = 1 })

        Box(
            modifier = Modifier
                .background(Color.Green)
                .clickable { selectedIndex = 2 }, contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "tab可以自定义任何compose组件"
            )
        }

    }

}

@Composable
private fun CustomTabs() {

    Title_Sub_Text(title = "5、定制化的tab样式")
    var selectedIndex by remember { mutableIntStateOf(0) }
    val list = listOf("Active", "Completed")
    TabRow(selectedTabIndex = selectedIndex,
        containerColor = Color(0xff1E76DA),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xff1E76DA))
            .padding(1.dp),
        indicator = { tabPositions: List<TabPosition> ->
            Box {}
        }
    ) {
        list.forEachIndexed { index, text ->
            val selected = selectedIndex == index
            Tab(
                modifier = if (selected) Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                else Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xff1E76DA)),
                selected = selected,
                onClick = { selectedIndex = index },
                text = { Text(text = text, color = Color(0xff6FAAEE)) }
            )
        }
    }
}

@Composable
private fun ScrollTabsShow() {

    Title_Sub_Text(title = "6、ScrollTabsRow 可滑动的tabs")
    var selectedIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "Msg",
        "Contacts",
        "Circle",
        "Settings",
        "Video",
        "Story",
        "Moments",
        "Group",
        "Notices",
        "Tools"
    )
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Color.White,
        edgePadding = 0.dp//两边的padding，内边距
    ) {
        tabs.forEachIndexed { index, s ->
            Tab(selectedIndex == index, { selectedIndex = index }) {
                //tab的另一种构建方式，更灵活，内部随意compose,这里是列column形式的容器布局
                Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = null)
                Text(text = s)
            }
        }
    }

}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun TopAppBarPreview() {
    TopAppbarTabs_Screen()
}