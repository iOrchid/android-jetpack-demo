package org.zhiwei.jetpack.kt.common

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年10月23日 10:20
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 演示kotlin中常用操作符的示例
 */
object OperatorApi {

    //<editor-folder desc="String常用api">
    private var strOne = "this is string one"
    private var strTwo = "…… == 这个是用于演示的string 的two == ……"
    private var strThree = "《 String Three 》\n<dd就是社么六六六>"

    /**
     * 这里仅仅演示部分那些不是顾名思义的，以及也不是在Collections中说明过的api函数
     */
    @UseExperimental(ExperimentalStdlibApi::class)
    fun testStr() {
        //1、转化为byte的inputStream对象
        strOne.byteInputStream()
        //复制为首字母大写后的string
        println(strOne.capitalize())
        //指定位置标号的char字符，在unicode中的编号
        println(strOne.codePointAt(2))
        //对比，如果不小于后者，则返回自身，否则，返回后者，也就是保证有最小值，string，Int的都可以有这个函数
        println(strOne.coerceAtLeast("this"))
        //对比，确保在范围内，超出，则取边界值。
        println(strOne.coerceIn("ABC", "DDEF"))
        //分片成数组List
        println(strOne.chunked(5))
        //找到两个string的共有前缀部分,没有就是空字符
        println(strOne.commonPrefixWith("this is 啥啊"))
        //对比后缀
        println(strOne.commonSuffixWith("strThree one"))
        //将首字母小写
        println("This 小写么".decapitalize())
        //丢弃掉指定范围的，前三个
        println(strOne.drop(3))

        println(strOne.encodeToByteArray().size)

        println(strOne.endsWith('c'))

        println("TT %s 00数字".format(" 占位符 "))
        //
        println("\u3300".hasSurrogatePairAt(0))
        //规范输出，返回自身值，主要用于确保string 存在于唯一的字符串池中
        println(strTwo.intern())

        println("  ".ifBlank { "if string 空了，返回这个" })
        //多行的string，转为string的list
        println(strThree.lines())
        //正则匹配
        println(strOne.matches(Regex("^t[*]")))
        //偏移，
        println(strOne.offsetByCodePoints(1, 3))
        //如果字符长度不达到指定的长度，那么就在后面拼接上char字符，到该长度，类似的有padStart，在前面拼接.第二个参数默认实空格。一般这里用作数字格式补全
        println(strTwo.padEnd(40, '&'))
        //string根据内部的匹配规则，生成pair对，first是满足的，second是不满足的。
        println(strOne.partition { char ->
            //区分H字母之后，之前的比对，
            char > 'h'
        })
        //缩进
        println(strThree.prependIndent())
        //转化为string reader
        println(strThree.reader().readText())
        //对比两个string，在指定位置范围的字符顺序是否一致，true，表示一致.演示，这里匹配的是 his
        println(strOne.regionMatches(1, "A This", 3, 3))
        //移除指定范围的字符，
        println(strOne.removeRange(3..7))
        //如果string是指定的字符串开头和结尾，那么移除该指定字符串，如果不是同时开头和结尾，就不管。
        println("one is one not two，last one".removeSurrounding("one"))
        //替换指定范围的字符
        println(strOne.replaceRange(0..3, "哈哈"))
        //类似断言assert，如果strOne是单个字符，就是true，不是，就异常
        println("c".single())
        //多行String的去除 行首特殊字符。默认换行占位符是|
        println(
            """&&&&&&&&空格在前strOne
                    |继续
                    |接着
                    |然后结果中就没了
                """.trimMargin().trimStart('&')
        )
        //移除两头的指定字符，如果有。
        println("this is a string this jjj".trimMargin("this"))
        //行缩进的移除
        println(
            """
            
            段落缩进，打印出来就会没有
            继续
            
                接着
            然后结果中就没了
            但是依旧保留原有相对缩进，以及前后空行
            
            """.trimIndent()
        )


    }

    //</editor-folder>


}