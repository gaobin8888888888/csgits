package com.sts.csgits.controller;

import com.sts.csgits.entity.Goods;
import com.sts.csgits.entity.Redeem;
import com.sts.csgits.entity.Student;
import com.sts.csgits.inc.Const;
import com.sts.csgits.service.GoodsService;
import com.sts.csgits.service.RedeemService;
import com.sts.csgits.service.StudentService;
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
 * 积分兑换控制层
 * @author ：gb
 * @date ：Created in 2020/3/28 14:20
 */
@Controller
@Slf4j
public class RedeemController {

    @Autowired
    private RedeemService redeemService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StudentService studentService;

    @RequestMapping("/student/redeem/add")
    public @ResponseBody String add(HttpServletRequest request, Integer toRedeemId){
        try {
            Student student = (Student) request.getSession().getAttribute("student");
            Goods goods = goodsService.selectByPrimaryKey(toRedeemId);
            if (goods != null && goods.getNum() > 0 && student != null && student.getCredits() > goods.getCredits()){
                Redeem redeem = new Redeem();
                redeem.setSole(student.getSole());
                redeem.setGoodId(toRedeemId);
                redeem.setStatus(Const.REDEEM_STATUS_DEALING);
                int i = redeemService.insert(redeem);
                if (i > 0){
                    //修改学生的积分
                    student.setCredits(student.getCredits() - goods.getCredits());
                    studentService.updateByPrimaryKey(student);

                    //修改物品的数量
                    goods.setNum(goods.getNum() - 1);
                    goodsService.updateByPrimaryKey(goods);

                    return JSONResult.successInstance("兑换成功，之后会有管理员联系您");
                }
            }else {
                return JSONResult.successInstance("物品数量为0或者您的积分不够，请重新选择");
            }
        }catch (Exception e){
            log.info("RedeemController add error {}", e);
        }
        return JSONResult.successInstance("兑换失败，请重试");
    }

    @RequestMapping("/admin/redeem/updateStatus")
    public @ResponseBody String updateStatus(Integer id){
        try {
            Redeem redeem = new Redeem();
            redeem.setId(id);
            redeem.setStatus(Const.REDEEM_STATUS_FINISH);
            int update = redeemService.updateByPrimaryKey(redeem);
            if (update > 0){
                return JSONResult.successInstance("状态修改成功");
            }
        }catch (Exception e){
            log.info("RedeemController updateStatus error {}", e);
        }
        return JSONResult.errorInstance("状态修改失败，请重试");
    }

    @RequestMapping("/admin/redeem/selectAll")
    public ModelAndView selectAll(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc5");
        List<Redeem> redeemList = null;
        try {
            redeemList = redeemService.selectAll();
            modelAndView.addObject("redeemList", redeemList);
        }catch (Exception e){
            log.info("RedeemController selectAll error {}", e);
        }
        return modelAndView;
    }
}
