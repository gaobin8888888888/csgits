package com.sts.csgits.dao;

import com.sts.csgits.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 16:50
 */
public interface StudentMapper extends BaseMapper<Student, Integer> {

    /**
     * 判断学生表是否已存在
     * @param tableName
     * @return
     */
    int isExistsTable(String tableName);

    /**
     * 执行建表sql
     * @param sql
     */
    void executeSql(String sql);

    /**
     * 根据学校id与学生id删除
     * @param schoolId
     * @param id
     * @return
     */
    int delete(@Param("schoolId") Integer schoolId, @Param("id") Integer id);

    /**
     * 根据学校id查询该学校学生的信息
     * @param schoolId
     * @return
     */
    List<Student> selectOneSchoolStudent(@Param("schoolId") Integer schoolId);

    /**
     * 根据学校id与sole查询学生信息
     */
    Student selectOneStudentBySole(@Param("schoolId") Integer schoolId, @Param("sole") String sole);

    /**
     * 根据学生信息查询
     * @param student
     * @return
     */
    List<Student> selectByStudent(Student student);
}
