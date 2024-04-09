package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.model.motorCardList
import org.zhiwei.compose.ui.widget.MotorcycleCard

/**
 * 动态列 容器，可理解为recycleView的竖向方式
 */
@Composable
internal fun LazyColumn_Screen(modifier: Modifier = Modifier) {

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),//item之间的间距 指定。
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //lazyColumn可以逐个的添加item，也可以items加一个列表
        items(motorCardList()) { motoEntity ->
            //给每个数据元素，配置item的compose的控件效果
            MotorcycleCard(motor = motoEntity)
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun LazyColumnPreview() {
    LazyColumn_Screen()
}