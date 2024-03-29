package com.sts.csgits.controller;

import com.sts.csgits.entity.School;
import com.sts.csgits.inc.Const;
import com.sts.csgits.inc.LocalCache;
import com.sts.csgits.service.CreateTableService;
import com.sts.csgits.service.SchoolService;
import com.sts.csgits.utils.JSONResult;
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
 * 学校管理
 * @author ：gb
 * @date ：Created in 2020/2/10 16:54
 */
@Controller
@Slf4j
@RequestMapping("/admin/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private CreateTableService createTableService;

    @Autowired
    private LocalCache localCache;

    /**
     * 添加学校信息
     * @param schoolName
     * @param description
     * @param image
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView add(String schoolName, String description, MultipartFile image){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/toAddSchoolPage");
        try {
            if (StringUtils.isEmpty(schoolName)){
                modelAndView.addObject("message", "学校名称不能为空");
                return modelAndView;
            }
            if (StringUtils.isEmpty(description)){
                modelAndView.addObject("message", "学校介绍不能为空");
                return modelAndView;
            }
            if (image == null || image.getSize() <= 0 || image.getBytes().length <= 0){
                modelAndView.addObject("message", "请选择文件");
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

            School school = new School();
            school.setName(schoolName);
            school.setDescription(description);
            school.setImagePath(Const.IMAGE_VIRTUAL_PATH + imageName);
            int insert = schoolService.insert(school);
            if (insert > 0){
                //添加该学校的学生表
                school = schoolService.selectOne();
                createTableService.createStudentTable(school.getId());
                localCache.initSchool();
                modelAndView = new ModelAndView("redirect:/admin/school/selectAll");
                modelAndView.addObject("message", "添加成功");
                return modelAndView;
            }
        }catch (Exception e){
            log.info("SchoolController add学校信息 error {}", e);
        }
        modelAndView.addObject("message", "添加失败，请稍后再试");
        return modelAndView;
    }

    /**
     * 删除学校信息
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public @ResponseBody
    String delete(@PathVariable("id") Integer id){
        try {
            int delete = schoolService.deleteByPrimaryKey(id);
            if (delete > 0){
                localCache.initSchool();
                return JSONResult.successInstance("删除成功");
            }
        }catch (Exception e){
            log.info("SchoolController delete学校信息 error {}", e);
        }
        return JSONResult.errorInstance("删除失败，请稍后再试");
    }

    /**
     * 更新学校信息
     * @param id
     * @param description
     * @return
     */
    @RequestMapping("/update")
    public ModelAndView update(Integer id, String description){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/school/selectAll");
        try {
            if (StringUtils.isEmpty(description)){
                return modelAndView.addObject("message", "学校描述不可为空");
            }
            School school = new School();
            school.setDescription(description);
            school.setId(id);
            int update = schoolService.updateByPrimaryKey(school);
            if (update > 0){
                localCache.initSchool();
                return modelAndView.addObject("message", "更新成功");
            }
        }catch (Exception e){
            log.info("SchoolController update学校信息 error {}", e);
        }
        return modelAndView.addObject("message", "更新失败，请稍后再试");
    }

    /**
     * 查询所有数据
     * @return
     */
    @RequestMapping("/selectAll")
    public ModelAndView selectAll(String schoolName){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc2");
        List<School> schoolList = null;
        try {
            School school = new School();
            if (StringUtils.isNotEmpty(schoolName)){
                school.setName(schoolName);
            }
            schoolList = schoolService.selectBySchool(school);
            modelAndView.addObject("schoolList", schoolList);
            modelAndView.addObject("schoolName", schoolName);
        }catch (Exception e){
            log.info("SchoolController selectAll学校信息 error {}", e);
        }
        return modelAndView;
    }


    @RequestMapping("/selectByName")
    public ModelAndView selectByName(String schoolName){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/school/selectAll");
        modelAndView.addObject("sname", schoolName);
        if (StringUtils.isEmpty(schoolName)){
            return modelAndView;
        }
        modelAndView = new ModelAndView("/admin/lyear_pages_doc");
        List<School> schoolList = null;
        try {
            schoolList = schoolService.selectByName(schoolName);
            modelAndView.addObject("schoolList", schoolList);
        }catch (Exception e){
            log.info("SchoolController selectByName学校信息 error {}", e);
        }
        return modelAndView;
    }

    /**
     * 更新学院信息
     * @param id
     * @return
     */
    @RequestMapping("/updateColleges")
    public @ResponseBody String updateColleges(Integer id, String tags){
        try {
            if (StringUtils.isEmpty(tags)){
                return JSONResult.errorInstance("学校描述不可为空");
            }
            School school = new School();
            school.setColleges(tags);
            school.setId(id);
            int update = schoolService.updateByPrimaryKey(school);
            if (update > 0){
                localCache.initSchool();
                return JSONResult.successInstance("更新成功");
            }
        }catch (Exception e){
            log.info("SchoolController updateColleges error {}", e);
        }
        return JSONResult.errorInstance("更新失败，请稍后再试");
    }

}
