package com.linsir.core.constant;

/**
 * description：Token 相关
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/2 16:27
 */
public interface TokenConstant {

    /*前缀*/
    public static final String PREFIX_USER_TOKEN  = "prefix_user_token_";

    /* Token缓存时间：3600秒即一小时 */
    public static final int  TOKEN_EXPIRE_TIME  = 3600;

    /* 登录二维码 */
    public static final String  LOGIN_QRCODE_PRE  = "QRCODELOGIN:";

    /*登陆二维码*/
    public static final String  LOGIN_QRCODE  = "LQ:";

    /* 登录二维码token */
    public static final String  LOGIN_QRCODE_TOKEN  = "LQT:";


    public final static String X_ACCESS_TOKEN = "X-Access-Token";
    public final static String X_SIGN = "X-Sign";
    public final static String X_TIMESTAMP = "X-TIMESTAMP";
    public final static String TOKEN_IS_INVALID_MSG = "Token失效，请重新登录!";

    public final static String AUTHORIZATION = "Authorization";

    public final static String BASIC = "Basic ";

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX_BEARER = "Bearer";


    /**
     * 多租户 请求头
     */
    public final static String TENANT_ID = "tenant-id";

    /**
     * 微服务读取配置文件属性 服务地址
     */
    public final static String CLOUD_SERVER_KEY = "spring.cloud.nacos.discovery.server-addr";

    /**
     * 第三方登录 验证密码/创建用户 都需要设置一个操作码 防止被恶意调用
     */
    public final static String THIRD_LOGIN_CODE = "third_login_code";

    /**
     * 第三方APP同步方向：本地 --> 第三方APP
     */
    String THIRD_SYNC_TO_APP = "SYNC_TO_APP";
    /**
     * 第三方APP同步方向：第三方APP --> 本地
     */
    String THIRD_SYNC_TO_LOCAL = "SYNC_TO_LOCAL";
}
