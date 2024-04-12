package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBoatFilled
import androidx.compose.material.icons.filled.DirectionsTransit
import androidx.compose.material.icons.outlined.DeviceHub
import androidx.compose.material.icons.rounded.DeviceThermostat
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Text

//演示使用ListItem控件
//用于构建简单的settings的item条目之类的控件
@Composable
internal fun ListItem_Screen(modifier: Modifier = Modifier) {

    Column(
        Modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Title_Text(title = "ListItem")
        ListItem(headlineContent = { Text(text = "秦楼不见吹箫女，空余上苑风光。粉英金蕊自低昂。东风恼我，才发一衿香。") })
        ListItem(
            headlineContent = {
                //⚠️老规矩，compose函数内，就可以是任意的其他compose组件，但是不同的控件接收的函数方式不同，
                //有的是box容器式的，有的是列/行。这里就是，限定直接收第一个compose控件，后面的就无效了。
                //但是可以使用row/column/box来自己组合,只要它直接子元素是一个就行
//                    Icon(
//                        imageVector = Icons.Default.DirectionsBoatFilled,
//                        contentDescription = null
//                    )
                Text(text = "三十年来麋鹿踪，若为老去入樊笼。")
                Text(text = "五湖春梦扁舟雨，万里秋风两鬓蓬。")
                Text(text = "远志出山成小草，神鱼失水困沙虫。")
                Text(text = "白头博得公车召，不满东方一笑中。")
            },
        )
        ListItem(
            headlineContent = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DirectionsBoatFilled,
                        contentDescription = null
                    )
                    Text(text = "琼窗梦醒留残日，当年得恨何长！碧阑干外映垂杨。暂时相见，如梦懒思量。")
                }
            },
        )
        ListItem(
            headlineContent = {
                Text(text = "嘹唳夜鸿惊，叶满阶除欲二更。一派西风吹不断，秋声，中有深闺万里情。")
            },
            modifier = Modifier.clickable { /*只有写明了clickable，才会有点击效果*/ },
            //就是在主内容上面的类似title的效果的位置，直接收一个子控件
            overlineContent = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.DeviceHub,
                        contentDescription = ""
                    )
                    Text(text = "临江仙·秦楼不见吹箫女")
                }
            },
            shadowElevation = 2.dp
        )
        ListItem(
            headlineContent = {
                Text(text = "片石冷于冰，雨袖霜华旋欲凝。今夜戍楼归梦里，分明，纤手频呵带月迎。")
            },
            modifier = Modifier.clickable { /*只有写明了clickable，才会有点击效果*/ },
            supportingContent = { Text(text = "supportingContent也就是下标脚注内容") },
            shadowElevation = 2.dp
        )
        ListItem(
            headlineContent = {
                Text(text = "百亩庭中半是苔，桃花净尽菜花开。种桃道士归何处，前度刘郎今又来。")
            },
            //开头的位置 控件 类似box的容器
            leadingContent = {
                Icon(
                    imageVector = Icons.Rounded.DeviceThermostat,
                    contentDescription = ""
                )
                Text(text = "开头位置")
            },
            shadowElevation = 2.dp
        )

        ListItem(
            headlineContent = {
                Text(text = "名场阅历莽无涯，心史纵横自一家。秋气不惊堂内燕，夕阳还恋路旁鸦。东邻嫠老难为妾，古木根深不似花。何日冥鸿踪迹遂，美人经卷葬年华。")
            },
            modifier = Modifier.clickable { /*只有写明了clickable，才会有点击效果*/ },
            //尾巴位置的 控件 类似box的容器
            trailingContent = {
                Icon(imageVector = Icons.Filled.DirectionsTransit, contentDescription = "")
                Text(text = "尾巴位置")
            },
            //可以给各部分配置颜色
            colors = ListItemDefaults.colors(containerColor = Color.White),
            tonalElevation = 2.dp,
            shadowElevation = 2.dp
        )
        var switched by remember { mutableStateOf(false) }
        var checked by remember { mutableStateOf(false) }
        //联合其他控件的状态,通过modifier的toggleable来联动内部的switch或checkbox
        ListItem(headlineContent = { Text(text = "组合switch开关") },
            modifier = Modifier.toggleable(value = switched) {
                switched = it
            },
            trailingContent = {
                Switch(
                    checked = switched,
                    onCheckedChange = { switched = it },
                )
            })
        ListItem(headlineContent = { Text(text = "组合Checkbox") },
            modifier = Modifier.toggleable(value = checked) {
                checked = it
            },
            trailingContent = {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it },
                )
            })
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun ListItemPreview() {
    ListItem_Screen()
}