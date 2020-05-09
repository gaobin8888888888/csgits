package com.sts.csgits.controller;

import com.sts.csgits.entity.Manager;
import com.sts.csgits.entity.Notice;
import com.sts.csgits.inc.Const;
import com.sts.csgits.service.ManagerService;
import com.sts.csgits.service.NoticeService;
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
 * 通知控制层
 * @author ：gb
 * @date ：Created in 2020/3/11 21:19
 */
@Controller
@RequestMapping("/admin/notice")
@Slf4j
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ManagerService managerService;

    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request, String noticeName, Integer noticeType, String content){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/toAddNoticePage");
        try {
            Integer managerId = (Integer) request.getSession().getAttribute("id");
            if (StringUtils.isEmpty(noticeName)){
                modelAndView.addObject("message", "通知标题不能为空");
                return modelAndView;
            }
            if (StringUtils.isEmpty(content)){
                modelAndView.addObject("message", "通知主体内容不能为空");
                return modelAndView;
            }

            Notice notice = new Notice();
            notice.setManagerId(managerId);
            notice.setTitle(noticeName);
            notice.setNoticeType(noticeType);
            notice.setContent(content);
            int insert = noticeService.insert(notice);
            if (insert > 0){


                modelAndView = new ModelAndView("redirect:/admin/notice/selectAll");
                modelAndView.addObject("message", "添加成功");
                return modelAndView;
            }
        }catch (Exception e){
            log.info("NoticeController add通知 error {}", e);
        }
        modelAndView.addObject("message", "添加失败，请稍后再试");
        return modelAndView;
    }

    /**
     * 删除通知
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public @ResponseBody
    String delete(@PathVariable("id") Integer id){
        try {
            int delete = noticeService.deleteByPrimaryKey(id);
            if (delete > 0){
                return JSONResult.successInstance("删除成功");
            }
        }catch (Exception e){
            log.info("NoticeController delete通知 error {}", e);
        }
        return JSONResult.errorInstance("删除失败，请稍后再试");
    }

    /**
     * 更新通知
     * @param id
     * @param content
     * @return
     */
    @RequestMapping("/update")
    public ModelAndView update(Integer id, String content){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/notice/selectAll");
        try {
            if (StringUtils.isEmpty(content)){
                modelAndView.addObject("message", "通知主体内容不能为空");
                return modelAndView;
            }
            Notice notice = new Notice();
            notice.setId(id);
            notice.setContent(content);
            int update = noticeService.updateByPrimaryKey(notice);
            if (update > 0){
                return modelAndView.addObject("message", "更新成功");
            }
        }catch (Exception e){
            log.info("NoticeController update通知 error {}", e);
        }
        return modelAndView.addObject("message", "更新失败，请稍后再试");
    }

    /**
     * 查询所有数据
     * @return
     */
    @RequestMapping("/selectAll")
    public ModelAndView selectAll(HttpServletRequest request, String noticeName){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc1");
        List<Notice> noticeList = null;
        try {
            Manager manager = (Manager) request.getSession().getAttribute("manager");
            Notice notice = new Notice();
            if (StringUtils.isNotEmpty(noticeName)){
                notice.setTitle(noticeName);
            }
            if (manager != null){
                if (Const.ADMIN_NO.equals(manager.getNo())){
                    noticeList = noticeService.selectByNotice(notice);
                    for (Notice notice1 : noticeList){
                        Manager manager1 = managerService.selectByPrimaryKey(notice1.getManagerId());
                        notice1.setManager(manager1);
                    }
                }else {
                    notice.setManagerId(manager.getId());
                    noticeList = noticeService.selectByNotice(notice);
                }
            }

            modelAndView.addObject("noticeList", noticeList);
            modelAndView.addObject("noticeName", noticeName);
        }catch (Exception e){
            log.info("NoticeController selectAll通知 error {}", e);
        }
        return modelAndView;
    }

    /**
     * 查询自己发布的通知
     * @param request
     * @return
     */
    @RequestMapping("/selectByNotice")
    public ModelAndView selectBySelf(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/notice/selectAll");

        Integer managerId = (Integer) request.getSession().getAttribute("id");
        List<Notice> noticeList = null;
        try {
            Notice notice = new Notice();
            notice.setManagerId(managerId);
            noticeList = noticeService.selectByNotice(notice);
            modelAndView.addObject("noticeList", noticeList);
        }catch (Exception e){
            log.info("NoticeController selectBySelf通知 error {}", e);
        }
        return modelAndView;
    }

}
