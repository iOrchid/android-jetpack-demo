package org.zhiwei.jetpack.databinding.tool;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Author: zhiwei.
 * Date: 2018/11/2 0002,15:32.
 */
public class Presenter {

	public static void showToast(View view) {
		Toast.makeText(view.getContext(), "Static show of Button", Toast.LENGTH_SHORT).show();
	}

	public void show(Context context) {
		Toast.makeText(context, "Context show of Button", Toast.LENGTH_SHORT).show();
	}
}
