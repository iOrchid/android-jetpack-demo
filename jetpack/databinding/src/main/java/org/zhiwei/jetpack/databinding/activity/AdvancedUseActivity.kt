package org.zhiwei.jetpack.databinding.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.zhiwei.jetpack.databinding.R
import org.zhiwei.jetpack.databinding.bean.ObUser
import org.zhiwei.jetpack.databinding.databinding.ActivityAdvancedUseBinding

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月09日 15:41
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * DataBinding的高级用法的演示
 * 1、双向绑定
 * 2、自定义属性（todo 注意databinding支持view的属性，需要有对应setXXX,getXXX的格式的函数（或者boolean的isXXX），才能被框架感知。
 * 如果一个View的属性，不是规范的setXXX，getXXX的设置/获取函数，那么就不行。可以继承该函数，设置setXXX/getXXX，在xml中自定义的View，就可以支持。）
 * 3、转换器converters
 * 4、自定义控件支持dataBinding,@BindingMethods、@InverseBindingMethods等
 */
class AdvancedUseActivity : AppCompatActivity() {

	val refreshing = MutableLiveData<Boolean>()//标记是否刷新中的liveData对象。不能private，因为要用在xml中

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val binding = DataBindingUtil.setContentView<ActivityAdvancedUseBinding>(
			this,
			R.layout.activity_advanced_use
		)
		//user设置
		val user = ObUser("张三", 30, 1, "没有修改名字前的数据")
		binding.user = user

		//url
		val url = "http://img.redocn.com/sheying/20140731/qinghaihuyuanjing_2820969.jpg"
		binding.url = url
		//设置myView的img参数
		binding.myBg = ResourcesCompat.getDrawable(resources, R.drawable.img_banner, null)
		//演示自定义View实现双向绑定
		// 1、先单向绑定 也就是将viewModel的数据变化，通知UI来刷新；(这里也就是在[BCTool]中static声明自定义属性，refreshing)
		// 2、将UI的变化，反向绑定，来通知数据模型的状态变化。
		// 3、完成双向绑定，避免死循环。

		//这里是个长文本，配合演示swipeRefreshLayout的状态感知
		binding.tvLongTextAdBinding.text = strText
		//
		binding.activity = this
		//这里记录log，liveData感知，也就证明，ui的刷新，将状态反向绑定给了data
		refreshing.observe(this, Observer<Boolean> {
			Log.i("AdvancedUseActivity", "refreshing $it")
		})

	}
	/*
	问题：1、@{user.name}如果user为null，是否运行崩溃
	2、DataBinding是否支持所有View的属性
	3、双向绑定时候，是否会数据陷入死循环。

	解答：1，如果xml中的variable没有binding赋值，如上面的myBg那一行如果注释掉。运行时就会崩溃。而如果只是name为空，就会显示null，而不会崩溃。
	2、上面已经知道，不是所有的View的属性都可以直接dataBinding的，需要满足标准setXXX/getXXX的函数方式，或者按照[MyImageView]中注释写的那三种方法，
	扩展view的属性binding支持
	3、双向绑定也不会死循环，因为实现类会做old==new的value值校验，并return，避免陷入双向刷新的死循环。
	 */
}

const val strText = """
	《琵琶行》 唐·白居易
元和十年，予左迁九江郡司马。明年秋，送客湓浦口，闻舟中夜弹琵琶者，听其音，铮铮然有京都声。问其人，本长安倡女，尝学琵琶于穆、曹二善才，年长色衰，委身为贾人妇。遂命酒，使快弹数曲。曲罢悯然，自叙少小时欢乐事，今漂沦憔悴，转徙于江湖间。予出官二年，恬然自安，感斯人言，是夕始觉有迁谪意。因为长句，歌以赠之，凡六百一十六言，命曰《琵琶行并序》。

浔阳江头夜送客，枫叶荻花秋瑟瑟。

主人下马客在船，举酒欲饮无管弦。

醉不成欢惨将别，别时茫茫江浸月。

忽闻水上琵琶声，主人忘归客不发。

寻声暗问弹者谁，琵琶声停欲语迟。

移船相近邀相见，添酒回灯重开宴。

千呼万唤始出来，犹抱琵琶半遮面。

转轴拨弦三两声，未成曲调先有情。

弦弦掩抑声声思，似诉平生不得志。

低眉信手续续弹，说尽心中无限事。

初为《霓裳》后《六幺》。

大弦嘈嘈如急雨，小弦切切如私语。

嘈嘈切切错杂弹，大珠小珠落玉盘。

间关莺语花底滑，幽咽泉流冰下难。

冰泉冷涩弦凝绝，凝绝不通声暂歇。

别有幽愁暗恨生，此时无声胜有声。

银瓶乍破水浆迸，铁骑突出刀枪鸣。

曲终收拨当心画，四弦一声如裂帛。

东船西舫悄无言，唯见江心秋月白。

沉吟放拨插弦中，整顿衣裳起敛容。

自言本是京城女，家在虾蟆陵下住。

十三学得琵琶成，名属教坊第一部。

曲罢曾教善才服，妆成每被秋娘妒。

五陵年少争缠头，一曲红绡不知数。

钿头银篦击节碎，血色罗裙翻酒污。

今年欢笑复明年，秋月春风等闲度。

弟走从军阿姨死，暮去朝来颜色故。

门前冷落鞍马稀，老大嫁作商人妇。

商人重利轻别离，前月浮梁买茶去。

去来江口守空船，绕船月明江水寒。

夜深忽梦少年事，梦啼妆泪红阑干。

我闻琵琶已叹息，又闻此语重唧唧。

同是天涯沦落人，相逢何必曾相识！

我从去年辞帝京，谪居卧病浔阳城。

浔阳地僻无音乐，终岁不闻丝竹声。

住近湓江地低湿，黄芦苦竹绕宅生。

其间旦暮闻何物？杜鹃啼血猿哀鸣。

春江花朝秋月夜，往往取酒还独倾。

岂无山歌与村笛？呕哑嘲哳难为听。

今夜闻君琵琶语，如听仙乐耳暂明。莫辞更坐弹一曲，

为君翻作《琵琶行》。感我此言良久立，却坐促弦弦转急。

凄凄不似向前声，满座重闻皆掩泣。

座中泣下谁最多？江州司马青衫湿。
"""