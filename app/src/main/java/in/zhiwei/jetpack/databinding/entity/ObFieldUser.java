package in.zhiwei.jetpack.databinding.entity;

import androidx.databinding.ObservableField;

/**
 * Author: zhiwei.
 * Date: 2018/11/2 0002,16:08.
 */
public class ObFieldUser {
    public ObservableField<String> name = new ObservableField<>();//需要public，否则无法反射

    public ObFieldUser(String name) {
        this.name.set(name);
    }
}
