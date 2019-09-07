package org.zhiwei.jetpack.aac.persistent;

import org.zhiwei.jetpack.aac.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhiwei.
 * Date: 2018/11/6 0006,20:07.
 */
public class VirtualLocalData {
	private static VirtualLocalData instance = new VirtualLocalData();

	private VirtualLocalData() {
	}

	public static VirtualLocalData getInstance() {
		return instance;
	}

	public UserModel getUser(String name) {
		UserModel usr = null;
		for (UserModel userModel : getUserList()) {
			if (userModel.getName().equals(name)) {
				usr = userModel;
			}
		}
		return usr;
	}

	public List<UserModel> getUserList() {
		List<UserModel> users = new ArrayList<>();
		UserModel user;
		for (int i = 0; i < 20; i++) {
			user = new UserModel();
			user.setAge(20 + i);
			user.setName("小明 " + i);
			user.setCity("上海：" + i);
			users.add(user);
		}
		return users;
	}
}
