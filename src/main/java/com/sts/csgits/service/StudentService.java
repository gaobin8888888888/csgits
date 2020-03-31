package com.sts.csgits.service;

import com.sts.csgits.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/10 17:10
 */
public interface StudentService extends BaseService<Student, Integer> {

    /**
     * 根据学校id与学生id删除
     * @param schoolId
     * @param id
     * @return
     */
    int delete(Integer schoolId, Integer id);

    /**
     * 根据学校id查询该学校学生的信息
     * @param schoolId
     * @return
     */
    List<Student> selectOneSchoolStudent(Integer schoolId);

    /**
     * 根据学校id与sole查询学生信息
     */
    Student selectOneStudentBySole(Integer schoolId, String sole);

    /**
     * 根据学生信息查询
     * @param student
     * @return
     */
    List<Student> selectByStudent(Student student);

}
