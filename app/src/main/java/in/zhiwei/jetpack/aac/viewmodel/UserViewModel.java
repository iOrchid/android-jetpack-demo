package in.zhiwei.jetpack.aac.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import in.zhiwei.jetpack.aac.model.UserModel;
import in.zhiwei.jetpack.aac.persistent.DataRepository;

import java.util.List;

/**
 * Author: zhiwei.
 * Date: 2018/11/6 0006,19:20.
 */
public class UserViewModel extends AndroidViewModel {

    private DataRepository repository;
    MutableLiveData<List<UserModel>> userList = new MutableLiveData<>();
    MediatorLiveData<String> userInfo = new MediatorLiveData<>();

    /**
     * 需要调用基类的super（application）
     *
     * @param application
     */
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance();
    }

    public void loadData() {
        userList.setValue(repository.getLocalData());
    }

    public void loadUserName(String name) {
        userInfo.setValue(repository.getUserByName(name).toString());
    }

    public LiveData<List<UserModel>> getUserList() {
        return userList;
    }

    public LiveData<String> getUserInfo() {
        return userInfo;
    }
}
