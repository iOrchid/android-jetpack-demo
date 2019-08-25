package org.zhiwei.jetpack.databinding.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import org.zhiwei.jetpack.BR;
import org.zhiwei.jetpack.R;
import org.zhiwei.jetpack.databinding.entity.CommonUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhiwei.
 * Date: 2018/11/2 0002,17:04.
 */
public class LvAdapter extends BaseAdapter {

	List<CommonUser> list = new ArrayList<>();

	public LvAdapter() {
		CommonUser user;
		for (int i = 0; i < 4; i++) {
			user = new CommonUser(20 + i, "ListName " + i, i % 2 == 0);
			list.add(user);
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewDataBinding dataBinding;
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		if (convertView == null) {
			dataBinding = DataBindingUtil.inflate(inflater, R.layout.item_lv, parent, false);
		} else {
			dataBinding = DataBindingUtil.getBinding(convertView);
		}
		dataBinding.setVariable(BR.lvusr, list.get(position));
		return dataBinding.getRoot();
	}
}
