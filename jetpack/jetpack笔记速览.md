#### Jetpack笔记速览

##### 一、前言

> [Jetpack](https://developer.android.google.cn/jetpack?hl=zh-cn)
> ,结合官方的github学习示例最好[github](https://github.com/orgs/android/repositories?type=all)
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

##### 五、Navigation

> Navigation是用于UI页面导航的组件库，支持deeplink；支持Activity/Fragment的跳转，可定义route路由。

1. 配合BottomNavigation的常规用法

    - 在xml里声明`fragment`或`FragmentContainerView`，注意它需要`defaultNavHost`、`namme`、`navGraph`
      三个关键要素

      ```xml
       <!--todo 使用navigation的关键，是写这个，也可以用fragment标签；其中name，defaultNavHost，navGraph三个必须同时有-->
          <androidx.fragment.app.FragmentContainerView
              android:id="@+id/fcv_jetpack"
              android:name="androidx.navigation.fragment.NavHostFragment"
              android:layout_width="match_parent"
              android:layout_height="0dp"
              app:defaultNavHost="true"
              app:layout_constraintBottom_toTopOf="@id/bnv_jetpack"
              app:layout_constraintTop_toTopOf="parent"
              app:navGraph="@navigation/graph_main_jetpack" />
      <!-- bottomNavigationView只需要设置menu即可-->
          <com.google.android.material.bottomnavigation.BottomNavigationView
              android:id="@+id/bnv_jetpack"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              app:layout_constraintBottom_toBottomOf="parent"
              app:menu="@menu/bnv_menu_jetpack" />
      ```

        - 要点是menu里面的item的id要与`navigation`
          下的graph文件的fragment节点定义id一致。如此方能切换tab保持fragment的联动切换。
        - graph文件定义中`startDestination`和`name`，或`route`为要点，也可以有`arguments`参数配置要求。

    - 如果是`fragment`标签的话，无需特别设置bottomNavigation对象设置setupWithNavController来关联即可。

      ```kotlin
       val navController = fcv.findNavController()
      //        val navController = findNavController(R.id.fcv_jetpack)//也可以这么写
              bnv.setupWithNavController(navController)
      ```

      需要注意的是，如果是`FragmentContainerView`标签的话，上面两行代码就要在onCreate之后最好是onStart内执行，因为Bug。

2. Navigation的使用，就是在activity或者fragment中通过findNavController来`navigate/navigateUP`
   等函数来跳转⏭️返回🔙页面，并可选择传参。

3. 如果使用了safe args插件，则可以简便的获取fragment的入参；

   ```kotlin
   //项目build.gradle中添加
   classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
   //模块的gradle中
   //使用navigation的safe args，在项目根build.gradle添加了classpath
   id("androidx.navigation.safeargs.kotlin")
   //使用的fragment处
   private val args by navArgs<WorkFragmentArgs>()
   ```

4. **注意⚠️：**BottomNavigationView结合Navigation的时候，切换tab是**重新创建**
   fragment的对象。而fragment通过navigation跳转到其他fragment页面再返回的话，fragment不会onCreate，但是
   **View会重建**。

##### 六、WorkManager

> WorkManager适用于那些即使应用程序退出，系统（原生Android可以，国内rom未必）也能够保证这个任务正常运行的场景，比如将应用程序数据上传到服务器。
> 它不适用于应用进程内的后台工作，如果应用进程消失，就可以安全地终止，对于这种情况，推荐你使用线程池。

1. 使用要点：三要素`worker`、`request`、`manager`；
    - 继承worker或CoroutineWorker，在doWork中做一些后台任务。可接收入参，可返回结果参数；
    - 构建WorkRequest，有两种：一次性的OneTime，周期性的PeriodicWorkRequest（最小间隔15分钟），可添加Constraints约束条件。
    - Workmanager来添加request调用enqueue。可用于管理reqeust和监听结果。使用liveData或flow都行。
2. WorkStatus包含请求的队列处理状态。
3. 可设置串行、并行、合流等多任务执行方式。具体可参照demo中的代码和注释。
4. WorkManager.initialize默认自动初始化，也可合适的时机自定义初始化配置。

##### 七、Room

>
room是jetpack中关于数据库操作的组件，[room](https://developer.android.google.cn/jetpack/androidx/releases/room?hl=zh-cn)

依赖库添加`ksp("androidx.room:room-compiler:2.7.7")`
和`implementation("android.room:room-ktx:2.7.7")`

1. 使用三要素：`Entity`，`Dao`，`Database`
   - 创建数据类，添加`@Entity`注解，内可声明主键，外键，表格字段名，忽略字段等。

     ⚠️使用普通class最佳。若是`data class`就需要设置默认值，因为创建对象要求有默认无参构造函数。

     主键自增，注意默认值从0开始，否则会自增冲突而报错。

   - 创建接口Dao，使用`@Dao`注解，内声明对数据表的增删改查操作，分别是`@Insert/@Delete/@update/@Query`
     ，其中查询可使用`sqlite语句`；注意返回值，如果是suspend修饰，返回结果就是普通类型的。不带suspend的话，可以返回liveData或flow类型。

   - 声明抽象类继承`RoomDatabase`，并添加注解`@Database`，内声明抽象函数获取对表操作的各个`Dao`
     ；另可声明一个创建`database`的单例对象的函数。

2. 注意：`room`
   支持普通数据类型，LiveData和flow的，可以挂起函数。数据库的操作，避开UI线程，使用协程时候，协操作也要指定避开main协程，最好是分配到IO协程。读取的话，可使用flow形式。

##### 八、Paging

> Paging3 库有几个核心类，主要用于分页加载多条数据，能够配置预加载，分页策略等。

1. 核心要素：**PagingSource**、PagingConfig、**Pager**、**PagingData**、**PagingDataAdapter**；
   数据流向![Paging3](https://developer.android.google.cn/static/codelabs/android-paging-basics/img/566d0f6506f39480_1920.jpeg?hl=zh-cn)
2. 使用步骤：

   - 添加必要依赖,`paging`相关的，如果数据源结合`room`则也需要`room-paging`的库依赖；
   - 创建数据model的类；而后创建`PagingSource`的实现类，，决定数据加载，刷新的方式。

   ```kotlin
   class TeacherPagingSource : PagingSource<Int, Teacher>() {
   
       override fun getRefreshKey(state: PagingState<Int, Teacher>): Int? {
   ...
           return ensureValidKey(teacher.id - (state.config.pageSize / 2))
       }
   
       @RequiresApi(Build.VERSION_CODES.O)
       override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Teacher> {
           ...
           )
       }
   ....
   }
   ```

   -
   得到的数据PagingSource需要配合PagingAdapter来适配给RecyclerView；PagingAdapter的实现，注意参数DiffUtil.itemCallback的设置，里面有`areItemsTheSame`
   和`areContentsTheSame`判断方式。
   - 数据加载可以放在viewModel中，使用flow形式或者LiveData都可以。创建Pager的时候，可以设置PagingConfig，配置页面大小，预加载触发数等。
   - 在UI中可以在协程中，监测数据加载，`adapter.submitData`
     来配置pagingSource的数据给PagingAdapter。同时能够监听Paging的加载、预加载、，刷新的状态。

   ```kotlin
           lifecycleScope.launch {
               repeatOnLifecycle(Lifecycle.State.STARTED) {
                   //加载状态的loading配置
                   teacherAdapter.loadStateFlow.collect {
   //                        it.source.prepend is LoadState.Loading //有三种状态，refresh，append，prepend
                       pbPaging.isVisible = it.source.append is LoadState.Loading
                   }
               }
           }
   ```

   



