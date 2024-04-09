package org.zhiwei.compose.screen.basic.material3

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
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
            withCanvas()
            //shape图形，也就是通过modifier的clip属性裁剪,或者shadow，等限定shape的方式。
            shapeAndFilter()
            //图层相关
            withGraphics()
            //其他
            scaleType()
            //第三方网络图片加载库
            Title_Sub_Text(title = "6、coil网络图片库")
            val imageUrl =
                "https://img.pconline.com.cn/images/upload/upc/tx/itbbs/1404/29/c19/33702528_1398764805257.jpg"
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                //加载网络图片资源，需要给Image控件一个初始尺寸，不然无从显示
                modifier = Modifier
                    .sizeIn(200.dp, 200.dp)
                    .background(Color(0xFFF0FCFF))
            )
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

@Composable
private fun withCanvas() {
    Title_Sub_Text(title = "2、通过Canvas画布可以在ImageBitmap上绘制和保存图像")
    Title_Desc_Text(desc = "> 2.1 用painter在imageBitmap上基础绘制")
    val imageBitmap = ImageBitmap.imageResource(id = R.drawable.moto_run)
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
            drawCircle(Color.Yellow, radius = 100f)
        }
    }
    Image(
        painter = painter,
        contentDescription = null
    )
    Title_Desc_Text(desc = "> 2.2 通过canvas在imageBitmap上自由绘制")
    val imageBitmap2 = ImageBitmap.imageResource(id = R.drawable.sexy_girl)
    val measurer = rememberTextMeasurer()
    //这里可以注意，⚠️，modifier的限定区域是浅灰色，但是drawImage的尺寸会超出限制范围
    Canvas(
        modifier = Modifier
            .background(Color.LightGray)//画布背景色
            .size(imageBitmap2.width.dp, (imageBitmap2.height / 3).dp)
    ) {
        drawImage(imageBitmap2)
        drawText(
            textMeasurer = measurer,
            text = "自定义绘制的文本，机车帅哥美女一起来啊",
            topLeft = Offset(100f, 200f),
            TextStyle(Color.Yellow),
        )
        //画一个矩形框
        drawRoundRect(
            color = Color.Yellow,
            topLeft = Offset(imageBitmap2.width / 4f, imageBitmap2.height / 4f),
            style = Stroke(width = 5f),
            size = Size(imageBitmap2.width / 2f, imageBitmap2.height / 2f),
            cornerRadius = CornerRadius(5f)
        )

        //使用旧view体系的绘制text的方式
        val paint = android.graphics.Paint().apply {
            textSize = 50f
            color = Color.Red.toArgb()
        }
        drawContext.canvas.nativeCanvas.drawText(
            "旧的view方式drawText",
            center.x / 3,
            center.y / 3,
            paint
        )
    }
    //加一个间距，便于看上面的图片效果
    Spacer(modifier = Modifier.height(50.dp))
    Title_Desc_Text(desc = "> 2.3 旧体系的bitmap的方式")
    //添加水印给图片，并可以保存图片
    val gufengBitmap = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        R.drawable.img_gufeng,
        BitmapFactory.Options()
            .also { it.inPreferredConfig = Bitmap.Config.ARGB_8888;it.inMutable = true })
        .asImageBitmap()
    androidx.compose.ui.graphics.Canvas(gufengBitmap).apply {
        //图片上绘制一个圆
        drawCircle(Offset(1200f, 180f), 100f, Paint().also { it.color = Color.Magenta })
    }
    //显示添加水印操作后的bitmap
    Image(bitmap = gufengBitmap, contentDescription = "", Modifier.size(300.dp))

}

@Composable
private fun shapeAndFilter() {
    Title_Sub_Text(title = "3、Shape&Filter 图形和滤色")
    Title_Desc_Text(desc = "> shape ")
    val icon01 = painterResource(id = R.drawable.avatar01)
    val icon02 = painterResource(id = R.drawable.avatar02)
    val icon03 = painterResource(id = R.drawable.avatar03)
    val icon04 = painterResource(id = R.drawable.avatar04)
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0XFFFFFEFA))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = icon01, contentDescription = "圆角矩形",
            Modifier
                .size(80.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
        )
        Image(
            painter = icon02, contentDescription = "圆形",
            Modifier
                .size(80.dp)
                .shadow(
                    4.dp,
                    CircleShape
                )
        )
        Image(
            painter = icon03, contentDescription = "切角矩形",
            Modifier
                .size(80.dp)
                .clip(
                    CutCornerShape(10.dp)
                )
        )
        //使用自定义的shape，菱形💠
        Image(
            painter = icon04, contentDescription = "菱形",
            Modifier
                .size(80.dp)
                .shadow(
                    2.dp,
                    diamondShape, clip = true
                )
        )
    }
    Title_Desc_Text(desc = "> filter ")
    //颜色过滤，ColorFilter有tint，lighting，colorMatrix；在tint中有BlendMode有28种方式，指定融合方式
    //todo 注意⚠️，该功能，可用于实现图片滤镜效果
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0XFFFFFEFA))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = icon01, contentDescription = "",
            Modifier
                .size(80.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                ),
            colorFilter = ColorFilter.tint(Color.Green, BlendMode.Darken)
        )
        Image(
            painter = icon02, contentDescription = "",
            Modifier
                .size(80.dp)
                .shadow(
                    4.dp,
                    CircleShape
                ),
            colorFilter = ColorFilter.tint(Color.Blue, BlendMode.Lighten)
        )
        Image(
            painter = icon03, contentDescription = "",
            Modifier
                .size(80.dp)
                .clip(
                    CutCornerShape(10.dp)
                ),
            colorFilter = ColorFilter.tint(Color.Green, BlendMode.Difference)
        )
        //使用自定义的shape，菱形💠
        Image(
            painter = icon04, contentDescription = "",
            Modifier
                .size(80.dp)
                .shadow(
                    2.dp,
                    diamondShape, clip = true
                ),
            colorFilter = ColorFilter.tint(Color(0xffEEEEEE), BlendMode.Saturation)
        )
    }
}


@Composable
private fun withGraphics() {
    Title_Sub_Text(title = "4、graphicLayer修饰符，用于图形的变形处理")
    Title_Desc_Text(desc = "rotate旋转")
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0XFFFFFEFA))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //以x轴对称 旋转45度
        Image(
            painter = painterResource(id = R.drawable.avatar01),
            contentDescription = null,
            Modifier
                .size(80.dp)
                .graphicsLayer(rotationX = 45f)
        )
        //以y轴对称 旋转45度
        Image(
            painter = painterResource(id = R.drawable.avatar01),
            contentDescription = null,
            Modifier
                .size(80.dp)
                .graphicsLayer(rotationY = 45f)
        )
        //以z轴对称 旋转45度
        Image(
            painter = painterResource(id = R.drawable.avatar01),
            contentDescription = null,
            Modifier
                .size(80.dp)
                .graphicsLayer(rotationZ = 45f)
        )
        //x,y,z轴 都旋转45度
        Image(
            painter = painterResource(id = R.drawable.avatar01),
            contentDescription = null,
            Modifier
                .size(80.dp)
                .graphicsLayer(
                    rotationX = 45f,
                    rotationY = 45f,
                    rotationZ = 45f,
                )
        )
    }
    Title_Desc_Text(desc = "缩放、平移，视角转换等")
    //todo 此外在graphicsLayer还可以做shape，clip，阴影和renderEffect等更多样的处理
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0XFFFFFEFA))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //scaleX 0.8倍
        Image(
            painter = painterResource(id = R.drawable.avatar01),
            contentDescription = null,
            Modifier
                .size(80.dp)
                .graphicsLayer(scaleX = .8f)
        )
        //scaleY 0.8倍
        Image(
            painter = painterResource(id = R.drawable.avatar01),
            contentDescription = null,
            Modifier
                .size(80.dp)
                .graphicsLayer(scaleY = .8f)
        )
        //x,y方向都平移
        Image(
            painter = painterResource(id = R.drawable.avatar01),
            contentDescription = null,
            Modifier
                .size(80.dp)
                .graphicsLayer(translationX = 10f, translationY = 10f)
        )
        //视角范围，在3d图形效果下，会更清晰的看出差异
        Image(
            painter = painterResource(id = R.drawable.avatar01),
            contentDescription = null,
            Modifier
                .size(80.dp)
                //比如此处，x轴有转角，然后变化不同的cameraDistance，就会看到不一样的视角范围
                .graphicsLayer(cameraDistance = .8f, rotationX = 5f)
        )
    }
    Title_Desc_Text(desc = "设置透明度和blendMode来实现图像的融合挖孔")
    val blueBitmap = ImageBitmap.imageResource(id = R.drawable.composite_src)
    val redBitmap = ImageBitmap.imageResource(id = R.drawable.composite_dst)

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color(0xFFE0F0E9))
            //透明不是1的时候，就会默认使用合成图层展现UI效果。,如果是alpha=1f那么就不会有想要的效果；
            .graphicsLayer(alpha = .99f)
    ) {
        //在canvas内，绘制是逐层叠加的，不同先后顺序，对应不同的层级，所以效果也就不同。
        drawImage(blueBitmap)
        drawImage(redBitmap, blendMode = BlendMode.SrcOut)

        //对比效果，所以给它们两偏移到右侧
        drawImage(blueBitmap, Offset(600f, 0f))
        drawImage(redBitmap, Offset(600f, 0f), blendMode = BlendMode.DstOut)
    }
}


@Composable
private fun scaleType() {
    Title_Sub_Text(title = "5、ContentScale 不同的缩放类型")
    //限定宽度的情况下
    val imageModifier =
        Modifier
            .fillMaxWidth()
            .aspectRatio(4 / 3f)
            .background(Color(0xFFFFFBF0))
    Title_Desc_Text(desc = "Original 原图默认样式")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier
    )
    Title_Desc_Text(desc = "ContentScale.None")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier,
        contentScale = ContentScale.None
    )
    Title_Desc_Text(desc = "ContentScale.Crop")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier,
        contentScale = ContentScale.Crop
    )
    Title_Desc_Text(desc = "ContentScale.FillBounds")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier,
        contentScale = ContentScale.FillBounds
    )
    Title_Desc_Text(desc = "ContentScale.FillHeight")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier,
        contentScale = ContentScale.FillHeight
    )
    Title_Desc_Text(desc = "ContentScale.FillWidth")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier,
        contentScale = ContentScale.FillWidth
    )
    Title_Desc_Text(desc = "ContentScale.Fit")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier,
        contentScale = ContentScale.Fit
    )
    Title_Desc_Text(desc = "ContentScale.Inside")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier,
        contentScale = ContentScale.Inside
    )
    //限定高度的情况下
    val imageModifier2 =
        Modifier
            .height(200.dp)
            .aspectRatio(1f)
            .background(Color(0xFFD5CCD3))
    Title_Desc_Text(desc = "Original 原图默认样式")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier2
    )
    Title_Desc_Text(desc = "ContentScale.None")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier2,
        contentScale = ContentScale.None
    )
    Title_Desc_Text(desc = "ContentScale.Crop")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier2,
        contentScale = ContentScale.Crop
    )
    Title_Desc_Text(desc = "ContentScale.FillBounds")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier2,
        contentScale = ContentScale.FillBounds
    )
    Title_Desc_Text(desc = "ContentScale.FillHeight")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier2,
        contentScale = ContentScale.FillHeight
    )
    Title_Desc_Text(desc = "ContentScale.FillWidth")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier2,
        contentScale = ContentScale.FillWidth
    )
    Title_Desc_Text(desc = "ContentScale.Fit")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier2,
        contentScale = ContentScale.Fit
    )
    Title_Desc_Text(desc = "ContentScale.Inside")
    Image(
        painter = painterResource(id = R.drawable.moto_run),
        contentDescription = null,
        modifier = imageModifier2,
        contentScale = ContentScale.Inside
    )
}


/**
 * 自定义的shape，这是个菱形
 */
private val diamondShape = GenericShape { size: Size, _: LayoutDirection ->
    moveTo(size.width / 2f, 0f)
    lineTo(size.width, size.height / 2f)
    lineTo(size.width / 2f, size.height)
    lineTo(0f, size.height / 2f)
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