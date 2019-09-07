package org.zhiwei.jetpack.room;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * Room数据库演示类
 * Author: zhiwei.
 * Date: 2018/11/5 0005,15:41.
 */
public class RoomActivity extends AppCompatActivity {
	private TextView tvInsert, tvDelete, tvUpdate, tvQueryID, tvQueryAll, tvSize, tvAll;
	private UserDatabase instance;
	private UserDao userDao;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);
		tvInsert = findViewById(R.id.tv_insert_room);
		tvDelete = findViewById(R.id.tv_delete_room);
		tvUpdate = findViewById(R.id.tv_update_room);
		tvQueryID = findViewById(R.id.tv_query_id_room);
		tvQueryAll = findViewById(R.id.tv_query_all_room);
		tvSize = findViewById(R.id.tv_size_room);
		tvAll = findViewById(R.id.tv_all_room);
		//
		instance = UserDatabase.getInstance(this);
		deleteDatabase(UserDatabase.DB_NAME);//删除数据库，属于contextwrapper中的函数
		userDao = instance.getUserDao();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		instance.clearAllTables();
	}

	//todo 可以看出，这里插入数据的single是有区分的，但是查询出的结果，却都是默认值，因为single字段在数据库中没有，在entity中却有，所以数据库中出去来生成的对象，这个字段就是默认值。配置ignore的作用
	public void insert(View view) {
//        List<DbUser> users = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		DbUser user;
		for (int i = 0; i < 5; i++) {
			user = new DbUser();
			user.setAge(20 + i);
			user.setCity("北京 " + i);
			user.setName("小明 " + i);
			user.setSingle(i % 2 == 0);
			userDao.insertAll(user);
			sb.append(user.toString()).append("\n");
		}
//        userDao.insertAll(users.get(0),users.get(1));
		tvInsert.setText(sb.toString());
		getAll();
	}

	private void getAll() {
		List<DbUser> all = userDao.getAll();
		StringBuilder sb = new StringBuilder();
		for (DbUser user : all) {
			sb.append("uid: ")
					.append(user.getUid())
					.append("姓名: ")
					.append(user.getName())
					.append("年龄: ")
					.append(user.getAge())
					.append("城市: ")
					.append(user.getCity())
					.append("Single: ")
					.append(user.isSingle())
					.append("\n");
		}
		String text = "All Size ： " + all.size();
		tvSize.setText(text);
		tvAll.setText(sb.toString());
	}

	public void delete(View view) {
		DbUser user = userDao.findByName("小明 " + 3, 23);
		userDao.delete(user);
		//
		tvDelete.setText(user.toString());
		getAll();
	}

	public void update(View view) {
		DbUser user = userDao.findByName("小明 " + 2, 22);
		user.setAge(33);
		user.setCity("上海");
		user.setName("张三");
		user.setSingle(true);
		userDao.update(user);
		tvUpdate.setText(user.toString());
		getAll();
	}

	public void queryId(View view) {
		DbUser userById = userDao.getUserById(3);
		if (userById != null) {
			tvQueryID.setText(userById.toString());
		} else {
			Toast.makeText(this, "id=3 的user查询不到", Toast.LENGTH_SHORT).show();
		}
		//为了显示操作后的更新数据
		getAll();
	}

	public void queryAll(View view) {
		getAll();
	}
}
