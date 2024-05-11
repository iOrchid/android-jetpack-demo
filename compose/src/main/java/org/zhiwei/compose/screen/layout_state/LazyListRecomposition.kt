package org.zhiwei.compose.screen.layout_state

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.random.Random


/**
 * 演示动态列表的数据刷新和UI一起感知数据变化的时候，重组的元素范围
 */
@Composable
internal fun LazyListRc_Screen(modifier: Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        Title_Text(title = "LazyList Recomposition")
        Title_Sub_Text(title = "列表的元素自身数据变化，会触发自己compose重组，滑动scroll或者数据变化也会触发重组。但是如果不特殊处理，即使列表内容数据为变化，整个外部UI变化数据，也可能引起list的整体重组，会造成资源消耗浪费。")
        val viewModel = MyViewModel()
        MainScreen(viewModel = viewModel)
    }
}

@Composable
private fun MainScreen(viewModel: MyViewModel) {

    var counter by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.padding(8.dp),

        ) {
        val people = viewModel.people

        Text(text = "Counter $counter")

        Button(onClick = { counter++ }) {
            Text(text = "增量 $counter")
        }

        Spacer(modifier = Modifier.height(10.dp))

        //⚠️关键点就在于此，虽然上面按钮变化counter的时候，ListScreen的数据源并无变更，但是也会被触发重组。
        // 原因在ListScreen接收的数据也不是stable的，前面章节UI_CommonStable中有类似演示。

        //定义remember状态化的，lambda形式的点击，可以形成compose作用域，来隔离重组recompose的不必要触发
        val itemClick = remember {
            { index: Int ->
                viewModel.toggleSelection(index)
            }
        }
        ListScreen(
            people = people,
//            onItemClick = viewModel::toggleSelection//如果是这样的方式，就会有
            onItemClick = itemClick//使用remember的lambda的形式，来防止不必要的触发重组
        )
    }
}

@Composable
private fun ListScreen(
    people: List<Person>,//SnapshotStateList<Person>,此处也可以使用SnapshotStateList
    onItemClick: (Int) -> Unit,
) {

    SideEffect {
        println("🍌 外层ListScreen容器 重组 ...$people")
    }

    Column {
        Text(
            text = "标题Title",
            modifier = Modifier.border(2.dp, randomColor()),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .border(3.dp, randomColor(), RoundedCornerShape(8.dp))
        ) {
            items(
                items = people,
                key = { it.hashCode() }
            ) {
                ListItem(item = it, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun ListItem(item: Person, onItemClick: (Int) -> Unit) {

    SideEffect {
        println("🍎 ListItem触发重组 ${item.id}, selected: ${item.isSelected}")
    }

    Column(
        modifier = Modifier
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .border(2.dp, randomColor(), RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick(item.id)
                }
                .padding(8.dp)
        ) {
            Text("Index: ${item.id}, ${item.name}", fontSize = 20.sp)
            if (item.isSelected) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .background(Color.Red, CircleShape),
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.Green,
                )
            }
        }
    }
}

data class Person(val id: Int, val name: String, val isSelected: Boolean = false)

class MyViewModel : ViewModel() {

    private val initialList = List(30) { index: Int ->
        Person(id = index, name = "Person: $index")
    }

    val people = mutableStateListOf<Person>().apply {
        addAll(initialList)
    }


    fun toggleSelection(index: Int) {
        val item = people[index]
        val isSelected = item.isSelected
        people[index] = item.copy(isSelected = !isSelected)
    }

    // 🔥 If you use list and call updateItemSelection whole list is recomposed
    // when you chance one item selection status
    var personList by mutableStateOf(initialList)

    // 🔥 setting new value to MutableState triggers recomposition for whole LazyColumn
    fun updateItemSelection(id: Int) {
        val newList = personList.map {
            if (it.id == id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        personList = newList
    }

}

//简单的模拟生成随机色
private fun randomColor(): Color {
    return Color(
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
    )
}
