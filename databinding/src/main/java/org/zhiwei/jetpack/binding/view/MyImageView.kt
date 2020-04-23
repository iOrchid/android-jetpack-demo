package org.zhiwei.jetpack.binding.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月11日 10:17
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 简单的自定义View，用于演示，演示扩展dataBinding支持view的属性
 * 1、通过静态函数的@BindAdapter方式，在[BCTool]中有演示
 * 2、通过继承view，定义确实的标准格式的setXXX函数
 * 3、通过@BindMethod方式，做函数映射。todo 这里注意，@BindMethods可以用于任何使用类上面，只要内部的BindMethod设置需要的view以及函数即可。
 */
@BindingMethods(
    BindingMethod(
        type = AppCompatImageView::class,
        attribute = "image",
        method = "setImageDrawable"
    )
)
//2、这里自定义View，实现setImg函数，就可以在xml中使用MyImageView来databinding用img属性
class MyImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    /**
     * 添加setImg函数，才能在xml中使用img的属性
     */
    fun setImg(drawable: Drawable) {
        setImageDrawable(drawable)
    }

}
