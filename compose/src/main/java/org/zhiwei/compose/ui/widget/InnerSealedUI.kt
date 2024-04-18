package org.zhiwei.compose.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.model.MotorcycleCardEntity

//简单封装定义一些项目内可用的compose基础元素配置或组合

/**
 * 用于demo演示标题的text的封装，compose-ui和compose-material3分别都有Text控件，
 * 可根据业务需要选择是否有material3，或者用基本的。
 */
@Composable
internal fun Title_Text(title: String) {
//Text文本元素，字体内容，字号大小，字重，
    Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
}

@Composable
internal fun Title_Sub_Text(title: String) {
//Text文本元素，字体内容，字号大小，字重，
    Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Normal)
}

@Composable
internal fun Title_Desc_Text(desc: String) {
    Text(
        text = desc,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
}

/**
 * 摩托车🏍️展示卡片
 */
@Composable
internal fun MotorcycleCard(
    motor: MotorcycleCardEntity,
    modifier: Modifier = Modifier,
) {
    Card(
        //只有配置clickable才会有点击效果
        modifier = modifier.clickable { },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        //因为右上角有个喜欢 💗按钮，所以用box容器
        Box(contentAlignment = Alignment.TopEnd) {
            Column(Modifier.background(Color.White)) {
                Image(
                    painter = painterResource(id = motor.imgResId),
                    contentDescription = motor.desc,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = motor.brand,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = motor.color
                )
                Text(
                    text = motor.desc,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = motor.color
                )
            }
            val isLike = remember { mutableStateOf(false) }
            IconToggleButton(checked = isLike.value, onCheckedChange = { isLike.value = it }) {
                Icon(
                    imageVector = if (isLike.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "",
                    tint = motor.color
                )
            }
        }
    }
}

//用于标记，当前代码适用于IDE预览，还是实际代码环境
@Stable
internal val isInPreview @Composable get() = LocalInspectionMode.current