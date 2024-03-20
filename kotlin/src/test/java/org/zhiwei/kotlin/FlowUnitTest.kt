package org.zhiwei.kotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main(vararg args: String) {
    //如此，可以输出协程信息，或者在IDE的debug上设置 VM options ： -Dkotlinx.coroutines.debug
    System.setProperty("kotlinx.coroutines.debug", "on")

//    flowCreate()
//    testCollect()
    testFoldReduce()

//    testFilerOperator()
//    testMapOperator()
//    testCombineOperator()
//    testFuncOperator()
}

private fun flowCreate() {
    val flow = channelFlow<String> {
        send("Hello ${Thread.currentThread().name}")
        withContext(Dispatchers.IO) {
            send("切换协程 ${Thread.currentThread().name}")
        }
    }
    //测试
    runBlocking {
        flow.collect {
            println("收到🫡 $it")
        }
    }
}

private fun testCollect() = runBlocking {
    val flow = listOf("A", "B", "C", "D", "E", "F").asFlow()
    flow.collect { println("搜集 $it") }
    flow.collectIndexed { index, value -> println("在index搜集 $index ,, $value") }
    val latestFlow = flow<String> {
        for (letter in listOf("A", "B", "C", "D", "E", "F")) {
            //模拟100ms发送一个
            delay(50)
            emit(letter)
        }

    }
    latestFlow.collectLatest {
        println("------ start 处理 ----  $it")
        //模拟处理数据流的相对慢，所以就会对应丢弃未处理好的，来处理最新的
        delay(60)
        println(">>>> 模拟处理结果---- $it")
    }

}

private fun testFoldReduce() = runBlocking {
    val flow = listOf("A", "B", "C", "D", "E", "F").asFlow()
    val foldResult = flow.fold(100) { acc: Int, value: String ->
        println("flow的fold: $acc ,, $value")
        acc + value.length
    }
    println("fold的result $foldResult")

    val reduce = flow.reduce { accumulator, value -> accumulator + value }
    println("reduce的result $reduce")

    flow.runningFold(100) { acc: Int, value: String ->
        acc + value.length
    }.collect {
        println("runningFold的result $it")
    }

    flow.runningReduce { accumulator, value -> accumulator + value }.collect {
        println("runningReduce的result $it")
    }

}


private fun testMapOperator() = runBlocking {
//    flow { repeat(20){emit(it)} }.map { "🪄$it" }.collect{ println("... >> $it") }
//    flow { repeat(20){emit(it)} }.mapLatest { delay(20); "🪄$it" }.collect{ println("... >> $it") }
//    flow { repeat(20) { emit(it) } }
//        .transformWhile {
//            delay(20)
//            emit("🪄$it")
//            it < 10
//        }.collect { println(it) }

    //scan，是依次叠加并输出，返回结果是flow
//    flow { repeat(20) { emit(it) } }.scan("种子🫘") { accumulator: String, value: Int ->
//        "$accumulator - $value"
//    }.collect { println(it) }

    //fold，返回值就是元素叠加后的结果
    val fold = flow { repeat(20) { emit(it) } }.fold("种子🫘") { accumulator: String, value: Int ->
        "$accumulator -- $value"
    }
    println("结果： $fold")

}


private fun testFilerOperator() = runBlocking {
    val flow = listOf("A", 'c', "11C", "D56", null, 6, 80, 232, false).asFlow()
//    flow.filter { it is String }.collect { println("String >>>> 搜集到 $it") }
//    flow.filterIsInstance<Char>().collect { println("Char >>>> 搜集到 $it") }
//    flow.filterNot { it.toString().length>2 }.collect { println("filterNot >>>> 搜集到 $it") }
//    flow.filterNotNull().collect { println("filterNotNull>>>> 搜集到 $it") }
    //drop
//    flow.drop(2).collect { println("drop >>>> 搜集到 $it") }
//    flow.dropWhile { it.toString().contains("11") }.collect { println("dropWhile >>>> 搜集到 $it") }
//    flow.dropWhile { it.toString().length==1}.collect { println("dropWhile >>>> 搜集到 $it") }

//    flow.take(2).collect { println("take >>>> 搜集到 $it") }
    //如果第一个就不满足条件，则不再看后面的数据
//    flow.takeWhile { it.toString().length == 1 }.collect { println("takeWhile >>>> 搜集到 $it") }

    //用时间轴分析比较合理，debounce是复制一份数据流，每次元素发送后，等timeOut周期内取最新值；
    //sample则是从数据流开始，定时取样，周期内的最新值。
//    flow {
//        emit(1)
//        delay(50)
//        emit(2)
//        //对于此示例，以上两个元素，属于同一个100ms内，所以会收到最新的数据，即 2
//        delay(200)
//        emit(3)
//        //又一次100ms内的
//        delay(150)
//        //接下来的100ms内，有两个元素，所以 取最新。
//        emit(4)
//        delay(50)
//        emit(5)
////    }.debounce(100).collect { println("debounce >>>> 搜集到 $it") }
//    }.sample(100).collect { println("sample >>>> 搜集到 $it") }

    //元素变化才搜集，判断相等的条件可以自定义
    flow.distinctUntilChanged { old, new -> old.toString().length == new.toString().length }
        .collect { println("change >>>> 搜集到 $it") }

}

private fun testCombineOperator() = runBlocking {
    val flow = listOf("A", "B", "C", "D", "E").asFlow()
    val flow2 = listOf(1, 52, 35, 4, 115, 60, 7, 8, 12).asFlow()
    //combine组合两个数据流,会组合成新的数据流
    //todo 简单理解，就是用流各自最新的值相结合，而不一定是元素的下标要对应！，如果模拟两个不懂发送速率的流，就更明显
    flow.combine(flow2) { a: String, b: Int ->
        //定义各个元素的组合规则 ，， E和 115 组合完，flow没有新值，flow2有新值，则就会用最后一个flow的值，与之新值匹配。
        "$a,,$b"
    }.collect {
        println(">>>> 搜集 $it")
    }

    println("------------ 分隔符 -------- ")

    //并流
    flow.zip(flow2) { a: String, b: Int ->
        //定义各个元素的组合规则
        "$a,,$b"
    }.collect {
        println("      搜集 $it")
    }
}

private fun testFuncOperator() = runBlocking {
    val flow = listOf("A", "B", "C", "D", "E").asFlow()
    //接收数据的时候，可用于判断是否协程已经取消，若取消了就异常
//    testCatch(flow)
//    testTry(flow)

    //注意操作符作用先后顺序会有不同结果
    flow.conflate().onEach { delay(200) }.collect {
        println("...>>>> 搜集 $it")
    }

}

private suspend fun testTry(flow: Flow<String>) {
//    flow.retry { false }
    flow.onStart { error("🏃异常") }
        .retryWhen { cause, attempt ->
            //cause是异常原因，attempt是重试次数，从0开始
            //返回boolean，若是true则继续重试
            println("重试 $attempt")
            attempt < 3L
        }.collect {
            println("...>>>> 搜集 $it")
        }
}

private suspend fun testCatch(flow: Flow<String>) {
    flow.onStart {
        error("测试XX异常")
    }.catch {
        println("这里能捕获异常么 ${it.message}")
        emit("异常捕获了，再发送补充的")
        emit("A")
        emit("C")
    }.onEach { if (it == "C") error("发送C的异常，不会被捕获啊") }
        .collect {
            println("...>>>> 搜集 $it")
        }
}