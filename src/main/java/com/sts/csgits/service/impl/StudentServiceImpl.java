package com.sts.csgits.service.impl;

import com.sts.csgits.dao.StudentMapper;
import com.sts.csgits.entity.Student;
import com.sts.csgits.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Service("studentService")
public class StudentServiceImpl extends BaseServiceImpl<Student, Integer> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public int delete(Integer schoolId, Integer id) {
        return studentMapper.delete(schoolId, id);
    }

    @Override
    public List<Student> selectOneSchoolStudent(Integer schoolId) {
        return studentMapper.selectOneSchoolStudent(schoolId);
    }

    @Override
    public Student selectOneStudentBySole(Integer schoolId, String sole) {
        return studentMapper.selectOneStudentBySole(schoolId, sole);
    }

    @Override
    public List<Student> selectByStudent(Student student) {
        return studentMapper.selectByStudent(student);
    }
}
