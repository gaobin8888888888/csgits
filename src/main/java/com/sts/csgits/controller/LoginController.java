package com.sts.csgits.controller;

import com.sts.csgits.entity.Manager;
import com.sts.csgits.service.ManagerService;
import com.sts.csgits.utils.ImageCodeUtil;
import com.sts.csgits.utils.MD5EncoderUtil;
import com.sts.csgits.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录控制层
 * @author ：gb
 * @date ：Created in 2020/3/4 22:39
 */
@Slf4j
@Controller
public class LoginController {

    @Autowired
    private ManagerService managerService;

    /**
     * 管理员跳转到登录页面
     * @return
     */
    @RequestMapping("/admin/loginPage")
    public String toAdminLogin(){
        return "/admin/lyear_pages_login";
    }

    /**
     * 登录页面获取图形验证码
     * @param request
     * @param response
     */
    @RequestMapping("/getCheckCode.do")
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response){
        ImageCodeUtil.sendImageCode(request.getSession(), response);
    }

    /**
     * 管理员登录验证
     * @param username
     * @param password
     * @param imageCode
     * @return
     */
    @RequestMapping("/admin/login")
    public ModelAndView login(String username, String password, String imageCode, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_login");
        if (StringUtils.isEmpty(username)){
            modelAndView.addObject("message", "用户名不可为空");
            return modelAndView;
        }

        if (StringUtils.isEmpty(password) || password.length() < 6){
            modelAndView.addObject("message", "密码至少6位");
            return modelAndView;
        }

        if (StringUtils.isEmpty(imageCode)){
            modelAndView.addObject("message", "验证码不可为空");
            return modelAndView;
        }

        // 验证码检查
        if (!ImageCodeUtil.checkImageCode(request.getSession(), imageCode)) {
            modelAndView.addObject("message", "验证码不正确");
            return modelAndView;
        }
        Manager manager = null;
        try {
            manager = new Manager();
            manager.setNo(username);
            manager.setPassword(MD5EncoderUtil.encode(password));
            manager = managerService.selectByNoAndPassword(manager);
            if (manager != null){
                request.getSession().setAttribute("id", manager.getId());
                request.getSession().setAttribute("no", manager.getNo());
                request.getSession().setAttribute("imagePath", manager.getImagePath());
                request.getSession().setAttribute("realName", manager.getRealName());
                request.getSession().setAttribute("schoolId", manager.getSchoolId());
                request.getSession().setAttribute("manager", manager);
                modelAndView = new ModelAndView("redirect:/admin/toAdminIndex");
                return modelAndView;
            }
        }catch (Exception e){
            log.info("LoginController login error {}", e);
        }

        modelAndView.addObject("message", "用户名或者密码错误，请重试");
        return modelAndView;
    }


    @RequestMapping("/admin/logout")
    public ModelAndView logout(HttpServletRequest request){
        request.getSession().setAttribute("id", null);
        request.getSession().setAttribute("no", null);
        request.getSession().setAttribute("imagePath", null);
        request.getSession().setAttribute("realName", null);
        request.getSession().setAttribute("manager", null);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/loginPage");
        return modelAndView;
    }

}
