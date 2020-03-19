package com.sts.csgits.controller;

import com.sts.csgits.entity.Manager;
import com.sts.csgits.inc.Const;
import com.sts.csgits.service.ManagerService;
import com.sts.csgits.utils.MD5EncoderUtil;
import com.sts.csgits.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

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

    @RequestMapping("/admin/toAdminIndex")
    public String toIndex(){
        return "/admin/index";
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
    public ModelAndView updateMsg(HttpServletRequest request, Integer id, String realName, String tel, MultipartFile image){
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
     * @param id
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

    @RequestMapping("/admin/toAddSchoolPage")
    public ModelAndView toAddSchoolPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddGoodsPage")
    public ModelAndView toAddGoodsPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc2");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddNoticePage")
    public ModelAndView toAddNoticePage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc3");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddTeacherPage")
    public ModelAndView toAddTeacherPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc4");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddRecruitPage")
    public ModelAndView toAddRecruitPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc5");
        return modelAndView;
    }

    @RequestMapping("/admin/toAddStudentPage")
    public ModelAndView toAddStudentPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_add_doc6");
        return modelAndView;
    }

}
