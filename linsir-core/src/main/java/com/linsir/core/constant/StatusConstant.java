package com.linsir.core.constant;

/**
 * description：装填类型的
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/2 14:49
 */
public interface StatusConstant {

    /*------------------默认系统状态---------------------*/
    /*正常状态*/
    public static final Integer STATUS_NORMAL = 0;

    /*禁用状态*/
    public static final Integer STATUS_DISABLE = -1;


    /**发布状态（0未发布，1已发布，2已撤销）*/
    public static final String NO_SEND  = "0";
    public static final String HAS_SEND  = "1";
    public static final String HAS_CANCLE  = "2";


    /**阅读状态（0未读，1已读）*/
    public static final String HAS_READ_FLAG  = "1";
    public static final String NO_READ_FLAG  = "0";


    /**
     * 状态(0无效1有效)
     */
    public static final String STATUS_0 = "0";
    public static final String STATUS_1 = "1";


    /**
     * 是否配置菜单的数据权限 1是0否
     */
    public static final Integer RULE_FLAG_0 = 0;
    public static final Integer RULE_FLAG_1 = 1;


    /*------------------考勤补卡业务状态---------------------*/
    /**
     * 考勤补卡业务状态 （1：同意  2：不同意）
     */
    public static final String SIGN_PATCH_BIZ_STATUS_1 = "1";
    public static final String SIGN_PATCH_BIZ_STATUS_2 = "2";

    /*------------------系统通告消息状态---------------------*/

    /** 系统通告消息状态：0=未发布 */
    String ANNOUNCEMENT_SEND_STATUS_0 = "0";
    /** 系统通告消息状态：1=已发布 */
    String ANNOUNCEMENT_SEND_STATUS_1 = "1";
    /** 系统通告消息状态：2=已撤销 */
    String ANNOUNCEMENT_SEND_STATUS_2 = "2";
}
