package com.sts.csgits.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sts.csgits.entity.Student;
import com.sts.csgits.inc.Const;
import com.sts.csgits.service.RedisService;
import com.sts.csgits.service.StudentService;
import com.sts.csgits.utils.JSONResult;
import com.sts.csgits.utils.MD5EncoderUtil;
import com.sts.csgits.utils.PickUtil;
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
 * @author ：gb
 * @date ：Created in 2020/3/28 17:20
 */
@Controller
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PickUtil pickUtil;

    @RequestMapping("/admin/student/add")
    public ModelAndView add(String no, String realName, Integer schoolId, String college, String classNo){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/toAddStudentPage");
        try {
            if (StringUtils.isEmpty(no)){
                modelAndView.addObject("message", "学生学号不能为空");
                return modelAndView;
            }
            if (StringUtils.isEmpty(realName)){
                modelAndView.addObject("message", "学生真实姓名不能为空");
                return modelAndView;
            }
            if (StringUtils.isEmpty(college)){
                modelAndView.addObject("message", "学生所属学院不能为空");
                return modelAndView;
            }
            if (StringUtils.isEmpty(classNo)){
                modelAndView.addObject("message", "学生所在班级不能为空");
                return modelAndView;
            }
            Student student = new Student();
            student.setNo(no);
            student.setSchoolId(schoolId);
            student.setCollege(college);
            student.setRealName(realName);
            student.setClassNo(classNo);
            student.setPassword(MD5EncoderUtil.encode(Const.DEFAULT_PASSWORD));
            student.setGrade(Integer.parseInt(no.substring(0, 2)));
            student.setSole(MD5EncoderUtil.encode(no + realName));
            student.setImagePath(Const.DEFAULT_IMAGE_PATH);
            int insert = studentService.insert(student);
            if (insert > 0){
                pickUtil.addOrDescPeopleNum(schoolId, Const.ADD_NUM);

                modelAndView = new ModelAndView("redirect:/admin/student/selectAll");
                modelAndView.addObject("message", "添加成功");
                return modelAndView;
            }
        }catch (Exception e){
            log.info("StudentController add error{}", e);
        }
        return modelAndView;
    }

    @RequestMapping("/admin/student/selectAll")
    public ModelAndView selectAll(String studentName, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc8");
        try {
            Student student = new Student();
            student.setRealName(studentName);
            Integer schoolId = (Integer) request.getSession().getAttribute("schoolId");
            student.setSchoolId(schoolId);
            List<Student> studentList = studentService.selectByStudent(student);
            modelAndView.addObject("studentList", studentList);
            modelAndView.addObject("studentName", studentName);
        }catch (Exception e){
            log.info("StudentController selectAll error {}", e);
        }
        return modelAndView;
    }

    @RequestMapping("/admin/student/update")
    public ModelAndView update(String classNo, Integer grade, Integer id, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/student/selectAll");
        try {
            Integer schoolId = (Integer) request.getSession().getAttribute("schoolId");
            Student student = new Student();
            student.setClassNo(classNo);
            student.setGrade(grade);
            student.setSchoolId(schoolId);
            student.setId(id);
            int update = studentService.updateByPrimaryKey(student);
            if (update > 0){
                return modelAndView.addObject("message", "更新成功");
            }
        }catch (Exception e){
            log.info("StudentController update error{}", e);
        }
        return modelAndView.addObject("message", "更新失败");
    }

    @RequestMapping("/admin/student/updateFine")
    public @ResponseBody String
    updateFine(Integer id, Boolean fine, HttpServletRequest request){
        try {
            Integer schoolId = (Integer) request.getSession().getAttribute("schoolId");
            Student student = new Student();
            student.setSchoolId(schoolId);
            student.setId(id);
            student.setFine(fine);
            int update = studentService.updateByPrimaryKey(student);
            if (update > 0){
                return JSONResult.successInstance("更新学生信息成功");
            }
        }catch (Exception e){
            log.info("StudentController updateFine error{}", e);
        }
        return JSONResult.errorInstance("更新失败");
    }

    @RequestMapping("/admin/student/updateGraduate")
    public @ResponseBody String
    updateGraduate(Integer id, Boolean graduate, HttpServletRequest request){
        try {
            Integer schoolId = (Integer) request.getSession().getAttribute("schoolId");
            Student student = new Student();
            student.setSchoolId(schoolId);
            student.setId(id);
            student.setGraduate(graduate);
            int update = studentService.updateByPrimaryKey(student);
            if (update > 0){
                return JSONResult.successInstance("更新学生信息成功");
            }
        }catch (Exception e){
            log.info("StudentController updateGraduate error{}", e);
        }
        return JSONResult.errorInstance("更新失败");
    }

    @RequestMapping("/admin/student/delete/{id}")
    public @ResponseBody String
    delete(@PathVariable("id") Integer id, HttpServletRequest request){
        try {
            Integer schoolId = (Integer) request.getSession().getAttribute("schoolId");
            int update = studentService.delete(schoolId, id);
            if (update > 0){
                pickUtil.addOrDescPeopleNum(schoolId, Const.DESC_NUM);

                return JSONResult.successInstance("删除学生信息成功");
            }
        }catch (Exception e){
            log.info("StudentController delete error{}", e);
        }
        return JSONResult.errorInstance("删除失败");
    }

    @RequestMapping("/admin/student/updateAchievement")
    @ResponseBody
    public String updateAchievement(Integer id, Integer schoolId, String params, HttpServletRequest request){
        try {
            Student student = new Student();
            student.setSchoolId(schoolId);
            student.setId(id);
            JSONObject newParam = null;
            if (StringUtils.isNotEmpty(params)){
                JSONArray jsonArray = JSON.parseArray(params);
                newParam = new JSONObject();
                for (int i = 0; i < jsonArray.size(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    newParam.put(jsonObject.getString("key"), jsonObject.getString("value"));
                }
            }
            student.setAchievement(newParam.toJSONString());
            int update = studentService.updateByPrimaryKey(student);
            if (update > 0){
                return JSONResult.successInstance("更新成功");
            }
        }catch (Exception e){
            log.info("StudentController updateAchievement error{}", e);
        }
        return JSONResult.errorInstance("更新失败");
    }

}
