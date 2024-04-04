package org.zhiwei.compose.screen.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 演示行、列、箱 容器控件的UI页面
 */
//想看预览效果也可以直接在composable的函数上加上preview注解
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun BoxColumnRowScreen(modifier: Modifier = Modifier) {
    //最外层还是使用一个竖直排列元素的列 容器，可超屏滑动
    LazyColumn {
        //LazyColumn内部可以代码形式的items，也可以单个item一个个的加,item内部也就保持和外部LazyXXX的排列属性；
        //即对于LazyColumn，Item内就是列的方式，在LazyRow就是行的排列方式
        item {
            //Text文本元素，字体内容，字号大小，字重，
            Text(text = "Row", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Row也就是一个Compose的行排列布局容器，将子元素按照设定水平排放，可设置垂直位置方式",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            // todo 水平排布元素，而竖直有对齐方式，因为modifier都是默认wrapContent的，如不设置height，这里可能看不出竖直对齐的差异
            //Row布局容器的使用，演示排版start的布局方式,即元素都拥挤靠前
            RowTextWithArrangementAndAlignment(Arrangement.Start, Alignment.Top)
            //Row布局容器的使用，演示排版End的布局方式，即元素都拥挤考后
            RowTextWithArrangementAndAlignment(Arrangement.End, Alignment.CenterVertically)
            //Row布局容器的使用，演示排版Center的布局方式，即元素都拥挤居中
            RowTextWithArrangementAndAlignment(Arrangement.Center, Alignment.Bottom)
            //Row布局容器的使用，演示排版SpaceEvenly的布局方式，即元素外的剩余空间平均分配,竖直方向按比例排放
            RowTextWithArrangementAndAlignment(
                Arrangement.SpaceEvenly,
                BiasAlignment.Vertical(-0.2f)
            )
            //Row布局容器的使用，演示排版SpaceEvenly的布局方式，即每个元素两边的空间是一样的,竖直方向按比例排放
            RowTextWithArrangementAndAlignment(
                Arrangement.SpaceAround,
                BiasAlignment.Vertical(0.3f)
            )
            //Row布局容器的使用，演示排版SpaceEvenly的布局方式，即元素间的空间大小一致。,竖直方向按比例排放
            RowTextWithArrangementAndAlignment(
                Arrangement.SpaceBetween,
                BiasAlignment.Vertical(0.8f)
            )
        }
        //其实都可以写在一个item{}内，这里为了代码形式上的区分，所以写了多个item块
        item {
            Text(text = "Column", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Column也就是一个Compose的列排列布局容器，将子元素按照设定竖直方向排放,可设置水平位置方式",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            //Column布局容器的使用，演示排版top的布局方式,即元素都拥挤靠前,
            ColumnTextWithArrangementAndAlignment(Arrangement.Top, Alignment.Start)
            //Column布局容器的使用，演示排版bottom的布局方式，即元素都拥挤考后
            ColumnTextWithArrangementAndAlignment(Arrangement.Bottom, Alignment.End)
            //Column布局容器的使用，演示排版Center的布局方式，即元素都拥挤居中
            ColumnTextWithArrangementAndAlignment(Arrangement.Center, Alignment.CenterHorizontally)
            //Column布局容器的使用，演示排版SpaceEvenly的布局方式，即元素外的剩余空间平均分配,水平方向按比例排放的
            ColumnTextWithArrangementAndAlignment(
                Arrangement.SpaceEvenly,
                BiasAlignment.Horizontal(0.8f)
            )
            //Column布局容器的使用，演示排版SpaceEvenly的布局方式，即每个元素两边的空间是一样的,水平方向按比例排放的
            ColumnTextWithArrangementAndAlignment(
                Arrangement.SpaceAround,
                BiasAlignment.Horizontal(-0.2f)
            )
            //Column布局容器的使用，演示排版SpaceEvenly的布局方式，即元素间的空间大小一致。,水平方向按比例排放的
            ColumnTextWithArrangementAndAlignment(
                Arrangement.SpaceBetween,
                BiasAlignment.Horizontal(0.3f)
            )
        }

        item {
            Text(text = "Box", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Box是一个Compose的堆栈的布局容器，将子元素层叠摆放。",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            //Box同样是三个属性值modifier修饰符，contentAlignment对齐方式，propagateMinConstraints
            BoxWithAlignment(Alignment.TopStart, false)//默认就是topStart
            BoxWithAlignment(Alignment.Center, false)
            BoxWithAlignment(Alignment.BottomEnd, false)
            //还有其他好几种Alignment，同理可知。Top有三种，center有三种，bottom有三种；
            //可以给每个子元素，单独设置对齐方式，通过Modifier的align属性
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)) {
                Text(
                    text = "Kotlin",
                    modifier = Modifier
                        .size(160.dp)
                        .background(Color(0xFF1976D2))
                        .align(Alignment.TopCenter),
                    color = Color.White
                )
                Text(
                    text = "Jetpack",
                    modifier = Modifier
                        .size(130.dp)
                        .background(Color(0xFF2196F3))
                        .align(Alignment.TopEnd),
                    color = Color.White
                )
                Text(
                    text = "Compose",
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFF64B5F6))
                        .align(Alignment.Center),
                    color = Color.White
                )
                //Box容器中，最后出现的元素，会层叠在最上面。这就是堆栈的效果。
                Text(
                    text = "Android",
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFF64B5CF))
                        .align(Alignment.BottomStart),
                    color = Color.White
                )
            }
        }
    }
}


/**
 * 简单封装一个compose的代码块，这也是compose的一个有点，UI部分的封装复用比较方便
 * Row有三个属性参数，modifier修饰符，horizontalArrangement水平排版的方式，verticalAlignment竖直对齐的方式；
 * 这里需要注意的是，水平排版需要的是Arrangement.Horizontal的值，竖直对齐需要的是Alignment.Vertical的值，与Column不同
 * todo 注意对齐方式可使用BiasAlignment.Vertical的，自定义bias偏移量，-1～1，-1就是top，0 center，1 bottom
 */
@Composable
private fun RowTextWithArrangementAndAlignment(
    arrangement: Arrangement.Horizontal,
    alignment: Alignment.Vertical,
) {
    Text(
        text = "$arrangement 以及 $alignment".replace('#', '.'),
        fontSize = 12.sp,
        fontWeight = FontWeight.ExtraLight
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Gray),
        horizontalArrangement = arrangement,
        verticalAlignment = alignment
    ) {
        Text(
            text = "Kotlin",
            modifier = Modifier
                .background(Color(0xFFFF9800))
                .padding(4.dp)
        )
        Text(
            text = "Jetpack",
            modifier = Modifier
                .background(Color(0xFFFFA726))
                .padding(4.dp)
        )
        Text(
            text = "Compose",
            modifier = Modifier
                .background(Color(0xFFFFB74D))
                .padding(4.dp)
        )
        Text(
            text = "Android",
            modifier = Modifier
                .background(Color(0xFFFFB7E6))
                .padding(4.dp)
        )
    }
}

/**
 * Column也有三个属性参数，modifier修饰符，verticalArrangement竖直排版的方式，horizontalAlignment水平对齐的方式
 * todo 注意对齐方式可使用BiasAlignment.Horizontal，自定义bias偏移量，-1～1，-1就是start，0 center，1 end
 */
@Composable
private fun ColumnTextWithArrangementAndAlignment(
    arrangement: Arrangement.Vertical,
    alignment: Alignment.Horizontal,
) {
    Text(
        text = "$arrangement 以及 $alignment".replace('#', '.'),
        fontSize = 12.sp,
        fontWeight = FontWeight.ExtraLight
    )
    //想看出列排版的效果，所以这里加了背景色，设置了高度，不然默认的收wrapContent，就看不出效果了。
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.LightGray),
        verticalArrangement = arrangement,
        horizontalAlignment = alignment
    ) {
        Text(
            text = "Kotlin",
            modifier = Modifier
                .background(Color(0xFF8BC34A))
                .padding(4.dp)
        )
        Text(
            text = "Jetpack",
            modifier = Modifier
                .background(Color(0xFF9CCC65))
                .padding(4.dp)
        )
        Text(
            text = "Compose",
            modifier = Modifier
                .background(Color(0xFFAED581))
                .padding(4.dp)
        )
        Text(
            text = "Android",
            modifier = Modifier
                .background(Color(0xFFAED599))
                .padding(4.dp)
        )
    }
}

/**
 * Box演示代码封装，可设置内部元素对齐方式
 * @param alignment 是Alignment的值，与Alignment.Horizontal或Alignment.Vertical不是一个。
 * @param propagate 设置是否传递最小约束给内部元素，默认false，
 * 如果true,那么子元素自身的size设置就无效了，Box的size/height/width的约束来控制
 *      可以讲下面的Box的modifier设置为如下Modifier
 *             .height(200.dp)
 *             .wrapContentWidth()
 *             .background(Color.LightGray),
 *  然后分别使用true和false，就能看处效果了。
 */
@Composable
private fun BoxWithAlignment(alignment: Alignment, propagate: Boolean) {
    Box(
        modifier = Modifier
            .size(200.dp)//这里是用size，为的是更好的看布局效果
            .background(Color.LightGray),
        contentAlignment = alignment,
        propagateMinConstraints = propagate
    ) {
        Text(
            text = "Kotlin",
            modifier = Modifier
                .size(160.dp)
                .background(Color(0xFF1976D2)),
            color = Color.White
        )
        Text(
            text = "Jetpack",
            modifier = Modifier
                .size(130.dp)
                .background(Color(0xFF2196F3)),
            color = Color.White
        )
        Text(
            text = "Compose",
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFF64B5F6)),
            color = Color.White
        )
        //Box容器中，最后出现的元素，会层叠在最上面。这就是堆栈的效果。
        Text(
            text = "Android",
            modifier = Modifier
                .size(80.dp)
                .background(Color(0xFF64B5CF)),
            color = Color.White
        )
    }
}
