package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Text

/**
 * 演示material3的Text文本框的使用
 * 颜色值采取自[中国传统色](https://colors.masantu.com/#/)
 * 这个也不错[中国传统色](https://www.zhongguose.com/)
 */
@Composable
internal fun Text_Screen(modifier: Modifier = Modifier) {
    LazyColumn(modifier.fillMaxSize()) {
        item {
            Title_Text(title = "Text")
            Title_Desc_Text(desc = "Font Color 字体颜色")
            TextRow {
                Text(text = "玄色", color = Color(0XFF622A1D))
                Text(text = "景泰蓝", color = Color(0XFF8ABCD1))
                Text(text = "雪白", color = Color(0XFFF2FDFF))
                Text(text = "木槿紫", color = Color(0XFFA381BA))
                Text(text = "朱红", color = Color(0XFFED5126))
                Text(text = "苍青", color = Color(0XFF7397AB))
                Text(text = "雁灰", color = Color(0XFF80766E))
                Text(text = "玉簪绿", color = Color(0XFFA4CAB6))
                Text(text = "雌黄", color = Color(0XFFFFC64B))
            }
            Title_Desc_Text(desc = "FontSize 字号大小")
            TextRow {
                //字号单位有三种，sp，em，Unspecified，为定义就是默认sp；compose提供了扩展函数，所以可以使用.sp,.em,等
                Text(text = "14sp", fontSize = 14.sp)
                Text(text = "18em", fontSize = 18.em)
                Text(text = "24sp", fontSize = TextUnit(24f, TextUnitType.Sp))
                Text(text = "36sp", fontSize = 36.sp)
            }
            Title_Desc_Text(desc = "FontStyle 字的样式，端正或倾斜")
            TextRow {
                Text(text = "端正", fontStyle = FontStyle.Normal)
                Text(text = "倾斜", fontStyle = FontStyle.Italic)
            }
            Title_Desc_Text(desc = "FontWeight 字重，理解为笔画粗细")
            TextRow {
                Text(text = "Black", fontWeight = FontWeight.Black)
                Text(text = "Bold", fontWeight = FontWeight.Bold)
                Text(text = "ExtraBold", fontWeight = FontWeight.ExtraBold)
                Text(text = "ExtraLight", fontWeight = FontWeight.ExtraLight)
                Text(text = "Light", fontWeight = FontWeight.Light)
                Text(text = "Medium", fontWeight = FontWeight.Medium)
            }
            TextRow {
                Text(text = "Normal", fontWeight = FontWeight.Normal)
                Text(text = "SemiBold", fontWeight = FontWeight.SemiBold)
                Text(text = "Thin", fontWeight = FontWeight.Thin)
                Text(text = "W100", fontWeight = FontWeight.W100)
                Text(text = "W200", fontWeight = FontWeight.W200)
                Text(text = "W300", fontWeight = FontWeight.W300)
            }
            TextRow {
                Text(text = "W400", fontWeight = FontWeight.W400)
                Text(text = "W500", fontWeight = FontWeight.W500)
                Text(text = "W600", fontWeight = FontWeight.W600)
                Text(text = "W700", fontWeight = FontWeight.W700)
                Text(text = "W800", fontWeight = FontWeight.W800)
                Text(text = "W900", fontWeight = FontWeight.W900)
            }
        }
    }
}


/**
 * 简单封装的，用于text一行展示的row控件
 */
@Composable
private fun TextRow(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        content()
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun Text_Screen_Preview() {
    Text_Screen()
}