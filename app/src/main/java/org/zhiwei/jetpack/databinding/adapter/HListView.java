package org.zhiwei.jetpack.databinding.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义的listView，由于原生ListView 与ScrollView存在事件冲突，这里就要重新计算自身高度，避免出现listView在ScrollView中就不能滑动，以及只有一条的高度
 * Author: zhiwei.
 * Date: 2018/11/5 0005,10:40.
 */
public class HListView extends ListView {
    public HListView(Context context) {
        super(context, null);
    }

    public HListView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public HListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
