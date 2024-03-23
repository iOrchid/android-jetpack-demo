#### Jetpack笔记速览

##### 一、前言

> [Jetpack](https://developer.android.google.cn/jetpack?hl=zh-cn)
> 是Android官方推出的一些列组件库，最早`databinding`
> 也属于官方推荐的工具写法，但是由于其影响编译性能，且`xml`
> 中编写出错的时候IDE不易准确检测和报错，逐步不再使用。且`compose`
> 更为高效的UI编写方式，所以该项目内`dataBinding`有独立module作为代码示例，并有详细注释说明，便于学习。
>
> 如果不是接手老项目需要，可以跳过，直接学习compose；如果是老项目有databinding，则可以学习了解即可。

Jetpack的其他组件则都会共用在同一个项目module内，同时也是使用MVVM的架构设计模式，来简单实现一个组合使用各个库的示例。

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

##### 三、LiveData

> 又一个观察者模式的实现，数据变化时候可以得到及时的通知。两个核心库`lifecycle-livedata-core`
> 和`lifecycle-livedata`

1. `core`库就三个文件：`Observer`接口，`LiveData`抽象类和`MutableLiveData`
   的类。LiveData内有观察者的注册/注销，及数据变化的管理和通知等逻辑。
2. `livedata`库有`MediatorLiveData`，`Transformations`和一些**协程**相关的实现类，扩展函数等。

```kotlin
```

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