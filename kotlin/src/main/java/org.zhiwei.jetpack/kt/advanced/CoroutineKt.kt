package org.zhiwei.jetpack.kt.advanced

import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年11月20日 19:39
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 用于演示协程相关使用的代码
 */
object CoroutineKt {
    /**
     * 测试协程
     */
    fun testCon() {
//        launchOne()
//        launchTwo()
//        launchTwo2()

//        jobOne()

//        cooperationCoroutine()

//        corScope()

//        testGlobal()

//        cancelJoin()
//        cancelJob2()

//        cancelFinally()
//        cancelFinally2()

        timeOut()
    }

    //<editor-folder desc="1、基本的协程使用，阻塞等待">
    private fun launchOne() {
        //launch三个参数，CoroutineContext 上下文，CoroutineStart 启动策略，立即执行还是懒加载。第三个参数接收一个挂起函数。这里用了lambda方式，就放在()外的{}里了。
        GlobalScope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            //启动一个协程
            delay(1000L)//非阻塞等待1s
            println("World in LaunchOne")
        }
        println("Hello in LaunchOne 的主线程")//协程挂起的时候，不影响主线程继续,
        Thread.sleep(2000L)//因为代码向下执行，这个函数执行出来，走完Hello，如果不暂停一下，JVM执行函数就结束了。launch里面由于挂起，就不能执行了。、

        //以下代码，类似于以上的代码效果,但是不同的是thread不是挂起函数，而是线程。不能调用delay（挂起函数）
        thread {
            Thread.sleep(1000L)//阻塞式的函数
            println("World in thread")
        }
        println("Hello after thread in Launch 的主线程")
        Thread.sleep(2000L)//等待一下

    }

    private fun launchTwo() {

        GlobalScope.launch {
            delay(1000L)
            println("World in launchTwo")
        }
        println("Hello in LaunchTwo 的主线程")
        //阻塞式的协程代码块,runBlocking会阻塞线程，直到内部代码块执行完
        runBlocking {
            delay(2000L)
        }
    }

    private fun launchTwo2() {
        //代码块内会阻塞，执行完毕，runBlocking默认返回类型Unit，可以指定
        val runBlocking = runBlocking<String> {
            //默认主协程
            GlobalScope.launch {
                //开启一个新的协程
                delay(1000L)
                println("World in launchTwo2")
            }
            println("Hello in LaunchTwo2 的主协程")
            delay(2000L)
            //因为指定了返回类型，所以要有返回值
            return@runBlocking "返回值，在runBlocking中"
        }
        println(runBlocking)
    }

    //</editor-folder>

    //<editor-folder desc="2、基本的协程使用 job ">

    //2、非阻塞式的协程操作
    private fun jobOne() = runBlocking {
        val job = GlobalScope.launch {
            delay(1000L)
            println("World in JobOne Launch")
        }
        println("Hello in JobOne 的主协程")
        //原来这里要用sleep等待launch里面的代码执行完，现在用job操作
        job.join()
    }

    //3、结构化并发，也就是控制协程的生命周期，避免协程泄露，无需join cancel之类的控制，只有作用域内的所有协程都完成，才完成自身。

    private fun cooperationCoroutine() = runBlocking {
        //顶层的一个协程，生命周期作用范围整个函数块

        launch {
            //启动一个新的协程，有自身的作用域和生命周期
            doSome()
        }
        println("Hello in coop 的主协程")
        //这里就不需要job的join，也不需要sleep等待阻塞，来让launch内部执行完毕。
        // 因为这是结构化并发，作用域@runBlocking的生命周期的协程，内部所有执行完，才会结束自身。
        //但是如果显式的调用cancel，那么runBlocking的协程就结束了。如果内部有子作用域没有结束，就会异常。
//        cancel()
    }

    //抽取封装 挂起函数 需要suspend修饰
    private suspend fun doSome() {
        delay(1000L)
        println("World in coop launch")
    }

    //4、coroutineScope,类似于runBlocking，也是可以等内部子协程完成之后，再结束自身协程，但是不同的是，它不会阻塞，而runBlocking会阻塞。
    private fun corScope() = runBlocking {
        //这里写runBlocking，只是为了演示coroutineScope，因为它需要再挂起函数内调用
        //启动一个新的协程
        launch {
            delay(200L)
            println("after delay in launch of corScope")//第二个执行
        }

        //这是一个组合协程，生命周期，会等待内部的协程完工后，再结束自己，但是不会阻塞外部协程的执行
        /*
        在 coroutine内的，launch外的delay后
        after delay in launch of corScope
        delay launch in coroutine 的launch
        属于corScope的最外部的一个sout
         */
        coroutineScope {
            launch {
                delay(500L)
                println(" delay launch in coroutine 的launch")//第三个执行
            }

            delay(100L)
            println("在 coroutine内的，launch外的delay后")//第一个执行
        }
        println("属于corScope的最外部的一个sout")//最后一个执行
    }

    //GlobalScope启动的协程生命周期和应用一样，可作为守护线程。
    private fun testGlobal() = runBlocking {
        //为了演示join，因为GlobalScope启动的协程，不会保持进程的持续存活
        val job = GlobalScope.launch {
            repeat(1000) {
                delay(500L)
                println("守护 协程 $it")
            }
        }
//        delay(2000)//这里用delay，保持JVM存货2s，那么launch内就可以输出2s的运行。然后就结束了。
        job.join()//不用delay的话，用join，可以等待launch执行完。如果外层不是runBlocking之类的，那么就不能你用job操作。
    }

    //</editor-folder>


    //<editor-folder desc="3、协程的操作，取消与超时">

    //1、协程的创建，会生成一个job对象，可以有cancel、join函数操作。Job的取消是协作的，如果job内有计算任务，cancel不会停止计算的。
    private fun cancelJoin() = runBlocking {
        val startTime = System.currentTimeMillis()
        //注意，这里用到了dispatchers的配置，default的默认启动协程的方式，如果不填，就是empty的，那样的效果是，阻塞式的，launch执行完，才会执行下面的。
        //现在default，则类似于异步执行。
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job： 第几次执行打印 ${i++}")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L)//
        println("cancel之前，等候1.3s之后")
        job.cancelAndJoin()
        println("代码执行完毕了")
    }

    //2、如果要取消协程的计算代码执行，要检查取消状态，而且取消不一定百分百成功，可能会有CancellationException异常
    private fun cancelJob2() = runBlocking {
        val startTime = System.currentTimeMillis()
        //注意，这里用到了dispatchers的配置，default的默认启动协程的方式，如果不填，就是empty的，那样的效果是，阻塞式的，launch执行完，才会执行下面的。
        //现在default，则类似于异步执行。
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) {//条件变化了，用来判断协程是否还在,这样，由于下面delay是1.3s，所以只有三次输出，然后就被cancel了。
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job： 第几次执行打印 ${i++}")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L)//
        println("cancel之前，等候1.3s之后")
        job.cancelAndJoin()
        println("代码执行完毕了")
        /*
        job： 第几次执行打印 0
        job： 第几次执行打印 1
        job： 第几次执行打印 2
        cancel之前，等候1.3s之后
        代码执行完毕了
         */
    }

    //finally 代码块的兜底执行。
    private fun cancelFinally() = runBlocking {
        //注意，这里用到了dispatchers的配置，default的默认启动协程的方式，如果不填，就是empty的，那样的效果是，阻塞式的，launch执行完，才会执行下面的。
        //现在default，则类似于异步执行。
        val job = launch {
            try {
                //模拟大计算代码，cancel的话，可能会异常，需要try finally兜底操作。
                repeat(1000) {
                    println("job： 第几次执行打印 $it")
                    delay(500L)
                }
            } finally {
                println("finally 代码块")
            }
        }
        delay(1300L)//
        println("cancel之前，等候1.3s之后")
        job.cancelAndJoin()//join函数，会等待launch的finally完毕，才会取消协程
        println("代码执行完毕了")
    }

    private fun cancelFinally2() = runBlocking {
        //注意，这里用到了dispatchers的配置，default的默认启动协程的方式，如果不填，就是empty的，那样的效果是，阻塞式的，launch执行完，才会执行下面的。
        //现在default，则类似于异步执行。
        val job = launch {
            try {
                //模拟大计算代码，cancel的话，可能会异常，需要try finally兜底操作。
                repeat(1000) {
                    println("job： 第几次执行打印 $it")
                    delay(500L)
                }
            } finally {
                //finally内调用挂起函数，可能会引起cancel异常，因为有些代码是不能取消的。要运行一些不可取消的函数块，需要使用withContext
                withContext(NonCancellable) {
                    println("finally内，withContext中")
                    delay(1000L)
                    println("延迟等待1s后，finally内的non cancel的输出")
                }
            }
        }
        delay(1300L)//
        println("cancel之前，等候1.3s之后")
        job.cancelAndJoin()//join函数，会等待launch的finally完毕，才会取消协程
        println("代码执行完毕了")
    }


    //3、协程的超时，这里超时后，继续执行repeat的话，就会抛出异常 TimeoutCancellationException
    private fun timeOut() = runBlocking {
        //还有一个  withTimeoutOrNull()
        withTimeout(1300L) {
            repeat(1000) {
                println("重复循环输出 $it")
                delay(500L)
            }
            println("in timeOUt内")
        }
        println("timeOut的完成")
    }


    //</editor-folder>

}