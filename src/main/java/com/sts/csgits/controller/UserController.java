package com.sts.csgits.controller;

import com.sts.csgits.entity.*;
import com.sts.csgits.service.*;
import com.sts.csgits.utils.Condition;
import com.sts.csgits.utils.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/4/6 15:56
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/student/toIndex")
    public ModelAndView toStudentIndex(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/student/index");
        try {
            Notice notice = new Notice();
            List<Notice> noticeList = noticeService.selectByNotice(notice);
            modelAndView.addObject("noticeAllList", noticeList);
            if (noticeList != null && noticeList.size() > 5){
                noticeList = noticeList.subList(0, 5);
            }
            modelAndView.addObject("noticeList", noticeList);

            Integer schoolId = (Integer) request.getSession().getAttribute("schoolId");
            Manager manager = new Manager();
            manager.setFine(true);
            manager.setSchoolId(schoolId);
            List<Manager> teacherList = managerService.selectByManager(manager);
            modelAndView.addObject("teacherAllList", teacherList);
            if (teacherList != null && teacherList.size() > 5){
                teacherList = teacherList.subList(0, 5);
            }
            modelAndView.addObject("teacherList", teacherList);

            Student student = new Student();
            student.setSchoolId(schoolId);
            student.setFine(true);
            List<Student> studentList = studentService.selectByStudent(student);
            modelAndView.addObject("studentAllList", studentList);
            if (studentList != null && studentList.size() > 5){
                studentList = studentList.subList(0, 5);
            }
            modelAndView.addObject("studentList", studentList);


            Condition condition = Condition.newInstance();
            condition.addCondition("status", 0);
            List<Recruit> recruitList = recruitService.selectByCondition(condition);
            modelAndView.addObject("recruitAllList", recruitList);
            if (recruitList != null && recruitList.size() > 5){
                recruitList = recruitList.subList(0, 5);
            }
            modelAndView.addObject("recruitList", recruitList);

            Goods goods = new Goods();
            List<Goods> goodsList = goodsService.selectByGoods(goods);
            modelAndView.addObject("goodsAllList", goodsList);
            if (goodsList != null && goodsList.size() > 5){
                goodsList = goodsList.subList(0, 5);
            }
            modelAndView.addObject("goodsList", goodsList);
        }catch (Exception e){
            log.error("UserController toStudentIndex error {}", e);
        }
        return modelAndView;
    }

    @RequestMapping("/user/notice/toMoreNotice")
    public @ResponseBody
    String toMoreNotice(){
        try {
            Notice notice = new Notice();
            List<Notice> noticeList = noticeService.selectByNotice(notice);
            return JSONResult.successInstance("查询成功", noticeList);
        }catch (Exception e){
            log.error("toMoreNotice error {}", e);
        }
        return JSONResult.errorInstance("查询失败");
    }

    @RequestMapping("/student/test")
    public ModelAndView test(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/student/test");
        return modelAndView;
    }
}
