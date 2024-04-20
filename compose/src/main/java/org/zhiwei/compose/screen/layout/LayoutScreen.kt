package org.zhiwei.compose.screen.layout

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
import org.zhiwei.compose.model.LayoutScreenUIs

@Composable
fun LayoutScreen(navController: NavController) {
    //外部使用列容器LazyColumn，里面就是代码形式便捷创建多个列;LazyColumn就是类似与传统RecyclerView的列容器 ，
    // 如果多个item超出了屏幕，可实现滑动，而且不用代码手动些什么ViewHolder之类的性能管理缓存。
    LazyColumn {
        //后续会详细学习，LazyColumn内部排列多个item的时候，接收items的数据源，而后是每个item的Compose布局实现
        items(LayoutScreenUIs.layoutCourses()) { model ->
            //每个item这里使用了ListItem，框架提供的简便的一个控件
            ListItem(
                modifier = Modifier
                    .wrapContentHeight()
                    .clickable { navController.navigate(model.title) },
                headlineContent = { Text(text = model.title, fontSize = 14.sp) },
                supportingContent = { Text(text = model.description, fontSize = 12.sp) },
                colors = ListItemDefaults.colors(containerColor = Color(0XFFF2FDFF))
            )
            HorizontalDivider()
        }
    }
}


/**
 * 预览界面，仅作用域AndroidStudio的编辑预览，配置参数可与实际不同，所以具体运行效果要看实际配置
 */
@Preview
@Composable
private fun LayoutScreenPreview() {
    LayoutScreen(navController = rememberNavController())
}