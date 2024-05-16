package org.zhiwei.compose.screen.layout_state

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.zhiwei.compose.R

/**
 * 演示使用constraintLayout的布局使用
 * 1. ConstraintLayout容器，几个关键的操作符createRef,createRefs,constrainAs都需要ConstraintLayoutScope下
 * 2. 先声明一些引用，用于需要的控件关联一下引用。然后就可以在constrainAs中做约束条件处理
 */
@Composable
internal fun ConstraintLayout_Screen(modifier: Modifier = Modifier) {
    //直接通过一个分享面板来演示constraintLayout的基础使用
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier.fillMaxWidth(.7f)) {
            //constraintLayout
            ConstraintLayout(Modifier.fillMaxWidth()) {
                //使用createRef/createRefs声明一些约束引用。
                val (title, image, box, desc, name, age, location) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.sexy_girl),
                    contentDescription = null,
                    Modifier
                        .constrainAs(image) {
                            //父容器水平居中，也可以额外bias偏移比例
                            centerHorizontallyTo(parent)
                        }
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "性感女孩",
                    //关联一个约束生命，就有点类似xml中的id;但是不同于modifier的layoutId的作用。
                    Modifier.constrainAs(title) {
                        //这里根据ref来做约束
                        top.linkTo(image.top, margin = 15.dp)
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                    }, color = Color.Cyan
                )

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0xCC000000))
                    .constrainAs(box) {
                        bottom.linkTo(parent.bottom)
                    })
                Text(
                    text = "薇薇安",
                    Modifier.constrainAs(name) {
                        start.linkTo(box.start, margin = 10.dp)
                        top.linkTo(box.top, margin = 5.dp)
                    },
                    color = Color.White,
                    fontSize = 12.sp,
                )
                Text(
                    text = "清纯可爱的小妹妹一个，你就说你爱不爱，是不是啊啊,想想都牙疼",
                    Modifier.constrainAs(desc) {
                        start.linkTo(name.end, margin = 8.dp)
                        baseline.linkTo(name.baseline)
                        end.linkTo(parent.end)
                        //设置宽度约束,不同的设置，会在文本超长时候，显示不同效果。
                        width = Dimension.fillToConstraints
                    },
                    //这两个是用于限定文本过长，显示...
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontSize = 10.sp,
                )
                //使用barrier栅栏，同理guideLine也是类似用法
                val bottomBarrier = createBottomBarrier(name, desc)
                Text(
                    text = "上海交通大学·社会与科学学院",
                    Modifier.constrainAs(location) {
                        start.linkTo(name.start)
                        top.linkTo(bottomBarrier)
                    },
                    color = Color.White,
                    fontSize = 10.sp,
                )
            }
        }
    }

}

@Preview
@Composable
private fun PreviewConstraintLayout() {
    ConstraintLayout_Screen()
}
