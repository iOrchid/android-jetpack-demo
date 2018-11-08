package in.zhiwei.jetpack.navigation;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import in.zhiwei.jetpack.R;

/**
 * navigation框架的演示
 * Author: zhiwei.
 * Date: 2018/11/5 0005,17:49.
 */
public class NaviActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    /**
     * navigation相关的操作
     *
     * @return 是否向上返回 栈
     */
    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.fg_main_navi).navigateUp();
    }
}
