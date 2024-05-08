package org.zhiwei.compose.screen.state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.zhiwei.compose.model.StateScreenUIs

@Composable
fun StateScreen(navController: NavController) {
    LazyColumn {
        //后续会详细学习，LazyColumn内部排列多个item的时候，接收items的数据源，而后是每个item的Compose布局实现
        items(StateScreenUIs.stateCourses()) { model ->
            //每个item这里使用了ListItem，框架提供的简便的一个控件
            ListItem(
                modifier = Modifier
                    .wrapContentHeight()
                    .clickable { navController.navigate(model.title) },
                headlineContent = { Text(text = model.title, fontSize = 14.sp) },
                supportingContent = { Text(text = model.description, fontSize = 12.sp) },
                colors = ListItemDefaults.colors(containerColor = Color(0XFFF0FCFF))
            )
            HorizontalDivider()
        }
    }
}


@Preview
@Composable
private fun StateScreenPreview() {
    StateScreen(navController = rememberNavController())
}