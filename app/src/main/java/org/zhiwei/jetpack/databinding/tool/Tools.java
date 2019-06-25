package org.zhiwei.jetpack.databinding.tool;

import android.view.View;

/**
 * Author: zhiwei.
 * Date: 2018/11/2 0002,14:06.
 */
public class Tools {

    public static String addHeader(String str) {
        return "[Header] " + str;
    }

    public static String getNull(String title) {
        return null;
    }


    public static int visi(boolean b) {
        if (b) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }
}
