package com.sts.csgits.controller;

import com.sts.csgits.entity.Manager;
import com.sts.csgits.entity.School;
import com.sts.csgits.inc.Const;
import com.sts.csgits.inc.LocalCache;
import com.sts.csgits.service.ManagerService;
import com.sts.csgits.service.RedisService;
import com.sts.csgits.service.SchoolService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

/**
 * @author ：gb
 * @date ：Created in 2020/3/11 19:48
 */
@Controller
@Slf4j
@RequestMapping("/admin/teacher")
public class TeacherController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private PickUtil pickUtil;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private LocalCache localCache;

    @RequestMapping("/add")
    public ModelAndView add(String teacherNo, String teacherName, MultipartFile image, String tel, Integer schoolId, String college){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/toAddTeacherPage");
        try {
            if (StringUtils.isEmpty(teacherNo)){
                modelAndView.addObject("message", "教师编号不能为空");
                return modelAndView;
            }
            if (StringUtils.isEmpty(teacherName)){
                modelAndView.addObject("message", "教师真实姓名不能为空");
                return modelAndView;
            }
            if (image == null || image.getSize() <= 0 || image.getBytes().length <= 0){
                modelAndView.addObject("message", "请选择文件");
                return modelAndView;
            }
            if (StringUtils.isEmpty(tel)){
                modelAndView.addObject("message", "联系方式不能为空");
                return modelAndView;
            }
            if (schoolId == null){
                modelAndView.addObject("message", "教师所在学校不能为空");
                return modelAndView;
            }
            if (StringUtils.isEmpty(college)){
                modelAndView.addObject("message", "教师所在学院不能为空");
                return modelAndView;
            }

            //获取文件后缀
            String filenameExt = StringUtils.endString(image.getOriginalFilename(), "\\.");

            if (!Const.IMAGE_KINDS.contains(filenameExt)){
                modelAndView.addObject("message", "图片格式有误，请重试");
                return modelAndView;
            }
            String imageName= StringUtils.generateUniqueId() + "." + filenameExt;
            File file = new File(imageName);
            image.transferTo(file);

            Manager manager = new Manager();
            manager.setNo(teacherNo);
            manager.setRealName(teacherName);
            manager.setTel(tel);
            manager.setSchoolId(schoolId);
            manager.setCollege(college);
            manager.setPassword(MD5EncoderUtil.encode(Const.DEFAULT_PASSWORD));
            manager.setImagePath(Const.IMAGE_VIRTUAL_PATH + imageName);
            int insert = managerService.insert(manager);
            if (insert > 0){
                pickUtil.addOrDescPeopleNum(schoolId, Const.ADD_NUM);
                localCache.initManager();
                modelAndView = new ModelAndView("redirect:/admin/teacher/selectAll");
                modelAndView.addObject("message", "添加成功");
                return modelAndView;
            }
        }catch (Exception e){
            log.info("TeacherController add教师信息 error {}", e);
        }
        modelAndView.addObject("message", "添加失败，请稍后再试");
        return modelAndView;
    }

    /**
     * 删除教师信息
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public @ResponseBody String
    delete(@PathVariable("id") Integer id){
        try {
            Manager manager = managerService.selectByPrimaryKey(id);
            if (manager != null){
                int delete = managerService.deleteByPrimaryKey(id);
                if (delete > 0){
                    pickUtil.addOrDescPeopleNum(manager.getSchoolId(), Const.DESC_NUM);
                    localCache.initManager();
                    return JSONResult.successInstance("删除成功");
                }
            }
        }catch (Exception e){
            log.info("TeacherController delete教师信息 error {}", e);
        }
        return JSONResult.errorInstance("删除失败，请稍后再试");
    }

    /**
     * 更新教师信息
     * @param id
     * @param description
     * @return
     */
    @RequestMapping("/update")
    public ModelAndView update(Integer id, String description){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/teacher/selectAll");
        try {
            if (StringUtils.isEmpty(description)){
                return modelAndView.addObject("message", "教师描述不可为空");
            }
            Manager manager = new Manager();
            manager.setDescription(description);
            manager.setId(id);
            int update = managerService.updateByPrimaryKey(manager);
            if (update > 0){
                localCache.initManager();
                return modelAndView.addObject("message", "更新成功");
            }
        }catch (Exception e){
            log.info("TeacherController update教师信息 error {}", e);
        }
        return modelAndView.addObject("message", "更新失败，请稍后再试");
    }

    @RequestMapping("/updateFine/{id}")
    public @ResponseBody String updateFine(@PathVariable("id") Integer id, Boolean fine){
        try {
            Manager manager = new Manager();
            manager.setFine(fine);
            manager.setId(id);
            int update = managerService.updateByPrimaryKey(manager);
            if (update > 0){
                localCache.initManager();
                return JSONResult.successInstance("修改是否优秀教师成功");
            }
        }catch (Exception e){
            log.info("TeacherController updateFine error {}", e);
        }
        return JSONResult.errorInstance("修改是否优秀教师失败，请稍后再试");
    }

    @RequestMapping("/updateConfined/{id}")
    public @ResponseBody String updateConfined(@PathVariable("id") Integer id, Boolean confined){
        try {
            Manager manager = new Manager();
            //manager.setConfined(confined);
            manager.setId(id);
            int update = managerService.updateByPrimaryKey(manager);
            if (update > 0){
                return JSONResult.successInstance("修改是否限制成功");
            }
        }catch (Exception e){
            log.info("TeacherController updateConfined error {}", e);
        }
        return JSONResult.errorInstance("修改是否限制失败，请稍后再试");
    }

    /**
     * 查询所有数据
     * @return
     */
    @RequestMapping("/selectAll")
    public ModelAndView selectAll(String teacherName){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc3");
        List<Manager> managerList = null;
        try {
            Manager manager = new Manager();
            if (StringUtils.isNotEmpty(teacherName)){
                manager.setRealName(teacherName);
            }
            managerList = managerService.selectByManager(manager);
            if (managerList != null && managerList.size() > 0){
                for (Manager manager1 : managerList){
                    School school = schoolService.selectByPrimaryKey(manager1.getSchoolId());
                    manager1.setSchool(school);
                }
            }
            modelAndView.addObject("teacherList", managerList);
            modelAndView.addObject("teacherName", teacherName);
        }catch (Exception e){
            log.info("TeacherController selectAll教师信息 error {}", e);
        }
        return modelAndView;
    }


    @RequestMapping("/selectByName")
    public ModelAndView selectByName(String teacherName){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/teacher/selectAll");
        modelAndView.addObject("teacherName", teacherName);
        if (StringUtils.isEmpty(teacherName)){
            return modelAndView;
        }
        modelAndView = new ModelAndView("/admin/lyear_pages_doc4");
        List<Manager> managerList = null;
        try {
            Manager manager = new Manager();
            manager.setRealName(teacherName);
            managerList = managerService.selectByManager(manager);
            modelAndView.addObject("teacherList", managerList);
        }catch (Exception e){
            log.info("TeacherController selectByName教师信息 error {}", e);
        }
        return modelAndView;
    }
}
