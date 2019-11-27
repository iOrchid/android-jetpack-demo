package org.zhiwei.jetpack.kt.advanced

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.system.measureTimeMillis

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
 * 用于演示协程相关使用的代码，简单的job调用、cancel、join，生命周期以及timeOut
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

/**
 * 协程的进阶用法
 * 1、组合挂起函数。async类似于launch都是创建新的协程，但是async是返回的job的子类，deferred，观察者模式的，延迟获取数据。所以job的操作，如join、cancel都是可以的。
 */
object CoroutineKt2 {

    //演示 入口
    fun testCor2() {
//        common2()
//        async2()
//        asyncLazy2()

//        asyncTest()
//        asyncTest2()

        constructTest()

    }

    //1、模拟业务场景，两个耗时操作，得到结果后，合并结果操作
    private fun common2() = runBlocking {
        //计算函数耗时的
        val measureTimeMillis = measureTimeMillis {
            val a = numOne()
            val b = numTwo()
            println("两个耗时结果，共同操作 ${a + b}")
        }

        println("顺序执行函数耗时： $measureTimeMillis")
    }


    //2、使用async并发，优化函数调用.这样执行结果，会快很多。todo 注意a、b都是Deferred，不要val a = async { numOne()}.await(),反而会更加耗时
    private fun async2() = runBlocking {
        //计算函数耗时的
        val measureTimeMillis = measureTimeMillis {
            val a = async { numOne() }
            val b = async { numTwo() }
            println("两个耗时结果，共同操作 ${a.await() + b.await()}")
        }

        println("顺序执行函数耗时： $measureTimeMillis")
    }

    private fun asyncLazy2() = runBlocking {
        //添加了lazy的启动策略，得到的a、b只有在a.start，b.start调用时候，才会真的执行协程创建。或者直接用await获取结果时候，才执行(此时，就耗时了，不如start那样可以异步)。
        val measureTimeMillis = measureTimeMillis {
            val a = async(start = CoroutineStart.LAZY) { numOne() }
            val b = async(start = CoroutineStart.LAZY) { numTwo() }
            //开始执行,
            a.start()
            b.start()
            println("两个耗时结果，共同操作 ${a.await() + b.await()}")
        }

        println("顺序执行函数耗时： $measureTimeMillis")
    }

    //3、演示异步函数的组合定义，使用，这里外部不需要定义为挂起函数，只是在结果处理时候，用到挂起函数块
    private fun asyncTest() {
        val time = measureTimeMillis {
            //非挂起函数，可以在任何地方调用，但是想得到结果去操作，就需要在挂起函数块内执行
            val a = numOneAsync()
            val b = numTwoAsync()

            runBlocking {
                println("异步函数的两个结果 ${a.await() + b.await()}")
            }
        }
        println("耗时: $time")
    }

    // 合理封装的异步，结构化的并发
    private fun asyncTest2() = runBlocking {
        val millis = measureTimeMillis {
            println("异步封装后，计算结果： " + cooperationSum())
        }
        println("代码执行耗时： $millis")
    }


    private fun constructTest() = runBlocking<Unit> {
        //执行的协程，会有异常
        try {
            simulateError()
        } catch (e: ArithmeticException) {
            println("捕获到 协程的异常")
        }
    }

    //模拟出错的 结构化并发协程
    private suspend fun simulateError(): Int = coroutineScope {
        val a = async<Int> {
            try {
                delay(Long.MAX_VALUE)
                42
            } finally {
                println("在asyncOne中的finally模块代码，被取消的时候执行了。")
            }
        }

        val b = async<Int> {
            println("这里模拟抛出异常")
            throw ArithmeticException()
        }
        //return 的结果
        a.await() + b.await()
    }


    //todo Kotlin 推荐的封装方式
    private suspend fun cooperationSum(): Int = coroutineScope {
        val a = async { numOne() }
        val b = async { numTwo() }
        a.await() + b.await()
    }


    //todo 2、模拟两个异步的函数 注意这不是挂起函数，因此可以在任何地方调用，只是是个异步的。
    // 这里用的是Global的生命周期Scope。但是Kotlin中不推荐这么封装!!!!不能够结构化并发
    private fun numOneAsync() = GlobalScope.async {
        numOne()
    }

    private fun numTwoAsync() = GlobalScope.async {
        numTwo()
    }

    //todo 1、两个模拟耗时的挂起函数
    private suspend fun numOne(): Int {
        delay(1000L)
        return 12
    }

    private suspend fun numTwo(): Int {
        delay(800L)
        return 20
    }
}

/**
 * 协程调度器、context上下文
 */
object CoroutineKt3 {

    //演示入口，dispatcher、context
    fun testDisCtx() {
//        testDispatcher()
//        testUnLimitedDispatcher()
//        testCtx()
//        testJobScope()
//        testJobScope2()
//        namedCor()

        testThread()
    }

    //1、演示调度器的类型及作用 Dispatchers.Default/Unconfined/IO/Main(这个需要添加额外的android依赖库)
    private fun testDispatcher() = runBlocking {
        //工作在上层的协程中，这里就是runBlocking的主协程
        launch { println("运行在父协程（runBlocking）的context中： thread=${Thread.currentThread().name}") }
        //工作在不受限制的协程中，也就是主线程了
        launch(Dispatchers.Unconfined) { println("运行在Unconfined： thread=${Thread.currentThread().name}") }
        //工作在默认的调度器
        launch(Dispatchers.Default) { println("运行在Default： thread=${Thread.currentThread().name}") }
        //工作在一个新的线程中的协程里
        val context = newSingleThreadContext("自定义NewThread")
        launch(context) { println("运行在自定义新线程： thread=${Thread.currentThread().name}") }
        context.close()//newSingleThreadContext是开启一个新的专用线程，这个很浪费资源，需要的时候，要关闭

        println("外层的runBlocking的代码块 thread=${Thread.currentThread().name}")

    }

    //2、受限&非受限调度器,Unconfined,适合用于不消耗cpu时间的任务，
    private fun testUnLimitedDispatcher() = runBlocking {

        println("RunBlocking根节点的Thread协程 ${Thread.currentThread().name}")

        //使用的是Unconfined的非受限调度器，注意这里delay前后，运行线程的区别。挂起&恢复
        launch(Dispatchers.Unconfined) {
            println("Unconfined delay之前thread ${Thread.currentThread().name}")
            delay(500L)
            println("Unconfined ==delay后thread== ${Thread.currentThread().name}")
        }
        //默认的，就是使用上层的协程context，
        launch {
            println("runBlocking的 delay之前thread ${Thread.currentThread().name}")
            delay(1000L)
            println("runBlocking的 ==delay后thread== ${Thread.currentThread().name}")
        }

        /*todo 运行结果如下：可以看出Unconfined的调度，挂起之前是继承上层的context，恢复的时候，就到默认的DefaultExecutor中去了。一般不推荐使用Unconfined。
           这就体现了一个特点：协程可以在一个线程挂起，而在另一个线程恢复。
        RunBlocking根节点的Thread协程 main @coroutine#1
        Unconfined delay之前thread main @coroutine#2
        runBlocking的 delay之前thread main @coroutine#3
        Unconfined ==delay后thread== kotlinx.coroutines.DefaultExecutor @coroutine#2
        runBlocking的 ==delay后thread== main @coroutine#3
         */
    }


    //3、线程间跳转，context的切换
    private fun testCtx() {
        //use函数，会自动关闭资源
        newSingleThreadContext("Ctx1").use { ctx1 ->
            newSingleThreadContext("Ctx2").use { ctx2 ->
                runBlocking(ctx1) {
                    println("runBlocking 使用Ctx1")
                    withContext(ctx2) {
                        println("withContext 使用Ctx2")
                    }
                    println("在withContext之后，回归Ctx的runBlocking")
                }
            }
        }
    }

    //3、在协程CoroutineScope内部启动新的协程，就构成了子协程的关系，如果父协程被取消，它生命周期内的子协程会被递归取消。GlobalScope特殊点，它没有父协程
    private fun testJobScope() = runBlocking {
        //模拟一个耗时请求，内部有两个协程，一个Global，一个直接继承当前Scope的
        val requestJob = launch {
            //GlobalScope的，它创建的协程，没有父协程ctx，它的生命周期，就不受外层scope影响控制
            GlobalScope.launch {
                println("GlobalScope中，delay之前")
                delay(1000L)
                println("== GlobalScope中，delay之后 ==")
            }
            //普通的scope 协程，属于上层的子协程，生命周期收上层控制
            launch {
                delay(100L)
                println("子协程中")
                delay(1000L)//由于时间设置的问题，下面的print执行时候，其实已经调用了requestJob的cancel，所以不会输出。但是global中的可以输出。不受影响的。
                println("== 子协程中，delay之后 ==")
            }

        }
        //演示延迟以下，看看cancel后，global中的会怎样,延时也是为了保活jvm，以便观看global的执行。业务写法不能这么
        delay(500L)
        requestJob.cancel()
        delay(1000L)
        println("------结束了-----")

    }

    private fun testJobScope2() = runBlocking {
        val request = launch {
            repeat(3) {
                launch {
                    delay(it * 500L)
                    println("子协程的任务输出")
                }
            }
            println("外层协程的   输出text")
        }
        //这里只需join最外的协程，不必join内部的子协程，依然可以保证等到所有子协程执行完毕
        request.join()
        println("----------------over----------------")
    }

    //todo 4、给协程命名，便于管理追踪，协程的context，可以由多种元素拼接
    private fun namedCor() = runBlocking {
        val launch = launch(CoroutineName("协程launch")) {
            println("launch coroutine kotlin name ")
        }
        //组合元素，协程的context，可以由多种元素拼接 使用+
        val async = async(CoroutineName("async 协程名") + Dispatchers.Default) {
            println(
                "async 异步 协程 name"
            )
            999
        }

        println("launch $launch \n async ${async.await()}")
    }


    //5、协程生命周期的管控.在activity需要同步协程周期于activity的ui周期，可以用MainScope得到对象，然后在destroy中cancel，控制协程，也可以让activity实现协程生命周期CoroutineScope接口

    private fun testScope() = runBlocking {
        //1、 获取mainScope,在destroy中cancel,需要的地方，用来produce或者launch函数
        val mainScope = MainScope()

        //2、让activity实现接口,并委托给CoroutineScope配置管理生命周期，如此，在activity被销毁的时候，内部的协程，会被自动cancel
        class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {}


        val coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope
    }

    //todo 6、线程局部数据。协程，并不同于线程，它可能不依赖据特定线程，所以将线程内的数据共享与不同的协程之间，就需要额外的context。
    private val threadLocal = ThreadLocal<String?>()//声明的线程局部变量

    private fun testThread() = runBlocking {
        threadLocal.set("This is a Value of Main")
        println("pre main-------------------当前线程 ${Thread.currentThread()} 线程内局部变量 ${threadLocal.get()}------------------------")
        val launch =
            launch(Dispatchers.Default + threadLocal.asContextElement("That value of Launch")) {
                println("开始launch ，当前线程是 ${Thread.currentThread()} 线程内局部变量：${threadLocal.get()}")
                yield()//调用yield，使线程取消
                println("yield 之后 ，当前线程是 ${Thread.currentThread()} 线程内局部变量：${threadLocal.get()}")
            }
        launch.join()
        println("post main-------------------当前线程 ${Thread.currentThread()} 线程内局部变量 ${threadLocal.get()}------------------------")

        /* 运行结果:表明，协程间的数据改变，不会传递。如果想同步共享，需要用withContext asContextElement，ThreadContextElement之类的。
pre main-------------------当前线程 Thread[main @coroutine#1,5,main] 线程内局部变量 This is a Value of Main------------------------
开始launch ，当前线程是 Thread[DefaultDispatcher-worker-2 @coroutine#2,5,main] 线程内局部变量：That value of Launch
yield 之后 ，当前线程是 Thread[DefaultDispatcher-worker-3 @coroutine#2,5,main] 线程内局部变量：That value of Launch
post main-------------------当前线程 Thread[main @coroutine#1,5,main] 线程内局部变量 This is a Value of Main------------------------

         */
    }


}

/**
 * 协程 异步流
 * yield作用
 * 1.暂时打断一件长耗时的任务，并且让其他任务一个公平的机会去执行
 * 2.检查当前任务是否是被取消，这个任务可能并没有在最后检查自己是否被取消
 * 3.当你的入栈的任务数大于当前允许并行的数目时，允许子任务执行。这个对与任务依赖很重要。
 *  map、filter、transform、flow、take、collect等操作符，类似于rxJava中的操作符。
 */
object CoroutineKt4 {

    fun testKt4() {

//        testSeq()
//        testFlow()
//        testMap()
//        testTrans()
//        testChangeCtx()
//        testCom()
//        testFlowMap()

//        testEx()
//        testEx2()
        testEx3()

    }

    //1、Sequence表示一个序列数据,同步计算数据流，传统的数据方式，这里会阻塞线程
    private fun foo(): Sequence<Int> = sequence {
        for (i in 1..3) {
            Thread.sleep(1000)
            yield(i)//产生数据
        }
    }

    //异步模拟数据结果
    private suspend fun foo2(): List<Int> {
        delay(1000)
        return listOf(1, 2, 3)
    }

    private fun testSeq() {
        //普通的foo函数，产生数据会阻塞线程
//        foo().forEach { println("value=$it") }
        //这里是使用协程异步的函数调用
        runBlocking {
            foo2().forEach { println("coroutine value=$it") }
        }
    }

    //todo 2、kotlin高阶函数，产生流数据,需要匹配collect收集来操作
    // ,flow代码块内的函数可以挂起，而且自身就是一个挂起函数,!!! 注意，flow数据流，成为冷数据流，只有在调用collect的时候，才会执行。单独的调用foo，并不会。
    // 流数据可以取消；还有flowOf，asFlow，flow{}用于构建流数据
    private fun fooFlow(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            emit(i)
        }
    }

    //演示 kotlin 流数据的处理
    private fun testFlow() = runBlocking {
        launch {
            for (i in 1..3) {
                println("这个输出就是为了验证，并发携程并没有阻塞主线程")
                delay(1000)
            }
        }
        fooFlow().collect { println("流数据的结果 $it") }

        //不同的构造方式
        (1..3).asFlow()
        flowOf(1, 23, 32)

        flow {
            emit(999)
        }
    }

    //3、过渡流map、filter 。流数据于sequence的区别在于，流操作符内的代码块可以是挂起函数。
    private suspend fun longTimeRequest(request: Int): String {
        //模拟耗时请求的结果响应
        delay(1000)
        return "Response for request $request"
    }

    //模拟map数据流的操作
    private fun testMap() = runBlocking {
        (1..3).asFlow().map { req ->
            //模拟调用请求
            longTimeRequest(req)
        }.collect { rsp ->
            println("得到响应的数据结果--//-- $rsp")
        }
    }

    //4、转换操作符 transform
    private fun testTrans() = runBlocking {
        (1..3).asFlow().transform { req ->
            emit("==开始请求== request $req")
            emit(longTimeRequest(req))
        }.collect { rsp ->
            println("transform的rsp： $rsp")
        }
    }

    //todo 5、操作符演示使用:限长操作符take、末端流操作符（toList/toSet/first/single/reduce/fold）。
    // 流的收集发生在调用协程的context中。
    private fun testOperator() = runBlocking {
        (1..99).asFlow()
            .take(10)//摘取指定个数
            .reduce { a, b -> a + b }//将流数据的元素，按照规则，整合为一个
        fooFlow().zip(fooFlow()) { a, b -> "$a , $b" }//关于flow流的zip合并操作符
    }

    //6、切换context的场景
    private fun testChangeCtx() = runBlocking {
        //todo flow流数据的发射应在搜集的协程ctx，而不能单独配置ctx，会异常 Flow invariant is violated
        fun foo() = flow {
            kotlinx.coroutines.withContext(Dispatchers.Default) {
                for (i in 1..3) {
                    delay(200)
                    emit(i)
                }
            }
        }
        //注释掉，异常
//        foo().collect { println(it) }

        // todo 正确的切换ctx方式是flowOn
        fun boo() = flow {
            for (i in 1..3) {
                delay(200)
                emit(i)
            }
        }.flowOn(Dispatchers.Default)
        boo().collect { println(it) }
    }

    //7、测试缓存，如上flow发射数据单个元素耗时，collect搜集时候，也是耗时。可以考虑将发射元素放在buffer内，就能再发射时候进行异步发射。
    private fun testBuffer() = runBlocking {
        fooFlow()
            .buffer()//使用buffer缓存，这样发射耗时就会节省很多
            .conflate()//操作符的作用是，跳过不必要的数值，直接给最新值。用于收集器太慢的时候

//            .collectLatest()//只搜集最后一个值
    }

    //todo 操作符 combine
    private fun testCom() = runBlocking {
        val nums = (1..3).asFlow().onEach { delay(300) } // 发射数字 1..3，间隔 300 毫秒
        val strs = flowOf("one", "two", "three").onEach { delay(400) } // 每 400 毫秒发射一次字符串
        val startTime = System.currentTimeMillis() // 记录开始的时间
        nums.zip(strs) { a, b -> "$a -> $b" } // 使用“zip”组合单个字符串
            .collect { value ->
                // 收集并打印
                println("Zip操作符 $value at ${System.currentTimeMillis() - startTime} ms from start")
            }
        // combine操作符，就好比，两个栈取数值，然后组合计算，不同的时间，各自栈顶的数都要运行计算。而zip，则是元素数匹配，按照最慢的节奏来一对一合并。
        nums.combine(strs) { a, b -> "$a -> $b" } // 使用combine操作符，不同于zip的结果
            .collect { value ->
                // 收集并打印
                println("-- combine 操作符 $value at ${System.currentTimeMillis() - startTime} ms from start -- ")
            }
    }

    //展平流，将每个流元素，转化为flow流，类似的有flatten flatMap，flattenConcat，flatMapConcat
    private fun testFlowMap() {
        fun requestFlow(i: Int): Flow<String> = flow {
            emit("$i: First")
            delay(500)
            emit("$i: Second")
        }
        /*
        1: First collect输出，展平流
        1: Second collect输出，展平流
        2: First collect输出，展平流
        2: Second collect输出，展平流
        3: First collect输出，展平流
        3: Second collect输出，展平流
         */
        runBlocking {
            //                        (1..3).asFlow()
//                .map { requestFlow(it) }
//                .collect { flow ->
//                    flow.collect {
//                        println("$it collect输出，展平流 112233")
//                    }
//                }

            //flatMapMerge 略有不同，会将流的结果单独展开,
            /*
                1: First collect输出，展平流
                2: First collect输出，展平流
                3: First collect输出，展平流
                1: Second collect输出，展平流
                2: Second collect输出，展平流
                3: Second collect输出，展平流
             */
//            (1..3).asFlow()
//                .flatMapMerge { requestFlow(it) }
//                .collect { flow ->
//                    println("$flow collect输出，展平流 123 123")
//                }

            //flatMapLastest emit如果耗时，会接收最新值，取消所有其他的，直接执行最新的那个流元素
            (1..3).asFlow()
                .flatMapLatest { requestFlow(it) }
                .collect { flow ->
                    println("$flow collect输出，展平流 1233")
                }
        }
    }

    //测试流 异常 try catch
    private fun testEx() {
        fun foo(): Flow<Int> = flow {
            for (i in 1..3) {
                println("Emitting $i")
                emit(i) // emit next value
            }
        }

        runBlocking {
            try {
                foo().collect { value ->
                    check(value <= 1) { "Collected $value" }
                    //check是内联函数，满足条件则运行，否则抛异常
                    println(value)
                }
            } catch (e: Throwable) {
                println("Caught $e")
            }
        }
    }

    //上面的异常捕获实在搜集端，这里的是在发射端
    private fun testEx2() {
        fun foo() = flow {
            for (i in 1..3) {
                println("Emitting $i")
                emit(i) // emit next value
            }
        }.map {
            check(it <= 1) { "检查出现异常 $it" }
            "正常值 $it"
        }

        runBlocking {
            try {
                foo()
//                    .catch { e->emit("异常了，这个元素 $e 也挺特别的") }//将异常信息再次转化为流元素发射
                    .collect { value -> println(value) }
            } catch (e: Throwable) {
                println("Caught $e")
            } finally {
                //最终代码块，必然执行
                println("Done Done Done!!!")
            }

            fooFlow()
                .onCompletion { println("完成时候的执行") }
                .collect { println("it it it $it") }

        }
    }

    private fun testEx3() {
        fun foo(): Flow<Int> = flow {
            emit(1)
            throw RuntimeException()
        }

        runBlocking {
            foo().onCompletion { cause -> if (cause != null) println("声明式发现异常信息，但不会捕获，并输出") }
                .catch { cause -> println("catch 才真正的捕获异常") }
                .collect { println("it ex it $it") }
        }
    }

    private fun testOther() {
        runBlocking {
            (1..3).asFlow()
                .onEach { println("onEach $it") }
                .launchIn(this)
        }
    }
}