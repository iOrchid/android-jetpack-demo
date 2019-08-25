package org.zhiwei.jetpack.paging.list;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import org.zhiwei.jetpack.R;
import org.zhiwei.jetpack.paging.db.Student;

/**
 * 分页paging的listAdapter，这个PagedListAdapter继承自RecyclerViewAdapter
 * Author: zhiwei.
 * Date: 2018/11/6 0006,10:42.
 */
public class MyAdapter extends PagedListAdapter<Student, MyViewHolder> {

	//DiffUtil是RecyclerView提供的，用于对比和生成差异数据
	private static final DiffUtil.ItemCallback<Student> DIFF_CALLBACK = new DiffUtil.ItemCallback<Student>() {
		@Override
		public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {

			return oldItem.getId() == newItem.getId();
		}

		@SuppressLint("DiffUtilEquals")
		@Override
		public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {

			return oldItem == newItem;
		}
	};

	/**
	 * 继承PagedListAdapter，必须要调用基类的一个构造函数
	 */
	public MyAdapter() {
		super(DIFF_CALLBACK);
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_paging, parent, false);
		return new MyViewHolder(inflate);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		//数据源的提供来自diff
		holder.setName(getItem(position));
	}
}
