package in.zhiwei.jetpack.databinding;

import android.os.Bundle;
import android.util.ArrayMap;
import android.widget.CompoundButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import in.zhiwei.jetpack.R;
import in.zhiwei.jetpack.databinding.adapter.LvAdapter;
import in.zhiwei.jetpack.databinding.adapter.RvAdapter;
import in.zhiwei.jetpack.databinding.entity.CommonUser;
import in.zhiwei.jetpack.databinding.entity.ObFieldUser;
import in.zhiwei.jetpack.databinding.entity.ObservableUser;
import in.zhiwei.jetpack.databinding.tool.Presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: zhiwei.
 * Date: 2018/11/2 0002,11:31.
 */
public class DataBindingActivity extends AppCompatActivity {

    NamedDB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_databinding);//使用databindingutils来代替原有的setContentView
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);

        binding.mTitle = "Title";
        binding.mNumber = 22;
        binding.mIsAdult = true;
        //list
        CommonUser user;
        Map<Integer, CommonUser> map = new ArrayMap<>();
        List<CommonUser> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            user = new CommonUser(20 + i, "ListName " + i, i % 2 == 0);
            list.add(user);
            map.put(i, user);
        }
        binding.setUserList(list);
        //include，响应式变化
        final ObservableUser observableUser = new ObservableUser(18, "小明", true);
        binding.mObusr = observableUser;
        binding.cbShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String name = isChecked ? "checked 小明" : "unChecked 小明明";
                observableUser.setName(name);//设置名称，则会UI变化
            }
        });
        //envent
        binding.mPresenter = new Presenter();
        //
        ObFieldUser ofU = new ObFieldUser("Of User 小明");
        binding.mOfuser = ofU;

        binding.mUser = new CommonUser(22, "张三", true);//设置user，也可以set

        //LV adapter
        LvAdapter lvAdapter = new LvAdapter();
        binding.setLvadapter(lvAdapter);
        //RV
        RvAdapter rvAdapter = new RvAdapter();
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.setRvadapter(rvAdapter);
    }


}
