package org.zhiwei.jetpack.databinding.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import org.zhiwei.jetpack.BR;

/**
 * Author: zhiwei.
 * Date: 2018/11/2 0002,15:03.
 * 可响应数据变化的User类,databinding中，需要继承BaseObservable 并且在需要监测的数据上，@Bindable，set中notify,使用BR关联id 也是一个R文件
 */
public class ObservableUser extends BaseObservable {

    //如果用这类方式，则不需要继承BaseObservable，也不需要@bindable以及notify
    public ObservableBoolean isChecked = new ObservableBoolean();//databinding中的可变属性
    public ObservableField<String> title = new ObservableField<>();

    int age;//年龄
    String name;//名称
    boolean single;//是否单身

    public ObservableUser() {
    }

    public ObservableUser(int age, String name, boolean single) {
        this.age = age;
        this.name = name;
        this.single = single;
    }

    //监测变化
    @Bindable
    public int getAge() {
        return age;
    }

    //notify变化
    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
        notifyPropertyChanged(BR.single);
    }
}
