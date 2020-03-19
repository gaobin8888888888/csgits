package com.sts.csgits.entity;

import java.awt.image.BufferedImage;

/**
 * 验证码属性
 * @author ：gb
 * @date ：Created in 2019/9/30 14:18
 */
public class ImageCode {

    public BufferedImage image;
    public String code;

    public ImageCode(BufferedImage image, String code) {
        this.image = image;
        this.code = code;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
