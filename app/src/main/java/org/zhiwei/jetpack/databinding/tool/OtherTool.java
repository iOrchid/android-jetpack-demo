package org.zhiwei.jetpack.databinding.tool;

import android.graphics.Color;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;

import org.zhiwei.jetpack.databinding.entity.CommonUser;

/**
 * Author: zhiwei.
 * Date: 2018/11/2 0002,16:29.
 */
public class OtherTool {

	//这里使用databinding的转换功能，将对象转化为string，这个在xml的text属性中，就能使用对象来自动转换为string,主要是不需要显示的导入和引用此函数
	@BindingConversion
	public static String converStr(CommonUser user) {
		return user.getAge() + user.getName() + user.isSingle();
	}

	//同上，不需要显示因如何调用，在xml中即可使得textview有一个 bg 的属性，app命名空间的，
	@BindingAdapter({"bg"})
	public static void randomName(TextView tv, String color) {
		tv.setTextColor(Color.parseColor(color));
	}

}
