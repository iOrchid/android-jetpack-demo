package org.zhiwei.jetpack.livedata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import org.zhiwei.jetpack.R;

/**
 * LiveData的演示界面
 * Author: zhiwei.
 * Date: 2018/11/5 0005,11:11.
 */
public class LiveDataActivity extends AppCompatActivity {

	private TextView tvAge;
	private TextView tvName;
	private TextView tvCity;
	private TextView tvInfo;

	private LiveUser user;//部分属性可以live响应的数据对象
	//用于演示 transformation
	private MutableLiveData<LiveUser> liveUserLiveData = new MutableLiveData<>();
	//map转化liveData的返回类型，用一个liveData可以向下转化出所需要的liveData。还有一个switchmap函数类似
	private LiveData<String> userInfo = Transformations.map(liveUserLiveData, user -> "姓名： " + user.getName().getValue() + " 年龄：" + user.getAge().getValue() + " 城市：" + user.getCity());

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_livedata);

		tvAge = findViewById(R.id.tv_age_live);
		tvName = findViewById(R.id.tv_name_live);
		tvCity = findViewById(R.id.tv_city_live);
		tvInfo = findViewById(R.id.tv_info_live);

		user = new LiveUser("北京", "小明", 20);
		//普通的 user属性
		tvCity.setText(user.getCity());
		//liveData的user属性
		user.getAge().observe(this, integer -> {
			String text = integer.toString();
			tvAge.setText(text);
		});
		//liveData的变化setValue，应在model中，View只负责显示
		packLive(user.getName(), tvName);

		//liveData转换的操作
		liveUserLiveData.setValue(user);
		//liveData形式的数据显示整个user对象数据
		userInfo.observe(LiveDataActivity.this, s -> tvInfo.setText(s));
	}

	/**
	 * 简单封装
	 *
	 * @param liveData liveData
	 * @param view     view
	 */
	private void packLive(LiveData<String> liveData, TextView view) {
		//JDK8 中的 方法引用 view::setText
		liveData.observe(this, view::setText);
	}

	public void changeLive(View view) {
		//UI上这两个会变化，因为使用了liveData
		user.setAge(22);
		user.setName("小明明");
		//UI上这个不会变化，因为是普通的属性字段
		user.setCity("上海");
		//transformations map 的演示变化，这里只是将数据源的user改变，UI界面上就能响应变化，不需要setText手动调用。
		liveUserLiveData.setValue(user);
		Toast.makeText(this, "请注意，city字段在View上并未变化", Toast.LENGTH_SHORT).show();
		//普通属性的字段，想要同步变化，还需要再setText一次，而且是要每变化一次，都要手动调用代码setText。liveData则会响应变化
//        tvCity.setText(user.getCity());

	}
}
