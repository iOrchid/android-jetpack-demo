package in.zhiwei.jetpack.livedata;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Author: zhiwei.
 * Date: 2018/11/5 0005,11:17.
 */
public class LiveUser {

    private String city;

    private MutableLiveData<String> name = new MediatorLiveData<>();
    private MutableLiveData<Integer> age = new MutableLiveData<>();

    public LiveUser(String city, String name, Integer age) {
        this.city = city;

        this.name.setValue(name);
        this.age.setValue(age);

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public MutableLiveData<Integer> getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age.setValue(age);
    }
}
