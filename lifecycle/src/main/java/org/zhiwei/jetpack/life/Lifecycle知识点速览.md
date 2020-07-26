#### Lifecycle知识点速览



######  一、基础使用

1. 依赖库

```groovy
//	implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'//已经废弃
	implementation 'androidx.lifecycle:lifecycle-livedata:2.3.0-alpha05'
	implementation 'androidx.lifecycle:lifecycle-viewmodel:2.3.0-alpha05'
	implementation 'androidx.lifecycle:lifecycle-runtime:2.3.0-alpha05'
	implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.3.0-alpha05'
	implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0-alpha05'
	kapt "androidx.lifecycle:lifecycle-common-java8:2.3.0-alpha05"
```

2. liveData、ViewModel的配合使用



###### 二、分析及应用

- 原理 Observable 观察者模式
- 继承ComponentActivity的子类，而不是Activity

```kotlin
//ComponentActivity 实现lifecycleOwner
mLifecycleRegistry
getState
addObserver
```

- 媒体播放，位置获取，添加生命周期感知,自定义observer实现

```kotlin
lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> {
                    }
                    Lifecycle.Event.ON_START -> {
                    }
                    Lifecycle.Event.ON_RESUME -> {
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                    }
                    Lifecycle.Event.ON_STOP -> {
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                    }
                }
            }
        })
```





#### ViewModel知识速览

##### 一、基础使用

1. 继承viewmodel/或androidViewModel

2. 在Activity/Fragment中使用

   ```kotlin
   ViewModelProviders.of(getActivity()).get()
   by viewmodel
   ```

   

3. 生命周期自动感知

   onClear回调函数

   **ViewModel绝对不能引用View、Lifecycle或任何可能包含对Activity上下文的引用的类**

4. ![img](https://img-blog.csdn.net/20180428114611418?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2h1YW5ncnVxaTg4OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

5. 用于fragment之间的数据共享

6. viewmodel+livedata+room 数据感知，绘制UI