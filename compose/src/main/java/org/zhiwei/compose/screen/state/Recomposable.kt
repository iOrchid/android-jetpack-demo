package org.zhiwei.compose.screen.state

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
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


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun StateRecPreview() {
    StateReComposable_Screen()
}