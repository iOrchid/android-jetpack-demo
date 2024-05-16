package org.zhiwei.compose.screen.layout_state

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import kotlinx.coroutines.launch
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.random.Random

/**
 * 演示Effect相关的使用
 */
@Composable
internal fun Effect_Screen(modifier: Modifier = Modifier) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        UI_RememberEffect()
//        LifecycleEffect()
        UI_rememberScopeUpdate()
        UI_Movable()
    }
}


//region effect相关

/**
 * 演示与Compose相关的 效应 Effect 函数的使用
 * 简要理解，伴随composable的UI控件生命周期 ：创建---组合（单/多次）---销毁 ，可以使用一些Effect函数来感知其周期。
 * ⚠️：一般不建议在Composable函数中封装Effect，而应该根据业务需求，尽可能低限度的使用必要的Effect，通常compose函数要与状态分离，实现设计架构理念里的 单向数据流的要求。
 * 1. LaunchEffect，可用于感知当前compose函数的创建，它接收参数key，当key不变时，它伴随compose生命周期，仅回调一次；当key变化时，它会再次回调。（是在compose内部组合完成时，最后一个调用它）
 * 2. SideEffect，每次compose的组合都会调用。可理解为传统view的draw或show。也是在每次compose组合完成组合后调用。
 * 3. DisposableEffect，销毁效应，也是伴随生命周期，它自身接收key参数，类似于LaunchEffect，会随生命周期创建一次（如果key不变的话），其内部的onDispose则会在compose销毁时候调用。若key变化，也会回调（包括内部onDispose）。
 * 它们的回调时机，根其在compose函数中的代码顺序位置无关。
 * 4. remember可以接收多个key作为标记内部数据是否变化的flag，即使内部数据没更改，而key变化了，也会触发外部接收者的数据变化。
 */
@Composable
private fun UI_RememberEffect() {
    //这里remember加了key参数，如果key修改，则也会触发keyNum变化，即使内部数值可能没有修改。⚠️这里keyStr也必须是remember{mutableState}的，否则compose是感知不到变化的。
    var keyStr by remember { mutableStateOf("key") }
    //每次重组，会让remKeyNum归零，为初始值;⚠️注意使用mutableStateOf，需要变化对象，才会触发数据变更，如果只是更改TNum的内部值，是不会触发的。因为TNum内部值没有进入到compose作用域的监管。
//    val remKeyNum = remember { mutableStateOf(TNum(0)) }
    val remKeyNum = remember(keyStr) { mutableIntStateOf(0) }

    //compose组件每次绘制组合，都会调用的Effect
    SideEffect {
        println("♻️：。。。每次组合都调用。。。")
    }

    //如果key不变，则compose组合创建时候会调用，onDispose在compose销毁时候回调。可用于处理资源释放，管理lifeCycle等。如果key变化，则会触发回调。
    DisposableEffect(key1 = null) {
        println("🧵：>>> DisposableEffect关联 <<<")
        onDispose {
            println("🗑️：---销---毁---")
        }
    }

    //闯将compose的时候调用，如果key不变，则生命周期内仅调用一次。如果key变化，则会触发回调。
    LaunchedEffect(key1 = null) {
        println("🚀：->->->->->-> 启动 ---> --> ->")
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        println("⌚️：***进入column***")
        Title_Text(title = "Effect")
        Title_Sub_Text(title = "1. 演示remember标记key的变化来引起compose作用域的重组，以及使用LaunchEffect、SideEffect和DisposableEffect来感知composable的生命周期。")
        //点击此按钮，会让remKeyNum的remember的key变化，也就会触发它刷新数据，即使其内部数字未变更。
        Button(onClick = { keyStr = "keyStr${Random.nextInt(3)}" }) {
            println("🍎️：...变更key。。。")
            Text(text = "点击变更key")
        }
        //正常的变更数值
        Button(onClick = { remKeyNum.intValue++ }) {
            println("🪮：…………………………数字变更")
            Text(text = "点击计数：${remKeyNum.intValue}")
        }
        //感知变化。
        Text(text = "显示数字🔢：${remKeyNum.intValue}")
        println("🧮🔢：·······················")
    }
    //外部的重组的时候，会输出log
    println("🏃🏃🏃🏃🧗～～～～～～～～～～")
}

private data class TNum(var num: Int)

/**
 * 演示rememberCoroutineScope和rememberUpdatedState
 * 1. LaunchEffect启动一个伴随compose生命周期的协程，它会随LaunchEffect而创建和销毁；而rememberCoroutineScope创建的协程会跟随声明处的compose的生命周期。
 */
@Composable
private fun UI_rememberScopeUpdate() {
    //该协程生命周期会伴随UI_rememberScopeUpdate。LaunchEffect可能会因为key变化而重建。
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var counter by remember { mutableIntStateOf(0) }
    Title_Sub_Text(title = "2. 演示rememberCoroutineScope和rememberUpdatedState的使用")
    Title_Desc_Text(desc = "rememberCoroutineScope简单理解为创建一个协程，伴随调用处的compose的生命周期。而rememberUpdatedState则会及时响应外部数据的变更。")
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color.Cyan),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(onClick = {
            coroutineScope.launch {
                Toast.makeText(context, "🈵🦶你", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "点击弹toast")
        }
        OutlinedButton(onClick = { counter++ }) {
            Text(text = "点击增量$counter")
        }
        Text(text = "数字$counter")
        HorizontalDivider()
        //独立的compose函数，入参变化，来演示它内部的感知
        NumZone(input = counter)
    }
}

//注意看，不同的数据接收类型方式，入参变化时候，compose重组，但获取值是否更新，却不一样。
@Composable
private fun NumZone(input: Int) {
    val rememberUpdatedStateInput by rememberUpdatedState(input)
    val rememberedInput = remember { input }
    val rememberedStateInput = remember { mutableIntStateOf(input) }
    Text(text = "使用rememberUpdatedState：$rememberUpdatedStateInput")
    Text(text = "使用remember：$rememberedInput")
    Text(text = "使用remember加mutableStateOf：${rememberedStateInput.intValue}")
    Text(text = "原始数据：$input")
}

/**
 * lifecycle的compose组件，提供的几个函数，用于compose组件声明周期的感知
 * 感知的是compose组件的生命周期，如果通过state切换来销毁compose，那么它不需要感知到销毁资源的回调。
 * 如果是界面UI的整体销毁,就可能因为有了onDestroy而导致onStopOrDispose和onPauseOrDispose各走两次。
 * var show by remember { mutableStateOf(false) }
 *
 *     Column {
 *         Button(onClick = { show = show.not() }) {
 *             Text(text = "切换")
 *         }
 *         if (show) {
 *             LifecycleEffect()
 *         }
 *     }
 *
 * 如上，点击按钮而隐藏LifecycleEffect，则onStopOrDispose和onPauseOrDispose只回调一次。
 * 而如果，显示了LifecycleEffect，将当前页面onBackPress销毁，
 * 那么会在onDestroy之后，再触发一次onStopOrDispose和onPauseOrDispose的销毁资源回调。
 */
@Composable
private fun LifecycleEffect(modifier: Modifier = Modifier) {
    val TAG = "LifecycleEffect"
    Log.v(TAG, "🚀： ------------")
    //不可用于监听onDestroy，否则报错,
    //This function should not be used to listen for Lifecycle.Event.ON_DESTROY
    // because Compose stops recomposing after receiving a Lifecycle.Event.ON_STOP
    // and will never be aware of an ON_DESTROY to launch onEvent.
    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
        Log.d(TAG, "🚄： ------创建------")
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_STOP) {
        Log.d(TAG, "♻️： ------停止------")
    }

    LifecycleStartEffect(key1 = Lifecycle.Event.ON_START) {
        Log.i(TAG, "🍊： ------启动------")
        //这里会在onStop和onDestroy都触发
        onStopOrDispose {
            Log.i(TAG, "🍊： ------启动内>> 停止/销毁------")
        }
    }

    LifecycleResumeEffect(key1 = Lifecycle.Event.ON_RESUME) {
        Log.w(TAG, "🍎： ------显示------")
        //这里会在onPause和onDestroy都触发
        onPauseOrDispose {
            Log.w(TAG, "🍎： ------显示内>> 暂停/销毁------")
        }
    }
}

//endregion

//region 其他相关

@Composable
private fun UI_Other() {
    //produceState用于从composable创建state出去给其他地方使用，结果可给非compose的业务逻辑使用。
    //它必须在@composable的函数中
    val produceState = produceState(initialValue = TNum(0)) {
        //按照某种业务规则，产生新的状态的值
        value = TNum(2)
    }
    genTNumState()
    //snapshotFlow可用于创建一个flow数据流，可用在composable或非composable函数中
    val snapshotFlow = snapshotFlow {
        TNum(9)
    }

    //⚠️Modifier的多个操作符，可以包含可变数据，比如height可能是外部入参可变值，offSetX等。如果Modifier的某个数据感知变化，
    //那么在使用该Modifier的地方，都会触发重组。
    var height by remember { mutableIntStateOf(20) }
    val modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
    Box(modifier = modifier)
    //如上，如果某地方，修改了height的数值，那么modifier也会变化，使用modifier的compose组件，就会触发重组。
    //⚠️如果是lambda函数作为入参，层层递传，那么入参变化，可能仅影响到最里层使用lambda参数的地方。如果不是lambada作为入参，而是直接参数，那么会触发层层重组。

    @Composable
    fun Inner(off: () -> Int) {
        Text(text = "Inner内部", Modifier.offset { IntOffset(off.invoke(), 0) })
    }

    //如果out和inner都不是lambda的入参，如下方式的话，那么都会层层触发重组。
    @Composable
    fun common(offset: Int) {
    }

    @Composable
    fun OutDeffer(offset: () -> Int) {
        Column {
            Text(text = "out内嵌")
            Inner(offset)
        }
    }
    //简单演示,mock模拟,OutDeffer内也有其他compose组件，都没有使用offset的数值，但是使用的lambda的传参，所以只会在inner的text发生重组。
    OutDeffer {
        10
    }
    val asRow by remember { mutableStateOf(false) }
    val notRow = asRow.not()//boolean值取反
}

private fun genTNumState(): State<TNum> {
    //derivedStateOf用于在非Composable函数中，创建state值，可用于给compose使用,而不用使用remember；
    //它不能放在@composable的函数中。
    return derivedStateOf {
        TNum(2)
    }
}

//endregion

//region Movable

//演示movable
@Composable
private fun UI_Movable() {
    Title_Text(title = "Movable")
    Title_Sub_Text(title = "1. 演示使用movableContentOf来处理数据集变化状态不同步的场景。")
    val list = remember {
        mutableStateListOf(
            MockItem("Item1", checked = false),
            MockItem("Item2", checked = true),
            MockItem("Item3", checked = true),
        )
    }
    val list2 = remember {
        mutableStateListOf(
            MockItem("Item1", checked = false),
            MockItem("Item2", checked = true),
            MockItem("Item3", checked = true),
        )
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //普通的增删操作方式，可以看得出，增删的item对原有item是状态不对的
        Column {
            Title_Desc_Text(desc = "未用movableContentOf")
            OutlinedButton(onClick = { list.add(0, MockItem("新增Item", false)) }) {
                Text(text = "增加item")
            }
            OutlinedButton(onClick = { list.removeFirstOrNull() }) {
                Text(text = "删除item")
            }
            list.forEach {
                ItemUI(item = it)
            }
        }
        //使用movableContentOf
        val mapedList = list2.map { item ->
            movableContentOf { ItemUI(item = item) }
        }
        Column {
            Title_Desc_Text(desc = "用movableContentOf")
            OutlinedButton(onClick = { list2.add(0, MockItem("新增Item", true)) }) {
                Text(text = "增加item")
            }
            OutlinedButton(onClick = { list2.removeFirstOrNull() }) {
                Text(text = "删除item")
            }
            mapedList.forEach { it.invoke() }
        }
    }


}


private data class MockItem(val text: String, var checked: Boolean = false)

@Composable
private fun ItemUI(item: MockItem) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        //必须是remember的值，才能让checkbox自身有UI交互的点击切换。
        var checked by remember {
            mutableStateOf(item.checked)
        }
        Title_Sub_Text(title = item.text)
        Checkbox(checked = checked, onCheckedChange = { checked = it;item.checked = it })
        Title_Desc_Text(desc = "item：${item.checked}")
    }
}

//endregion

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun EffectPreview() {
    Effect_Screen()
}