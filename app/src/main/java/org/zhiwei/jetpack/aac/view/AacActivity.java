package org.zhiwei.jetpack.aac.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import org.zhiwei.jetpack.R;
import org.zhiwei.jetpack.aac.model.UserModel;
import org.zhiwei.jetpack.aac.viewmodel.UserViewModel;

/**
 * view层的Activity
 * Author: zhiwei.
 * Date: 2018/11/6 0006,15:19.
 */
public class AacActivity extends AppCompatActivity {

	private UserViewModel viewModel;
	private TextView tvList, tvInfo;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aac);
		tvList = findViewById(R.id.tv_user_list_aac);
		tvInfo = findViewById(R.id.tv_user_info_aac);
		//实例化viewModel
		viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

		viewModel.getUserList().observe(this, userModels -> {
			StringBuilder sb = new StringBuilder();
			for (UserModel model : userModels) {
				sb.append(model.toString()).append("\n");
			}
			tvList.setText(sb.toString());
		});

		viewModel.getUserInfo().observe(this, s -> tvInfo.setText(s));
	}

	public void load(View view) {
		viewModel.loadData();
		viewModel.loadUserName("小明 3");
	}
}
