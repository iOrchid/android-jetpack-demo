package org.zhiwei.jetpack.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.zhiwei.jetpack.mvvm.model.UserModel;
import org.zhiwei.jetpack.mvvm.persistent.DataRepository;

import java.util.List;

/**
 * Author: zhiwei.
 * Date: 2018/11/6 0006,19:20.
 */
public class UserViewModel extends ViewModel {

	MutableLiveData<List<UserModel>> userList = new MutableLiveData<>();
	MediatorLiveData<String> userInfo = new MediatorLiveData<>();
	private DataRepository repository;

	public UserViewModel() {
		super();
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
