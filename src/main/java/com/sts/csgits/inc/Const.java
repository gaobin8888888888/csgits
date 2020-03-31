package com.sts.csgits.inc;

/**
 * 常量
 * @author ：gb
 * @date ：Created in 2020/3/1 16:58
 */
public class Const {

    /**
     * 后台返回结果，0代表成功，1代表失败
     */
    public static final int RETURN_SUCCESS = 0;
    public static final int RETURN_ERROR = 1;

    //关于redis常量
    public static final String REDIS_SET_RETURN_OK = "OK";

    public static final String SESSION_FILTER_CONTAINS_URL = "/login";

    /**
     * 上传图片格式
     */
    public static final String IMAGE_KINDS = "jpg,png";

    /**
     * 图片虚拟路径
     */
    public static final String IMAGE_VIRTUAL_PATH = "/static/images/";

    /**
     * 管理员
     */
    public static final String ADMIN_NO = "admin";

    /**
     * 初始化默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     *
     */
    public static final String DEFAULT_IMAGE_PATH = "/static/images/202003150020299393.jpg";

    /**
     * 积分兑换的状态 1:正在处理 2：已完成
     */
    public static final int REDEEM_STATUS_DEALING = 1;
    public static final int REDEEM_STATUS_FINISH = 2;
}
