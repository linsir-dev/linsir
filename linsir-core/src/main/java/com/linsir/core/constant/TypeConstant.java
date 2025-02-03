package com.linsir.core.constant;

/**
 * description：类型相关
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/2 14:57
 */
public interface TypeConstant {


    /*------------------字符集类型---------------------*/
    /*默认字符集UTF-8*/
    public static final String CHARSET_TYPE_UTF8 = "UTF-8";

    /*------------------系统日志类型---------------------*/
    /*系统日志类型： 登录*/
    public static final int LOG_TYPE_1 = 1;

    /*系统日志类型： 操作*/
    public static final int LOG_TYPE_2 = 2;

    /*操作日志类型： 查询*/
    public static final int LOG_TYPE_3 = 3;

    /*操作日志类型： 添加*/
    public static final int LOG_TYPE_4 = 4;

    /*操作日志类型： 更新*/
    public static final int LOG_TYPE_5 = 5;

    /*操作日志类型： 删除*/
    public static final int LOG_TYPE_6 = 6;

    /*操作日志类型： 倒入*/
    public static final int LOG_TYPE_7 = 7;

    /*操作日志类型： 导出*/
    public static final int LOG_TYPE_8 = 8;

    /*------------------树节点---------------------*/
    /*树根节点*/
    public static final String TREE_ROOT_ID = "0";

    /*id空的默认值，避免null*/
    public static final String ID_PREVENT_NULL = "0";

    /*------------------菜单类型 相关---------------------*/
    /*0：一级菜单*/
    public static final Integer MENU_TYPE_0  = 0;

    /*1：子菜单*/
    public static final Integer MENU_TYPE_1  = 1;

    /*2：按钮权限*/
    public static final Integer MENU_TYPE_2  = 2;

    /*------------------优先级 相关---------------------*/
    /**优先级（L低，M中，H高）*/
    public static final String PRIORITY_L  = "L";
    public static final String PRIORITY_M  = "M";
    public static final String PRIORITY_H  = "H";


    /*------------------优先级 相关---------------------*/
    /**
     * 短信模板方式  0 .登录模板、1.注册模板、2.忘记密码模板
     */
    public static final String SMS_TPL_TYPE_0  = "0";
    public static final String SMS_TPL_TYPE_1  = "1";
    public static final String SMS_TPL_TYPE_2  = "2";


    /*------------------同步工作流引擎1同步0不同步---------------------*/
    /**
     * 同步工作流引擎1同步0不同步
     */
    public static final Integer ACT_SYNC_1 = 1;
    public static final Integer ACT_SYNC_0 = 0;


    /*------------------消息类型---------------------*/

    /**
     * 消息类型1:通知公告2:系统消息
     */
    public static final String MSG_CATEGORY_1 = "1";
    public static final String MSG_CATEGORY_2 = "2";

    /*------------------字典翻译文本后缀---------------------*/
    /**字典翻译文本后缀*/
    public static final String DICT_TEXT_SUFFIX = "_dictText";


    /*------------------单设计器主表类型---------------------*/
    /*表单设计器主表类型*/
    public static final Integer DESIGN_FORM_TYPE_MAIN = 1;

    /*表单设计器子表表类型*/
    public static final Integer DESIGN_FORM_TYPE_SUB = 2;

    /*表单设计器URL授权通过*/
    public static final Integer DESIGN_FORM_URL_STATUS_PASSED = 1;

    /*表单设计器URL授权未通过*/
    public static final Integer DESIGN_FORM_URL_STATUS_NOT_PASSED = 2;

    /*------------------online参数值设置---------------------*/

    /**
     * online参数值设置（是：Y, 否：N）
     */
    public static final String ONLINE_PARAM_VAL_IS_TURE = "Y";
    public static final String ONLINE_PARAM_VAL_IS_FALSE = "N";


    /*------------------文件上传类型---------------------*/
    /**
     * 文件上传类型（本地：local，Minio：minio，阿里云：alioss）
     */
    public static final String UPLOAD_TYPE_LOCAL = "local";
    public static final String UPLOAD_TYPE_MINIO = "minio";
    public static final String UPLOAD_TYPE_OSS = "alioss";

    /*文档上传自定义桶名称*/
    public static final String UPLOAD_CUSTOM_BUCKET = "eoafile";

    /*文档上传自定义路径*/
    public static final String UPLOAD_CUSTOM_PATH = "eoafile";

    /*文件外链接有效天数*/
    public static final Integer UPLOAD_EFFECTIVE_DAYS = 1;

    /*------------------员工身份---------------------*/
    /**
     * 员工身份 （1:普通员工  2:上级）
     */
    public static final Integer USER_IDENTITY_1 = 1;
    public static final Integer USER_IDENTITY_2 = 2;



    /*------------------在线聊天---------------------*/

    /**
     * 在线聊天 是否为默认分组
     */
    public static final String IM_DEFAULT_GROUP = "1";
    /**
     * 在线聊天 图片文件保存路径
     */
    public static final String IM_UPLOAD_CUSTOM_PATH = "imfile";
    /**
     * 在线聊天 用户状态
     */
    public static final String IM_STATUS_ONLINE = "online";

    /**
     * 在线聊天 SOCKET消息类型
     */
    public static final String IM_SOCKET_TYPE = "chatMessage";

    /**
     * 在线聊天 是否开启默认添加好友 1是 0否
     */
    public static final String IM_DEFAULT_ADD_FRIEND = "1";

    /**
     * 在线聊天 用户好友缓存前缀
     */
    public static final String IM_PREFIX_USER_FRIEND_CACHE = "sys:cache:im:im_prefix_user_friend_";

    /*------------------公文文档---------------------*/

    /**
     * 公文文档上传自定义路径
     */
    public static final String UPLOAD_CUSTOM_PATH_OFFICIAL = "officialdoc";
    /**
     * 公文文档下载自定义路径
     */
    public static final String DOWNLOAD_CUSTOM_PATH_OFFICIAL = "officaldown";

    /**
     * WPS存储值类别(1 code文号 2 text（WPS模板还是公文发文模板）)
     */
    public static final String WPS_TYPE_1="1";
    public static final String WPS_TYPE_2="2";


    /**ONLINE 报表权限用 从request中获取地址栏后的参数*/
    String ONL_REP_URL_PARAM_STR="onlRepUrlParamStr";

    /**POST请求*/
    String HTTP_POST = "POST";

    /**PUT请求*/
    String HTTP_PUT = "PUT";

    /**PATCH请求*/
    String HTTP_PATCH = "PATCH";

    /**未知的*/
    String UNKNOWN = "unknown";

    /**字符串http*/
    String STR_HTTP = "http";

    /**String 类型的空值*/
    String STRING_NULL = "null";

    /**java.util.Date 包*/
    String JAVA_UTIL_DATE = "java.util.Date";

    /**.do*/
    String SPOT_DO = ".do";


    /**前端vue版本标识*/
    String VERSION="X-Version";

    /**前端vue版本*/
    String VERSION_VUE3="vue3";


    /**
     * 字典的缓存key
     */
    public static String CACHE_NAME_DICTIONARY = "linsir:dictionary";

    /**
     * 国际化缓存
     */
    public static String CACHE_NAME_I18N = "linsir:i18n";

    /**
     * 系统配置缓存
     */
    public static String CACHE_NAME_SYSTEM_CONFIG = "linsir:system-config";
}
