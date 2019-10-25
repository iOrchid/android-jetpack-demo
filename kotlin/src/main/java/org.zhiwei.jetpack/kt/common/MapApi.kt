package org.zhiwei.jetpack.kt.common


/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年10月23日 10:19
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 演示map操作的Api示例
 */
object MapApi {

	private val mapOne = mutableMapOf(
		1 to "A", 2 to "B", 3 to "C", 5 to "Ejj"
	)

	private val epMap = emptyMap<Int, String>()


	//todo 单个api去掉注释，配合单元测试，即可运行结果。因为是单独api测试的，所以下面注释如果都放开，可能存在某种冲突。
	fun testMap() {
//        //1、get元素
//        mapOne.get(1)//在kotlin中，如果确保非空的map，可以使用map[key]的获取指定key的value
//        mapOne[2]
//        //2、map.all，里面接收一个函数，判断map中所有的entry满足某个boolean的条件，则返回true，不是全部满足就是false
//        println(mapOne.all { (key, value) -> key * 2 < 100 })
//        //3、判断map是不是空的，如果有一个entry，就是true。any{}表示map中有一个满足条件的话，就是true，否则false
//        println(mapOne.any())
//        println(mapOne.any { (key, value) -> key * 2 < 2 })
//        //4、asIterable 转化为迭代器对象
//        for (entry in mapOne.asIterable()) {
//            println(entry)
//        }
//        //5、asSequence作为一个序列
//        mapOne.asSequence()
		//6、compute就是，现在map中找到指定key的value，然后根据表达式，计算新的value，再更新进去。如果原来没有，那么就NPL了。computeIfAbsent/Present 类似的。
//        mapOne.compute(3) { key, oldVal -> "更改一下$oldVal" }
//        println(mapOne[3])
		//7、contains是否包含
//        mapOne.contains(9)
//        mapOne.containsKey(4)
//        mapOne.containsValue("C")
//        mapOne.count()//统计元素的个数
//        mapOne.count { (key,value)-> key <3 }//map中满足指定条件的元素的个数
//        mapOne.entries//获得一个set集合，元素就是map的entry
//        //8、filter函数，过滤指定条件，得到一个新的map，类似的还有filterKeys/Values/To等
//        mapOne.filter { (key,value)-> value =="D" }
		//9、flatMap 按照条件转化为一个list，flatMapTo类似。
//        val flatMap = mapOne.flatMap { it.value.asIterable() }
//        flatMap.forEach { println(it) }
		//10、forEach 遍历元素entry
//        mapOne.forEach { t, u -> println("$t $u") }
//        mapOne.getOrPut(3) { "心" }//getOrPut，getOrElse，getOrDefault都是处理get可能异常的时候的方式
//        val ifEmpty = epMap.ifEmpty { mapOne }//如果元素值为空，填充默认的东西（可以是map，可以不是），后返回map，非空，就是map本身
//        println(ifEmpty)
//        //获取迭代器
//        mapOne.iterator()
//        mapOne.keys
//        //根据指定条件，转化map为list,类似的有mapKeys,mapValues,mapTo,mapNull之类的。
//        val list = mapOne.map { (key, value) -> "key $key value$value" }

//        //11、maxBy 根据指定条件，获取最大的entry元素，或者null。相似的有minBy，minWith
//        val maxBy = mapOne.maxBy { (key, value) -> key }
//        println(maxBy)
//        //根据指定的比对规则，返回最大值的entry
//        mapOne.maxWith(Comparator { o1, o2 -> if (o1.key+1>o2.key) 1 else -1 })
		//12、merge，指定key替换新的value，如果没有这个key，就新增key和value，如果有，那么就替换为后面表达式的结果值
//        mapOne.merge(
//            3,
//            "999"
//        ) { key, old -> "reMap " }//这里，3 是有的，所以替换为reMap，如果是9，没有这个key，则就新增 9:"999"了。
//        println(mapOne)
		//13、从map中移除指定key的entry，minus，可以用 - 符号，类似的有plus，+ 符号
//        mapOne.minusAssign(3)
//		println(mapOne - 3)//同上，得到的是移除key = 3 的entry之后的map
//		//14、判断是不是没有满足条件的entry，如果没有，返回true，有点类似any
//		mapOne.none()
//		mapOne.none { it.key > 3 }
		//15、有点类似forEach，就是对每个map的元素都做一个action操作
//		mapOne.onEach { (key, value) ->
//			println("key $key,val $value")
//		}
//		println(mapOne)
		//16、PUT REMOVE REPLACE ,如果没有key，或者key对应的value是null，那么就赋值，但是返回null告知你，如果有值，就返回值。
//		val putIfAbsent = mapOne.putIfAbsent(9, "333")//null,
//		println(putIfAbsent)

//		println(mapOne)

//      //todo // 以下是Kotlin的扩展操作符,任何kotin类都有这些

//		//1、also就是关联一个对象，可以执行一系列针对该对象的代码块操作，在代码块中，it默认代之对象 它返回值还是对象自身，所以可用于链式操作.
//		val also = mapOne.also {
//			/*mapIt->*/
//			//这里面想操作mapOne就必须it引用（当然如果自定义了名字，如上注释掉的mapIt，那么就在内部使用mapIt）
//			it.set(3, "alsoValue")
//			it.getValue(5)//如果操作没有的key，会报错
//		}
//		println(mapOne)
//		println(also)
////		//2、apply也类似于also，执行代码块返回自身引用，内部默认this，代之对象。
//		val apply = mapOne.apply {
//			//这里不需要显式应用this，就能直接调用mapOne的函数
//			println(this.values)
//			println(keys)
//			set(4, "ddd444")
//		}
//		println(mapOne)
//		println(apply)
//		//3、let，也是对象代码块，内部it代之，但是它返回值为最后一行代码的返回结果
//		val let = mapOne.let {
//			//如同also，这里面就需要显式的引用对象，才能操作mapOne
//			println(it.keys)
//			it.set(12,"33kkdksk")
//			"333jjj"//那么，这个let的最终结果返回值就是string
//		}
//
//
//		//4、有点类似apply,返回最后的执行结果
//		mapOne.run {/*mapIt->*/
//			//代码块内有this引用，代之mapOne，所以可以直接调用它的函数。但是如果有了命名的mapIt，那么就不能这么省略this的写法了
//			this.set(3,"fjdjjs")
//			this@MapApi//这样才是引用外部对象
//			set(9,"99dd")
////			println(getValue(44))//会崩溃
//		}
//		println(mapOne)
//		//类似于run，多了内部代码块异常可以catch掉,会得到执行结果Result对象
//		val runCatching = mapOne.runCatching {
//			println(getValue(99))//不会崩，虽然是null
//			getValue(44)
//		}
		//5、执行返回满足代码块中boolean条件的map对象，如果没有，就是null
		val takeIf = mapOne.takeIf {
			mapOne.containsKey(3)
			5 > 9
		}
		println(mapOne)
		println(takeIf)
		//与takeIf相反，它返回不满足块内boolean条件的map集合，没有就是null
		val takeUnless = mapOne.takeUnless {
			5 > 9
		}
		println(takeUnless)

//      // todo // to 是中缀函数，构建 pair数据对象用
//		mapOne.to("dd")
//
//		mapOne.toList()
//		mapOne.toMap()

//		//17、withDefault，就是将原map包裹一下，那么在将来使用的时候，如果getValue到一个没有的key，就能返回默认值
//		val message = mapOne.withDefault { key -> "$key : 默认" }
//		println(message)
//		println(message.getValue(999))


		//以下为常用，顾名思义的api，不做演示了
		mapOne.set(9, "ddd")
		mapOne.size
		mapOne.values
		mapOne.orEmpty()
		mapOne.isNotEmpty()
		mapOne.isNullOrEmpty()
		mapOne.isEmpty()
		mapOne.clear()
	}
}