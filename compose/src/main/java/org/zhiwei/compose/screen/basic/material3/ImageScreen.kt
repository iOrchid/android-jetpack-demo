package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

/**
 * 图像Image相关的使用与演示API
 */
@Composable
internal fun Image_Screen(modifier: Modifier = Modifier) {

    LazyColumn(modifier.fillMaxSize()) {
        item {
            Title_Text(title = "Image")
            //加载图片资源,预览效果的时候，可以根据需要注释部分，放开部分；或者自己写preview函数
            LoadResource()
            //canvas
            Title_Sub_Text(title = "2、通过Canvas画布可以在ImageBitmap上绘制和保存图像")
            Title_Desc_Text(desc = "> 2.1 用painter在imageBitmap上基础绘制")
            val imageBitmap = ImageBitmap.imageResource(id = R.drawable.img_motocycle)
            //自定义painter
            val painter = object : Painter() {

                override val intrinsicSize: Size
                    get() = Size(imageBitmap.width.toFloat(), imageBitmap.height.toFloat())

                override fun DrawScope.onDraw() {
                    drawImage(imageBitmap)
                    drawLine(
                        color = Color.Red,
                        start = Offset(0f, 0f),
                        end = Offset(imageBitmap.width.toFloat(), imageBitmap.height.toFloat()),
                        strokeWidth = 5f,
                    )
                    drawCircle(Color.Green, radius = 100f)
                }
            }
            Image(
                painter = painter,
                contentDescription = null
            )
            Title_Desc_Text(desc = "> 2.2 通过canvas在imageBitmap上自由绘制")

        }
    }
}

/**
 * 加载图片资源
 */
@Composable
private fun LoadResource() {
    Title_Sub_Text(title = "1、Image控件可用于展示位图bitmap、vector矢量图和painter绘制图等")
    Title_Desc_Text(desc = "> 1.1 使用painter加载图片资源")
    Image(
        painter = painterResource(id = R.drawable.img_motocycle),
        contentDescription = "摩托车图片"
    )
    Title_Desc_Text(desc = "> 1.2 加载矢量图vector")
    Row {
        Image(
            //可以直接imageVector构建，
            imageVector = ImageVector.vectorResource(id = R.drawable.img_beaker),
            contentDescription = "烧杯"
        )
        Image(
            //也可以作为painterResource构建
            painter = painterResource(id = R.drawable.img_electrocardiogram),
            contentDescription = "心电图"
        )
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.img_nurse),
            contentDescription = "护士"
        )
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.img_test_tube),
            contentDescription = "试管"
        )
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.img_location),
            contentDescription = "定位"
        )
    }
    Title_Desc_Text(desc = "> 1.3 加载ImageBitmap")
    Image(
        bitmap = ImageBitmap.imageResource(id = R.drawable.img_moto_girl),
        contentDescription = null
    )
}


@Preview(showBackground = true)
@Composable
private fun ImageScreenPreview() {
    Image_Screen(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}