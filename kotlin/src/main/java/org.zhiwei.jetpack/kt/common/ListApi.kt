package org.zhiwei.jetpack.kt.common

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年10月23日 10:18
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 演示List的Api示例,todo 近日整理基础语法颇有感触，平时编码过程中，用到某个类的时候，还是养成习惯，看一下它的所有api函数比较好，丰富自身api储备，有利于开发出更好的程序及逻辑
 */
object ListApi {

    private val listOne =
        arrayListOf(1, 2, 3, 4, 452, 55, 1, 2, 3, 4, 9, 235, 253, 56, 9, 90, 98, 76)
    private val listTwo = arrayListOf(6, 8, 10, 12)
    private val listStr = arrayListOf("A", "B", "C", "D", "E", "F")


    //单个api分开测试，注释掉其他
    fun testList() {
        //1、常规的普通操作api
//        listOne[1]
//        listOne.add(2222222)
//        listOne.add(0,10086)
//        listOne.addAll(listTwo)
//        listOne.addAll(10, listTwo)
//        //用于判断list中所有元素item是否满足代码块内的boolean条件，只有所有item都满足，才会返回true，否则false
//        val all = listOne.all { it > 50 }
//        println(all)
//
//        //todo 以下为kotlin的standard标准库的扩展函数，在MapApi中有过演示，这里简单描述一下
//        listOne.also {
//            //also代码块，执行相关操作，返回调用着自身引用
//            it.size
//        }
//        //let代码块，返回块内最后一行代码的结果
//        val let = listOne.let {
//            it.size
//            ""
//        }
//        //takeif 判断list是否满足某个条件，如果满足，返回自身引用，不满足，就是null
//        listOne.takeIf {
//            it.get(2)==100
//        }
//        //类似takeIf，这里就是对象如果不满足某个条件，就返回它，满足的话，就null
//        listOne.takeUnless {
//            it.size>100
//        }
//        //内部是this引用
//        listOne.apply {
//            //代码块内默认引用this为该对象，可以执行任意代码，apply的返回值还是对象自身
//            size
//            get(2)
//            ""
//
//        }
//        //run 运行代码块，返回运行结果
//        val run = listOne.run {
//            get(2) > 100
//            "result "
//        }
//        //类似run，但是这里有catch功能，可以兜底异常,块内有异常不会崩溃程序，然后返回的结果也是一个Result的封装对象
//        val runCatching = listOne.runCatching {
//            size
//            get(2) > 100
//        }
//
//        //如果调用方对象有一个以上元素，就返回true，下面{}形式的，就是如果有满足内部条件的，就返回true
//        listOne.any()
////        listOne.any {  }
//        //转为迭代器、翻转顺序的，以及转为序列对象
//        listOne.asIterable()
//        listOne.asReversed()
//        listOne.asSequence()
//        //将list的元素遍历，按照指定的规则，生成一个map
//        val associate = listOne.associate { item ->
//            item to "Name:$item"
//        }
//        //同上，写法更简便，代码块内就是Key对应Value
//        val associateWith = listOne.associateWith {
//            "item$it"
//        }
//        //也是将list转为map，只不过，这里item就成了value，key则是根据规则自定
//        val associateBy = listOne.associateBy {
//            "$it 作为Key"
//        }
        //将listTwo的item，按照制定规则，匹配tempMap的类型，添加到tempMap中去，并返回最终的tempMap
//        val tempMutableMap = mutableMapOf(  "str 999" to 999)
//        val associateTo = listOne.associateTo(tempMutableMap, {
//            "Str $it" to it
//        })
        //替换上面的，如果运行，就单独的使用一个，注释掉其他，不然会影响数据
//        val associateByTo = listTwo.associateByTo(tempMutableMap, { i -> "str的$i" })
//        println(associateByTo)
//        listOne.associateWithTo()//类似，不做演示
//        listOne.average()//int 的list时候，可以求所有item的平均值

//        println(listOne.sorted())
//        //二分查找法，查找指定元素，根据范围，或者整个list,这里需要注意的前提是，list要排序好才行，不然搜索结果基本都是错的。
//        val binarySearch = listOne.sorted().binarySearch(9,0,10)
//        println(binarySearch)
//        listOne.binarySearchBy()//类似

        //将listOne按照指定大小切分成小的list，剩余的不足size，就是实际大小。也可以加个参数，就是将切分后的小list转为指定的对象。
        println(listOne)
//        val chunked = listOne.chunked(5)
//        println(chunked)
//
////        listOne.clear()
////        listOne.clone()
//        listOne.component1()//获取第一个元素，同理有component2，3，4，5
//        //判断是否包含
//        listOne.contains(3)
//        listOne.containsAll(listTwo)
//        //统计元素个数，默认是size，也可以传入匹配规则的boolean条件
//        listOne.count()//就是list的size
//        listOne.count {  }

        //去重复,后面的元素如果前面已经有了，就舍弃掉
//        println(listOne.distinct())
//        listOne.distinctBy { }//按照指定的方式，去重复，或者可以转化元素

        //将第9个下标的元素开始，丢弃之前的，生成新的list
//        println(listOne.drop(9))
        //与上面相反，也就是丢弃从第9个小标开始之后的，不丢弃第9个
//        println(listOne.dropLast(9))
        //从后面算，丢弃满足条件的item，必须是从最后一个开的都满足，才会被丢弃。
//        println(listOne.dropLastWhile {
//            it > 70
//        })
        //类似于上面的，这个是从前开始，满足条件的，丢弃掉
//        println(listOne.dropWhile {
//            it < 100
//        })
//        listOne.elementAt(3)
//        println(listOne.elementAtOrElse(99) { i -> 5 + i })//返回对应下标的元素值，如果越界，就返回指定规则的结果
        println(listOne.elementAtOrNull(99))//越界就返回null
//        listOne.fill()
//        listOne.filter {  }
//        listOne.filterIndexedTo()
//        listOne.filterIsInstance()
//        listOne.filterIsInstanceTo()
//        listOne.filterNot {  }
//        listOne.filterNotNull()
//        listOne.filterNotNullTo()
//        listOne.filterNotTo()
//        listOne.find {  }
//        listOne.findLast {  }
//        listOne.first()
//        listOne.firstOrNull {  }
//        listOne.flatMap {  }
//        listOne.flatMapTo()
//        listOne.fold()
//        listOne.foldIndexed()
//        listOne.foldRight()
//        listOne.foldRightIndexed()
//        listOne.forEach {  }
//        listOne.forEachIndexed { index, i ->  }
//        listOne.get()
//        listOne.getOrElse()
//        listOne.getOrNull()
//        listOne.groupBy {  }
//        listOne.groupByTo()
//        listOne.groupingBy {  }
//        listOne.ifEmpty {  }
//        listOne.indexOf()
//        listOne.indexOfFirst {  }
//        listOne.indexOfLast {  }
//        listOne.indices
//        listOne.intersect()
//        listOne.isEmpty()
//        listOne.isNotEmpty()
//        listOne.isNullOrEmpty()
//        listOne.iterator()
//        listOne.joinTo()
//        listOne.joinToString {  }
//        listOne.last {  }
//        listOne.lastIndex
//        listOne.lastIndexOf()
//        listOne.lastOrNull {  }
//        listOne.listIterator()
//        listOne.map {  }
//        listOne.mapIndexed { index, i ->  }
//        listOne.mapIndexedNotNull()
//        listOne.mapIndexedNotNullTo()
//        listOne.mapIndexedTo()
//        listOne.mapNotNull {  }
//        listOne.mapNotNullTo()
//        listOne.mapTo()
//        listOne.max()
//        listOne.maxBy {  }
//        listOne.maxWith()
//        listOne.min()
//        listOne.minBy {  }
//        listOne.minus()
//        listOne.minusAssign()
//        listOne.minusElement()
//        listOne.minWith()
//        listOne.none {  }
//        listOne.onEach {  }
//        listOne.orEmpty()
//        listOne.parallelStream()
//        listOne.partition {  }
//        listOne.plus()
//        listOne.plusAssign()
//        listOne.plusElement()
//        listOne.random()
//        listOne.reduce { acc, i ->        }
//        listOne.reduceIndexed()
//        listOne.reduceRight()
//        listOne.reduceRightIndexed { index, i, acc ->  }
//        listOne.remove()
//        listOne.removeAll {  }
//        listOne.removeAt()
//        listOne.removeIf {  }
//        listOne.replaceAll()
//        listOne.reverse()
//        listOne.reversed()
//        listOne.set()
//        listOne.shuffle()
//        listOne.shuffled()
//        listOne.single()
//        listOne.singleOrNull {  }
//        listOne.slice()
//        listOne.sortBy {  }
//        listOne.sortedByDescending {  }
//        listOne.sortWith()
//        listOne.spliterator()
//        listOne.stream()
//        listOne.subList()
//        listOne.subtract()
//        listOne.sum()
//        listOne.sumByDouble {  }
//        listOne.take()
//        listOne.takeLast()
//        listOne.takeLastWhile {  }
//        listOne.takeWhile {  }
//        listOne.toArray()
//        listOne.toSortedSet()
//        listOne.trimToSize()
//        listOne.union()
//        listOne.windowed()
//        listOne.withIndex()
//        listOne.zip()
//        listOne.zipWithNext { a, b ->  }


    }



}