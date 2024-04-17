package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.zhiwei.compose.model.motorCardList
import org.zhiwei.compose.ui.widget.MotorcycleCard
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

/**
 * 动态列 容器，可理解为recycleView的,可以竖向，可以横向，
 */
@Composable
internal fun LazyList_Screen(modifier: Modifier = Modifier) {
    //列效果
//    LazyColumnContent(modifier)
    //需要看row效果的时候，打开下面注释，关闭上面即可
//    LazyRowContent()
    //显示布局layoutInfo的信息
//    LazyRowLayoutInfo()
    //分组列表
    StickerHeaderList()
    //测试Modifier
//    tmpTestModifierRequireIn()
}

@Composable
private fun tmpTestModifierRequireIn() {
    Column {
        Spacer(modifier = Modifier.height(100.dp))
        Row(
            Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            Text(
                "固定200",
                Modifier
                    .height(200.dp)
                    .background(Color.Cyan), Color.Black
            )
            VerticalDivider()
            Text(
                "约定220-300",
                Modifier
                    // 这里是，期望约束值能在范围内，不在的话，也没法。
                    .heightIn(220.dp, 300.dp)
                    .background(Color.Cyan), Color.Green
            )
            VerticalDivider()
            Text(
                "要求220-300",
                Modifier
                    //⚠️注意，requiredHeightIn会强制要求父容器一定要满足自己的要求，如果就算父容器尺寸小于要求条件，超出绘制区域，也要显示它。
                    .requiredHeightIn(220.dp, 300.dp)
                    .background(Color.Cyan), Color.Red
            )
        }
    }
}

//列效果
@Composable
private fun LazyColumnContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),//item之间的间距 指定。
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //lazyColumn可以逐个的添加item，也可以items加一个列表
        items(motorCardList()) { motoEntity ->
            //给每个数据元素，配置item的compose的控件效果
            MotorcycleCard(motor = motoEntity, Modifier.fillMaxWidth())
        }
    }
}

//行效果
@Composable
private fun LazyRowContent() {
    Column(
        Modifier
            .safeContentPadding()
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Title_Text("LazyRow")
        Title_Sub_Text("机车品牌图片模拟")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFFC8ADC4))
                .wrapContentHeight(),
            contentPadding = PaddingValues(
                horizontal = 10.dp,
                vertical = 8.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),//item之间的间距 指定。
        ) {
            //lazyColumn可以逐个的添加item，也可以items加一个列表
            items(motorCardList()) { motoEntity ->
                //给每个数据元素，配置item的compose的控件效果
                MotorcycleCard(motor = motoEntity, Modifier.wrapContentHeight())
            }
        }
    }
}

@Composable
private fun LazyRowLayoutInfo() {
    Column(
        Modifier
            .safeContentPadding()
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Title_Text("LazyRow")
        Title_Sub_Text("机车品牌图片模拟")
        val horizontalPadding = remember {
            mutableFloatStateOf(0f)
        }
        val rowState = rememberLazyListState()
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFFC8ADC4))
                .wrapContentHeight(),
            state = rowState,
            contentPadding = PaddingValues(
                horizontal = horizontalPadding.floatValue.toInt().dp,
                vertical = 8.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),//item之间的间距 指定。
        ) {
            //lazyColumn可以逐个的添加item，也可以items加一个列表
            items(motorCardList()) { motoEntity ->
                //给每个数据元素，配置item的compose的控件效果
                MotorcycleCard(motor = motoEntity, Modifier.wrapContentHeight())
            }
        }
        //seekbar,动态变更lazyRow的contentPadding设置，lazyColumn是类似的
        Slider(
            value = horizontalPadding.floatValue,
            onValueChange = { horizontalPadding.floatValue = it },
            valueRange = 0f..50f
        )
        val observableText by remember {
            derivedStateOf {
                buildString {
                    //这里可感知lazyRow的一些状态变化
                    append("第一个可见的item下标:")
                    appendLine(rowState.firstVisibleItemIndex)//第一个可见的item下标
                    append("第一个可见的item的滑动偏移量:")
                    appendLine(rowState.firstVisibleItemScrollOffset)//第一个可见的item的滑动偏移量
                    val info = rowState.layoutInfo//布局信息
                    append("afterContentPadding:")
                    appendLine(info.afterContentPadding)
                    append("beforeContentPadding:")
                    appendLine(info.beforeContentPadding)
                    append("orientation:")
                    appendLine(info.orientation)//方向
                    append("totalItemsCount:")
                    appendLine(info.totalItemsCount)//所有item的个数
                    append("viewportStartOffset:")
                    appendLine(info.viewportStartOffset)//控件窗口的偏移量
                    appendLine("visibleItemsInfo:")
                    info.visibleItemsInfo.forEach { itemInfo ->
                        appendLine(itemInfo.toString())
                    }
                }
            }
        }
        Text(text = observableText)
    }
}

//演示对lazyColumn的简单操作
@Composable
private fun 操作列表() {
    //可观察的数据源写法，使用remember
    val list = remember { mutableListOf("") }
    //列表的滑动状态
    val scrollState = rememberLazyListState()
    //协程作用域
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        //普通的添加一个item的方式
        item { Text("普通添加item的方式") }
        //给item标记唯一的key ，指定content的类型type，如此，在UI界面数据发生变化，导致需要重组compose的时候，会根据item的id和类型标记，来判断，数据是否已经变化，是否需要重绘item
        item(key = "itemId_666", contentType = String) {
            Text("指定key和type的，普通添加item的方式")
        }
        items(
            count = 10,
            key = { index -> "itemId_$index" },
            contentType = { index: Int -> String }) {
            Text(
                text = "添加一定数量的item，并指定每个item的key和type，按照规则"
            )
        }
        //⚠️使用remember后的数据源的变化，会自动触发列表的刷新，而不需要像RecyclerView那样还要adapter的通知刷新适配器
        items(list) { Text(text = "也可以传入list列表，此处省略了每个item配置key，contentType等") }
        items(arrayOf("")) { Text(text = "也可以传入数组array，此处省略了每个item配置key，contentType等") }
        //伪代码演示 操作列表
        item {
            Row {
                Button(onClick = {
                    //suspend函数，要在协程内操作
                    coroutineScope.launch {
                        //动画的形式，滑动到第一条数据的位置； 同理，可滑动到最后，或者指定item的位置；
//                        list.lastIndex //列表的最后一个数据的index索引
                        scrollState.animateScrollToItem(0)
                    }
                }) {
                    Text(text = "回到顶部")
                }
                Button(onClick = {
                    //如果list是remember的形式的，那么数据变化，会自动触发compose的重绘
                    list.add("")
                    list.remove("a")
                }) {
                    Text(text = "增删列表")
                }
            }
        }

        itemsIndexed(list) { index, item ->
            //这里可以有带有index编号的列表操作方式
        }
    }
}

//带有悬浮标题的，分组式的列表，
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StickerHeaderList() {
    //将数据list按照需要的规则，分组，
    val groupBy = motorCardList().groupBy { it.brand }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .safeContentPadding(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),//item之间的间距 指定。
    ) {
        groupBy.forEach { (header, subList) ->
            stickyHeader {
                Text(
                    ">> 悬浮标题: $header",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = subList.first().color,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(20.dp)
                        .background(Color.LightGray, shape = CutCornerShape(4.dp))
                        .padding(2.dp)
                )
            }
            //lazyColumn可以逐个的添加item，也可以items加一个列表
            items(subList) { motorItem ->
                //给每个数据元素，配置item的compose的控件效果
                MotorcycleCard(motor = motorItem, Modifier.fillMaxWidth())
            }
        }

    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun LazyListPreview() {
    LazyList_Screen()
}