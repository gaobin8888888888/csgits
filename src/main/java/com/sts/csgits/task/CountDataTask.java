package com.sts.csgits.task;

import com.sts.csgits.entity.Student;
import com.sts.csgits.service.SchoolService;
import com.sts.csgits.service.StudentService;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时统计数据定时任务
 * @author ：gb
 * @date ：Created in 2020/4/4 19:06
 */
@Component
public class CountDataTask {

    @Autowired
    private StudentService studentService;
    @Autowired
    private SchoolService schoolService;

    /**
     * 每天01:00:00统计学校中的人数信息
     */
    @Scheduled(cron = "0 0 1 * * ?")
    @SchedulerLock(name = "CountDataTask.countSchoolData", lockAtLeastFor = 10 * 60 * 1000)
    public void countSchoolData(){
        schoolService.updateSchoolNumMsg();
    }
}
