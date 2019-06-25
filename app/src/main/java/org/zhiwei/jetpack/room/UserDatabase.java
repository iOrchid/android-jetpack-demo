package org.zhiwei.jetpack.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Room框架的database对象类,应该使用单例模式
 * Author: zhiwei.
 * Date: 2018/11/5 0005,15:19.
 */
@Database(entities = {DbUser.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public static final String DB_NAME = "user.db";
    private static UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, UserDatabase.class, DB_NAME)
                    .allowMainThreadQueries()//默认room不允许在主线程操作数据库，这里设置允许
                    .build();
        }
        return instance;
    }

    public abstract UserDao getUserDao();

}
