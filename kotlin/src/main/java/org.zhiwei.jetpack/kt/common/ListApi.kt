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

//        listOne.fill(10080)//将list所有元素替换为指定值
//        //过滤boolean条件的元素，得到过滤后的list
//        val filter = listOne.filter { it > 99 }
//        //过滤listOne满足一定条件的item，添加到另一个list中去，得到新的list
//        val filterIndexedTo =
//            listOne.filterIndexedTo(listTwo, { index, i -> index > 10 && i > 100 })
//        val filterIsInstance =
//            listOne.filterIsInstance<Int>()//kotlin中list的元素可以是多个类型的，该api就是过滤同一类型的元素为新的list
//        //这里就是演示，将listOne中指定类型的元素，过滤出来，添加到listTwo中去。
//        val filterIsInstanceTo =
//            listOne.filterIsInstanceTo<MutableList<Int>, Int>(listTwo, Int::class.java)
//        val filterNot = listOne.filterNot { it > 100 }//过滤出不满足条件的元素组成新的list
////        listOne.filterNotTo()//类似上面，只不过将过滤后的元素添加的指定的list中去，得到新的list
//        val filterNotNull = listOne.filterNotNull()//剔除掉null元素，
//
////        listOne.filterNotNullTo()
//        //过滤是得到的数组list，查找得到的是单个元素，第一个匹配的
//        val find = listOne.find { it > 100 }
//        val findLast = listOne.findLast { it > 100 }//list中最后一个匹配的
//        val first = listOne.first()
//        val firstOrNull = listOne.firstOrNull { it > 10000 }//返回第一个匹配的元素，没有就是null
	    //将listOne中的每个元素item，转换为一个可遍历的对象，重新组合成新的list
//        val flatMap = listOne.flatMap { item -> arrayListOf(item,"dd") }
//        println(flatMap)
//        listOne.flatMapTo()

	    //这个理解有点麻烦，首先就是有个原始值 tt，然后根据指定规则（这里是"【$acc==item$item】"）生成一个新的结果，
	    //再用这个结果，继续按照规则生成下一个结果，一直循环list的item个数次.todo 这个特性好像可用于一些复杂算法，斐波那契，阶乘或者兔子问题之类的。
//        var counter = 0
//        val fold = listOne.fold("tt", { acc, item ->
//            counter++
//            "【$acc==item$item】"
//        })
//        println(fold)
//        println("counter$counter, listOne.size ${listOne.size}")
//        listOne.foldIndexed("tt",{index,acc,item->""})//类似上面，这里用到的index
//        listOne.foldRight("tt",{item,acc->""})//与fold类似，只不过这个是从list尾部开始向前
//        listOne.foldRightIndexed()//类似，多了index

	    //下面是循环，带index循环，获取元素，以及带有规则的get
//        listOne.forEach {  }
//        listOne.forEachIndexed { index, i ->  }
//        listOne.get()
//        listOne.getOrElse()
//        listOne.getOrNull()

	    //给list的元素，按照某种规则分组，得到一个map，key是指定的类型，value是当前组的list元素
//        val groupBy = listOne.groupBy {
//            when {
//                it > 200 -> {
//                    ">200"
//                }
//                it in 101..199 -> "100~200"
//                else -> {
//                    "<100"
//                }
//            }
//        }
//        println(groupBy)
//        listOne.groupByTo()//类似上面，只不过多了个指定map组合
	    //得到一个新的Grouping对象类型的数据
//        val groupingBy = listOne.groupingBy {
//            when {
//                it > 200 -> {
//                    ">200"
//                }
//                it in 101..199 -> "100~200"
//                else -> {
//                    "<100"
//                }
//            }
//        }
	    //如果list是没有元素的（size==0），就根据块内规则，变成默认的数据了，这里就是得到一个string，如果有元素，那么就返回list自身了。
//        val ifEmpty = listOf<Int>().ifEmpty {
//            "的的的"
//        }
//        println(ifEmpty)

	    //以下函数，顾名思义，不做演示
//        listOne.indexOf()
//        listOne.indexOfFirst {  }
//        listOne.indexOfLast {  }
//        listOne.indices//用于循环遍历，得到一个下标所以的range值
//
//        //两集合的交集，得到一个set，保持原有元素的顺序，不重复
//        val intersect = listOne.intersect(listTwo)

//        listOne.isEmpty()
//        listOne.isNotEmpty()
//        listOne.isNullOrEmpty()
//        listOne.iterator()//迭代器

	    //将list转为string，并插入一下字符
//        val joinTo =
//            listOne.joinTo(StringBuffer(), " ^^ ", "pre-\n", "\n-pos", 5, "***", { i -> "tans == $i" })
//
//        println(joinTo)
//        listOne.joinToString {  }

//        listOne.last {  }
//        listOne.lastIndex
//        listOne.lastIndexOf()
//        listOne.lastOrNull {  }

	    listOne.listIterator()//得到一个listIterator对象

	    //list的item转化为指定规则的结果，得到新的list
	    val map = listOne.map {
		    "¥it $it"
	    }
	    //如下类似
//        listOne.mapIndexed { index, i ->  }
//        listOne.mapIndexedNotNull()
//        listOne.mapIndexedNotNullTo()
//        listOne.mapIndexedTo()
//        listOne.mapNotNull {  }
//        listOne.mapNotNullTo()
//        listOne.mapTo()

	    //list中最大，最小值，或者可以自定对比规则
//        listOne.max()
//        listOne.maxBy {  }
//        listOne.maxWith()
//        listOne.min()
//        listOne.minBy {}
//        listOne.minWith()

	    //list的元素移除操作，移除第一个匹配到的，返回一个新的list
//        println(listOne.minus(2))
//        listOne.minusElement()//等同上

//        listOne.minusAssign(2)//移除第一个匹配到的，操作原list对象
//        println(listOne)

//        listOne.none {  }
//        listOne.onEach {  }
//        listOne.orEmpty()//如果null，返回emptyList

//        listOne.parallelStream()//得到steam流对象
	    //切分成一对list，根据指定的boolean规则
//        listOne.partition {
//            it>100
//        }

	    //类似于minus，这里是添加操作
//        listOne.plus()
//        listOne.plusAssign()
//        listOne.plusElement()

//        listOne.random()//随机一个元素出来
	    //有点类似于fold操作符，只不过这里初始值是list第一个item，从头至尾，下面的right，从尾至前
//        listOne.reduce { acc, i ->        }
//        listOne.reduceIndexed()
//        listOne.reduceRight()
//        listOne.reduceRightIndexed { index, i, acc ->  }

	    //顾名思义的操作
//        listOne.remove()
//        listOne.removeAll {  }
//        listOne.removeAt()
//        listOne.removeIf {  }
//        listOne.replaceAll()

	    //顺序反转
//        listOne.reverse()
//        listOne.reversed()

//        listOne.set()

	    //随机顺序打乱
//        listOne.shuffle()
//        listOne.shuffled()

//        listOne.single()//单元素获取，或者null和异常
//        listOne.singleOrNull {  }
	    // 切片指定位置的list出来
	    val slice = listOne.slice(0..3)

	    //排序，list的一些操作是需要基于排序的基础上的。
//        listOne.sort()
//        listOne.sortBy {}
//        listOne.sortedByDescending {  }
//        listOne.sortWith()

//        listOne.spliterator()//转化为spliterator对象
//        listOne.stream()//java的stream流对象

	    val subList = listOne.subList(0, 9)//子列表，不如slice灵活
	    val subtract = listOne.subtract(listTwo)//返回余集，也就是属于listOne，但是不属于list'Two的
//        listOne.sum()
//        listOne.sumByDouble {  }
//        listOne.take(4)//从前开始，提出指定个数的元素为一个新的list

//        listOne.takeLast(5)//后面开始
//        listOne.takeLastWhile {  }//从后开始，并满足条件，不满足就没有。
//        listOne.takeWhile {  }//从前开始满足条件的

//        listOne.toArray()
//        listOne.toSortedSet()

//        listOne.trimToSize()

	    //两个list合并为一个set
	    val union = listOne.union(listTwo)

	    //得到一个list<list>,其中item的list是根据下面指定大小，指定偏移step，来提取listOne得到的。partialWindows=true表示不满足size大小也提取，false则不。最后一个参数是将list《list》的item形式的list转化为某种对象
	    println(listOne.windowed(5, 2, true, {
		    it.size
	    }))
	    listOne.withIndex()//得到一个迭代器
	    //zip操作符号，是将两个list的item元素依次对应组合成pair，成一个新的list,也可以指定配对规则,得到一个list<V>而不是list《pair》
	    val zip = listOne.zip(listStr)

	    val zipWithNext = listOne.zipWithNext { a, b ->
		    "a b $a,$b"
	    }


    }



}