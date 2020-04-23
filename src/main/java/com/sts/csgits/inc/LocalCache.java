package com.sts.csgits.inc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sts.csgits.entity.School;
import com.sts.csgits.entity.Student;
import com.sts.csgits.service.RedisService;
import com.sts.csgits.service.SchoolService;
import com.sts.csgits.service.StudentService;
import com.sts.csgits.utils.MD5EncoderUtil;
import com.sts.csgits.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地緩存
 * @author ：gb
 * @date ：Created in 2020/3/29 12:30
 */
@Service
@Slf4j
public class LocalCache {

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private RedisService redisService;

    public static Map<Integer, School> schoolMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        log.info("LocalCache init start...");
        initSchool();
        initStudent();
        log.info("LocalCache init over...");
    }

    /**
     * 初始化学校信息
     */
    public void initSchool(){
        try {
            Map<Integer, School> schoolConcurrentHashMap = new ConcurrentHashMap<>();
            List<School> schoolList = schoolService.selectAll();
            for (School school : schoolList){
                schoolConcurrentHashMap.put(school.getId(), school);
            }
            schoolMap = schoolConcurrentHashMap;
        }catch (Exception e){
            log.info("LocalCache initSchool error {}", e);
        }
    }

    public School getSchool(Integer schoolId){
        try {
            School school = schoolMap.get(schoolId);
            if (school != null){
                return school;
            }else {
                school = schoolService.selectByPrimaryKey(schoolId);
                if (school == null){
                    school = new School();
                }
                schoolMap.put(schoolId, school);
                return school;
            }
        }catch (Exception e){
            log.info("LocalCache getSchool error {}", e);
            return null;
        }
    }

    /**
     * 初始化学生信息
     */
    public void initStudent(){
        try {
            List<School> schoolList = schoolService.selectAll();
            for (School school : schoolList){
                List<Student> students = studentService.selectOneSchoolStudent(school.getId());
                for (Student student : students){
                    String key = String.format(Const.STUDENT_SOLE, MD5EncoderUtil.encode(student.getNo() + student.getRealName()));
                    String studentStr = JSONObject.toJSONString(student);
                    redisService.set(key, studentStr);
                }
            }
        }catch (Exception e){
            log.error("initStudent error {}", e);
        }
    }

    public Student getStudentBySole(String sole){
        try {
            String key = String.format(Const.STUDENT_SOLE, sole);
            String studentStr = (String) redisService.get(key);
            if (StringUtils.isNotEmpty(studentStr)){
                Student student = JSON.parseObject(studentStr, Student.class);
                return student;
            }
        }catch (Exception e){
            log.error("getStudentBySole error {}", e);
        }
        return null;
    }

    /**
     * 对新增更新删除的学生信息重载
     * @param channel
     * @param object
     */
    public void reloadStudentToRedis(String channel, Object object) {
        Student student = JSON.parseObject(object.toString(), Student.class);
        String key = MD5EncoderUtil.encode(student.getNo() + student.getRealName());
        switch (channel){
            case Const.REDIS_CHANNEL_ADD_STUDENT:
            case Const.REDIS_CHANNEL_UPDATE_STUDENT:
                String studentStr = JSONObject.toJSONString(student);
                redisService.set(key, studentStr);
                break;
            case Const.REDIS_CHANNEL_DEL_STUDENT:
                redisService.del(key);
                break;
        }
    }

    /**
     * 接收消息，重载
     * @param channel
     * @param object
     */
    public void localLoad(String channel, Object object) {
        if (StringUtils.isEmpty(channel)) {
            return;
        }

        switch (channel){
            case Const.REDIS_CHANNEL_SCHOOL:
                initSchool();
                break;
            case Const.REDIS_CHANNEL_ADD_STUDENT:
            case Const.REDIS_CHANNEL_DEL_STUDENT:
            case Const.REDIS_CHANNEL_UPDATE_STUDENT:
                reloadStudentToRedis(channel, object);
                break;
            default:
                log.error("channel {}", channel);
        }
    }

}
