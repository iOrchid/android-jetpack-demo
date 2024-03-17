package org.zhiwei.kotlin.collections

import androidx.collection.arraySetOf

/**
 * Kotlin语法基础之 集合
 */
class KtCollections {

    //0. kotlin中集合常见的有 list set map三种；其值都是型变的，即 声明基类，可添加子类;
    //分为可变和不可变两种，可变的是提供集合元素的读写增删操作。不可变的，初始化之后，不可增删；
    //如List，Map，Set等，不可变；可变的就是MutableList,MutableSet,MutableMap


    //注意var和val是表示变量是否可变，与集合的可变与否不是一个概念；
    var list: MutableList<Number> = mutableListOf()

    private fun testCollections() {
        //这个变化是var的原因，若是val，就不可更改list对象的引用地址；
        list = mutableListOf(1, 2, 3)
        list = mutableListOf(2.0, 3.3, 5.2)

        //这个叫做集合的可变
        list.add(7)
        list.removeAt(2)
        //如果list不是Mutable的，就不能有add和remove操作。
    }

    //1. list 是列表，顺序存放元素，底层实现方式有多种。可以存多个null值。不同于Array数组，没有预设大小
    private fun testList() {
        //1.1 初始化的方式
        val a = listOf("") //根据元素，推断类型
        val b = arrayListOf('c')
        val c = List(3) { "$it" }//表达式方式初始化
        val d = emptyList<String>()//空列表
    }

    //2. set 元素具有唯一性，可存唯一一个null ，两个set对比size相同且每个元素都相等才算set相等；
    private fun testSet() {
        //1.1 初始化的方式
        val a = setOf("") //根据元素，推断类型
        val b = arraySetOf('c')
        val c = buildSet<String> { add("agd");add("dsags") }//表达式方式初始化
        val d = emptySet<String>()//空列表
    }

    //3. map 也算集合，key-value的存储，不一定顺序一样，键值对一样就判断map相等
    private fun testMap() {
        //1.1 初始化的方式
        val a = mapOf("dg" to 3) //根据元素，推断类型
        val c = buildMap<String, Int> { put("agd", 3) }//表达式方式初始化
        val d: Map<String, Any> = emptyMap()//空map
    }

    //4. ArrayDeque 双端的队列？
    private fun testArrayDeque() {
        val que = ArrayDeque<String>()
        que.addFirst("sd")
        que.addLast("dagd")
        que.removeFirst()
        que.removeLast()
    }


    //5. 迭代器 ，集合提供了iterator迭代器，可以在遍历的同时，进行读写操作；
    private fun testIt() {
        val list = listOf("ADBAs", "dagdsa", "dsg")
        val it = list.listIterator()
        while (it.hasNext()) {
            it.next()
        }
        it.hasPrevious()
        val mlist = mutableListOf("adag", "dags")
        while (mlist.listIterator().hasPrevious()) {
            mlist.listIterator().add("可以增删操作")
            mlist.listIterator().remove()
        }
    }

    //6. rangeTo ,downTo ,step 在循环中的使用。Sequence序列，类似于iterator迭代器，作用于集合操作；
    //迭代器如果执行多种操作时候，元素赋值，map转换，迭代器是先将整个集合都依次先 赋值 整个集合，再 依次整个集合转化；
    //而Sequence序列是，先对单个元素执行多个操作，计算结束后，再实际指定到整个集合。所以会占用内存开销。
    private fun testSq() {
        val sq = sequenceOf("adg", "dag", "dagds")
        val sq2 = listOf("ada", "dags").asSequence()
        val sssqqq = generateSequence() {
            "string的序列元素"
        }
        //这样会产生无限个元素,需要在适当的时候，停止产生。
        generateSequence(1) {
            if (it > 9) null else it
        }
        sssqqq.take(10)
        sequence<String> {
            yield("a")
            yieldAll(listOf("", ""))
        }
    }


    //对比sequence和iterator的不同,可以在单元测试中看结果
    private fun testVs() {
        //将句子分割成单词的集合，然后过滤长度>3的单词，取出前边4个，并输出他们的长度
        //迭代器形式
        val words = "The quick brown fox jumps over the lazy dog".split(" ")

        val lengthsList = words.filter {
            println("过滤: $it")
            it.length > 3
        }.map {
            println("长: ${it.length}")
            it.length
        }.take(4)

        println("iterator的 -----   前四个长度大于3的单词的长度是 :$lengthsList")

        //序列形式
        val wordsSequence = words.asSequence()

        val lengthsSequence = wordsSequence.filter {
            println("过滤: $it")
            it.length > 3
        }.map {
            println("长: ${it.length}")
            it.length
        }.take(4)

        println("Sequence的 ----- 前四个长度大于3的单词的长度是 :${lengthsSequence.toList()}")
    }


    //7. 集合有的公共操作函数
    private fun testCollFun() {
        //7.1 map/mapXXX转换操作，对每个集合元素执行转换指定方式的操作
        val list = listOf("sdag")
        list.map { it.toInt() }//就是一种转换
        val list2 = listOf('d')
        //zip 合拢操作，两个集合合并成一个，以元素少的为准。
        val pp = list.zip(list2)
        //associate关联，就是将元素于其他管理，组合成集合的map
        val listass = list.associate { it to it.length }
        //flatten 展平，就是集合内如果元素还是某个集合，改操作是让所有元素都平等，得到一个元素纬度的集合
        val flatten = listOf(setOf(""), setOf("")).flatten()
        //类似的有flatMap操作符
        list.joinToString()//转化为string表示
        //过滤 filterXXX
        val filter = list.filter { it.length > 2 }
        //划分，就是根据规则，将list划分为满足条件和不满足条件的,返回的Pair，前部是满足的，后部是不满足的
        val partition = list.partition { it.length > 3 }
        val (matched, rest) = list.partition { it.length > 3 }
        //检验集合元素的
        list.any()
        list.all { it.length > 2 }
        list.none()
        //+ - 操作
        val plus = list.plus(list)
        val minus = list.minus(list)
        //分组,得到map
        val groupBy = list.groupBy { it.length }
        //取部分
        val slice = list.slice(0..2)
        val take = list.take(2)//takeLast
        val drop = list.drop(2)//dropLast
        //得到分块后的一堆list
        val chunked = list.chunked(2)//分块
        //滑动窗口，可以得到指定大小的子集合会是怎样的，不同于分块
        val windowed = list.windowed(2)
        val zipWithNext = list.zipWithNext()//指定2个元素的滑动窗口
        //取单个元素
        val get = list.get(9)
        list.first()
        list.last()
        list.find { it.length == 5 }
        list.elementAt(5)
        list.random()
        //是否包含
        list.contains("adas")

        //排序
        list.sorted()
        list.reversed()//反转
        list.shuffled()//随机抖乱
        //聚合,sum average等
        list.min()
        list.max()
        list.count()
        //滑动聚集,
        list.fold(1) { index, str ->
            //包含起始元素依次操作
            str.length
        }
        list.reduce { index, str ->
            //从第一个开始操作
            "asdf"
        }
        //读写，add remove相关

    }

    //8.list特有操作函数
    private fun testLt() {
        val list: List<Number> = listOf(1, 33.5, 535, 4)
        // 取值
        list.getOrElse(1, { 3 })
        list.getOrNull(3)
        //部分
        list.subList(1, 4)
        //查找
        list.indexOf(3)
        list.lastIndexOf(32)
        list.binarySearch {
            it.toInt()
        }
    }

    //9.set相关特有操作
    private fun testSt() {
        val set = setOf("")
        val set2 = setOf("")

        //并集
        set union set2
        //交集
        set intersect set2
        //差集
        set subtract set2

    }

    //10.map也有一些和list相似的特有函数，比如getOrNull

}