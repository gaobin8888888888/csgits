package com.sts.csgits.utils;


import com.sts.csgits.entity.ImageCode;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 验证码工具类
 * @author ：gb
 * @date ：Created in 2019/9/30 13:56
 */
public class ImageCodeUtil {

    private final static String SESSION_CODE = "sessionCode";

    private final static String IMAGE_CODES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    /**
     * 检查用户填写的的验证码是否和session中存储的一致，不管是否一致，都把session中的验证码删掉
     * @param session
     * @param requestCode
     * @return
     */
    public static boolean checkImageCode(HttpSession session, String requestCode) {

        String sessionCode = (String) session.getAttribute(SESSION_CODE);

        session.removeAttribute(SESSION_CODE);
        if (requestCode == null) {
            return false;
        }

        return requestCode.equalsIgnoreCase(sessionCode);
    }


    /**
     * 根据指定源生成随机的四位验证码
     * @param codeSize
     * @return
     */
    public static String generateCode(int codeSize) {

        int length = IMAGE_CODES.length();

        Random random = new Random();

        StringBuilder imageCode = new StringBuilder(codeSize);

        for (int i = 0; i < codeSize; i++){
            imageCode.append(IMAGE_CODES.charAt(random.nextInt(length - 1)));
        }
        return imageCode.toString();
    }

    /**
     * 根据已生成的四位验证码，生成验证码图片
     * @param width
     * @param height
     * @param codeSize
     * @return
     */
    public static ImageCode createImageCode(int width, int height, int codeSize) {
        return createImageCode(width, height, generateCode(codeSize));
    }

    public static ImageCode createImageCode(int width, int height, String imageCode) {
        int codeSize = imageCode.length();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
                Color.ORANGE, Color.PINK, Color.YELLOW};
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, width, height);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, width, height - 4);

        // 绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, width, height, c);// 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = height - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = imageCode.toCharArray();
        for (int i = 0; i < codeSize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
                    (width / codeSize) * i + fontSize / 2, height / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((width - 10) / codeSize) * i + 5, height / 2 + fontSize / 2 - 10);
        }

        g2.dispose();
        return new ImageCode(image, imageCode);
    }


    private static Color getRandColor(int fc, int bc) {
        Random random = ThreadLocalRandom.current();

        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        Random random = ThreadLocalRandom.current();
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {
        Random random = ThreadLocalRandom.current();
        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * phase) / frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {
        Random random = ThreadLocalRandom.current();
        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * phase) / frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }
    }


    public static void sendImageCode(HttpSession session, HttpServletResponse response) {

        sendImageCode(100, 35, 4, session, response);

    }

    public static void sendImageCode(int width, int height, int codeSize, HttpSession session,
                                     HttpServletResponse response) {

        try {
            // 生成图片验证码
            ImageCode imageCode = createImageCode(width, height, codeSize);
            // 把图片发送到浏览器
            ImageIO.write(imageCode.image, "jpg", response.getOutputStream());
            // 把验证码存入session
            session.setAttribute(SESSION_CODE, imageCode.code);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
