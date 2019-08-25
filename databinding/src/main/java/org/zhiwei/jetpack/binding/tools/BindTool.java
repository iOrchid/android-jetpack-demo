package org.zhiwei.jetpack.binding.tools;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月25日 20:02
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * _              _           _     _   ____  _             _ _
 * / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 * / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 * / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 * /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 * <p>
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * java版的用于xml的dataBinding的静态函数类,kotlin的写法，参照BindUtil中
 */
public class BindTool {


	/**
	 * 简单的一个静态函数，databinding在xml中使用的函数，必须是public的static函数
	 *
	 * @param name 名称
	 * @param age  年龄
	 * @return string的拼接
	 */
	public static String nameAge(String name, int age) {
		return name + age;
	}

	/**
	 * 点击就打印一个log
	 */
	public static void log() {
		Log.i("BindTool", "dataBinding的普通点击，静态java函数的log日志");
	}

	/**
	 * 使用view参数，显示toast
	 *
	 * @param view view控件
	 */
	public static void toast(View view) {
		Toast.makeText(view.getContext(), "普通点击view显示toast", Toast.LENGTH_SHORT).show();
	}
}

