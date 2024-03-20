package org.zhiwei.kotlin.flow

import android.content.ComponentCallbacks
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.Duration

/**
 * Flow语法的学习笔记📑
 */
class FlowQuick {
    //0. Flow是数据流，kotlin协程库提供的；用不严谨的分类，可以分为热流和冷流；
    //热流就是产生数据后，不论有无接收者，它会按照自己的逻辑，继续生产数据；
    //冷流 如果无接收者，它不产生数据，有接收者的时候，会接收数据，多个接收者，会接收到全部完整的数据流。


    //1. 一般创建的flow，只有一个观察者，属于冷数据流
    private fun createFlow() = runBlocking {
        //1.1 创建flow的方式
        flow<String> {
            emit("Hello")
            //一般flow创建内部，不可切换协程，否则报错，下面就是会报错
            withContext(Dispatchers.IO) {
                emit("随心的📊")
            }
        }

        emptyFlow<Int>()

        flowOf("World")
        flowOf("你好", "数据流Flow")

        testCallbackFlow()
        testChannelFlow()

        listOf("").asFlow()
        Channel<Int> { }.consumeAsFlow()
        Channel<Int> { }.receiveAsFlow()

        //热流有状态的Flow ，可以有多个观察者，热流
        MutableStateFlow<String>("🐰")
        /*
        可定制化的StateFlow，可以有多个观察者，热流.
        无需初始值，有三个可选参数：
        replay - 重播给新订阅者的值的数量（不能为负，默认为零）。
        extraBufferCapacity - 除了replay之外缓冲的值的数量。 当有剩余缓冲区空间时， emit不会挂起（可选，不能为负，默认为零）。
        onBufferOverflow - 配置缓冲区溢出的操作（可选，默认为暂停尝试发出值）
        使用SharedFlow 你可以写个 FlowEventBus
         */
        MutableSharedFlow<String>()

    }

    private fun testCallbackFlow() {
        //1.1 将普通回调函数，改造成flow可用
        val callbackflow = callbackFlow<String> {
            //产生新数据
            send("")
            //异常的时候
            cancel()
            //完成操作
            close()
            //在close的是，执行一些回调
            awaitClose {

            }
        }
        //随便模拟演示
        val ac: AppCompatActivity? = null
        callbackFlow<String> {
            val callback = object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    //模拟数据变化，或者拿到结果OK的时候
                    launch { send("") }
                }

                override fun onLowMemory() {
                    //模拟异常场景
                    launch { cancel() }
                }
            }

            ac?.registerComponentCallbacks(callback)
            awaitClose {
                ac?.unregisterComponentCallbacks(callback)
            }
        }


    }

    //一般的flow是冷流，且不允许在构造flow的代码中切换协程 ，而ChannelFlow是可以允许内部切换协程的
    private fun testChannelFlow() {
        //类似produce的channel，
        channelFlow<String> {
            send("发送🦷")
            //这里在创建flow的时候，切换了context，flow在搜集的时候，就会是在切换后的协程上；
            //即使上面看似在别的协程send的，但是并未触发操作，创建的时候，最后一个协程环境为准。
            withContext(Dispatchers.IO) {
                send("另一个丫丫1️⃣")
            }
        }
    }

    //2. 操作符号©️ 中间操作符（包括回调，变换，过滤，组合 和功能） 和 末端操作符
    private fun flowOperator() = runBlocking {
        //2.1 末端操作符，就是处理数据流结果的，会触发数据流的执行
        val asFlow = listOf("A", "B").asFlow()
        //collect是搜集，获取所有数据流的发送结果，
        asFlow
            .collect {
                println("搜到 $it")
            }
        //类似的有CollectIndexed，CollectLatest
        asFlow.collectIndexed { index, value ->
            println("带有index的$index,$value")
        }
        //具体效果，见FlowUnitTest的testCollect函数
        asFlow.collectLatest {
            println("处理最新的数据，会丢弃未来得及处理的上个数据")
        }
        //添加到集合
        asFlow.toCollection(mutableListOf())
        asFlow.toList()
        asFlow.toSet()
        //让flow在指定的协程上执行，会触发数据流，得到一个协程job对象。todo 一般配合onEach/onCompletion使用。
        asFlow.launchIn(CoroutineScope(Job()))
        asFlow.last()//得到数据流的最后一个数据，如果null会抛异常，下面的，不会异常，会返回null
        asFlow.lastOrNull()
        //类似上面，这个是获取数据流的第一个数据，或者指定条件的第一个数据
        asFlow.first { it.length == 3 }
        asFlow.firstOrNull()
        //获取单个数据的数据流的数据，如果流是有多个数据，就会报错
        asFlow.single()
        asFlow.singleOrNull()
        //获取数据流的数据个数，或者指定条件的个数，todo 对sharedFlow热流无效
        asFlow.count()
        asFlow.count { it.length == 3 }
        //fold 叠加，如下就是 初始值100，把flow的数据，依次相加，100+A的length，+ B.length，，，，
        val fold = asFlow.fold(100) { acc, value ->
            acc + value.length
        }
        //reduce 也是累加，叠加，层叠的效果，只不过没有初始值及类型 ,见flowUnitTest的testFoldReduce
        asFlow.reduce { accumulator, value -> accumulator + value }
        asFlow.runningReduce { accumulator, value -> accumulator + value }
    }

    //2.2 中间操作符，用于数据流接收前的操作，只有在出现末端操作符的时候，才会真正的触发；其返回对象仍是flow本身，便于链式操作
    private fun testCallback() {
        //回调操作符
        val flow = listOf("A", "B", "C", "D", "E").asFlow()
        flow.onStart {
            //onStart在flow流触发之前调用，可以在此发送额外的数据，也可以做一些其他事情
            emit("F")
            emitAll(listOf("aaa").asFlow())
        }.onCompletion {
            //flow数据流结束，或者异常取消的时候，回调。
            emit("EEE")
            emitAll(listOf("DDD").asFlow())
            it?.printStackTrace()
        }.onEach {
            //在数据上流发送数据的时候调用，
            println("每个数据发出的时候 $it")
        }.onEmpty {
            //数据流完成但是没有数据产生，会调用这里，可以emit数据，也可以做别的
            emit("")
        }
        MutableSharedFlow<Int>()
            .asSharedFlow()//将可变的flow转化为不可变的，类似于集合里面的mutable的变换，或者liveData于MutableLiveData,同理有asStateFlow（）
            .onSubscription {
                //在接收放于数据流建立连接的时候调用，这是sharedFlow的操作符，stateFlow是shareFlow的子类。
                //sharedFlow是热流，所以之前发的数据，新观察者未必收到，flow的onStart里面的操作，也是未必被收到。
            }
    }

    //2.3 转换操作符
    private fun testMapOperator() {
        //类似于集合里面的操作符，map用于转换数据
        val flow = listOf("A", "B", "C", "D", "E").asFlow()
        flow.map { it to it.length }
            .mapLatest {
                //如果转变比较费时，上一次还没完成转变时，来了新的数据，则放弃上个，接着新的转变
                it.first
            }
            .mapNotNull {
                //这个操作符的作用是，转换后的数据，会丢掉null的，仅发送非null数据出去
            }.transform {
                //区别于map，这里可以emit，可以多次操作，跳过，或者条件转换
                emit("")
            }.transformLatest {
                //作用类似其他xxxLatest，就是处理不完时，接收新的，就放弃掉旧的，
                emit(3)
            }.transformWhile {
                emit("DDE")
                //如果为false则不继续后续转换
                it > 99
            }
        runBlocking {
            flow.withIndex()
                .collect {
                    //上面有了withIndex，就会给flow的数据添加了序号
                }
            //scan的符号，类似于fold的作用，只不过fold结果是值，scan的结果还是flow的
            flow.scan(100) { accumulator, value ->
                accumulator + value.length
            }
            //将flow转化为一个produce的channel也就是ReceiveChannel
            flow.produceIn(MainScope()).consumeEach {
            }
        }
        //类似于fold ，但是 会将每个步骤都发送出去testFoldReduce 有类似感知
        flow.runningFold("SSS") { accumulator, value -> accumulator.toString() + value }
        //类似于reduce ，但是 会将每个步骤都发送出去testFoldReduce 有类似感知
        flow.runningReduce { accumulator, value -> accumulator + value }
        //转化为sharedFlow，
        /*
        其中 started 有一些可选项:
        Eagerly : 共享立即开始，永不停止
        Lazily : 当第一个订阅者出现时,永不停止
        WhileSubscribed : 在第一个订阅者出现时开始共享，在最后一个订阅者消失时立即停止（默认情况下），永久保留重播缓存（默认情况下）
        WhileSubscribed 具有以下可选参数：
        stopTimeoutMillis — 配置最后一个订阅者消失到协程停止共享之间的延迟（以毫秒为单位）。 默认为零（立即停止）。
        replayExpirationMillis - 共享的协程从停止到重新激活，这期间缓存的时效
         */
        flow.shareIn(
            MainScope(), //协程域
            SharingStarted.Lazily,//启动模式
            2,//重复给新的接收者几个数据
        )
        runBlocking {
            flow.stateIn(MainScope())//转化为stateFlow
        }

        val channel = Channel<Int>()
        channel.receiveAsFlow()//channel转化为flow的形式
        channel.consumeAsFlow()//channel接收数据用flow的形式

    }

    //2.4 过滤操作符
    private fun testFilterOperator() {
        val flow = listOf("A", "B", "C", "D", "E").asFlow()
        flow.filter { it.length > 2 }
        flow.filterNot { it.length > 2 }
        flow.filterNotNull() //过滤非空的出来
        flow.filterIsInstance<Int>()//过滤是指定类型的数据对象出来，对于此flow，都是String的，没有Int的
        //drop丢弃掉指定个数的数据
        flow.drop(3)//丢掉前n个数据
        // 如果第一个元素就不满足条件，则不再判断；如果第一个满足，则丢弃，依次判断，知道不满足的元素，开始返回。
        flow.dropWhile { it.length == 2 }//返回所有除了第一个满足条件的数据，作用效果来看，
        //取值
        flow.take(3)//取前n个数据
        flow.takeWhile { it.length == 2 }//如果满足条件，就取值满足条件的之前的，如果第一个就不满足条件，就是空数据流了。
        //防抖动
        flow.debounce(100L)//每次元素的触发时间窗口 100ms内直接收最新一个，其他的过滤掉
        flow.debounce { if (it == "A") 0L else 100L }//按照规则的时间过滤周期
        flow.debounce(Duration.parse("1000"))//Duration的单位
        //采样
        flow.sample(200)//指定时间周期内，获取最新数据
        //相邻去重复
        flow.distinctUntilChanged()//如果相邻的数据是相同的，只会第一个发出去
        flow.distinctUntilChanged { old, new -> old.length == new.length }//或者自己指定判断规则，满足规则的认为是相邻的相等
        flow.distinctUntilChangedBy { it.first() }//也可根据某个条件判断
    }

    //2.5 组合操作符
    private fun combineOperator() {
        val flow = listOf("A", "B", "C", "D", "E").asFlow()
        val flow2 = listOf(1, 52, 35, 4, 115, 60, 7, 8, 12).asFlow()
        //todo 简单理解，就是用流各自最新的值相结合，而不一定是元素的下标要对应！
        //combine组合两个数据流,会组合成新的数据流，
        flow.combine(flow2) { a: String, b: Int ->
            //定义各个元素的组合规则，E和 115 组合完，flow没有新值，flow2有新值，则就会用最后一个flow的值，与之新值匹配。
            "$a,,$b"
        }
        //组合两个数据流的元素，然后通过第三种数据类型的流，发射出去
        flow.combineTransform<String, Int, Boolean>(flow2) { a: String, b: Int ->
            this.emit(a.length == b)
        }
        //合并流,merge属于集合的操作符，
        runBlocking {
            listOf(flow, flow2).merge().collect {

            }
        }
        //flow<Flow<Int>> 展开数据流的数据，类似于list内嵌list的操作符，就是将子flow的元素，平级的展开出来
        flow<Flow<Int>> { }.flattenConcat()
        //
        val flatMapMerge2 =
            flow<Int> { }.flatMapMerge(concurrency = 2) { flowOf("VV: $it") } //concurrency=1就是flattenConcat 来限制并发数。
        flow.flatMapConcat { flowOf(it) }
        flow.flatMapLatest { flowOf(it) }
        //交织组合两个数据流元素，以最少的为准。得到新的数据流
        flow.zip(flow2) { s: String, i: Int ->
            "$s..$i"
        }
    }

    //2.6 功能行操作符
    private fun funcOperator() {
        val flow = listOf("A", "B", "C", "D", "E").asFlow()
        //接收数据的时候，可用于判断是否协程已经取消，若取消了就异常;默认flow构造函数和sharedFlow已经实现这个 。
        flow.cancellable()
        //catch捕获上游异常，这里的上游，指该操作符之前的数据流，对操作符之后的数据流无效
        flow.catch {
            println("${it.message}")
        }
        flow.retryWhen { cause, attempt ->
            //cause是异常原因，attempt是重试次数，从0开始
            //返回boolean，若是true则继续重试
            attempt == 3L
        }
        flow.retry {
            false
        }
        flow.buffer(20)//数据流的buffer缓存区，，会让数据操作在一个单独的协程，而不会影响原发射方协程
        flow.conflate()//仅保留最新值，也是单独的协程，再怎么慢都不阻塞
        flow.flowOn(Job())//指定flow的上游操作所在协程，用于执行切换flow作用线程的。
    }

    // 搜索场景使用debounce防抖，网络请求使用retry,组件通信使用SharedFlow, 数据合并使用combine等操作符
    //reduce是全部叠加计算完成后被收集
    //scan是每次叠加一次后收集一次数据


}