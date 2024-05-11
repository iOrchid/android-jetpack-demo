package org.zhiwei.compose.screen.layout_state

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.random.Random

/**
 * 演示compose中感知数据变化，引起重组；
 * 在一些封装的compose独立函数中，接收变化参数与否，会重组与否。
 * 添加@stable,@immutable @NonRestartableComposable 等不同的注解，会有不同的作用效果。
 * @stable 注解可用于类/接口，函数，会标注稳定的。
 * @immutable，注解标注产生的对象，以后是不可变的。@Immutable 代表类型完全不可变，@Stable 代表类型虽然可变但是变化可追踪。
 * 解释：@Stable 与 @Immutable 在编译器的处理上并没什么不同，都是在适当的代码位置插入参数比较代码，[知乎](https://zhuanlan.zhihu.com/p/620252416)
 * 而且 @Stable 相对于 @Immutable 的使用场景更广泛，除了修饰 Class，还可以修饰函数、属性等等 ，因此大家可以优先使用 @Stable，@Immutable 或许会在未来被逐渐废弃。
 * @NonRestartableComposable 用于注解compose函数，作用：1，调用被注解的compose函数的区域，有了重组recompose变化时，不论被注解函数的入参是否变化，它都会重组。
 * 2，被注解compose函数内自身区域值变化重组，也会引起其父作用域重组，尽管其外部不一定有数据变化。
 */
@Composable
internal fun UI_CommonStable() {
    //三种不同的数据形式
    var counter by remember { mutableIntStateOf(0) }
    val unstableData by remember { mutableStateOf(UnStableDataClass(0)) }
    val unstableData2 by remember { mutableStateOf(UnStableDataClass2(0)) }
    val stableData by remember { mutableStateOf(StableDataClas(0)) }

    Spacer(modifier = Modifier.height(10.dp))
    Title_Text(title = "Stable和Immutable")
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        Button(
            onClick = { counter++ },
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(text = "当前数：$counter")
        }
        Title_Desc_Text(desc = "注意观察 使用@stable注解与否的两个函数，它们感知重组的变化。")
        //可观察控制台输出的log看整个重组作用域，也可以看UI运行效果。
        OuterComposable {
            println("🔥创建OuterComposable ")
            MiddleComposable {
                // 本区域内有counter变化，所以会引起该区域重组
                println("🔥创建MiddleComposable ")
                //封装的compose独立函数，即使它们接收的参数没有变化，但是由于所在作用域重组，会引起其自身也重组。
                UnstableComposable(data = unstableData)
                //这个和上面基本一样，但是data是使用了@stable注解的，所以它不会因为外部区域的重组而重组，除非接收参数真的变化了。
                UnstableComposable2(data = unstableData2)
                //然而这个compose函数，接收到的参数是val不可变的，且其是非inline的独立compose组件，所以它不会重组。
                StableComposable(data = stableData)
                Counter(text = "计数 $counter")
            }
        }
    }
}

//region stable immutable

//含有一个var可变参数的类
data class UnStableDataClass(var value: Int)

//同上，但是添加有@stable注解
@Stable // @immutable也可以，但是它只能修饰class，而@stable更广泛。
data class UnStableDataClass2(var value: Int)

//含有val不可变参数的类
data class StableDataClas(val value: Int)

//compose函数接收对象，其data内部值是var的可变量
@Composable
private fun UnstableComposable(data: UnStableDataClass) {
    SideEffect {
        println("🍎 UnstableComposable")
    }
    Column(
        modifier = Modifier
            .padding(4.dp)
            .shadow(1.dp, shape = CutCornerShape(topEnd = 8.dp))
            .background(randomColor())
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        //而这个data的value是var可变的，外部触发函数作用域的时候，就引起自身也重组。
        Text(text = "入参compose仅是var的: ${data.value}")
    }
}

//和UnstableComposable基本一致，就是使用的data是不同的类，添加了@stable的注解与否
@Composable
private fun UnstableComposable2(data: UnStableDataClass2) {
    SideEffect {
        println("🍎 UnstableComposable2")
    }
    Column(
        modifier = Modifier
            .padding(4.dp)
            .shadow(1.dp, shape = CutCornerShape(topEnd = 8.dp))
            .background(randomColor())
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        //而这个data的value是var可变的，外部触发函数作用域的时候，就引起自身也重组。
        Text(text = "composable入参var，但是有@stable注解: ${data.value}")
    }
}

//compose控件接收的对象不可变，内部值也是val不可变量
@Composable
private fun StableComposable(data: StableDataClas) {
    SideEffect {
        println("🍏 StableComposable")
    }
    Column(
        modifier = Modifier
            .padding(4.dp)
            .shadow(1.dp, shape = CutCornerShape(topEnd = 8.dp))
            .background(randomColor())
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        //因为这个data的value是val的，在函数内就是不会变化的。
        Text(text = "Compose入参是val的: ${data.value}")
    }
}

// 外框
@Composable
private fun OuterComposable(content: @Composable () -> Unit) {
    //创建compose的时候会调用
    SideEffect {
        println("== 外边框的Composable函数 ==")
    }

    Column(
        modifier = Modifier
            .padding(4.dp)
            .shadow(1.dp, shape = CutCornerShape(topEnd = 8.dp))
            .background(randomColor())
            .fillMaxWidth()
            .padding(4.dp)
    ) { content() }
}

//中间框
@Composable
private fun MiddleComposable(content: @Composable () -> Unit) {
    SideEffect {
        println("--- 中间composable ---")
    }
    Column(
        modifier = Modifier
            .padding(4.dp)
            .shadow(1.dp, shape = CutCornerShape(topEnd = 8.dp))
            .background(randomColor())
            .fillMaxWidth()
            .padding(4.dp)
    ) { content() }
}

//计数显示
@Composable
private fun Counter(text: String) {
    SideEffect {
        println("--->>> 🧮🔢$text🧄⬆️ <<<---")
    }
    Column(
        modifier = Modifier
            .padding(4.dp)
            .shadow(1.dp, shape = CutCornerShape(topEnd = 8.dp))
            .background(randomColor())
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(text = "感知counter变化: $text")
    }
}

//endregion

private fun randomColor(): Color {
    return Color(
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
    )
}


//region NonRestartableComposable

@Composable
internal fun UI_NonRestart() {
    Title_Text(title = "NonRestartableComposable")
    Title_Sub_Text(title = "演示@NonRestartableComposable的注解效果")
    Title_Desc_Text(desc = "@NonRestartableComposable 用于注解compose函数，作用：1，调用被注解的compose函数的区域，有了重组recompose变化时，不论被注解函数的入参是否变化，它都会重组。\n2，被注解compose函数内自身区域值变化重组，也会引起其父作用域重组，尽管其外部不一定有数据变化。")
    println("🍏：进入UI_NonRestart")
    val counter = remember { mutableIntStateOf(0) }
    var number by remember { mutableIntStateOf(0) }

    Column(
        Modifier
            .fillMaxWidth()
            .background(randomColor())
            .padding(8.dp)
    ) {
        println("🍌：进入Column")
        //两个变量
        Button(onClick = { counter.intValue++ }) {
            Text(text = "Counter：${counter.intValue}")
        }
        Button(onClick = { number++ }) {
            Text(text = "number：$number")
        }
        //区域显示变化
        Text(text = "计数${counter.intValue}")
        Text(text = "统计${number}")
        //⚠️这里注意观察👀外部数据变化对下面自身compose重组的影响，以及有无@Non注解的内部compose它们自己内部value变化时候，外部的感知
        NR_One(counter.intValue)
        NR_Two(number)
    }
}

//正常的 独立compose函数
@Composable
private fun NR_One(counter: Int) {
    var value by remember { mutableIntStateOf(0) }
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(2.dp, Color.Blue)
            .padding(8.dp)
            .background(randomColor())
    ) {
        Text(text = "入参Counter：$counter")
        Text(text = "自身内部value：$value")
        Button(onClick = { value++ }) {
            Text(text = "内部value：$value")
        }
    }
}

//带有@NonRestartableComposable注解的
@NonRestartableComposable
@Composable
private fun NR_Two(number: Int) {
    var value by remember { mutableIntStateOf(0) }
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(2.dp, Color.Red)
            .padding(8.dp)
            .background(randomColor())
    ) {
        Text(text = "入参Number：$number")
        Text(text = "自身内部value：$value")
        Button(onClick = { value++ }) {
            Text(text = "内部value：$value")
        }
    }
}


//endregion