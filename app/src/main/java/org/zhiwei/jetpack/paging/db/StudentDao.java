package org.zhiwei.jetpack.paging.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Room数据库的标准格式，student的Dao操作层 Data Access Object
 * Author: zhiwei.
 * Date: 2018/11/6 0006,10:52.
 */
@Dao
public interface StudentDao {
    /**
     * 这里就是使用了paging的DataSource，用于便捷加载分页数据
     *
     * @return
     */
    @Query("SELECT * FROM Student ORDER BY name COLLATE NOCASE ASC")
    DataSource.Factory<Integer, Student> getAllStudent();

    @Insert
    void insert(List<Student> students);

    @Insert
    void insert(Student student);
}
