package org.zhiwei.kotlin

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.selects.selectUnbiased
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import org.junit.Test
import java.io.IOException
import kotlin.random.Random

/**
 * 协程相关的单元测试
 */
class CoroutinesUnitTest {

    /**
     * 测试job的函数使用，协程单元测试，所以用了runBlocking提供协程环境和阻塞的执行域
     * 根据需要，选择需要测试的函数，
     */
    @Test
    fun testJob() {
//        jobJoin()
//        jobHandler()
//        cancellable()
//        globalJob()
//        handleException()
//        handleExp()
//        supervisorTest()
//        testChannel()
//        testChannel2()
        testProduce()
//        testActor()
//        testTicker()
//        testSelectRec()
//        testSelectSend()
//        testSelectAwait()
    }

    private fun jobJoin() = runBlocking {
        val job = launch {
            println("执行launch之前 -------")
            delay(2000)
            println("====== 执行launch的之后")
        }
        job.join()//join的作用会等待job的协程执行完，再让后面执行，如果不join，协程job仍会执行，但是就是异步的，
        println("测testJob的结束执行代码")
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun jobHandler() = runBlocking {
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("协程$coroutineContext , 出现异常，被handler捕获了，${throwable.message}")
        }

        val jobHandler = launch(handler) {
            println("handler 执行launch之前 -------")
            delay(2000)
            //cancel和error 同时只用一个测试。
            cancel("抛🏃一个自定义的协程取消cancel信息")//只是抛出取消cancel信息，下面print还是会执行
//            error("咋了，这里error一个throwable抛出去")//正儿八经的error异常，下面println就不会执行了，但是会被invokeOnCompletion捕获到
            println("handler ====== 执行launch的之后")
        }
        jobHandler.invokeOnCompletion(true, true) {
            println("多参数的 jobHandler的invokeOnCompletion 执行到了 ${it?.message}")
        }
        jobHandler.invokeOnCompletion {
            println("jobHandler的invokeOnCompletion 执行到了 ${it?.message}")
        }
        //没有job的join，所以先输出这个，而后才是协程内的执行
        println("测jobHandler 的结束执行代码")
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun cancellable() = runBlocking {
        val job = launch {
            //withContext+NonCancellable，会创建一个不可取消的协程，即使job调用了cancel，它依旧执行；但是其自身内部可以cancel来结束。
            withContext(NonCancellable) {
                repeat(20) {
                    delay(200)
                    println("输出log模拟...")
                    //这cancel会取消withContext启动的子协程
//                    if (it==10)cancel("内部👘cancel可以吗？")
                }
            }
        }
        delay(1000)

//        job.cancel("⚠️手动取消协程任务，withContext的不会被取消执行")
        //这个带参数的，写法，虽然在cancel之后，但是能感知到（配置true的话）
//        job.invokeOnCompletion(true,true) {
//            println("此处立即感知 cancel的信息：${it?.message}")
//        }
        //而这个，必须job执行结束才会收到回调
        job.invokeOnCompletion {
            println("此处job完成结束才感知 cancel的信息：${it?.message}")
        }
        println("--------->>>>>   结束🔚 <<<<<-----   ")
    }

    private fun globalJob() {
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("异常捕获的handler来啦🤣 $coroutineContext ,,,  ${throwable.message}")
        }
        val job = GlobalScope.launch(handler) {
            repeat(20) {
                delay(200)
                println("模拟日志log打印...")
                if (it == 4) error("👘个异常👀")
            }
        }
        Thread.sleep(5000)
//        job.cancel("取消，看看Global会结束么")
        println("globalJob的最后一行.")
    }

    private fun handleException() = runBlocking() {

        val handler = CoroutineExceptionHandler { _, throwable ->
            println("异常捕获的handler来啦🤣 ${throwable.message}")
        }
        //因为是单元测试，外面使用了runBlocking，协程内部的异常，一般都会外抛到最外层的协程才能捕获。所以这里使用了supervisor来阻断外传异常，让handler能生效
        supervisorScope {
            try {
                val job = launch(handler) {
                    repeat(20) {
                        delay(200)
                        println("模拟log输出...")
                        if (it == 4) error("📖⚠️来一个异常error呀")
                    }
                }
                job.join()
            } catch (e: Exception) {
                println("try catch不住协程内的异常,cancel的也不能try catch住 ,但是能感知到信息，也就是能走到这里，但JVM依然exception ${e.message}")
            }
        }

        println("handleException 函数最后一行的 ----")
    }

    /**
     * 协程内异常可能会多个，连锁式的，handler中有内置的suppressed的数组，包含次生异常
     */
    private fun handleExp() = runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            // 第三, 这里的异常是第一个被抛出的异常对象
            println("捕捉的异常: $exception 和被嵌套的异常: ${exception.suppressed.contentToString()}")
        }
        val job = GlobalScope.launch(handler) {
            launch {
                try {
                    delay(Long.MAX_VALUE)
                } finally { // 当父协程被取消时其所有子协程都被取消, finally被取消之前或者完成任务之后一定会执行
                    throw ArithmeticException() // 第二, 再次抛出异常, 异常被聚合
                }
            }
            launch {
                delay(100)
                throw IOException() // 第一, 这里抛出异常将导致父协程被取消
            }
            delay(Long.MAX_VALUE)
        }
        job.join() // 避免GlobalScope作用域没有执行完毕JVM虚拟机就退出
    }

    private fun supervisorTest() = runBlocking {
        val handler = CoroutineExceptionHandler { _, throwable ->
            println("输出 捕获了异常 ${throwable.message}")
        }

        supervisorScope {
//            error("这个没用，捕获不住")
            launch(handler) {
//                error("这个好像能捕获")
                repeat(10) {
                    delay(200)
                    println("....打log啊🤔")
                }
            }
        }
        //supervisor自身就是阻塞的，需要执行完内部，外面的才执行
        launch {
            println("这里会在supervisor之后执行")
        }
        println("----- 最后一行 ")

    }

    private fun testChannel() = runBlocking {
        val channel = Channel<String>()
        println("--------start--------")
        launch {
            repeat(10) {
                delay(100)
                channel.send("$it")
            }
        }

        println("--------中间--------")
        launch {
//            channel.consumeEach {
//               println(" .. 接收到 ${it}")
//            }
            for (i in channel) {
                println(" .. 接收到 ${i}")
            }
        }

        delay(1000)

        println("--------End--------")
    }

    private fun testChannel2() {
        //trySend和tryReceive 可以不在协程作用域内使用，但是也就没有了挂起非阻塞的功能；如果Channel的capacity没有设置（默认0），那么就trySend不出去，也就无从receive，且阻塞。
        val channel = Channel<String>(3)
        println("--------start--------")

        val trySend = channel.trySend("try发送000")
        val trySend2 = channel.trySend("try发送000")
        println("try发送的是 $trySend ,$trySend2")

        println("--------中间--------")

        println("接收到 ${channel.tryReceive().getOrNull()} ")
        println("接收到2 ${channel.tryReceive().getOrNull()} ")

        println("--------End--------")
    }

    private fun testProduce() = runBlocking {
        val produce = produce<String> {
            repeat(20) {
                send("发送$it")
            }
        }
        produce.consumeEach {
            println("收到 🫡 $it")
        }
    }

    private fun testActor() = runBlocking {
        val actor = actor<String> {
            consumeEach {
                println("收到 🫡 $it")
            }
        }
        repeat(20) {
            actor.send("发送$it")
        }
        actor.close()
    }

    private fun testTicker() = runBlocking {
        ticker(1000).consumeEach {
            println("每间隔1s打印🖨️一次...")
        }

        /*
         //future是产生一个延迟得到结果的数据，通过返回对象的get获取值，todo get需要在协程内，否则会阻塞。
        val future = future {
            "😂哈哈，返回啊 "
        }

        launch {
            println("接收future的数值 ${future.getNow("没等到")}")
        }
        println("----接收future的数值 ${future.getNow("没等到")}")

         */

    }


    private fun testSelectRec() = runBlocking {
        println("--------- 开始 -----------")
        val channel = produce<Int> {
            repeat(10) {
                send(it)
            }
        }
        val channel2 = produce<String> {
            repeat(10) {
                send("🌬️$it")
            }
        }

        //监听channel的onSend，但是要注意，channel要调用receive才会让send的数据出来，不然会阻塞

        //可以选择想要的监听结果，指定类型，此处指定String,select可以同时监听多个channel，
        // 但是并不确定那个优先输出，多次执行，结果输出也不一致。
        repeat(10) {
            val selectAorB = selectAorB(channel, channel2)
            println("💣select选择器 : $selectAorB")
        }

        coroutineContext.cancelChildren()

        println("------- 🔚函数最后一行 ---------- ")

    }

    private suspend fun selectAorB(a: ReceiveChannel<Int>, b: ReceiveChannel<String>): String {
        val select = selectUnbiased<String> {
            //使用onReceiveCatching 可以避免onReceive因为channel关闭而异常
            b.onReceiveCatching {
                if (it.getOrNull() == null) "B 🚇 被关闭了" else "收到b的 ${it.getOrNull()}"
            }
            a.onReceiveCatching {
                if (it.getOrNull() == null) "A 🛣️ 被关闭了" else "收到a的 ${it.getOrNull()}"
            }
        }
        return select
    }

    private fun testSelectSend() = runBlocking {

        println(" --------- 开始 -------")

        //这里模拟快速发送数据，接收receive很慢的时候，send会阻塞，而select会分散阻塞压力，到多个channel上去
        fun produceNum(side: SendChannel<Int>) = produce<Int> {
            repeat(10) { num ->
                delay(100)
                select {
                    //这里使用两个发送channel来发送数据，select的作用，单次 是只会生肖其中一个，而不是说一个num发两次
                    this@produce.onSend(num) {}
                    side.onSend(num) {}
                }
            }
        }

        val side = Channel<Int>()
        launch {
            side.consumeEach { println(">>>>>>>    🧑‍💼副Channel接收 $it") }
        }
        //调用生产数字的channel，并穿过去一个side，
        produceNum(side).consumeEach { println("🧑‍🏫 模拟消费数据很慢 $it");delay(200) }

        coroutineContext.cancelChildren()
        println(" --------- 结束 -------")
    }

    private fun testSelectAwait() = runBlocking {
        //模拟10个 延迟异步操作，不认耗时时长的协程任务
        val list: List<Deferred<String>> = List(10) {
            val time = Random(322).nextInt(1000)
            async {
                delay(time.toLong())
                "产生delay了$time 毫秒的数"
            }
        }
        val result = select {
            list.withIndex().forEach { (index, defered) ->
                defered.onAwait.invoke { ret ->
                    " ...  异步任务的结果: $ret "
                }
            }
        }
        println("🐒select的结果 $result")

        val activeCount = list.count { it.isActive }
        println("还有活跃的异步任务 $activeCount 个")

        println("------------分隔符---------")


    }

}