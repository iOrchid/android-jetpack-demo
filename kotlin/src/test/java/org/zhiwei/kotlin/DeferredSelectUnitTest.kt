package org.zhiwei.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun CoroutineScope.switchMapDeferreds(input: ReceiveChannel<Deferred<String>>) = produce<String> {
    var current = input.receive() // 从第一个接收到的延迟值开始
    while (isActive) { // 循环直到被取消或关闭
        val next = select<Deferred<String>?> { // 从这个 select 中返回下一个延迟值或 null
            println("--------- 进入select----- current是${current}")

            input.onReceive.invoke { update ->
                println(" ..  执行update .. $update ")
                update
            }
            current.onAwait { value ->
                send(value) // 发送当前延迟生成的值
                input.receiveCatching().getOrNull() // 然后使用从输入通道得到的下一个延迟值
            }
        }
        if (next == null) {
            println("🔚 Channel 已经关闭")
            break // 跳出循环
        } else {
            current = next
        }
    }
}

fun CoroutineScope.asyncString(str: String, time: Long) = async {
    delay(time)
    str
}

fun main() = runBlocking<Unit> {
//sampleStart
    val chan = Channel<Deferred<String>>() // 测试使用的通道
    launch { // 启动打印协程
        for (s in switchMapDeferreds(chan)) println(s) // 打印每个获得的字符串
    }

    chan.send(asyncString("BEGIN", 100))
    delay(200) // 充足的时间来生产 "BEGIN"

    //todo 因为slow产生的慢，还没有得到结果，下一个就send过去了，在select中，onReceive就只会选取最新的值，
    chan.send(asyncString("Slow", 500))
    delay(100) // 不充足的时间来生产 "Slow"
    chan.send(asyncString("Replace", 100))
    delay(500) // 在最后一个前给它一点时间
    chan.send(asyncString("END", 500))
    delay(1000) // 给执行一段时间
    chan.close() // 关闭通道……
    delay(500) // 然后等待一段时间来让它结束

//sampleEnd
}