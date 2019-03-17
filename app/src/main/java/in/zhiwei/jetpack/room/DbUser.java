package in.zhiwei.jetpack.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Room数据库的entity对象类
 * Author: zhiwei.
 * Date: 2018/11/5 0005,15:05.
 */
@Entity(tableName = "db_user")
//room数据库的注解标记,数据表entity  (tableName="db_user",indices = {@Index(value = "uname",unique = true)})
public class DbUser {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "uname")
    private String name;

    @ColumnInfo
    private String city;

    @ColumnInfo
    private int age;
    //如此数据表中不会有@Ignore标记的属性字段
    @Ignore
    private boolean single;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    @Override
    public String toString() {
        return "DbUser{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                ", single=" + single +
                '}';
    }
}
