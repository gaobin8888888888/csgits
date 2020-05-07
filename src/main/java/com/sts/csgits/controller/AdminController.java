package com.sts.csgits.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sts.csgits.entity.*;
import com.sts.csgits.inc.Const;
import com.sts.csgits.service.*;
import com.sts.csgits.utils.Condition;
import com.sts.csgits.utils.MD5EncoderUtil;
import com.sts.csgits.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * 管理员页面控制层
 * @author ：gb
 * @date ：Created in 2020/2/8 14:04
 */
@Slf4j
@Controller
public class AdminController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private WriteRecordDataService writeRecordDataService;

    @Autowired
    private CreateRecordService createRecordService;

    @RequestMapping("/admin/toAdminIndex")
    public ModelAndView toIndex(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/admin/index");
        Manager manager = (Manager) request.getSession().getAttribute("manager");
        int totalPeopleNum;
        int newAddPeopleNum;
        int totalWriteRecordNum;
        int newAddWriteRecordNum;
        Condition condition = Condition.newInstance();
        CreateRecord createRecord = createRecordService.selectByCondition(condition);
        condition.addMapCondition("createRecordId", createRecord.getId());
        List<WriteRecordData> writeRecordDataList = Lists.newArrayList();
        if (Const.ADMIN_NO.equals(manager.getNo())){
            //管理员显示所有
            totalPeopleNum = redisService.get(Const.PEOPLE_NUM_TOTAL) == null ? 0 : (int) redisService.get(Const.PEOPLE_NUM_TOTAL);
            newAddPeopleNum = redisService.get(Const.PEOPLE_NUM_NEW_ADD) == null ? 0 : (int) redisService.get(Const.PEOPLE_NUM_NEW_ADD);
            totalWriteRecordNum = redisService.get(Const.WRITE_RECORD_NUM_TOTAL) == null ? 0 : (int) redisService.get(Const.WRITE_RECORD_NUM_TOTAL);
            newAddWriteRecordNum = redisService.get(Const.WRITE_RECORD_NUM_NEW_ADD) == null ? 0 : (int) redisService.get(Const.WRITE_RECORD_NUM_NEW_ADD);

            condition.addMapCondition("allMsg", "allMsg");
        }else{
            //只显示教师所在学校信息
            String key = String.format(Const.PEOPLE_SCHOOL_NUM_TOTAL, manager.getSchoolId());
            totalPeopleNum = redisService.get(key) == null ? 0 : (int) redisService.get(key);
            key = String.format(Const.PEOPLE_SCHOOL_NUM_NEW_ADD, manager.getSchoolId());
            newAddPeopleNum = redisService.get(key) == null ? 0 : (int) redisService.get(key);
            key = String.format(Const.WRITE_RECORD_SCHOOL_NUM_TOTAL, manager.getSchoolId());
            totalWriteRecordNum = redisService.get(key) == null ? 0 : (int) redisService.get(key);
            key = String.format(Const.WRITE_RECORD_SCHOOL_NUM_NEW_ADD, manager.getSchoolId());
            newAddWriteRecordNum = redisService.get(key) == null ? 0 : (int) redisService.get(key);

            condition.addMapCondition("schoolMsg", "schoolMsg");
            condition.addMapCondition("schoolId", manager.getSchoolId());
        }

        writeRecordDataList = writeRecordDataService.selectByCondition(condition);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", writeRecordDataList);
        modelAndView.addObject("totalPeopleNum", totalPeopleNum);
        modelAndView.addObject("newAddPeopleNum", newAddPeopleNum);
        modelAndView.addObject("totalWriteRecordNum", totalWriteRecordNum);
        modelAndView.addObject("newAddWriteRecordNum", newAddWriteRecordNum);
        modelAndView.addObject("writeRecordDataList", jsonObject);
        return modelAndView;
    }

    /**
     * 个人信息
     * @param id
     * @return
     */
    @RequestMapping("/admin/selectMsg/{id}")
    public ModelAndView selectMsg(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_profile");
        try {
            Manager manager = managerService.selectByPrimaryKey(id);
            modelAndView.addObject("manager", manager);
            return modelAndView;
        }catch (Exception e){
            log.info("AdminController selectMsg error {}", e);
        }
        modelAndView = new ModelAndView("/admin/index");
        return modelAndView;
    }

    @RequestMapping("/admin/updateMsg")
    public ModelAndView updateMsg(HttpServletRequest request, Integer id, String realName, String tel, MultipartFile image, String description){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/selectMsg/" + id);
        try {
            if (StringUtils.isEmpty(realName)){
                modelAndView.addObject("message", "真实姓名不能为空");
            }
            if (StringUtils.isEmpty(tel)){
                modelAndView.addObject("message", "联系方式不能为空");
            }
            Manager manager = (Manager) request.getSession().getAttribute("manager");
            manager.setRealName(realName);
            manager.setTel(tel);
            manager.setDescription(description);
            if (image != null){
                String filenameExt = (image.getOriginalFilename().toString()).substring(image.getOriginalFilename().toString().lastIndexOf(".") + 1);

                if (!Const.IMAGE_KINDS.contains(filenameExt)){
                    modelAndView.addObject("message", "图片格式有误，请重试");
                    return modelAndView;
                }
                String imageName= StringUtils.generateUniqueId() + "." + filenameExt;
                File file = new File(imageName);
                image.transferTo(file);

                manager.setImagePath(Const.IMAGE_VIRTUAL_PATH + imageName);
            }
            int update = managerService.updateByPrimaryKey(manager);
            if (update > 0){
                modelAndView.addObject("message", "修改成功");
                request.getSession().setAttribute("manager", manager);
                return modelAndView;
            }
        }catch (Exception e){
            log.info("AdminController updateMsg error {}", e);
        }
        modelAndView.addObject("message", "修改失败，请重试");
        return modelAndView;
    }

    /**
     * 跳往修改密码页面
     * @return
     */
    @RequestMapping("/admin/updatePasswordPage")
    public ModelAndView updatePassword(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_edit_pwd");
        return modelAndView;
    }

    /**
     * 修改密码操作
     * @param request
     * @param oldpwd
     * @param newpwd
     * @param confirmpwd
     * @return
     */
    @RequestMapping("/admin/updatePassword")
    public ModelAndView updatePassword(HttpServletRequest request, String oldpwd, String newpwd, String confirmpwd){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/updatePasswordPage");
        if (StringUtils.isEmpty(oldpwd) || StringUtils.isEmpty(newpwd) || StringUtils.isEmpty(confirmpwd)){
            modelAndView.addObject("message", "密码不可为空");
            return modelAndView;
        }
        if (!newpwd.equals(confirmpwd)){
            modelAndView.addObject("message", "新密码与验证密码不一致");
            return modelAndView;
        }
        Manager manager = (Manager) request.getSession().getAttribute("manager");
        if (manager != null && manager.getPassword().equals(MD5EncoderUtil.encode(oldpwd))){
            manager.setId(manager.getId());
            manager.setPassword(MD5EncoderUtil.encode(newpwd));
            int update = managerService.updateByPrimaryKey(manager);
            if (update > 0){
                modelAndView.addObject("message", "密码修改成功");
                request.getSession().setAttribute("manager", manager);
                return modelAndView;
            }
        }
        modelAndView.addObject("message", "密码修改失败，请重试");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddNoticePage")
    public ModelAndView toAddNoticePage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc1");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddSchoolPage")
    public ModelAndView toAddSchoolPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc2");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddTeacherPage")
    public ModelAndView toAddTeacherPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc3");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddGoodsPage")
    public ModelAndView toAddGoodsPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc4");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddCreateRecordPage")
    public ModelAndView toAddCreateRecordPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc6");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddRecruitPage")
    public ModelAndView toAddRecruitPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc7");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddStudentPage")
    public ModelAndView toAddStudentPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc8");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddManagerAchievementPage")
    public ModelAndView toAddManagerAchievementPage(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/admin/addManagerAchievementPage");
        String id = request.getParameter("id");
        if (StringUtils.isNotEmpty(id)){
            Integer schoolId = (Integer) request.getSession().getAttribute("schoolId");
            Student student = new Student();
            student.setId(Integer.parseInt(id));
            student.setSchoolId(schoolId);
            List<Student> students = studentService.selectByStudent(student);
            modelAndView.addObject("student", students.get(0));
        }
        return modelAndView;
    }

    @RequestMapping("/admin/toUpdateManagerAchievementPage")
    public ModelAndView toUpdateManagerAchievementPage(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/admin/updateManagerAchievementPage");
        String id = request.getParameter("id");
        if (StringUtils.isNotEmpty(id)){
            Integer schoolId = (Integer) request.getSession().getAttribute("schoolId");
            Student student = new Student();
            student.setId(Integer.parseInt(id));
            student.setSchoolId(schoolId);
            List<Student> students = studentService.selectByStudent(student);
            modelAndView.addObject("student", students.get(0));
        }
        return modelAndView;
    }


}
