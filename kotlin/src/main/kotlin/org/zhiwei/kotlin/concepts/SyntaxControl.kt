package org.zhiwei.kotlin.concepts

/**
 * kotlin语法基础之 流程控制
 */
class SyntaxControl {

    //region 1. 条件与循环。if作为条件判断，可以结合if...else...,等，替代Java中的三目运算符？：
    var number = 99
    val result = if (number > 100) "大于100的" else "小于100的"

    //when表达式，可以结果赋值
    val sum = when {
        number > 99 -> "大99"
        number < 10 -> "小10"
        else -> "else结果"
    }

    //when表达式一般必须有else代码块兜底，但是如果判断的是枚举类，enum或者密封类，等确切的对象，则可以不用else
    private enum class Bit { ZERO, ONE }

    private val aBit: Bit = Bit.ZERO

    //此时的when能推导出所有的可能，则不需要else
    val ss = when (aBit) {
        Bit.ZERO -> "zero"
        Bit.ONE -> "one"
    }

    //for循环有多重，fori，forr，forEach 等，可参见 basic包下的QuickSyntax中有示例
    val aaStr = arrayOf("ddd")
    private fun testFor() {
        //可以使用withIndex遍历，
        for ((str, index) in aaStr.withIndex()) {
            println("字符串$str index $index")
        }
        for (s in aaStr.iterator()) {

        }
        for (index in aaStr.indices) {

        }
        //也有do while和while循环
        while (number > 0) {

        }
        do {

        } while (number < 0)
    }
    //endregion

    //region 2. 返回与跳转 return break continue
    private fun testReturnJump() {
        var ret: Any? = null
        ret ?: return //如此写法，判断ret是否null，若是null，就return了，不执行下面代码

        //break continue用于循环中的跳转；break是中断循环，而continue则是跳过此次循环，进行下一次；
        loop1@ for (i in 0..100) {
            //如此，则循环只会执行0..8，后面就不执行了
            if (i == 8) break@loop1
            println("如此循环会执行$i")
        }
        //类似于Java和其他语言，如果存在循环嵌套的时候，可以给不同的循环做个标记，label@ 格式，
        loop2@ for (i in 0..100) {
            //如此循环会执行0..100,只是i==4的时候，不会println输出
            if (i == 4) continue@loop2
            println("如此循环会执行$i")
        }
        //一下两种方式return，范围不同，
        listOf(1, 2, 3, 45, 64).forEach {
            if (it == 3) return //如此，则return到函数testReturnJump()的调用处了
            println(it)
        }
        listOf(1, 2, 3, 45, 64).forEach {
            if (it == 3) return@forEach //此时作用类似于continue在for循环的作用，forEach没有break,continue 这里默认的标签，也可以显式的自己添加label@标记
            println(it)
        }
        //使用匿名函数作为forEach的参数，如此return就是返回出匿名函数了，也即是变相的返回forEach
        listOf(1, 2, 3, 45, 64).forEach(fun(value: Int) {
            if (value == 3) return //如此同上，也是类似于continue
            println(value)
        })

        //forEach中return就是return到外层的函数，要实现类似for循环中break的效果，可以给forEach包裹一个函数，return就类似break的效果了。
        run {
            listOf(1, 2, 3, 45, 64).forEach {
                if (it == 3) return
                println(it)
            }
        }

        //有返回值的时候，label@ 也可以
        labelFun@ fun ret(): Int {
            listOf(1, 2, 3, 45, 64).forEach {
                if (it == 3) return@labelFun 1 //此时return到外层函数，带有返回值
                println(it)
            }
            return 2
        }

    }

    //endregion

    //3. 异常 throwable；类似于java的异常体系，throw抛出异常，try..catch捕获异常；也是表达式
    val tryResult = try {
        "".toInt()
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        999
    }

    //也可以简便的runCatching
    private fun testEx() {
        runCatching {
            "d3".toInt()
        }.onFailure { it.printStackTrace() }
            .onSuccess { println("解析成功$it") }
            .getOrNull()
    }

    //一个注定抛异常的函数，其返回类型则为Nothing
    private fun fail(): Nothing {
        throw IllegalArgumentException("参数异常")
        //如上可简化为error("参数异常")
        error("") //它抛出的异常类型是IllegalStateException
    }

}