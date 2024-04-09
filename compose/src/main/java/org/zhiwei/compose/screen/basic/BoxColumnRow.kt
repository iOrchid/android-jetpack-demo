package org.zhiwei.compose.screen.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
internal fun Box_Column_Row_Screen(modifier: Modifier = Modifier) {
    //最外层还是使用一个竖直排列元素的列 容器，可超屏滑动
    LazyColumn(modifier) {
        //LazyColumn内部可以代码形式的items，也可以单个item一个个的加,item内部也就保持和外部LazyXXX的排列属性；
        //行 即对于LazyColumn，Item内就是列的方式，在LazyRow就是行的排列方式
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
            //Row布局容器的使用，演示排版spacedBy的布局方式，即元素间指定尺寸的间距。
            RowTextWithArrangementAndAlignment(
                Arrangement.spacedBy(10.dp),
                Alignment.CenterVertically
            )
        }
        //列 其实都可以写在一个item{}内，这里为了代码形式上的区分，所以写了多个item块
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
        //箱 堆栈，层叠的方式
        item {
            Text(text = "Box", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Box是一个Compose的堆栈的布局容器，将子元素层叠摆放。",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            //Box同样是三个属性值modifier修饰符，contentAlignment对齐方式，propagateMinConstraints
            Text(
                text = "这里演示的是Alignment.TopStart对齐方式",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            BoxWithAlignment(Alignment.TopStart, false)//默认就是topStart
            Text(
                text = "这里演示的是Alignment.Center对齐方式",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            BoxWithAlignment(Alignment.Center, false)
            Text(
                text = "这里演示的是Alignment.BottomEnd对齐方式",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            BoxWithAlignment(Alignment.BottomEnd, false)
            //还有其他好几种Alignment，同理可知。Top有三种，center有三种，bottom有三种；
            //可以给每个子元素，单独设置对齐方式，通过Modifier的align属性
            Text(
                text = "Box也可以通过modifier的align对齐方式作用于单个的子元素，不同方式。",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
            ) {
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

        //边距 padding 和margin，其实在compose里是没有margin的概念，而是通过外层容器的padding来实现
        item {
            //写一个行容器，里面分三部分来演示
            Text(text = "Padding和Margin", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "在Compose中可通过modifier设置padding来控制内部容器的布局可用空间，没有margin属性，设计理念如此，可通过padding实现效果。",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            PaddingAndMargin()
        }
        // 阴影效果和简单远郊切角,shadow是modifier的一个属性，里面可设置elevation和shape，
        //shape有常用的圆角，矩形，切角，还可以自定义shape,默认的是RectangleShape矩形
        item {
            Text(text = "Shadow和Shape", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Modifier是很重要且强大的修饰符，可配置Compose组件的shadow阴影，内可设置图形shape，阴影elevation。",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            ShadowSpacerAndWeight()
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
                .background(Color(0xFFFFB74F))
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


/**
 * 演示compose的形式如何实现padding和margin的布局效果
 */
@Composable
private fun PaddingAndMargin() {
    Row(
        modifier = Modifier
            .background(Color(0xFFF06292))//配置背景色，便于查看
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly,//空间均分
    ) {
        //第一列，来演示padding边距效果，可以看得出Modifier的属性设置，是按照顺序依次生效的。
        //可多次使用某属性，padding的设置，变相可实现内部content的margin效果
        Column(
            modifier = Modifier
                .background(Color(0xFFFFEB3B))
                .padding(15.dp)
                .background(Color(0xFFFFFFFF))
                .padding(8.dp)
        ) {
            Text(text = "天干 甲")
            Text(text = "天干 乙")
            Text(text = "天干 丙")
            Text(text = "天干 丁")
        }
        //第二列，先padding和先background效果作用区域是不同的，
        // 而后在此padding和background，都会影响到内部content的布局空间和形态
        Column(
            modifier = Modifier
                .padding(10.dp)
                .background(Color(0xFF80DEEA))
                .padding(end = 15.dp)
                .background(Color(0xFF9575CD))
                .padding(top = 12.dp, bottom = 22.dp)
        ) {
            Text(text = "地支 子")
            Text(text = "地支 丑")
            Text(text = "地支 寅")
            Text(text = "地支 卯")
        }
        //第三列，更为形象的看得出，text的文本内容，就会在modifier作用后修饰的content布局空间内
        Column(
            modifier = Modifier
                .background(Color(0xFF607D8B))
                .padding(15.dp)
                .background(Color(0xFFB2FF59))
        ) {
            Text(text = "音律 宫")
            Text(text = "音律 商")
            Text(text = "音律 角")
            Text(text = "音律 徵")
            Text(text = "音律 羽")
        }
    }
}

/**
 * 演示compose的阴影实现，切角，简单的空白空间占位和布局weight权重使用
 */
@Composable
private fun ShadowSpacerAndWeight() {
    Text(
        text = "这是圆角矩形的shape RoundedCornerShape(8.dp)",
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
    Row(
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = "甲", modifier = Modifier
                .background(Color(0xFFFF9800))
                .padding(4.dp)
        )
        Text(
            text = "乙",
            modifier = Modifier
                .background(Color(0xFFFFA726))
                .padding(4.dp)
        )
        Text(
            text = "丙",
            modifier = Modifier
                .background(Color(0xFFFFB74D))
                .padding(4.dp)
        )
        Text(
            text = "丁",
            modifier = Modifier
                .background(Color(0xFFFFB74F))
                .padding(4.dp)
        )
    }
    //shape可使用系统提供的RoundedCornerShape，CutCornerShape,CircleShape,RectangleShape
    Text(
        text = "这是切角矩形的shape CutCornerShape(8.dp)",
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
    Column(
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 4.dp,
                shape = CutCornerShape(8.dp),
            )
    ) {
        Text(
            text = "子",
            modifier = Modifier
                .background(Color(0xFF8BC34A))
                .padding(4.dp)
        )
        Text(
            text = "丑",
            modifier = Modifier
                .background(Color(0xFF9CCC65))
                .padding(4.dp)
        )
        Text(
            text = "寅",
            modifier = Modifier
                .background(Color(0xFFAED581))
                .padding(4.dp)
        )
        Text(
            text = "卯",
            modifier = Modifier
                .background(Color(0xFFAED599))
                .padding(4.dp)
        )
    }
    //不只是行、列、堆栈容器可以设置阴影和切角，所有compose的控件，都可以使用modifier来实现这个效果

    //演示空白控件space和weight的使用,注意⚠️weight仅作用于Row和column，对box没有这个作用属性
    Text(
        text = "演示圆角shape和shadow，同时主要看spacer空白控件和其他子控件weight的使用效果。",
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.LightGray),
    ) {
        Text(
            text = "Kotlin",
            modifier = Modifier
                //⚠️注意，这个Text虽然使用了CircleShape，但是受限于外部容器设置了height，所以显示出来不是圆形
                .shadow(elevation = 4.dp, shape = CircleShape)
                .background(Color(0xFFFF9800))
                .padding(4.dp)
        )
        //Spacer是空白控件，modifier的weight属性，表示权重，类似于传统view体系内线性布局linearLayout的weight差不多
        //weight会把剩余的空白控件，按权重比例分配个配置weight的控件上
        Spacer(modifier = Modifier.weight(1f))
        //这里可以看出，给该text加了weight，其也要多占一些空白空间
        Text(
            text = "Jetpack",
            modifier = Modifier
                .weight(1.5f)
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
                .background(Color(0xFFFFB74F))
                .padding(4.dp)
        )
    }
    //列 也有weight属性作用。weight是根据容器内所有weight总和来分配空间，然后按比例给子空间大小分配布局
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //2024年4月5日记：设计理念的原因，单个Text目前是没有让文本自居中的属性，只能通过外部容器配合来实现该效果。
        Text(
            text = "Kotlin",
            modifier = Modifier
                //注意⚠️，这里可以显示出圆形，还要记得modifier的属性是有顺序的，设置背景色和shadow如果顺序反了，效果就很不一样了。
                .shadow(elevation = 4.dp, shape = CircleShape)
                .background(Color(0xFF8BC34A))
                .weight(1f)
                .padding(4.dp)
        )
        Spacer(modifier = Modifier.weight(0.5f))
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
                .weight(1.5f)
        )
        Text(
            text = "Android",
            modifier = Modifier
                .background(Color(0xFFAED599))
                .padding(4.dp)
        )
    }
}

