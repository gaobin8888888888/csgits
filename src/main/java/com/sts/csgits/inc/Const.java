package com.sts.csgits.inc;

import com.sts.csgits.utils.MD5EncoderUtil;

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
    public static final String IMAGE_KINDS = "jpg,png,jpeg";

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
     * 默认图片
     */
    public static final String DEFAULT_IMAGE_PATH = "/static/images/student.png";

    /**
     * 积分兑换的状态 1:正在处理 2：已完成
     */
    public static final int REDEEM_STATUS_DEALING = 1;
    public static final int REDEEM_STATUS_FINISH = 2;

    /**
     * 登录时的身份
     */
    public static final String LOGIN_STATUS_TEACHER = "1";

    /**
     * 填写一次记录，积分加两分
     */
    public static final int ADD_CREDITS_WRITE_RECORD = 2;

/*******************************redis存储的key********************************************************************************/

    /**
     * 每次增加的数量
     */
    public static final long ADD_NUM = 1L;
    /**
     * 每次减少的数量
     */
    public static final long DESC_NUM = -1L;
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

    /*****学生填写个人情况*************************************************************************************************/

    /**
     * 从事与大学专业相关的工作或者学习
     */
    public static final Integer WRITE_RECORD_RELATED_NUM = 0;

    /**
     * 处于工作的状态
     */
    public static final Integer WRITE_RECORD_WORK_NUM = 0;
    /**
     * 处于学习的状态
     */
    public static final Integer WRITE_RECORD_STUDY_NUM = 1;

    /**
     * 大学生学历
     */
    public static final Integer WRITE_RECORD_DEGREE_COLLEGE_NUM = 0;
    /**
     * 研究生学历
     */
    public static final Integer WRITE_RECORD_DEGREE_GRADUATE_NUM = 1;
    /**
     * 博士学历
     */
    public static final Integer WRITE_RECORD_DEGREE_DOCTOR_NUM = 2;

    /**
     * 在家乡
     */
    public static final Integer WRITE_RECORD_HOMETOEN_NUM = 0;

    /**
     * 月薪范围
     * <option value="0">5000以下</option>
     * 									<option value="1">5000~8000</option>
     * 									<option value="2">8000~12000</option>
     * 									<option value="3">12000~20000</option>
     * 									<option value="4">20000以上</option>
     */
    public static final Integer WRITE_RECORD_SALARY0_NUM = 0;
    public static final Integer WRITE_RECORD_SALARY1_NUM = 1;
    public static final Integer WRITE_RECORD_SALARY2_NUM = 2;
    public static final Integer WRITE_RECORD_SALARY3_NUM = 3;
    public static final Integer WRITE_RECORD_SALARY4_NUM = 4;

    public static void main(String[] args) {
        System.out.println(MD5EncoderUtil.encode("123456"));
    }
}
