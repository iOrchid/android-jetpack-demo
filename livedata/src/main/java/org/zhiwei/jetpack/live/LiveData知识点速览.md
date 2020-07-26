#### LiveData知识点速览

###### 一、概念

> LiveData 是可被观察的数据持有类。具有生命周期（Activity/Fragment/Service）感知的（**确保active状态下接受data更新**）。

1. 背景 [原文](https://medium.com/mobile-app-development-publication/understanding-live-data-made-simple-a820fcd7b4d0)

   ![img](https://upload-images.jianshu.io/upload_images/7273107-7d77106ca025f2ec.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

   Android开发中 MVX的开发架构设计，生命周期的感知对于Controller/Presenter/ViewModel不是天然可知。数据资源与泄露的问题。

   ![MVX的生命周期传递](https://upload-images.jianshu.io/upload_images/7273107-49840f8342a90edb.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

2. 2018年 google的AAC（Android Architecture Components）。一套组合的Jetpack组件库，使得ViewModel具有生命周期感知能力。同时也有了数据的感知能力（LiveData）

   ![img](https://upload-images.jianshu.io/upload_images/7273107-6b0970d3f0051167.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

- 理解LiveData

  ![img](https://upload-images.jianshu.io/upload_images/7273107-eb90093d985c2041.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

  不同于`rxjava`的观察模式，这里仅通知处于`active`状态的观察者。

  **一旦观察者回复`Resume`状态，就会收到最新的数据**（有利有弊，特殊场景）

###### 二、使用LiveData

> LiveData是抽象类

- MutableLiveData

```kotlin
//声明一个liveData
val liveA = mutableLiveData<String>()
//在需要的时候赋值
liveA.value = "some value of liveA"

//////////////////////////////////////////////////////
//在UI中，观察，在active状态下可以感知变化
val liveAObserver = Observer<String>{
    value?.let{
        //do something
    }
}
liveA.observe(vivewLifeCycleOwner,liveAObserver)
```

- Transformations.map

```kotlin
//数据的来源多样，赋值于UI需要转换
liveA.map{
    //转换规则
}
```

![img](https://upload-images.jianshu.io/upload_images/7273107-1e4fe7fa99d6ad7c.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

> ==liveData的数据，不会通知inActive的观察者刷新数据，但是当observer恢复Resume的active后，也会得到最新的data==

![img](https://upload-images.jianshu.io/upload_images/7273107-de9eb65e74d7ad99.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

- MediatorLiveData

  中介者，媒介，将多个liveData的数据，合并处理成一个LiveData

  ![img](https://upload-images.jianshu.io/upload_images/7273107-5b08a18eb66f7ae9.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

  mediator的liveData可以监听A，B两个数据源的变化，通过addSource后，并响应A/B的变化，转化为mediator的变化。

  - 如果inactive下，A，B都变化，则resume后，也只接受最新的变化

- SwitchMap

> 用于数据源的转化，多数据源的切换和控制
>
> ![img](https://upload-images.jianshu.io/upload_images/7273107-66ea5872084b5204.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)
>
> 配合mediator的liveData使用，根据条件，选择数据源

```kotlin
btn_change1_live.setOnClickListener {
            liveOne.value = "one:${System.currentTimeMillis().toString().takeLast(6)}"
        }
        btn_change2_live.setOnClickListener {
            liveTwo.value = "two:${System.currentTimeMillis().toString().takeLast(6)}"
        }

        //中介者
        mediatorLive.addSource(liveOne) {
            Log.d("LiveActivity", "LiveActivity中 LiveOne ---> $it")
            mediatorLive.value = "one >>" to it
        }
        mediatorLive.addSource(liveTwo) {
            Log.d("LiveActivity", "LiveActivity中 LiveTwo ---> $it")
            mediatorLive.value = "two >>>>>" to it
        }
//switch map 结合mediator，通过条件，控制选择数据源,这里模拟的是，it的数字奇偶，控制最终输出
            val swLive = mediatorLive.switchMap {
                if (it.second.takeLast(1).toInt() % 2 == 0) liveOne else liveTwo
            }
            //UI可以看出，不论是one，还是 two，改变的话，只有满足条件，才会生效。
            swLive.observe(viewLifecycleOwner, Observer {
                tv_switch_live_apple.text = it
                Log.w("AppleFragment", "AppleFragment中 switchMap ---> $it")
            })
```

###### 三、使用问题 [问题](https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150)

1. 使用LiveData作为**事件Event**不合适

   > 场景：通过liveData的Event跳转详情页；
   >
   > 手动点击 触发 observe中true则跳转；
   >
   > 返回首页，则liveData重新active，则再次跳转。（不应尝试多次变更，不建议愚蠢的手动reset）

   原因在于，及时inactive时，livedata依旧持有set的值（不论有无observer），并在active时候，给observer。

   方案: 

   只保留一个observer接收通知消息，如果添加多个，选择一个。

   ```kotlin
   //Single   LiveEvent
   open class Event<out T> (private val content:T){
       var hasHandled = false
       	private set
       //已经处理就是null，没有的话，再要
       fun getContentIfnotHandled():T?{
           return if(hasHandled)null else {
               hasHandled = true
               content
           }
       }
       //不论是否已经处理，都要结果
       fun peekContent():T = content
   }
   
   //使用 
   val liveD = mutableLiveData<Event<String>>()
   //
   liveD.observe(this,Observer{
       it.getContentIfnotHandled()?.let{
           //do something
       }
   })
   ```

   ```kotlin
   class SingleLiveEvent<T> : MutableLiveData<T>() {
   
       private val mPending = AtomicBoolean(false)
   
       @MainThread
       override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
   
           if (hasActiveObservers()) {
               Log.w(
                   "SingleLiveEvent",
                   "Multiple observers registered but only one will be notified of changes."
               )
           }
   
           // Observe the internal MutableLiveData
           super.observe(owner, Observer { t ->
               if (mPending.compareAndSet(true, false)) {
                   observer.onChanged(t)
               }
           })
       }
   
       @MainThread
       override fun setValue(t: T?) {
           mPending.set(true)
           super.setValue(t)
       }
   
       /**
        * Used for cases where T is Void, to make calls cleaner.
        */
       @MainThread
       fun call() {
           value = null
       }
   }
   ```

   

###### 附

- LiveDataNetWork

  ```java
  public class NetworkLiveData extends LiveData<NetworkInfo> {
  
      private final Context mContext;
      static NetworkLiveData mNetworkLiveData;
      private NetworkReceiver mNetworkReceiver;
      private final IntentFilter mIntentFilter;
  
      private static final String TAG = "NetworkLiveData";
  
      public NetworkLiveData(Context context) {
          mContext = context.getApplicationContext();
          mNetworkReceiver = new NetworkReceiver();
          mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
      }
  
      public static NetworkLiveData getInstance(Context context) {
          if (mNetworkLiveData == null) {
              mNetworkLiveData = new NetworkLiveData(context);
          }
          return mNetworkLiveData;
      }
  
      @Override
      protected void onActive() {
          super.onActive();
          Log.d(TAG, "onActive:");
          mContext.registerReceiver(mNetworkReceiver, mIntentFilter);
      }
  
      @Override
      protected void onInactive() {
          super.onInactive();
          Log.d(TAG, "onInactive: ");
          mContext.unregisterReceiver(mNetworkReceiver);
      }
  
      private static class NetworkReceiver extends BroadcastReceiver {
  
          @Override
          public void onReceive(Context context, Intent intent) {
              ConnectivityManager manager = (ConnectivityManager) context
                      .getSystemService(Context.CONNECTIVITY_SERVICE);
              NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
              getInstance(context).setValue(activeNetwork);
  
          }
      }
  }
  
  //使用处
  NetworkLiveData.getInstance(this).observe(this, new Observer<NetworkInfo>() {
      @Override
      public void onChanged(@Nullable NetworkInfo networkInfo) {
          Log.d(TAG, "onChanged: networkInfo=" +networkInfo);
      }
  });
  ```

- 原理

  - 观察者模式
  - LifeCycle



