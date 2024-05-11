package org.zhiwei.compose.screen.layout_state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.random.Random

/**
 * 演示state的使用，以及composable组件的重组现象；
 * 1. compose默认最小化重组区域原则；
 * 2. 一般仅在scope内或lambda，非内联的compose函数内作用；
 * 3. state使用remember结合mutableStateOf的时候，有policy参数可配置变化。
 * 4. 抽取非内联compose函数可改变 重组 影响范围。
 * 5. @stable @Immutable @NonRestartComposable注解会影响compose函数的重组响应。
 */
@Composable
internal fun StateReComposable_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier.verticalScroll(rememberScrollState())
    ) {
        UI_State()
        UI_Remember()
        UI_ReComposable()
        UI_NonRestart()
    }
}

//region state的演示
private data class CounterNum(var num: Int)

/**
 * 演示state的使用
 */
@Composable
private fun UI_State() {
    Title_Text(title = "State的使用")
    Title_Sub_Text(title = "compose的重组变化，是通过state感知数据变化来触发重组的。")
    var number = 0//普通类型的数据方式，不会因为compose重组而更新读取数值
    //使用remember的，才可以让compose函数感知到变化的数据
    val counter = remember { mutableIntStateOf(0) }
    val cNum = remember { CounterNum(0) }
    Button(onClick = {
        number++
        counter.intValue += 1
        cNum.num++
    }) {
        Text(text = "点击变化数字", color = Color.White)
    }
    Text(text = "普通Num：$number ,remember的Counter：${counter.intValue},自定义数据：${cNum.num}")
}

@Composable
private fun UI_Remember() {
    Title_Text(title = "mutableStateOf的策略")
    Title_Sub_Text(title = "mutableStateOf可以配置感知数据变化的策略，有三种")
    //⚠️注意，之所以分开写在独立的compose函数住，就因为后续会讲到的 recompose重组会发生在 非内联的lambda和compose的scope作用域内
    NeverEqual()
    StructuralEqual()
    ReferenceEqual()
}

@Composable
private fun NeverEqual() {
    val counter = remember {
        mutableStateOf(
            0,
//        policy = structuralEqualityPolicy()//数据结构内容来判断是否相等，
//        policy = referentialEqualityPolicy()//对象引用判断是否相等
            policy = neverEqualPolicy()//不判断，直接认为是新的变化
        )
    }

    //初始值是0，然后点击变更一次值，多次点击，由于配置mutableStateOf的策略不同，变化的值虽然和上次一样，但是接收方的感知态度，根据策略而不一样
    Button(onClick = {
        counter.value = 1

    }) {
        Text(text = "点击变化", color = Color.White)
    }
    //运行时要看文字颜色的变化，来感知是否响应了数据变化
    Title_Desc_Text(desc = "虽然第一次之后的数值，都是1，但是策略使用了neverEqual，就会一直认为数据是变化的。")
    Text(text = "基础数值Int:${counter.value}", color = randomColor())
}

@Composable
private fun ReferenceEqual() {

    //⚠️remember有=号赋值 和 by 两种方式，前面章节有说到，使用方式略有不同。
    val refStr = remember {
        mutableStateOf(
            EStr("对象引用判断，referentialEqualityPolicy"),
            policy = referentialEqualityPolicy()
        )
    }

    //初始值是0，然后点击变更一次值，多次点击，由于配置mutableStateOf的策略不同，变化的值虽然和上次一样，但是接收方的感知态度，根据策略而不一样
    Button(onClick = {

        //这个对象引用变化，策略是referential的，所以会感知变化，虽然文字内容还是一致的。
        refStr.value = refStr.value.copy(str = "对象引用判断，referentialEqualityPolicy")

    }) {
        Text(text = "点击变化", color = Color.White)
    }
    //运行时要看文字颜色的变化，来感知是否响应了数据变化
    Title_Desc_Text(desc = "copy会创建新对象，对应策略会引起变化")
    Text(text = "引用地址策略：${refStr.value.str}", color = randomColor())
}

@Composable
private fun StructuralEqual() {
    var structuralStr by remember {
        mutableStateOf(
            EStr("数据内容结构变化判断，structuralEqualityPolicy"),
            policy = structuralEqualityPolicy()
        )
    }
    //初始值是0，然后点击变更一次值，多次点击，由于配置mutableStateOf的策略不同，变化的值虽然和上次一样，但是接收方的感知态度，根据策略而不一样
    Button(onClick = {
        //虽然copy函数创建新的对象，但是data class 的结构内容 没有变，策略是structuralEqualityPolicy 所以接收方也不会触发变化。
        structuralStr = structuralStr.copy(str = "数据内容结构变化判断，structuralEqualityPolicy")

    }) {
        Text(text = "点击变化", color = Color.White)
    }
    //运行时要看文字颜色的变化，来感知是否响应了数据变化
    Title_Desc_Text(desc = "copy会创建新对象，但是使用的策略是内容相等来判断变化。所以这里文字颜色不会变。")
    Text(text = "结构策略:${structuralStr.str}", color = randomColor())
}

private fun randomColor(): Color {
    return Color(
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
    )
}

private data class EStr(val str: String)

//endregion

//region recompose重组的状态演示
/**
 * 参见官方文档[Compose](https://developer.android.google.cn/develop/ui/compose/lifecycle?hl=zh-cn)
 * 1、compose的生命周期：进入组合---一次或多次组合---离开组合；Composable的作用域scope就是指 非内联composable函数的Unit返回值的函数；
 *      ⚠️这里特别说明，非内联，因为常用的Column，Row，Box 容器 是内联的inline composable函数。
 * 2、在composable作用域内，只要有状态数据发生变化，就会触发reComposable重组。
 * 3、重组会尽量 最小化 触发范围。封装出去的composable函数，即使在一个感受变化的作用域内，如果它不接收变化数据，则 其自身也不会重组。
 * 4、初步可以简单理解LaunchEffect、SideEffect、DisposableEffect的感知compose的生命周期的效果
 */
@Composable
private fun UI_ReComposable() {
    Title_Text(title = "Recompose重组")
    Title_Sub_Text(title = "composable元素生命周期相比Android的activity/fragment简单许多，创建--组合（单/多次）--销毁。而重组的多次绘制也不会影响过多的性能。reCompose会最小化组合元素区域，感知数据变化来触发。")
    RC_Simple()
    //重组 的作用域
    UI_CommonStable()
}

//简单演示 重组 作用域
@Composable
private fun RC_Simple() {

    Title_Desc_Text(desc = "简单演示recompose的作用域，注意观察log的输出。可以看出初次调用composable函数会绘制一次，而后的点击，则会触发数据接收方的感知变化。")
    val counter = remember { mutableIntStateOf(0) }
    val number = remember { mutableIntStateOf(0) }
    //这里就是演示 生命周期回调的Effect时机，根自身代码顺序无关。
    SideEffect {
        //SideEffect 会在compose每次重组都调用
        println("♻️每次都会调用。。。")
    }
    DisposableEffect(key1 = null) {
        //销毁compose会调用的效应effect，其内部必须调用onDispose来释放必要的资源
        println("🗑️这里如同LaunchEffect一样，初始化调用一次。")
        onDispose {
            //这里是compose销毁的时候，调用的作用域。
            println("💨释放.....资源")
        }
    }
    //composable的控件 三个生命周期：创建--绘制（单/多次)--销毁。不像Activity/Fragment有生命周期回调函数。这里可以用后续会学到的Effect效应函数来监控生命周期
    LaunchedEffect(key1 = null) {
        //启动效应函数，会在所属composable作用域进行创建的时候，调用且仅调用一次。内部有协程作用域，会伴随所属compose。
        println("🚀LaunchEffect创建compose的协程")
    }

    println("--->>> 👀 开始进入 composable 函数")
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        //可以查看Column源码，可发现，其是inline fun 内联函数。Box，Row也是。所以它们实际会内联懂到调用处，而不是独立函数。
        println("📖 进入Column区域")
        //可以看点击的时候，重组 的区域，就在与数据感知方。
        Button(
            onClick = { counter.intValue++ },
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            println("⚡️第一个Button按钮区域")
            Text(text = "计数：${counter.intValue}")
        }
        Button(
            onClick = { number.intValue++ },
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            println("🔘️第2个Button按钮区域")
            Text(text = "数字：${number.intValue}")
        }
        //这个控件也感知counter的变化，所以其所在scope作用域内，会被重组。
        // 而且，⚠️可以注意，log输出不只是Column的进入，而是会有👀开始的那个log，就因为Column是内联，而非独立composable函数
        Title_Sub_Text(title = "外部的统计数：${counter.intValue}")
    }

}


//endregion

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun StateRecPreview() {
    StateReComposable_Screen()
}