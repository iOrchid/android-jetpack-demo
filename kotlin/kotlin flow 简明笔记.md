#### kotlin flow 简明笔记

##### 一、分类

`flow`所谓冷流，即一个数据流，对应只有一个观察者；任何新建立的观察者，都会得到完整的数据流

```kotlin
//构建
val testFlow = flow<String> {
    emit("Hello")
    emit("world")
}
//接收
coroutineScope.launch {
    testFlow.collect { value ->
        println(value)
    }
}
//输出
Hello
world
```

`StateFlow`所谓热流，即有状态的数据流，有初始状态值。新的观察者不会接收之前的数据。

```kotlin
//构建
val uiState = MutableStateFlow(Result.Loading)
//监听
coroutineScope.launch {
    uiState.collect {
        println(it)
    }
}
//赋值
uiState.value = Result.Success
//输出
Result.Loading
Result.Success
```

`SharedFlow`可定制化的`StateFlow`,可以有多个观察者，无需初始值。

> `replay`，默认0，不能为负数。表示给新订阅者重播多少之前的旧数据。
>
> `extraBufferCapacity`，默认0，不能为负数，表示除`replay`之外的缓存数据量，有缓存空间时，`emit`并不会挂起。
>
> `onBufferOverflow`缓冲区溢出策略，默认暂停发送新数据。

```kotlin
//创建
val signEvent = mutableSharedFlow<String>()
//监听
coroutineScope.launch {
    signEvent.collect {
        println(it)
    }
}
//赋值
signEvent.tryEmit("Hello")
signEvent.tryEmit("world")
//输出
Hello
world
```

##### 二、操作符

1. 中间操作符，用来执行一些操作，返回值仍为`flow`
2. 末端操作符，会触发流的执行，返回值非`flow`

###### 创建Flow

用于创建`flow`对象的操作符

> ```kotlin
> //1. flow
> flow<String>{
>   emit("Hi")
>   emit("YoYo")
>   emitAll(flowOf("A","b"))//可发射一个流
> }
> //2. flowOf
> //3. asFlow
> listOf(1,2,3).asFlow()
> //4. callbackflow 讲回调方法 转换为flow，类似于suspendCoroutine
> callbackFlow<String>{
>   //里面有一些函数，用于桥接callback，
>   awaitClose{}
>   send("")
>   cancel()
>   channel.close()//kotlin的channel
> }
> //5. emptyFlow
> //6. channelFlow 一般的flow不能在构造代码中切换线程/协程，`ChannelFlow`则可以
> channelFlow<String>{
>   send("hello")
>   //切换协程
>   withContext(Dispatchers.IO){
>     send("channel flow")
>   }
> }
> ```

###### 末端操作符

1. `collect`触发数据流的执行，通常的监听方式。

2. `collectIndexed`

3. `collectLatest`

   > 与 `collect`的区别是 ，有新值发出时，如果此时上个收集尚未完成，则会**取消**掉上个值的收集操作

4. `toCollection`将结果添加到集合

5. `toList`

6. `toSet`

7. `launchIn`直接触发数据流的执行，一般搭配`onEach/onCompletion`等执行，返回`Job`

8. `last`/`lastOrNull`

9. `first/firstOrNull`

10. `single`接收数据流的第一个值，区别于`first`这里仅允许且必须发一个值，为空或者多个都会报错。

11. `singleOrNull`可为空的；

12. `count`返回数据流的个数，对`sharedflow`无意义。

13. `fold`从初始值开始，遍历并将结果作为下一个的操作入参

    ```kotlin
    //此处演示求和，其他操作类似
    val sum = flowOf(2,3,4).fold(1,{result,value->
                         result+value
                         })
    //初始值 1 ，依次执行操作 ，得到结果。
    ```

14. `reduce`类似于`fold`，无需初始值。

###### 回调操作符

- **onStart**，在上游数据流开始之前就被调用，可发出额外的元素，或者执行其他操作，比如日志搜集。

  ```kotlin
  flow<String>{
    emit("A")
  }.onStart{
    emit("B")
  }
  //实际输出
  B
  A
  ```

- **onCompletion**，在数据流取消或者结束时调用，可在此执行一些操作，发送元素或者日志等。

- **onEach**，上游数据发送每个元素到下游之前，回调此处。

- **onEmpty**，上游流数据完成，但实际空元素时候，回调，可用于兜底。

- **onSubscription**，属于`SharedFlow`的专属操作符，在建立订阅关系之后回调。区别于`onStart`，`onStart`
  内的操作未必每个后来的观察者都能收到。

###### 变换操作符

```kotlin
map
mapLatest //类比collectLatest 当有新值发送时候，如果上个操作没有完成，则会被先取消掉，不影响新值操作
mapNotNull //仅发送map后非null的值
transform //对发出的值变换，可跳过或多次发送，其接收者时FlowCollector
transformLatest//类似于mapLatest
transformWhile //匹配条件的转换
asStateFlow //将MutableStateFlow转化为不可变的StateFlow
asSharedFlow
receiveAsFlow //将channel转换为flow，可以有多个观察者，但不支持多播，可能会轮流收到值
consumeAsFlow //将channel转换为flow，不能多个观察者，会报错
withIndex //添加索引序号
scan //类似fold ，fold返回结果值，而san会把初始值和每步操作发送出去，依旧是flow形式
produceIn //转化为ReceiveChannel
runningFold //类似fold ，结果返回的是新的flow，每步操作发送出去
runningReduce // 类似reduce 返回新的flow，每步操作发送出去
shareIn //将flow转化为SharedFlow，三个参数scope，started策略，replay给新订阅者的重发旧据个数
stateIn //普通流转为StateFlow，
```

###### 过滤操作符

> filter/filterInstance/filterNot/filterNotNull
>
> drop：丢弃指定个数的值，从前部算；
>
> dropWhile：**注意**，并不是常规意义的理解丢掉满足条件的值。而是匹配第一个，满足与否，决定返回其之后的值
>
> take/takeWhie
>
> debounce防抖截流，指定时间内 只接受最新一个，其他则不处理
>
> sample 采样，类似于debounce，指定之间周期内，取最新值
>
> distinctUntilChjangedBy 去重复操作符，判断连续的两个值是否重复，可以选择丢弃与否。
>
> distinctUntilChanged，过滤用，连续两个值一样则跳过发送

###### 组合操作符

1. combine 组合两个数据流的元素发送
2. combineTransform 组合转换
3. merge 合并流成一个新的流
4. flattenConcat 以顺序的方式将给定的流展开为单个流
5. flattenMerge 类似于flattenConcat 若concurrency>1时，并发收集
6. flatMapContact 转换和展开流
7. flatMapLatest 类似其他的Latest操作符，保障最新值
8. flatMapMerge
9. zip 组合流，其一结束则结束

###### 功能操作符

- cancellable ，使flow在接收时可判断是否协程已被取消，若取消则抛异常
- catch 对上游异常捕获，对下游无影响
- retryWhen 有条件重试
- retry
- buffer 如果操作符的代码需要相当长的时间来执行，可使用buffer在执行期间创建单独的协程
- conflate 仅保留最新值
- flowOn 指定上游的操作执行线程，用于切换执行线程

> 上下游是指操作符前，后 而言。

