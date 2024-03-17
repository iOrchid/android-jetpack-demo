package org.zhiwei.kotlin.concepts

import android.view.View.OnClickListener

/**
 * Kotlin语法基础之 范型
 */
class SyntaxGenerics {

    // 0. kotlin中的范型有 in out where关键字，还有*星号

    //范型为的是更为通用的api，难点在于型变理解和处理。Kotlin中有 声明处型变 和 星号投影 两个概念；
    //Java的范型是 不型变 的，即List<String>并不是List<Object>的子类。Java有编译后的类型擦除。实际存储的都是Object
    //todo copy List<String> 到 List<Object>是安全的，但是范型的声明处需要写 ? extend E 的方式；规定上界类型，便于读取，称之为类型 协变。
    //todo 反之，如果写入元素，规定下界，则称之为 逆变。

    //1. 声明处型变
    /*
     Java编译器不允许Source<Object> objs = strs;(strs是Source<String>)虽然这样是安全的；
     Kotlin中如果范型T 在范型类中 只作为产物，而不是参数被消费，可以使用out 关键字修饰
     */
    private interface Source<out T> { //此时参数T称为协变参数，或Source在T上是协变的。
        fun next(): T //整个Source内只有返回产出T类型，而没有消费，此时可用out
    }

    //2. 逆变 in 关键字修饰，参数类型在范型类内，被消费，对应Java的 ？ super XX
    private interface GenConsumer<in E> {
        fun eat(e: E)
    }

    private fun testGen(strs: Source<String>) {
        //这里要求Source范型有out，不然就编译器不认
        val anySource: Source<Any> = strs
    }

    private fun testGen2(ec: GenConsumer<Number>) {
        ec.eat(2.0)//ec接收的类型是Number，此处传入的是Double，是可以的，消费类型安全
        //如果GenConsumer没有in修饰，就会编译报错
        val yec: GenConsumer<Int> = ec//赋值新的对象，也是Number子类的
        yec.eat(3)//所以可以接收Int，因为是Number的子类。
    }

    //3. 类型投影；对范型的生产，消费处，做in out的类型限制。有时候不便限制的，使用*星号
    private abstract class TUpper
    private interface Foo<out T : TUpper> //具有上界的协变，Foo<*>在读取的时候，等价于左边形式；
    private interface Foo2<in T> //逆变参数，Foo<*>在写入的时候，等价Foo<in Nothing>就是不可安全写入；
    private interface Foo3<T> //Foo<*>在读写时候，综合上面两种。

    //3. 范型函数,普通，扩展都可以
    private fun <T> common(a: T): List<T> {
        return listOf()
    }

    private fun <T> T.school() {

    }

    //4. 范型约束 上界 T:TUpper表明只有TUpper的子类可以作为范型的参数，可以有多个约束上界，
    private fun <T> upper(t: T) where T : Number, T : OnClickListener {
        //这里是演示的，要求范型参数T 是Number的子类，同时是OnClickListener的实现类，
    }

    //todo 范型参数，仅作用于编译期，运行时都是擦除后的，Object或Any

}