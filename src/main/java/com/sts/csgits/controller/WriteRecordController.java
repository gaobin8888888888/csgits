package com.sts.csgits.controller;

import com.sts.csgits.entity.CreateRecord;
import com.sts.csgits.entity.Student;
import com.sts.csgits.entity.WriteRecord;
import com.sts.csgits.inc.Const;
import com.sts.csgits.service.CreateRecordService;
import com.sts.csgits.service.StudentService;
import com.sts.csgits.service.WriteRecordService;
import com.sts.csgits.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 管理员创建记录控制层
 * @author ：gb
 * @date ：Created in 2020/3/21 19:44
 */
@Controller
@RequestMapping("/admin/writeRecord")
@Slf4j
public class WriteRecordController {

    @Autowired
    private WriteRecordService writeRecordService;

    @Autowired
    private PickUtil pickUtil;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CreateRecordService createRecordService;

    /**
     * 添加记录
     * @param request
     * @param related
     * @param type
     * @param degree
     * @param place
     * @param home
     * @param salary
     * @param comment
     * @return
     */
    @RequestMapping("/add")
    public @ResponseBody String
    add(HttpServletRequest request, Integer related, Integer type, Integer degree, String place, Integer home, Integer salary, String comment){
        try {
            Student student = (Student) request.getSession().getAttribute("student");
            if (related == null || type == null || degree == null || StringUtils.isEmpty(place) || home == null || salary == null){
                return JSONResult.errorInstance("除备注以外，其他项都不能为空");
            }

            Condition condition = Condition.newInstance();
            condition.addMapCondition("nowTime", DateUtil.format(new Date(), DateUtil.FMT_DATE_YYYY_MM_DD_HH_mm_ss));
            CreateRecord createRecord = createRecordService.selectByCondition(condition);

            WriteRecord writeRecord = new WriteRecord();
            writeRecord.setCreateRecordId(createRecord.getId());
            writeRecord.setSole(student.getSole());
            writeRecord.setRelated(related);
            writeRecord.setType(type);
            writeRecord.setDegree(degree);
            writeRecord.setPlace(place);
            writeRecord.setHome(home);
            writeRecord.setSalary(salary);
            writeRecord.setComment(comment);
            int insert = writeRecordService.insert(writeRecord);
            if (insert > 0){
                Integer schoolId = (Integer) request.getSession().getAttribute("schoolId");
                pickUtil.addWriteRecordNum(schoolId, Const.ADD_NUM);

                student.setCredits(student.getCredits() + Const.ADD_CREDITS_WRITE_RECORD);
                studentService.updateByPrimaryKey(student);

                return JSONResult.successInstance("添加成功");
            }
        }catch (Exception e){
            log.info("WriteRecordController add记录 error {}", e);
        }
        return JSONResult.errorInstance("添加失败，请稍后再试");
    }

    @RequestMapping("/selectAll")
    public ModelAndView selectAll(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc6");
        List<WriteRecord> writeRecordList = null;
        try {
            writeRecordList = writeRecordService.selectAll();
            modelAndView.addObject("writeRecordList", writeRecordList);
        }catch (Exception e){
            log.info("WriteRecordController selectAll记录信息 error {}", e);
        }
        return modelAndView;
    }

}
