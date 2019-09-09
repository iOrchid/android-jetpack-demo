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
 * 2、自定义属性
 * 3、转换器converters
 * 4、自定义控件支持dataBinding,@BindingMethods等
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
    }
}