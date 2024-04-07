package org.zhiwei.compose.ui.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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
