package com.sts.csgits.task;

import com.sts.csgits.service.SchoolService;
import com.sts.csgits.service.WriteRecordService;
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
    private SchoolService schoolService;

    @Autowired
    private WriteRecordService writeRecordService;

    /**
     * 每天01:00:00统计学校中的人数信息
     */
    @Scheduled(cron = "0 */1 * * ?")
    @SchedulerLock(name = "CountDataTask.countSchoolData", lockAtLeastFor = 10 * 60 * 1000)
    public void countSchoolData(){
        schoolService.updateSchoolNumMsg();
    }

    /**
     * 每天00:00:00清零当天统计的信息
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @SchedulerLock(name = "CountDataTask.resetData", lockAtLeastFor = 10 * 60 * 1000)
    public void resetData(){
        schoolService.resetData();
    }

    /**
     * 每隔10分钟统计学生填写的信息
     */
    @Scheduled(cron = "0 */10 * * * ?")
    @SchedulerLock(name = "CountDataTask.countWriteRecordData", lockAtLeastFor = 10 * 60 * 1000)
    public void countWriteRecordData(){
        writeRecordService.countWriteRecordData();
    }
}
