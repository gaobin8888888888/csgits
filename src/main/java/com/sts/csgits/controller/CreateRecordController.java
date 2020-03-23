package com.sts.csgits.controller;

import com.sts.csgits.entity.CreateRecord;
import com.sts.csgits.service.CreateRecordService;
import com.sts.csgits.service.CreateTableService;
import com.sts.csgits.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 管理员创建记录控制层
 * @author ：gb
 * @date ：Created in 2020/3/21 19:44
 */
@Controller
@RequestMapping("/admin/createRecord")
@Slf4j
public class CreateRecordController {

    @Autowired
    private CreateRecordService createRecordService;

    @Autowired
    private CreateTableService createTableService;

    /**
     * 添加记录表
     * @param request
     * @param createRecordName
     * @param description
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request, String createRecordName, String description, String startTime, String endTime){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/toAddCreateRecordPage");
        try {
            Integer managerId = (Integer) request.getSession().getAttribute("id");
            if (StringUtils.isEmpty(createRecordName)){
                modelAndView.addObject("message", "创建标题不能为空");
                return modelAndView;
            }

            CreateRecord createRecord = new CreateRecord();
            createRecord.setManagerId(managerId);
            createRecord.setName(createRecordName);
            createRecord.setDescription(description);
            createRecord.setStartTime(startTime);
            createRecord.setEndTime(endTime);
            int insert = createRecordService.insert(createRecord);
            if (insert > 0){
                createRecord = createRecordService.selectOne();
                createTableService.createWriteRecordTable(createRecord.getId());
                modelAndView = new ModelAndView("redirect:/admin/createRecord/selectAll");
                modelAndView.addObject("message", "添加成功");
                return modelAndView;
            }
        }catch (Exception e){
            log.info("CreateRecordController add记录表 error {}", e);
        }
        modelAndView.addObject("message", "添加失败，请稍后再试");
        return modelAndView;
    }

    @RequestMapping("/selectAll")
    public ModelAndView selectAll(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc7");
        List<CreateRecord> createRecordList = null;
        try {
            createRecordList = createRecordService.selectAll();
            modelAndView.addObject("createRecordList", createRecordList);
        }catch (Exception e){
            log.info("CreateRecordController selectAll记录信息 error {}", e);
        }
        return modelAndView;
    }

}
