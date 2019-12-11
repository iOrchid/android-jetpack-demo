package org.zhiwei.jetpack.kt.advanced

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年11月27日 19:53
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 协程中的管道，用于在协程间传递数据
 */
object Pip {

    fun testPip() {
//        testChannel()
//        testConsume()
//        testReceiveProduce()
//        testOut()
//        testIn()
//        testOther()

        testTimer()

    }

    //1、管道的操作符的发送于接收演示
    private fun testChannel() = runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) channel.send(x * x)//发送x2数据出去
            channel.close()//关闭发送
        }

        repeat(5) { println("接收的数据 ${channel.receive()}") }//repeat次数，如果不能于发的数目，就会造成持续的运行等待。单不会阻塞
        println("最外协程运行OK")
    }

    //2、使用扩展函数，构建消息生产于消费
    private fun testConsume() {
        fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
            for (i in 1..5) send(i)
        }

        runBlocking {
            //对比上一个函数中还要有close，还要有个数的匹配，这里就用consumeEach代替for循环。
            produceSquares().consumeEach { println("代替for循环来接收管道的数据$it") }
            println("哈哈哈，完成了")
        }
    }

    //3、演示创建生产者、消费者
    //模拟一直发送数据,
    private fun CoroutineScope.produceNum() = produce {
        var x = 1
        while (true) send(x++)
    }

    //消费数据，并再次发送
    private fun CoroutineScope.squareNum(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> =
        produce {
            for (x in numbers) send(x * x)
        }

    private fun testReceiveProduce() = runBlocking {
        val numbers = produceNum()
        val squares = squareNum(numbers)
        for (i in 1..5) println("输出square的数据 ${squares.receive()}")
        println("外部运行OK")
        coroutineContext.cancelChildren()//取消runblock内的子协程,如果不取消，子协程会一直运行
    }


    //4、扇出、扇入
    private fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1 // 从 1 开始
        while (true) {
            send(x++) // 产生下一个数字
            delay(100) // 等待 0.1 秒
        }
    }

    private fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
        //使用for循环可以完美的利用多核cpu，使用多个协程，若有一个失败，不影响其他
        //如果用sonsumeEach的化，就只在一个通道中执行
//        channel.consumeEach { }
        for (msg in channel) {
            println("Processor #$id received $msg")
        }
    }

    //todo 同一通道的数据，分发到多个协程
    private fun testOut() = runBlocking {
        val producer = produceNumbers()
        repeat(5) { launchProcessor(it, producer) }
        //延时，为了输出
        delay(950)
        producer.cancel()//取消所有的生产者
    }

    //todo 多个协程，发送数据到同一个通道
    //发送string，
    private suspend fun sendStr(channel: SendChannel<String>, s: String, time: Long) {
        while (true) {
            delay(time)
            channel.send(s)
        }
    }

    private fun testIn() = runBlocking {
        val channel = Channel<String>()
        //启动一个协程发送
        launch { sendStr(channel, "AAA", 200L) }
        //再来一个协程发送
        launch { sendStr(channel, "BBB", 300L) }

        repeat(6) {
            println("接收 第$it 个 , ${channel.receive()}")
        }

        //取消所有
        coroutineContext.cancelChildren()
    }

    //缓存池，通道对于协程是公平的，不偏不倚。
    private fun testOther() = runBlocking {
        val channel = Channel<Int>(3)//数字3表示通道的缓存大小
        val sender = launch {
            repeat(10) {
                channel.send(it)
                println("发射 $it")
            }
        }
        //延迟 等待，这里不用接收者，就看发送者的效果,等待再久也是只能发送缓存的个数。因为缓存通道的数字，没有被消费,如果消费掉一个，就会再次发送一个。
        delay(5000L)
        sender.cancel()//关闭
    }

    //计时器通道，有点不解!!!,特别的会合通道，经过指定延时后，会消费，然后产生Unit。
    private fun testTimer() = runBlocking {
        val tickerChannel = ticker(delayMillis = 100, initialDelayMillis = 0) //创建计时器通道
        var nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
        println("初始尚未经过延迟: $nextElement") // 初始尚未经过的延迟

        nextElement = withTimeoutOrNull(50) { tickerChannel.receive() } // 所有随后到来的元素都经过了 100 毫秒的延迟
        println("下一元素没准备好 在 50 ms: $nextElement")

        nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
        println("下一元素准备OK 在 100 ms: $nextElement")

        // 模拟大量消费延迟
        println("消费暂停 for 150ms")
        delay(150)
        // 下一个元素立即可用
        nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
        println("大量耗时后的新元素，立即可用 : $nextElement")
        // 请注意，`receive` 调用之间的暂停被考虑在内，下一个元素的到达速度更快
        nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
        println("消费者暂停150ms后，新元素可用: $nextElement")

        tickerChannel.cancel() // 表明不再需要更多的元素
    }
}