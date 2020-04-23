package com.sts.csgits.service.impl;

import com.sts.csgits.dao.ManagerMapper;
import com.sts.csgits.dao.SchoolMapper;
import com.sts.csgits.dao.StudentMapper;
import com.sts.csgits.entity.Manager;
import com.sts.csgits.entity.School;
import com.sts.csgits.entity.Student;
import com.sts.csgits.inc.Const;
import com.sts.csgits.service.ManagerService;
import com.sts.csgits.service.RedisService;
import com.sts.csgits.service.SchoolService;
import com.sts.csgits.utils.Condition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/2/23 17:20
 */
@Service("schoolService")
@Slf4j
public class SchoolServiceImpl extends BaseServiceImpl<School, Integer> implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public List<School> selectByName(String schoolName){
        return schoolMapper.selectByName(schoolName);
    }

    @Override
    public School selectOne(){
        return schoolMapper.selectOne();
    }

    @Override
    public List<School> selectBySchool(School school){
        return schoolMapper.selectBySchool(school);
    }

    /**
     * 根据学生、老师人数更新学校中的信息
     */
    @Override
    public void updateSchoolNumMsg(){
        log.info("开始执行统计人数信息定时任务");
        try {
            List<School> schoolList = schoolMapper.selectAll();

            if (schoolList != null && schoolList.size() > 0){
                for (School school : schoolList){
                    Condition condition = Condition.newInstance();
                    condition.addCondition("schoolId", school.getId());
                    Integer teacherNum = managerMapper.selectNumByCondition(condition);
                    school.setTeacherNum(teacherNum);

                    Student student = new Student();
                    student.setSchoolId(school.getId());
                    Integer totalNum = studentMapper.countNum(student);
                    student.setGraduate(false);
                    Integer stuNum = studentMapper.countNum(student);
                    school.setSchoolNum(stuNum);
                    school.setTotalNum(totalNum + teacherNum);

                }
            }
        }catch (Exception e){
            log.error("SchoolServiceImpl updateSchoolNumMsg error {}", e);
        }
        log.info("统计人数信息定时任务执行结束");
    }

    public void resetData(){
        log.info("开始执行清除当天数据定时任务");
        try {
            redisService.set(Const.PEOPLE_NUM_NEW_ADD, 0L);
            redisService.set(Const.WRITE_RECORD_NUM_NEW_ADD, 0L);

            List<School> schoolList = schoolMapper.selectAll();
            if (schoolList != null && schoolList.size() > 0){
                for (School school : schoolList){
                    String key = String.format(Const.PEOPLE_SCHOOL_NUM_NEW_ADD, school.getId());
                    redisService.set(key, 0L);
                    key = String.format(Const.WRITE_RECORD_SCHOOL_NUM_NEW_ADD, school.getId());
                    redisService.set(key, 0L);
                }
            }
        }catch (Exception e){
            log.error("SchoolServiceImpl resetData error {}", e);
        }
        log.info("清除当天数据定时任务结束");
    }
}
