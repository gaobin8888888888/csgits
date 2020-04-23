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

    /**
     * 登录时的身份
     */
    public static final String LOGIN_STATUS_TEACHER = "1";

/*******************************redis存储的key********************************************************************************/
    /**
     * 记录使用系统总人数
     */
    public static final String PEOPLE_NUM_TOTAL = "people.num.total";

    /**
     * 记录各个学校的总人数
     */
    public static final String PEOPLE_SCHOOL_NUM_TOTAL = "people.school.%s.num.total";

    /**
     * 记录使用系统新增人数
     */
    public static final String PEOPLE_NUM_NEW_ADD = "people.num.new.add";

    /**
     * 记录各个学校的新增人数
     */
    public static final String PEOPLE_SCHOOL_NUM_NEW_ADD = "people.school.%s.num.new.add";

    /**
     * 记录填写记录总人数
     */
    public static final String WRITE_RECORD_NUM_TOTAL = "write.record.num.total";

    /**
     * 记录填写记录中各个学校的总人数
     */
    public static final String WRITE_RECORD_SCHOOL_NUM_TOTAL = "write.record.school.%s.num.total";

    /**
     * 记录填写记录新增人数
     */
    public static final String WRITE_RECORD_NUM_NEW_ADD = "write.record.num.new.add";

    /**
     * 记录填写记录中各个学校的新增人数
     */
    public static final String WRITE_RECORD_SCHOOL_NUM_NEW_ADD = "write.record.school.%s.num.new.add";

    /**
     * 存储学生信息的key
     */
    public static final String STUDENT_SOLE = "student.sole.%s";

    /************广播前缀***************************************************************************************/
    /**
     * 通用广播Channel前缀
     */
    public static final String REDIS_CHANNEL = "csgits-channel-sync-";

    public static final String REDIS_CHANNEL_SCHOOL = REDIS_CHANNEL + "school";

    public static final String REDIS_CHANNEL_ADD_STUDENT = REDIS_CHANNEL + "add-student";

    public static final String REDIS_CHANNEL_DEL_STUDENT = REDIS_CHANNEL + "del-student";

    public static final String REDIS_CHANNEL_UPDATE_STUDENT = REDIS_CHANNEL + "update-student";



}
