package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.model.MotorcycleCardEntity
import org.zhiwei.compose.model.motorCardList
import org.zhiwei.compose.ui.widget.MotorcycleCard
import org.zhiwei.compose.ui.widget.Title_Text

//网格布局，类似于旧的GridView

@Composable
internal fun LazyGrid_Screen(modifier: Modifier = Modifier) {

    //常规grid方式演示
//    CommonShow(modifier)

    //自定义分栏方式的
    CustomColumnShow()
}

//网格布局,各种item的方式，就不演示了，类似于LazyColumn或LazyRow的，都差不多
@Composable
private fun CommonShow(modifier: Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),//指定分多少列
//        columns = GridCells.FixedSize(80.dp),//指定每个列是多宽，下面的水平 spacedBy 就无效了。
//        columns = GridCells.Adaptive(80.dp),//比较灵活一些，指定最小宽度，根据屏幕来适配，而且这样布局Arrangement会有效果
        modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        //如果是固定列数，可以使用spaceBy来设置水平/竖直方向上，item的间距
        horizontalArrangement = Arrangement.spacedBy(5.dp),//如果上面是FixedSize，那么spaceBy可能无效
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        //可以逐个展开不同的注释，来学习不同的实现样式
//        commonGrid()
//        spanGrid()
        headerGrid()
    }
}

//普通的grid；LazyGridScope.commonGrid()这是kotlin的扩展函数形式，如果kotlin不熟悉的，可以先学习kotlin部分；
private fun LazyGridScope.commonGrid() {
    items(
        items = motorCardList(),
        key = { item: MotorcycleCardEntity -> item.id },
        contentType = { item -> MotorcycleCardEntity::class }
    ) { motorItem ->
        //⚠️ Grid布局中，不论item的卡片设置怎样的大小size，如果是LazyVerticalGrid竖直网格，那么它的item的宽度，就是屏幕根据columns均分（包含间距），
        // 如果是水平的，那么高度是根据屏幕（含间距）均分rows数；
        MotorcycleCard(motor = motorItem, Modifier.size(120.dp))//所以这里item的size，仅能约束一个维度的尺寸。
    }
    //带有item索引标号的方式
//        itemsIndexed(motorCardList()){index: Int, item: MotorcycleCardEntity ->
//        }
}

//制定规则跨栏显示
private fun LazyGridScope.spanGrid() {
    items(
        items = motorCardList(),
        key = { item: MotorcycleCardEntity -> item.id },
        //todo⚠️，如果不写span，那么就是最简单普通的grid布局方式，可以注释掉看看效果。
        span = { item ->
            //maxCurrentLineSpan表示当前item去布局的时候，最多能够去申请占位的跨栏数；
            //maxLineSpan 表示当前grid的分栏最多有几栏
//                Log.d(
//                    "Compose",
//                    "LazyGrid_Screen函数，${item.id}号item的 maxCurrentLineSpan:${this.maxCurrentLineSpan}, maxLineSpan:${this.maxLineSpan}"
//                )
            //根据需要的规则，对特定位置的item设定跨栏的占位
            if (item.id % 7 == 0) GridItemSpan(maxLineSpan) else GridItemSpan(1)
        },
        contentType = { item -> MotorcycleCardEntity::class }
    ) { motorItem ->

        MotorcycleCard(motor = motorItem, Modifier.size(120.dp))//所以这里item的size，仅能约束一个维度的尺寸。
    }
}

//分组标题的grid ， 后续可以研究自定义实现悬浮header的形式
private fun LazyGridScope.headerGrid() {
    items(
        items = motorCardList(),
        key = { item: MotorcycleCardEntity -> item.id },
        span = { item ->
            if (item.id % 7 == 0) GridItemSpan(maxLineSpan) else GridItemSpan(1)
        },
    ) { motorItem ->
        if (motorItem.id % 7 == 0) {
            Title_Text(title = ">> ${motorItem.brand}")
        } else {
            MotorcycleCard(motor = motorItem, Modifier.size(120.dp))
        }
    }
}

@Composable
private fun CustomColumnShow() {
    LazyVerticalGrid(
        //todo ⚠️ 自定义分栏规则，实现 1:2:1分三栏的宽度占比效果，
        columns = object : GridCells {
            override fun Density.calculateCrossAxisCellSizes(
                availableSize: Int,//总的可用空间，LazyVerticalGrid是宽，LazyVerticalRow就是高
                spacing: Int,//每个间距，可能未定，可能是下面spaceBy设置的。
            ): List<Int> {
                // 假设需要分三栏，那么就是有两个间距
                // |元素|间隔|元素|间隔|元素|
                // 计算一下所有元素占据的空间
                val availableSizeWithoutSpacing = availableSize - 2 * spacing
                // 两头两个item占空间 =  剩余空间（总空间-间隔）/ 分栏比例基数。1:2:1就是4个基本单位
                val smallSize = availableSizeWithoutSpacing / 4
                // 中间大的 就是占用两个单位，2*smallSize
                val largeSize = availableSizeWithoutSpacing / 2
                //list的size是多少，就是分多少个栏
                return listOf(smallSize, largeSize, smallSize)
            }
        },
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        //如果是固定列数，可以使用spaceBy来设置水平/竖直方向上，item的间距
        horizontalArrangement = Arrangement.spacedBy(5.dp),//如果上面是FixedSize，那么spaceBy可能无效
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(
            items = motorCardList(),
            key = { item: MotorcycleCardEntity -> item.id },
            span = { item ->
                if (item.id % 7 == 0) GridItemSpan(maxLineSpan) else GridItemSpan(1)
            },
        ) { motorItem ->
            if (motorItem.id % 7 == 0) {
                Title_Text(title = ">> ${motorItem.brand}")
            } else {
                MotorcycleCard(motor = motorItem, Modifier.size(120.dp))
            }
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun LazyGridPreview() {
    LazyGrid_Screen()
}