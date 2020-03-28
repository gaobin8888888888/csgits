package com.sts.csgits.controller;

import com.sts.csgits.entity.Recruit;
import com.sts.csgits.service.RecruitService;
import com.sts.csgits.utils.Condition;
import com.sts.csgits.utils.JSONResult;
import com.sts.csgits.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 企业招聘控制层
 * @author ：gb
 * @date ：Created in 2020/3/15 17:04
 */
@Slf4j
@Controller
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    /**
     * add企业招聘信息
     * @param request
     * @param recruitName
     * @param description
     * @param url
     * @return
     */
    @RequestMapping("/admin/recruit/add")
    public ModelAndView add(HttpServletRequest request, String recruitName, String description, String url){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/toAddRecruitPage");
        try {
            if (StringUtils.isEmpty(recruitName)){
                modelAndView.addObject("message", "企业名称不能为空");
                return modelAndView;
            }
            if (StringUtils.isEmpty(description)){
                modelAndView.addObject("message", "企业描述不能为空");
                return modelAndView;
            }

            Integer managerId = (Integer) request.getSession().getAttribute("id");
            Recruit recruit = new Recruit();
            recruit.setName(recruitName);
            recruit.setDescription(description);
            recruit.setUrl(url);
            recruit.setManagerId(managerId);

            int insert = recruitService.insert(recruit);
            if (insert > 0){
                modelAndView = new ModelAndView("redirect:/admin/recruit/selectAll");
                modelAndView.addObject("message", "添加成功");
                return modelAndView;
            }
        }catch (Exception e){
            log.info("RecruitController add企业招聘信息 error {}", e);
        }
        modelAndView.addObject("message", "添加失败，请重试");
        return modelAndView;
    }

    /**
     * 删除企业招聘信息
     * @param id
     * @return
     */
    @RequestMapping("/admin/recruit/delete/{id}")
    public @ResponseBody String delete(@PathVariable("id") Integer id){
        try {
            int delete = recruitService.deleteByPrimaryKey(id);
            if (delete > 0){
                return JSONResult.successInstance("删除成功");
            }
        }catch (Exception e){
            log.info("RecruitController delete企业招聘信息 error {}", e);
        }
        return JSONResult.errorInstance("删除失败，请稍后再试");
    }

    /**
     * 更新企业招聘信息
     * @param id
     * @param description
     * @return
     */
    @RequestMapping("/admin/recruit/update")
    public ModelAndView update(Integer id, String recruitName, String description, String url){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/recruit/selectAll");
        try {
            if (StringUtils.isEmpty(recruitName)){
                return modelAndView.addObject("message", "企业名称不可为空");
            }
            if (StringUtils.isEmpty(description)){
                return modelAndView.addObject("message", "企业描述描述不可为空");
            }

            Recruit recruit = new Recruit();
            recruit.setUrl(url);
            recruit.setName(recruitName);
            recruit.setDescription(description);
            recruit.setId(id);
            int update = recruitService.updateByPrimaryKey(recruit);
            if (update > 0){
                return modelAndView.addObject("message", "更新成功");
            }
        }catch (Exception e){
            log.info("RecruitController update企业招聘信息 error {}", e);
        }
        return modelAndView.addObject("message", "更新失败，请稍后再试");
    }

    @RequestMapping("/admin/recruit/updateStatus/{id}")
    public @ResponseBody String
    update(@PathVariable("id") Integer id, Integer status){
        try {
            Recruit recruit = new Recruit();
            recruit.setId(id);
            recruit.setStatus(status);
            int update = recruitService.updateByPrimaryKey(recruit);
            if (update > 0){
                return JSONResult.successInstance("更新成功");
            }
        }catch (Exception e){
            log.info("RecruitController update企业招聘状态信息 error {}", e);
        }
        return JSONResult.errorInstance("更新失败，请稍后再试");
    }

    /**
     * 查询管理员添加的所有企业招聘数据
     * @return
     */
    @RequestMapping("/admin/recruit/selectAll")
    public ModelAndView selectAll(HttpServletRequest request, String recruitName){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc7");
        List<Recruit> recruitList = null;
        try {
            Condition condition = Condition.newInstance();
            Integer managerId = (Integer) request.getSession().getAttribute("id");
            condition.addCondition("managerId", managerId);
            condition.addCondition("recruitName", recruitName);
            recruitList = recruitService.selectByCondition(condition);
            modelAndView.addObject("recruitList", recruitList);
            modelAndView.addObject("recruitName", recruitName);
        }catch (Exception e){
            log.info("RecruitController selectAll企业招聘信息 error {}", e);
        }
        return modelAndView;
    }

    /**
     * 查询管理员添加的所有企业招聘数据
     * @return
     */
    @RequestMapping("/admin/recruit/selectByName")
    public ModelAndView selectByName(HttpServletRequest request, String recruitName){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc7");
        List<Recruit> recruitList = null;
        try {
            Condition condition = Condition.newInstance();
            Integer managerId = (Integer) request.getSession().getAttribute("id");
            condition.addCondition("managerId", managerId);
            condition.addCondition("recruitName", recruitName);
            recruitList = recruitService.selectByCondition(condition);
            modelAndView.addObject("recruitList", recruitList);
        }catch (Exception e){
            log.info("RecruitController selectByName企业招聘信息 error {}", e);
        }
        return modelAndView;
    }

    /**
     * 学生查询所有企业招聘数据
     * @return
     */
    @RequestMapping("/student/recruit/selectAll")
    public ModelAndView studentSelectAll(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc7");
        List<Recruit> recruitList = null;
        try {
            Condition condition = Condition.newInstance();
            condition.addCondition("status", 0);
            recruitList = recruitService.selectByCondition(condition);
            modelAndView.addObject("recruitList", recruitList);
        }catch (Exception e){
            log.info("RecruitController studentSelectAll企业招聘信息 error {}", e);
        }
        return modelAndView;
    }
}
