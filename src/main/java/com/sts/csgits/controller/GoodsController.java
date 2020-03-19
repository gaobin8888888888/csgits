package com.sts.csgits.controller;

import com.sts.csgits.entity.Goods;
import com.sts.csgits.inc.Const;
import com.sts.csgits.service.GoodsService;
import com.sts.csgits.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

/**
 *
 * 可兑换物品控制层
 * @author ：gb
 * @date ：Created in 2020/3/11 20:13
 */
@Controller
@RequestMapping("/admin/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 添加物品信息
     * @param goodName
     * @param description
     * @param image
     * @param num
     * @param credits
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView add(String goodName, String description, MultipartFile image, Integer num, Integer credits){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/toAddGoodsPage");
        try {
            if (StringUtils.isEmpty(goodName)){
                modelAndView.addObject("message", "物品名称不能为空");
                return modelAndView;
            }
            if (StringUtils.isEmpty(description)){
                modelAndView.addObject("message", "物品介绍不能为空");
                return modelAndView;
            }
            if (image == null){
                modelAndView.addObject("message", "请选择文件");
                return modelAndView;
            }

            //获取文件后缀
            String filenameExt = (image.getOriginalFilename().toString()).substring(image.getOriginalFilename().toString().lastIndexOf(".") + 1);

            if (!Const.IMAGE_KINDS.contains(filenameExt)){
                modelAndView.addObject("message", "图片格式有误，请重试");
                return modelAndView;
            }
            String imageName= StringUtils.generateUniqueId() + "." + filenameExt;
            File file = new File(imageName);
            image.transferTo(file);

            Goods goods = new Goods();
            goods.setName(goodName);
            goods.setDescription(description);
            goods.setNum(num);
            goods.setCredits(credits);
            goods.setImagePath(Const.IMAGE_VIRTUAL_PATH + imageName);
            int insert = goodsService.insert(goods);
            if (insert > 0){
                //异步任务创表


                modelAndView = new ModelAndView("redirect:/admin/goods/selectAll");
                modelAndView.addObject("message", "添加成功");
                return modelAndView;
            }
        }catch (Exception e){
            log.info("GoodsController add物品信息 error {}", e);
        }
        modelAndView.addObject("message", "添加失败，请稍后再试");
        return modelAndView;
    }

    /**
     * 删除物品信息
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/goods/selectAll");
        try {
            int delete = goodsService.deleteByPrimaryKey(id);
            if (delete > 0){
                return modelAndView.addObject("message", "删除成功");
            }
        }catch (Exception e){
            log.info("GoodsController delete物品信息 error {}", e);
        }
        return modelAndView.addObject("message", "删除失败，请稍后再试");
    }

    /**
     * 更新物品信息
     * @param id
     * @param description
     * @return
     */
    @RequestMapping("/update")
    public ModelAndView update(Integer id, String description, Integer num, Integer credits){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/goods/selectAll");
        try {
            if (StringUtils.isEmpty(description)){
                return modelAndView.addObject("message", "物品描述不可为空");
            }
            Goods goods = new Goods();
            goods.setDescription(description);
            goods.setId(id);
            goods.setNum(num);
            goods.setCredits(credits);
            int update = goodsService.updateByPrimaryKey(goods);
            if (update > 0){
                return modelAndView.addObject("message", "更新成功");
            }
        }catch (Exception e){
            log.info("GoodsController update物品信息 error {}", e);
        }
        return modelAndView.addObject("message", "更新失败，请稍后再试");
    }

    /**
     * 查询所有数据
     * @return
     */
    @RequestMapping("/selectAll")
    public ModelAndView selectAll(){
        ModelAndView modelAndView = new ModelAndView("/admin/lyear_pages_doc2");
        List<Goods> goodsList = null;
        try {
            goodsList = goodsService.selectAll();
            modelAndView.addObject("goodsList", goodsList);
        }catch (Exception e){
            log.info("GoodsController selectAll物品信息 error {}", e);
        }
        return modelAndView;
    }


    @RequestMapping("/selectByName")
    public ModelAndView selectByName(String goodsName){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/goods/selectAll");
        modelAndView.addObject("goodsName", goodsName);
        if (StringUtils.isEmpty(goodsName)){
            return modelAndView;
        }
        modelAndView = new ModelAndView("/admin/lyear_pages_doc2");
        List<Goods> goodsList = null;
        try {
            Goods goods = new Goods();
            goods.setName(goodsName);
            goodsList = goodsService.selectByGoods(goods);
            modelAndView.addObject("goodsList", goodsList);
        }catch (Exception e){
            log.info("GoodsController selectByName物品信息 error {}", e);
        }
        return modelAndView;
    }

}
