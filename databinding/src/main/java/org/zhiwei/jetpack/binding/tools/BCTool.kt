package org.zhiwei.jetpack.binding.tools

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.bumptech.glide.Glide
import org.zhiwei.jetpack.binding.R

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月04日 11:53
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Binding高级用法中，辅助工具类，演示@BindingConversion，@bindadapter等
 */
object BCTool {
    /**
     * 兼容适配view的background的str转color属性，这里函数名 可以随意，而且不需要其他地方显式的调用。
     * 只需要在此 ，静态函数的声明即可。（java的写法就是public static，这里不写java版的了。）
     */
    @JvmStatic
    @BindingConversion
    fun converStr2Color(str: String): Drawable {
        return when (str) {
            "red" -> {
                ColorDrawable(Color.RED)
            }
            "blue" -> ColorDrawable(Color.BLUE)
            else -> {
                ColorDrawable(Color.YELLOW)
            }
        }
    }

    /**
     * 用于appCompatImageView的自定义属性，imgSrc，可以用于加载url的图片
     * 函数名也是随意，主要是value的声明，就是新加的属性名了，可以多个属性同用，并配置是否必须一起作用
     * todo 加载网络图片，需要网络权限，别忘了
     */
    @JvmStatic
    @BindingAdapter(value = ["imgSrc"], requireAll = false)
    fun urlImageSrc(view: AppCompatImageView, url: String) {
        Glide.with(view)
            .load(url)
            .placeholder(R.drawable.img_banner)
            .centerInside()
            .into(view)
    }
}