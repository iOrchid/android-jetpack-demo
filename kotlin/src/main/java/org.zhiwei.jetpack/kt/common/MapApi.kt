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
        mapOne.merge(
            3,
            "999"
        ) { key, old -> "reMap " }//这里，3 是有的，所以替换为reMap，如果是9，没有这个key，则就新增 9:"999"了。
        println(mapOne)

        mapOne.also { }
        mapOne.let { }




        mapOne.isNotEmpty()
        mapOne.isNullOrEmpty()
        mapOne.isEmpty()
        mapOne.clear()
    }
}