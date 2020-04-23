package com.sts.csgits.controller;

import com.sts.csgits.entity.*;
import com.sts.csgits.inc.Const;
import com.sts.csgits.service.*;
import com.sts.csgits.utils.Condition;
import com.sts.csgits.utils.JSONResult;
import com.sts.csgits.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
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

    @Autowired
    private CreateRecordService createRecordService;

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

            condition = Condition.newInstance();
            condition.addCondition("nowTime", new Date());
            CreateRecord createRecord = createRecordService.selectByCondition(condition);
            modelAndView.addObject("createRecord", createRecord);

            student = (Student) request.getSession().getAttribute("student");
            modelAndView.addObject("student", student);
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

    @RequestMapping("/student/updateStudentMsg")
    public @ResponseBody String updateStudentMsg(HttpServletRequest request, MultipartFile image, String tel, String email, String description){
        Student student = (Student) request.getSession().getAttribute("student");
        try {
            if (StringUtils.isEmpty(tel) || StringUtils.isEmpty(email)){
                return JSONResult.errorInstance("联系方式与邮箱不可为空，请重试");
            }
            student.setTel(tel);
            student.setEmail(email);
            student.setDescription(description);

            if (image != null){
                //获取文件后缀
                String filenameExt = (image.getOriginalFilename().toString()).substring(image.getOriginalFilename().toString().lastIndexOf(".") + 1);
                if (!Const.IMAGE_KINDS.contains(filenameExt)){
                    return JSONResult.errorInstance("图片格式有误，请重试");
                }
                String imageName= StringUtils.generateUniqueId() + "." + filenameExt;
                File file = new File(imageName);
                image.transferTo(file);
                student.setImagePath(Const.IMAGE_VIRTUAL_PATH + imageName);
            }

            int update = studentService.updateByPrimaryKey(student);
            if (update > 0){
                return JSONResult.successInstance("更新成功");
            }
        }catch (Exception e){
            log.error("updateStudentMsg error {}", e);
        }
        return JSONResult.errorInstance("更新失败，请重试");
    }
}
