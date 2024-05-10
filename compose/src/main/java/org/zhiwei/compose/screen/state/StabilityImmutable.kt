package org.zhiwei.compose.screen.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlin.random.Random

/**
 * 演示compose中感知数据变化，引起重组；
 * 在一些封装的compose独立函数中，接收变化参数与否，会重组与否。
 * 添加@stable,@immutable @NonRestartableComposable 等不同的注解，会有不同的作用效果。
 * @stable 注解可用于类/接口，函数，会标注稳定的。
 * @immutable，标注立即变化的。
 */
@Composable
internal fun UI_CommonStable() {
    //三种不同的数据形式
    var counter by remember { mutableIntStateOf(0) }
    val unstableData by remember { mutableStateOf(UnStableDataClass(0)) }
    val unstableData2 by remember { mutableStateOf(UnStableDataClass2(0)) }
    val stableData by remember { mutableStateOf(StableDataClas(0)) }

    Column {
        Button(
            onClick = { counter++ },
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(text = "当前数：$counter")
        }
        Spacer(modifier = Modifier.height(20.dp))
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


//含有一个var可变参数的类
data class UnStableDataClass(var value: Int)

//同上，但是添加有@stable注解
@Stable
data class UnStableDataClass2(var value: Int)

//含有val不可变参数的类
data class StableDataClas(val value: Int)

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
        Text(text = "UnstableComposable() value: ${data.value}")
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
        Text(text = "UnstableComposable() value: ${data.value}")
    }
}


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
        Text(text = "StableComposable value(): ${data.value}")
    }
}


/**
 * 外框
 */
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
        Text(text = "计数框: $text")
    }
}

private fun randomColor(): Color {
    return Color(
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
    )
}