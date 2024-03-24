#### Jetpack笔记速览

##### 一、前言

> [Jetpack](https://developer.android.google.cn/jetpack?hl=zh-cn)
> 是Android官方推出的一些列组件库，最早`databinding`
> 也属于官方推荐的工具写法，但是由于其影响编译性能，且`xml`
> 中编写出错的时候IDE不易准确检测和报错，逐步不再使用。且`compose`
> 更为高效的UI编写方式，所以该项目内`dataBinding`有独立module作为代码示例，并有详细注释说明，便于学习。
>
> 如果不是接手老项目需要，可以跳过，直接学习compose；如果是老项目有databinding，则可以学习了解即可。

Jetpack的其他组件则都会共用在同一个项目module内，同时也是使用MVVM的架构设计模式，来简单实现一个组合使用各个库的示例。jetpack有个android的官方示例sunflow可github搜索。

<font color=orange>
以下笔记仅用于初步了解库的使用和简单构成，不太深入基本原理。先知其然，然后自然而然你会探索其所以然。</font>

可以在`IDE`的project模式下，`External Libraries`找到想看的库的编译源码。

##### 二、Lifecycle

> 简言之，Lifecycle就是观察者模式，用以感知UI元素的生命周期的。Android中Activity/Fragment级别的实现。

简要理解`Lifecycle`库就核心三个类文件：`Lifecycle`、`LifecycleOwner`、`LifecycleObserver`

1. `Lifecycle`就是表述生命周期的，抽象类，提供观察者的注册/注销。内有生命周期的不同状态`State`
   和`Event`。
2. `LifecycleOwner`就是上面`lifecycle`的持有者，Android中是`componentActivity/Fragment`
   类实现的。两个`componentActivity`类，一般使用`andriodx.activity`包下的，因为这个实现了`viewModel`
   的结合。开发中多用`AppcompatActivity来作为基类`，直接使用`Activity`作为基类就没有这个实现。
3. `LifecycleObserver`和子类`LifecycleEventObsever`就可以添加到上面的`lifecycle`
   注册观察，就能感知到生命周期变化的回调。具体你不实现，可参照源码，`LifecycleController`等分析。

⚠️：自定义的业务中，可以参照如上，实现自定义的lifecycle或者添加observe来关联Ui的生命周期。

##### 三、LiveData

> 又一个观察者模式的实现，数据变化时候可以得到及时的通知。两个核心库`lifecycle-livedata-core`
> 和`lifecycle-livedata`

1. `core`库就三个文件：`Observer`接口，`LiveData`抽象类和`MutableLiveData`
   的类。LiveData内有观察者的注册/注销，及数据变化的管理和通知等逻辑。
2. `livedata`库有`MediatorLiveData`，`Transformations`和一些**协程**相关的实现类，扩展函数等。

```kotlin
private val _liveStudentScore = MutableLiveData<String>()
//一般mutable的都是可修改的，开发习惯，可修改额私有，对外提供不可变的。
val liveScore: LiveData<String> = _liveStudentScore
//map函数，就是将一种数据类型，按照你需要的规则，转化为另一种数据类型，然后得到一个新的liveData，可用于观察
val map = _liveStudentScore.map { it.length }
//switchMap是将上游livedata的数据，以新的liveData的形式重新发射出去。
val switchMap = _liveStudentScore.switchMap { liveData { emit(it.length) } }
//使用协程的形式创建liveData
liveData<Int> {}

//创建一个新的liveData，感知数据变化，如果临近的两次数据值是一样的，它不会感知变化。
val distinctUntilChanged = _liveStudentScore.distinctUntilChanged()
//中介liveData,可用作桥接多个数据源，同一合并发送
val liveOne = MutableLiveData<Int>()
val liveTwo = MutableLiveData<String>()
val merge = MediatorLiveData<Any>()
merge.addSource(liveOne) {
    merge.postValue(it)
    //根据需要在适当的时候，移除数据源
    if (it == 4) merge.removeSource(liveOne)
}
merge.addSource(liveTwo) {
    merge.postValue(it)
}
merge.observe(viewLifecycleOwner) {
    Log.e(TAG, "MediatorLiveData 观察👀数据:$it")
}
```

⚠️LiveData有些特性或理解为设计的Bug：

-
如果在livedata调用observe之前就有数据产生了，那么它第一次observe会获得最新的那个数据。（如此在一些场景下，就可能有Bug。例如示例代码中，从DataBinding的Tab切换到Kotlin的Tab，Fragment对象实例是每次都新建的。会得到最新的那个数据，某些业务下，并不需要这样。）

##### 四、ViewModel

在Android开发中，jetpack的几个核心组件都是互相配合使用的。`Lifecycle+LiveData+ViewModel`三者最为常见。

1. ViewModel/AndroidViewModel(application)

```kotlin
//在fragment中获取它所依附的activity所持有的viewModel，得到的是activity的vm对象。
//该方式下，如果同一activity下有多个fragment，那么它们都如此获取vm，就可以实现fragment之间通过vm来数据共享。
private val vm: JetpackViewModel by activityViewModels()

//这是fragment 关联一个自身持有的vm的对象，多个fragment用此方式得到的vm，是不同的对象，所以数据不共享。
private val vm: JetpackViewModel by ViewModels()
```

2. viewModel作为UI与数据的桥梁层，可用做业务逻辑处理。旧版本使用`viewmodelprovider`
   获取vm的对象；方式已经废弃，一般使用如上的委托懒加载方式。其中可自定义viewModelFactory，根据业务架构，决定配置repostory管理层等。

Worker
    任务的执行者，是一个抽象类，需要继承它实现要执行的任务。

WorkRequest
指定让哪个 Worker 执行任务，指定执行的环境，执行的顺序等。
    要使用它的子类 OneTimeWorkRequest 或 PeriodicWorkRequest。

WorkManager
    管理任务请求和任务队列，发起的 WorkRequest 会进入它的任务队列。

WorkStatus
    包含有任务的状态和任务的信息，以 LiveData 的形式提供给观察者。

WorkManager适用于那些即使应用程序退出，系统也能够保证这个任务正常运行的场景，比如将应用程序数据上传到服务器。
它不适用于应用进程内的后台工作，如果应用进程消失，就可以安全地终止，对于这种情况，推荐你使用线程池