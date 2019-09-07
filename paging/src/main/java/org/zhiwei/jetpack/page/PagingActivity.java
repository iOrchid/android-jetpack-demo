package org.zhiwei.jetpack.page;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.zhiwei.jetpack.page.list.MyAdapter;

/**
 * paging演示分页加载界面
 * Author: zhiwei.
 * Date: 2018/11/6 0006,10:34.
 */
public class PagingActivity extends AppCompatActivity {

	//滑动list的时候，可以看到滚动条的变化，感知到数据的平滑加载

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paging);

		RecyclerView recyclerView = findViewById(R.id.rv_paging);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		MyAdapter adapter = new MyAdapter();
		recyclerView.setAdapter(adapter);

		MyPresenter presenter = new MyPresenter(this);

		presenter.allStudents.observe(this, adapter::submitList);
	}
}
