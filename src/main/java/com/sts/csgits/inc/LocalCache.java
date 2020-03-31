package com.sts.csgits.inc;

import com.sts.csgits.entity.School;
import com.sts.csgits.service.SchoolService;
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

    public static Map<Integer, School> schoolMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        log.info("LocalCache init start...");
        initSchool();
        log.info("LocalCache init over...");
    }

    /**
     * 初始化学校信息
     */
    public void initSchool(){
        try {
            schoolMap.clear();
            List<School> schoolList = schoolService.selectAll();
            for (School school : schoolList){
                schoolMap.put(school.getId(), school);
            }
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


}
