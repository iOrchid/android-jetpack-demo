### Kotlin语法拾遗

#### 一、基础语法

1. 操作符

   ```kotlin
   //let 用于执行if not null
   val value:String?
   value?.let{
     //非空的操作
   }
   
   //with 用于调用一个对象的多个函数
   with(instance){
     mathodA()
     mathodB()
     ...
   }
   //apply 用于配置对象属性
   Rectangle().apply{
     length = 33
     breadth = 22
     color = Color.Red
   }
   //also 用于交换变量的值
   a = b.also{b = a}
   ```

2. 类与对象

   > - 检测`lateinit var`是否初始化 `foo::bar.isInitialized`检测
   > - 多接口函数冲突，`super<A>.foo();super<B>.foo()`
   > - 可见性修饰符
       >
   - `private`文件内可见；
   >   - `internal`模块内可见；
   >   - `protected`不可用于顶层声明；用于类成员修饰的时候，文件内可见和子类可见；
   >   - `public`默认的修饰符，公开可见；

    - 数据类`data class`内部实现`equals/hashcode/tostring`以及`copy`函数，可解构；

    - 密封类和密封接口

        - 自身抽象不可实例化，构造函数不可公开`public`
        - 子类和实现类只能在同一个模块内，同一个`package`包内；（在其他模块命名相同的包名是不行的）

    - **范型和型变**

        - `in,out,where`

    - 嵌套类和内部类，内部类使用`inner`标记

    - **内联类**

      ```kotlin
      @JvmInline//jvm 内联标记；
      value class Password(private val s:String)
      ```

        1. 内联类，类似于数据类data class，但有且仅有唯一的一个属性在主构造函数内；运行时会被解构为内部字段，节省性能开销；
        2. 可实现接口，不可继承其他类，自身`final`不可被继承；
        3. 可有次构造函数,但不能有额外的成员字段声明。
        4. 内联类`===`引用相等的判断无效；
        5. 看似内联类只包含一个属性值，且有点类似于定义类型别名`typealias`
           ，但实际上内联类是新的类型作用，而别名则等同原名的类型。

    - 数据对象

      `data object XXX{}`,类似于数据类`data class `但是不会自动生成`copy`和`componentN()`等

      常用于`sealed class/interface`的实现类`object`时候；

    - **委托**，替代接口实现和类继承的，另一种不错的方案；

    - 属性委托

      延迟属性，可观察属性，`map`映射

      ```kotlin
      by Delegates.observable(),vetoable(),notNull()
      by Lazy
      //可以用另一个属性委托
      val a:String  = "name"
      val b :String by this::a//可用在@depracated废弃一些字段，使用新字段的场景
      
      //如果使用map委托，则字段要和key一致
      ```

      可局部变量使用委托；

3. 函数

    1. 尾递归函数：某些使用循环的算法，尾递归可避免栈溢出风险；

    2. 条件限制：

        - 调用自身的语句在函数内最后一条；
        - 不可用于`try…catch…finally`
        - 不能用于`open`的函数

       ```kotlin
       //传统写法，计算余弦不动点
       val exp = 1E-10 //标记计算精度
       private fun findFixPoint():Double{
         var x = 1.0
         while(true){
           val y = Math.cos(x)
           if(Math.abs(x-y)<eps)return x
           x = Math.cos(x)
         }
       }
       //尾递归的写法
       tailrec fun findFixPoint(x:Double = 1.0):Double{
        return if(Math.abs(x-Math.cos(x))<eps) x else findFixPoint(x)
       }
       ```

    3. 内联函数

       ```kotlin
       inline fun foo(inlined:()->Unit,noline noinlined:()->Unit){}
       //
       inline fun f(crossinline body:()->Unit){
         val f = object:Runnable{
           override fun run()=body()
         }
       }
       //内联具体化参数类型,可使用对应属性一般
       inline fun <reified T> TreeNode.findParentOfType():T?{
         ...
       }
       inline fun <reified T> membersOf() = T::class.members
       ```

        1. 内联属性

#### 二、泛型/协变/型变

- 型变

    - Java中范型不是型变的

      > 通过**限制通配符**提升API灵活性；
      >
      > `List<String>`并不是`List<Object>`的子类。

        - `? extends E`通配符方式解决如上问题，上界限定符`extends`使得类型是**协变的**（协定范围可变）
        - `? super String`类似的，下界限定，称之为 **逆变性**（理解为子类逆向向上转变）

    - `kotlin`中**声明处型变**

      ```java
      interface Source<T>{
        T nextT();
      }
      //demo
      void demo(Source<String> strs){
        Source<Object> objs = strs;//java中编译不过
      }
      ```

      kotlin中声明处型变，指定的范型T在类中只是生产，而不是消费，使用`out`关键字

      ```kotlin
      //kotlin中声明处型变
      interface Source<out T>{
        fun nextT():T
      }
      //demo
      fun demo(strs:Source<String>){
        val objs:Source<Any> = strs//kotlin中这个可行，因为T是一个out的参数
      }
      ```

      > 如上则`out`成为**型变注解**，对应类处**只生产**T而不消费，类似`? extends E`
      ，同时可实现`classXX<Base>`可理解为`classXX<Derived>`的超类。

      相似的还有`kotlin`中另一个型变注解`in`关键词的修饰，可用于处理**`逆变`**场景

      ```kotlin
      interface Compare<in T>{
        operator fun compareTo(other:T):Int//这里是消费T，而非生产T
      }
      //demo
      fun demo(x:Comparable<Number>){
        x.compareTo(1.0)//1.0是Double类型，Double是Number的子类
        val y:Comparable<Double> = x //这里kotlin编译器是可行的
      }
      ```

      便捷理解就是`in`消费，`out`生产

    - kotlin的**类型投影**

      ```kotlin
      //对于一个数组定义类，接收范型，就不能定义T为单纯的out或者in，因为它既需要产生也需要消费
      class Array<T>(val size:Int){
        operator fun get(index:Int):T{
          //...
        }
        operator fun set(index:Int,value:T){
          //...
        }
      }
      //此时就会有以下问题
      fun copy(from:Array<Any>,to:Array<Any>){
        assert(from.size == to.size)
        for(i in from.indices){
          to[i] = from[i]
        }
      }
      //copy函数如果定义如上，则在使用时就无法避免以下场景问题
      val ints:Array<Int> = arrayOf(1,2,3)
      val any = Array<Any>(3){""}
      copy(ints,any)//ints是Array<Int>并不是期待的Array<Any>就会有int无法转string的报错
      ```

      如上，则需要对入参做限定方可安全的copy，做类型限定的数组，就称为**类型投影**
      ；类似于java中的`? extends E`和`? super String`等，kotlin中有`out`和`in`

      ```kotlin
      fun copy(from:Array<out Any>,to:Array<Any>){
        //这里就能确保限定from 的get到的类型
      }
      ```

    - 星投影

      对类型参数未知的时候，依旧想安全的使用范型，可用`kotlin`中的`*`星号代替如上的类型投影

      ```kotlin
      //示例
      interface Function<in T, out U>
      //此时的星投影如下
      Function<*,String> 就等同于 Function<in Nothing,String>
      Function<Int,*> 等价于 Function<Int,out Any?>
      Function<*,*> 等同于 Function<in Nothing,out Any?>
      //总之一句话，in 限定 消费，out限定产生；
      ```

    - 范型约束

      范型可用于类和函数；类似于java中，可有**上界**约束`T extends CharSquence`
      ，在kotiln中`T:CharSquence`限定范型必须是`CharSquence`的子类。

      ```kotlin
      fun <T:Comparable<T>> sort(list:List<T>){}//如此限定T是Comparable<T>的子类才可以
      sort(listOf(1,2,3))//可行，Int是Comparable<Int>的子类
      sort(listOf(HashMap<Int,String>()))//报错，HashMap<Int,String>不是Comparable<HashMap<Int,String>>的子类
      ```

        - 默认上界是`Any?`，`<T:Comparable<T>>`的形式只能限定一个上界，若需要多个上界限定，使用`where`
          子句

          ```kotlin
          fun <T> copyWhenGreater(list:List<T>,threshold:T):List<String> where T:CharSequence,T:Comparable<T>{
            //...
          }
          //如上，多个上界限定，需要where子句，且在声明后，而非< >之内。
          ```

    - 在与Java交互时候，可能用到非空`non-nullable`的范型定义`T & Any`

      ```java
      public interface Game<T>{
        public T save(T x);
        @NotNull
        public T load(@NonNull T x){}
      }
      ```

      ```kotlin
      //在kotlin中如果继承并override上面的Java的函数，就需要用到非空范型
      interface AccadeGame<TT>:Game<TT>{
        override fun save(x:TT):TT
        //这里的TT定义必须是非空范型 
        override fun load(x:TT & Any):TT & Any
      }
      ```

      在kotlin中则不需要，只有与java交互时候需要；

- 泛型

  在编译期间类型检查，运行时是擦除的

  **reified**修饰泛型，可代指具体类型

  ```kotlin
  inline fun <reified A ,reified B> Pair<*,*>.asPairOf():Pair<A,B>?{
    if(first !is A || second !is B) return null//如上定义的函数，reified修饰后，内部才可以用以判定类型
    return first as A to second as B
  }
  ```

#### 三、协程

- 结构化并发，使用`CoroutineScope`创建一个协程作用域。

  > 即，协程作用域内开启新的协程；
  >
  > 父协程会限制子协程的生命周期，子协程承接父协程的上下文；

- 协程会等待其作用域内所有协程都执行完毕后才会关闭自身。

- `SupervisorJob`可以使子协程异常而不会取消父协程；（子协程所有的都会被取消）
