package org.zhiwei.jetpack.aac.persistent;

import org.zhiwei.jetpack.aac.model.UserModel;

import java.util.List;

/**
 * 数据代理类
 * Author: zhiwei.
 * Date: 2018/11/6 0006,19:30.
 */
public class DataRepository {

	private static DataRepository instance = new DataRepository();
	private VirtualLocalData localData;

	private DataRepository() {
		localData = VirtualLocalData.getInstance();
	}

	public static DataRepository getInstance() {
		return instance;
	}

	public List<UserModel> getLocalData() {
		return localData.getUserList();
	}

	public UserModel getUserByName(String name) {
		return localData.getUser(name);
	}
}
