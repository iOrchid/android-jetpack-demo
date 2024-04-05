package org.zhiwei.compose.screen.basic

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 用于演示surface面板使用，shape图形，以及常规的click点击事件
 */
@Composable
internal fun Surface_Shape_Clickable_Screen(modifier: Modifier = Modifier) {
    //还是为了更多的UI内容可以滑动，所以使用lazyColumn
    LazyColumn(modifier.fillMaxSize()) {
        item {
            ClickableCase()
            SurfaceCase()
        }
    }
}

/**
 * 点击事件的演示
 */
@Composable
private fun ClickableCase() {
    Text(text = "Clickable", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Text(
        text = "clickable是modifier的一个属性，可以作用于compose的任何组件，但是在modifier的顺序不同，点击范围也不同，比如在padding前后就有差异。",
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
    //通过LocalContext获取当前compose运行环境所依赖的context对象
    val context = LocalContext.current
    //一行对比两个子容器的控件，来感受点击区域的差别，⚠️注意看点击时候的ripple波纹区域
    Row(Modifier.height(120.dp)) {
        //一个列容器，weight = 1 来 和另一个列容器均分整个row控件
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFF388E3C))
                .clickable(onClick = {
                    Toast
                        .makeText(context, "点击了左侧的🫲绿色方块", Toast.LENGTH_SHORT)
                        .show()
                }),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = "来点我撒～"
            )
        }
        //蓝色的方块，注意该方块的clickable的区域，因为前面有padding，所以点击区域就会小于整个方块背景色
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFF1E88E5))
                .padding(15.dp)
                .clickable(onClick = {
                    Toast
                        .makeText(context, "⚡️了右侧🫱的蓝色", Toast.LENGTH_SHORT)
                        .show()
                }),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = "要不点我？"
            )
        }
    }
}

/**
 * 演示surface控件容器的使用
 */
@Composable
private fun SurfaceCase() {
    Text(text = "Surface", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Text(
        text = "1、Surface可理解为绘制面板，它可对其内部控件进行形状的裁剪。",
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
    //使用同一个modifier的基本图形，来设置不同的shape
    Row {
        val modifier = Modifier
            .aspectRatio(1.0f)//宽高比1:1
            .weight(1f)//让内部每个compose组件都占据相等的权重空间
            .padding(12.dp)//加个padding
        //注意compose的shape选择，是androidx.compose包下的，而不是Android传统graphic包下的。当然你选错了IDE也会提示。
        Surface(
            modifier, shape = RectangleShape,
            color = Color(0xFFFDD835)
        ) {
            //这里就简单设置了面板surface的背景色，没有填充内部compose组件，实际上这里面可以设置compose的其他组件
        }
        //圆角矩形的，可指定不同的角，或者所有角的半径
        Surface(
            modifier, shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF4511E)
        ) {}
        // 圆形的shape
        Surface(
            modifier = modifier,
            shape = CircleShape,
            color = Color(0xFF26C6DA)
        ) {}

        //切角矩形，也可以指定四个角不同的其角值，
        Surface(
            modifier = modifier,
            shape = CutCornerShape(10.dp),
            color = Color(0xFF7E57C2)
        ) {}
    }

    Text(
        text = "2、Surface可以通过border给内部控件设置边框，以及shadowElevation投影层深。",
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
    Row {
        val modifier = Modifier
            .aspectRatio(1.0f)
            .weight(1f)
            .padding(12.dp)
        //⚠️再次提示，compose的material和material3好多相同名字的控件，其属性参数可能有不少差异，要学会灵活变通
        Surface(
            modifier, shape = RectangleShape,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 10.dp,//该参数仅当color是.colorScheme.surface的时候，会根据light或dark模式来显示个高亮
            shadowElevation = 5.dp,//这是图形阴影颜色的高度值
            border = BorderStroke(2.dp, Color(0xFFFF6F00))
        ) {}
        //圆角矩形的，可指定不同的角，或者所有角的半径
        Surface(
            modifier, shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF4511E),
            shadowElevation = 8.dp,
            border = BorderStroke(3.dp, Color(0xFF6D4C41))
        ) {}
        // 圆形的shape
        Surface(
            modifier = modifier,
            shape = CircleShape,
            color = Color(0xFF26C6DA),
            shadowElevation = 12.dp,
            border = BorderStroke(5.dp, Color(0xFF004D40))
        ) {}

        //切角矩形，也可以指定四个角不同的其角值，
        Surface(
            modifier = modifier,
            shape = CutCornerShape(10.dp),
            color = Color(0xFF7E57C2),
            shadowElevation = 15.dp,
            border = BorderStroke(3.dp, Color(0xFFd50000))
        ) {}
    }

    Text(
        text = "3、Surface还可以通过contentColor给内部的Text文本或Icon图像指定颜色。",
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),//注意这里padding，下面surface设置颜色显示出来的区域，就不是全部宽度了
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFFDD835),//这是surface面板的背景色，因为有padding，所以不是全尺寸
        contentColor = Color(0xFF26C6DA)//给内部元素指定的颜色
    ) {
        Column {
            Text(text = "surface通过contentColor设定的颜色，就会被内部的Text作为默认色")
            //注意，可以理解surface是为了统一一块UI区域的主题配色之类的，会作用于其内部的compose组件。
            //Icon有此效果，但是Image则无。
            Icon(
                painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
                contentDescription = "Icon图标,默认tint的color就是surface指定的contentColor"
            )
        }
    }

    Text(
        text = "4、Surface面板可以嵌套，层叠，可实现x、y轴的偏移，以及区域的裁剪效果。",
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
    //通过不同的点击区域，来感知surface的裁剪范围
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = {
                Toast
                    .makeText(context, "外层Box容器被点击", Toast.LENGTH_SHORT)
                    .show()
            })
    ) {
        //内部surface进行嵌套，偏移和剪裁
        Surface(
            modifier = Modifier
                .size(150.dp)
                .padding(12.dp)
                .clickable(onClick = {
                    Toast
                        .makeText(context, "点击了黄色的面板", Toast.LENGTH_SHORT)
                        .show()
                }),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFFDD835),
            shadowElevation = 10.dp,
        ) {
            //内部嵌套surface，所以其不会多出外层的surface之外去
            Surface(
                modifier = Modifier
                    .size(80.dp)
                    //进行位置偏移
                    .offset(x = 50.dp, y = (-20).dp)
                    .clickable(onClick = {
                        Toast
                            .makeText(context, "Blue面板Clicked", Toast.LENGTH_SHORT)
                            .show()
                    }),
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF26C6DA),
                shadowElevation = 12.dp,
            ) {}
        }
        //之所以使用Box做最外层容器，就是为了这里更好的显示偏移效果，如果是行列排布，就不那么明显。因为Box默认元素左上角。
        Surface(
            modifier = Modifier
                .size(110.dp)
                .padding(12.dp)
                .offset(x = 110.dp, y = 20.dp)
                .clickable(onClick = {
                    Toast
                        .makeText(context, "红色🌹面板被点击", Toast.LENGTH_SHORT)
                        .show()
                }),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFF4511E),
            shadowElevation = 5.dp,
        ) {
            //内部暂不填充compose组件
        }

    }

}


/**
 * 预览本页面的元素，可根据喜好配置不同的预览模式和背景之类的
 * 但是注意，这里背景色白色为了的AndroidStudio预览清晰，实际运行效果根使用的modifier和theme有关
 */
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(device = Devices.PIXEL_C, showBackground = true)
@Composable
private fun SurfaceClickablePreview() {
    Surface_Shape_Clickable_Screen()
}