package org.zhiwei.jetpack.binding.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import org.zhiwei.jetpack.binding.R
import org.zhiwei.jetpack.binding.bean.ObUser
import org.zhiwei.jetpack.binding.databinding.ActivityAdvancedUseBinding

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月09日 15:41
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //glide的初始化
        Glide.init(this, GlideBuilder())

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
        binding.myBg = resources.getDrawable(R.drawable.img_banner)
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