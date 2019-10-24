package org.zhiwei.jetpack.kt.common

import java.util.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年10月23日 10:17
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 演示Collections的api的示例类
 */
class CollectionsApi {

    private val arrayOne = arrayListOf<Int>(1, 2, 4, 5)
    private val arrayThree = arrayListOf<Int>(1, 2, 4, 5)
    private val arrayFour = arrayListOf<Int>(0, 9, 88, 1, 2, 4, 5, 90, 100, 222, 1, 2, 4, 5)
    private var arrayTwo = MutableList<Int>(30) { i -> 5 * i }
    private val searchList =
        mutableListOf("abc", "ddd", "jjj", "jjd", "ddw", "jww", "jjjow", "jwoe", "zz%", "ddo")
    private val mapOne = mutableMapOf<Int, Boolean>()

    /**
     * 可以在单元测试类中，调用测试这里，根据需要注释掉不同的api调用
     */
    fun testCC() {

//        //1、addAll，将不定个数的元素添加到指定的集合中去，返回值是boolean
//        val b = Collections.addAll(arrayOne,3,2,3,4,5,23,5)
//        println(arrayOne)
//        //2、asLifoQueue转化为一个 后进先出 的队列
//        val queue = LinkedList<Int>(arrayOne)
//        val lifoQueue = Collections.asLifoQueue(queue)
//        lifoQueue.remove(4)
//        println(lifoQueue)
//        //3、binarySearch 二分查找，前提需要先排序好，然后会查出第一个指定元素所在的下标，没有的话就是-1，如果不排序，那么返回也是不准确的。可以传参一个自定义的compare
//        val sorted = searchList.sorted()
//        println(sorted)
//        val search = Collections.binarySearch(sorted, "jjd")
//        println(search)
//        //4、checkedCollection,用于校验列表的元素类型，返回一个类型安全的。类似的还有checkedList，checkedMap之类的。
//        val checkedCollection = Collections.checkedCollection(searchList, String::class.java)
//        println(checkedCollection)
//        //5、将arrayOne的元素，复制到 arrayTwo，只是将arrayOne的元素，依次从0号位，覆盖arrayTwo对应位的元素。其余不管。注意不能越界。
//        println(arrayTwo)
//        Collections.copy(arrayTwo,arrayOne)
//        println(arrayTwo)
//        //6、disjoint，判断两个集合是否 无交集，也就是 是不是彼此独立。注意：如果 没有 共有元素，则true，有共有元素，就是false
//        val disjoint = Collections.disjoint(arrayOne, searchList)
//        println(disjoint)//true，两个没有共有元素
//        //7、emptyList,emptyMap等，返回一个空元素的集合对象
//        Collections.emptyList<Int>()
//        //8、将指定的列表所有元素替换为指定的元素值
//        Collections.fill(arrayOne,999)
//        arrayOne.fill(999)//kotlin的便捷写法
//        //9、frequency 在指定的集合中找到 指定元素 的个数
//        val frequency = Collections.frequency(arrayTwo, 3)
//        println(frequency)
        //10、arrayThree作为一个子列表，在ArrayFour中第一个匹配的开始 位置 ，如果没有，就是-1.lastIndexOfSubList类似，是最后一个匹配子串的开始位置
//        val indexOfSubList = Collections.indexOfSubList(arrayFour,arrayThree)
//        println(indexOfSubList)
        //11、max min获取集合的最大，最小元素,不是数字，就按照ascII码对比?
//        println(Collections.max(searchList))
        //12、nCopies 就是构建一个arrayList，含有指定个数的元素，元素值也指定。
//        val nCopies = Collections.nCopies(3, "dd")
//        println(nCopies)
        //13、空的map<T,boolean>转为一个set<T>
//        val newSetFromMap = Collections.newSetFromMap(mapOne)
//        println(newSetFromMap)
        //14、替换指定元素值，所有的满足匹配的 都会替换。
//        val replaceAll = Collections.replaceAll(arrayFour, 2, 9)
//        println(arrayFour)
        //15、reverse翻转数组的顺序,头尾相调换
//        Collections.reverse(arrayFour) //Kotlin简便写法        arrayOne.reverse()
//        println(arrayFour)
        //16、rotate则是指定个数的元素提到前边来。
//        Collections.rotate(arrayFour,5)//将尾部的5个数据，保持顺序的提到最前头。
        //17、shuffle将指定的集合顺序，随机打乱。
//        val rnd = Random.Default.asJavaRandom()
//        Collections.shuffle(searchList,rnd)
//        println(searchList)
        //18、singleton,将指定元素构建成一个单元素的set，类似的有 singletonList，singletonMap
//        val singleton = Collections.singleton("jjjjlojioi")
//        println(singleton)
        //19、sort，给集合排序，默认是自然排序也就是数字大小，字符编码顺序之类的,也可以指定自定义的comparator
//        Collections.sort(arrayFour)
//        println(arrayFour)
        //20、swap交换指定位置的元素，
        Collections.swap(arrayOne, 2, 0)
        println(arrayOne)
        //21、synchronizedCollection，synchronizedMap/List/Set 等，得到一个操作上线程安全的集合
        //22、unmodifiableCollection/Set/List/Map 等，得到一个不可修改的集合
        val unmodifiableList = Collections.unmodifiableList(arrayOne)
//        unmodifiableList.set(0,99)//就会报错，不能修改了。


    }

}