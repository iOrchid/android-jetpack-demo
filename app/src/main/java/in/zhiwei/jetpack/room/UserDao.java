package in.zhiwei.jetpack.room;

import androidx.room.*;

import java.util.List;

/**
 * Room数据框架的Dao层,对数据表的CRUD
 * Author: zhiwei.
 * Date: 2018/11/5 0005,15:11.
 */
@Dao//room注解
public interface UserDao {

    @Query(value = "select * from db_user")
    List<DbUser> getAll();//查询所有数据


    @Query("SELECT * FROM db_user WHERE uid IN (:userIds)")
    List<DbUser> loadAllByIds(int[] userIds);//根据uid查询

    @Query("SELECT * FROM db_user WHERE uname LIKE :name AND "
            + "age LIKE :age LIMIT 1")
    DbUser findByName(String name, int age);

    @Query("select * from db_user where uid like :id")
    DbUser getUserById(int id);

    @Insert
    void insertAll(DbUser... users);//支持可变参数

    @Delete
    void delete(DbUser user);//删除指定的user

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(DbUser user);//更新，若出现冲突，则使用替换策略，还有其他策略可选择

}
