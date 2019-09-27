package org.zhiwei.jetpack.kt.base

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月19日 11:13
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Kotlin的类与对象的相关定义与信息
 * 1、kotlin中class定义类，默认都是public的，而且是final 的，不可继承
 * 2、若要可继承，就需要open关键词修饰
 * 3、一个kt文件中可以多个class，以及混合top level的变量/常量/函数的定义
 * 4、默认定义的class是final的，其内部的变量/函数，也就不必要使用protected的权限，其作用也就相当于private了。
 * 5、每个class类都有一个伴生静态object类companion object 在class加载的时候就初始化了，作为类的静态成员存在。（object类，自身是没有伴生类的）
 * 6、kotlin不同于java，一般的声明成员属性，不用getter/setter，当然除非你要控制只能getter不能setter，那样就需要重写
 * 7、在class定义函数和top level中差不都，也就是protected这个权限符号的区分。默认函数也是final的。
 * 8、如果class是final的，函数open修饰，是无效的。而且，open的函数或者变量不能private修饰，且与final修饰符是互斥的。
 * 9、构造函数分为主构造函数，次构造函数，而且kotlin中，如果主构造函数显式的写在class的名后，则所有次级构造函数必须调用主构造函数
 * 10、class类都有一个自身的init函数，在class加载时候就运行，早于构造函数的调用！！！
 * 11、接口实现，或者类的继承，需要用:，类似于java中的extends，implementation,可多个接口，但只有一个父类
 * 12、抽象类abstract，其抽象函数 不同于接口的函数，它是不能有默认方法体的。
 * 13、如果子类实现多个接口，且接口中拥有共同的函数命，就会冲突，在子类中就需要显式指明调用哪个
 * 14、单例类object，也就是静态的单例模式，
 * 15、数据类，一个数据bean对象的类，特殊的class 使用data class 声明
 */
public final class KtClazz {

	//<editor-folder desc="类的构造函数的讲解模块">

	constructor()//默认class类都有一个的无参构造函数

	constructor(name: String)//这样写三个构造函数，属于平级，所以不需要依次调用。倘若在上面class KtClazz后添加了构造函数

	constructor(name: String, age: Int)

	//</editor-folder>

	//<editor-folder desc="类函数的定义模块">

	//简单演示class中的变量/常量声明定义
	private var abc = "abc"
	protected var bbc = "bbc"//这里会注意，在top level中 不能protected修饰变量，函数的定义，class中可以
	// ，但是对于一个final的（也就是默认声明）类，这个权限也就相当于private了，只有对于open的class，protected的变量，才能被子类操作
	internal var cbc = "cbc"
	public var dbc = "dbc"

	var bNum = 0b001//二进制的表示数
	var hNum = 0x0f00e//16进制数，kotlin中没有8进制的表示
	var bigNum = 00_999_999_000.000//可以使用_下划线分割数据，便于读取，但是不会影响实际数值
	val longStr = """long str \srng\t\'在这种string的方式，转义符也会失效"""

	val name = "class name is a final value"//类中可以声明不可变量，也就是普通常量，但是不是static的静态的，如果声明静态需要在其伴生对象中


	/**
	 * 函数的定义，默认也是public final的，权限符号可以修改，如果想要被继承，就要用open修饰
	 * 只要class中有一个函数是open的，那么这个class就必须是open的。
	 */
	public final fun configData() {
		println("普通默认的无参函数定义")
	}

	/**
	 * 有参数的函数定义
	 */
	private fun paramConfig(name: String, age: Int) {
		println("name: " + name + "age: " + age)
	}

	/**
	 * 可变参数的函数定义，这里用到了vararg的关键词，如此调用方就可以传递一个或者多个参数进来。类似于java中的 (String... apple)的写法
	 */
	internal fun varargConfig(vararg apple: String) {
		println("该函数，是可以接收可变个数的参数")
	}

	//</editor-folder>

	/**
	 * 伴生的object类，静态类，可以写这个KtClassObj的名字，也可以省略不写。
	 */
	/*private*/ companion object /*KtClazzObj*/ {
		private const val constName = "静态常量名"//静态常量，声明定义在class的伴生对象类中

	}

}

//<editor-folder desc="构造函数演示">

//todo 演示主次构造函数，如果存在class声明处的构造函数（这里就是无参的，而且constructor可以省略掉）
//内部再有次级构造函数，则需要依次调用主构造函数
class KtClazz2 constructor() {
	constructor(name: String) : this()//调用主构造函数
}

//todo 在主构造函数中，可以同步声明参数为成员属性，就需要 var/val ，可根据实际，添加权限修饰符
//如果没有var/val，则在class内，函数不能直接引用到这个参数字段。需要内部再声明一个，并在init
class KtClazz3 /*constructor*/(private var name: String, apple: String) {//作为主构造函数 constructor可省略

	constructor(name: String, age: Int) : this(name, "")//调用主构造函数

	private var mApple = apple//

	fun testApple() {
		//可以引用到name，但是不能引用到apple 需要用mApple
		println(mApple)
		println(name)
	}
}

//todo 演示init与构造函数的调用时机，再UnitTest中看演示，得出结论，init函数调用早于构造函数！！，这个需要注意！
class KtClazz4 {

	constructor() {
		println("~~无参数的调用~~")
	}

	constructor(name: String) /*: this()*/ {//如果加上:this()就表示调用该有参函数之前，先调用上面的无参的。
		println("==这里是 含参数$name 构造函数的调用输出 ===")
	}

	init {
		println("---这里是init的调用输出---")
	}
}

//</editor-folder>


//<editor-folder desc="接口抽象类 演示">

//接口的定义
private interface IProxy {
	//    public var name: String//kotlin接口内可以定义属性，但是不能设置默认值，实现类就必须实现这个属性
	//kotlin中接口函数可以有默认的自定义内容，这样的话，实现接口的时候，就不必须实现这个有默认实现的函数方法
	public fun proxy(): String {
		println("默认定义接口的实现函数内容")
		return "default proxy"
	}

	//这个函数，没有默认实现，所以实现类就需要重写这个
	fun tt()

}

//可继承类，
open class opKtClazz {

	protected open fun ott() {

	}

	//定义这两个函数，就是为了演示subClass实现接口和继承类的时候，出现函数命冲突，的处理
	open fun tt() {
		println("opKtClazz的tt")
	}

	open fun proxy(): String {
		return "null"
	}

}

//抽象类，使用abstract，就不用open修饰了，冗余
abstract class AbsKtClazz {

	abstract fun abs()//抽象函数，不同于接口，不能有默认实现

}

//kotlin中实现接口，继承类，都是用:符号，多个接口和父类，可以用,分割;可以实现多接口，但是只能有一个父类，和java相同，不同于C/C++
private class ProxyImpl : IProxy, AbsKtClazz() {

	override fun abs() {

	}

	override fun tt() {
		println("实现类中的tt")
	}

}

//继承open的类
private class subKtClazz : opKtClazz(), IProxy {

	//和java类似，覆写函数，权限可以放宽，不能收紧
	public override fun ott() {
		super.ott()
	}

	override fun tt() {

	}

	//todo 这个函数名，在接口和父类中都有，所以可以指定调用哪个，或者根据条件区分
	override fun proxy(): String {
		//模拟条件判断，或者可根据业务选。
		if (1 > 0) super<opKtClazz>.proxy() else super<IProxy>.proxy()
		return "sub proxy"
	}
}

//</editor-folder>

//<editor-folder desc="数据类，单例类">

//数据类，就是final的不能open，不能abstract，不能inner，sealed，open,就一个主构造函数(必须有参数，至少一个)，不能有其他构造函数。可以有类代码体，变量声明，以及函数等。
// todo 但是，多数情况都是作为单独的数据bean封装。实例化数据类的时候，可以单独为一个变量，也可以直接将属性实例化，但是只能实例非私有的
//val (name, age, sex, desc) = org.zhiwei.jetpack.kt.base.User("小明", 22, 1, "小明是个男的，大学生一枚")
data class User(
	private val name: String,
	protected var age: Int,//因为final，所以protected也就失效，类似private
	internal val sex: Int,
	public var desc: String
) {
	//除了上面的构造函数内的声明变量，也可以在class内声明。todo 注意主构造函数内必须至少有一个变量声明。
	var address: String = "北京"

	//也可以有自己的函数方法
	fun configApple() {

	}

	//数据类可以有伴生对象
	companion object {
		//伴生类
	}
}

//单例类，特殊的一个类，用object声明，只有空参主构造函数，也就没有伴生对象了。一般用作工具类，单例模式等.调用方式就是SingleApple.getDeviceId()
object SingleApple {

	fun getDeviceId(): String = "deviceId Str"

}

//</editor-folder>
