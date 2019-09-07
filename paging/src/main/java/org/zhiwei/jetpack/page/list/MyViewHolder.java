package org.zhiwei.jetpack.page.list;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.zhiwei.jetpack.page.R;
import org.zhiwei.jetpack.page.db.Student;

/**
 * Author: zhiwei.
 * Date: 2018/11/6 0006,11:29.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

	private TextView tvName;

	public MyViewHolder(@NonNull View itemView) {
		super(itemView);
		tvName = itemView.findViewById(R.id.tv_name_item_rv_paging);
	}

	public void setName(Student student) {
		tvName.setText(student.getName());
	}

}
