package com.sts.csgits.controller;

import com.sts.csgits.entity.WriteRecord;
import com.sts.csgits.inc.Const;
import com.sts.csgits.service.WriteRecordService;
import com.sts.csgits.utils.PickUtil;
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
@RequestMapping("/admin/writeRecord")
@Slf4j
public class WriteRecordController {

    @Autowired
    private WriteRecordService writeRecordService;

    @Autowired
    private PickUtil pickUtil;

    /**
     * 添加记录
     * @param sole
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
    public ModelAndView add(HttpServletRequest request, String sole, Integer related, Integer type, Integer degree, String place, Integer home, Double salary, String comment){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/toAddCreateRecordPage");
        try {
            if (related == null || type == null || degree == null || StringUtils.isEmpty(place) || home == null || salary == null){
                modelAndView.addObject("message", "除备注以外，其他项都不能为空");
                return modelAndView;
            }

            WriteRecord writeRecord = new WriteRecord();
            writeRecord.setSole(sole);
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

                modelAndView = new ModelAndView("redirect:/admin/writeRecord/selectAll");
                modelAndView.addObject("message", "添加成功");
                return modelAndView;
            }
        }catch (Exception e){
            log.info("WriteRecordController add记录 error {}", e);
        }
        modelAndView.addObject("message", "添加失败，请稍后再试");
        return modelAndView;
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
