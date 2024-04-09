package org.zhiwei.compose.model

import androidx.compose.ui.graphics.Color
import org.zhiwei.compose.R
import kotlin.random.Random


/**
 * 摩托车卡片相关的数据类
 */
internal data class MotorcycleCardEntity(
    val id: Int,//数据id
    val brand: String,//品牌
    val color: Color,//颜色
    val desc: String,//描述
    val imgResId: Int,//图片资源的res id
)

private val motorImages = arrayOf(
    R.drawable.m001,
    R.drawable.m002,
    R.drawable.m003,
    R.drawable.m004,
    R.drawable.m005,
    R.drawable.m006,
    R.drawable.m007,
    R.drawable.m008,
)
private val motorBrands = arrayOf(
    "春风",
    "钱江",
    "无极",
    "赛科龙",
    "凯越",
    "豪爵",
    "长江",
    "宗申",
    "宝马",
    "杜卡迪",
    "哈雷",
    "印第安",
    "贝纳利",
    "川崎",
    "铃木",
    "雅马哈",
    "奥古斯塔"
)

internal fun motorCardList(): List<MotorcycleCardEntity> {
    val list = mutableListOf<MotorcycleCardEntity>()
    repeat(100) { index ->
        list.add(
            MotorcycleCardEntity(
                id = index,
                brand = motorBrands[index % motorBrands.size],
                color = randomColor(),
                desc = "${motorBrands[index % motorBrands.size]} 品牌的摩托车🏍️,🤩🐎",
                imgResId = motorImages[index % motorImages.size],
            )
        )
    }
    return list
}

//简单的模拟生成随机色
private fun randomColor(): Color {
    return Color(
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
    )
}