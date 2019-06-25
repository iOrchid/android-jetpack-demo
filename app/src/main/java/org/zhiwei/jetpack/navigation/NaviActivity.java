package org.zhiwei.jetpack.navigation;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import org.zhiwei.jetpack.R;

/**
 * todo 目前已知使用Navigation框架，配置navGraph需要AndroidStudio3.2以上版本，在settings--experimental--enable navigation editor
 * todo 就能使用类似于写layout的形式，看navGraph的配置了。而且配置的fragment每次触发都是创建新的对象实例（返回不会新建），这个google官方已经知晓，切不修改，认为这个是符合新的设计理念规范的。
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
