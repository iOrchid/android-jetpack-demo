package in.zhiwei.jetpack.paging.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * student的对象类，在room数据库中的表
 * Author: zhiwei.
 * Date: 2018/11/6 0006,10:44.
 */
@Entity()//使用java写Room的entity类，似乎必须要有getter和setter
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo//一般情况也可以不写，默认是加入数据表的，除非注释ignore，才会忽略掉
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
