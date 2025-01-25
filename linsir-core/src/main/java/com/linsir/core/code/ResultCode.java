package com.linsir.core.code;


/**
 * @author ：linsir
 * @date ：Created in 2022/6/11 14:21
 * @description：基础统一 代码
 *
 * 三位数代码为HTTP返回码 ，四位数的代码为系统自动一的代码
 * @modified By：
 * @version: 0.0.1
 */
public enum ResultCode implements ICode,Cloneable{

    /*http状态返回代码 1xx（临时响应）*/
    /*继续*/
    CONTINUE(100,"请求者应当继续提出请求。返回此代码表示已收到请求的第一部分，正在等待其余部分。",Boolean.FALSE),
    /*切换协议*/
    SWITCHING_PROTOCOL(101,"请求者已要求服务器切换协议，服务器已确认并准备切换。",Boolean.FALSE),
    /*有潜在的性能问题*/
    WARN_PERFORMANCE_ISSUE(1001, "潜在的性能问题",Boolean.FALSE),
    /*---------------------------------------*/


    /*http状态返回代码 2xx （成功）*/
    /*成功*/
    SUCCESS(200, "服务器已成功处理了请求。", Boolean.TRUE),
    /*已创建*/
    CREATED(201,"请求成功并且服务器创建了新的资源。",Boolean.TRUE),
    /*已接受*/
    ACCEPTED(202,"服务器已接受请求，但尚未处理。",Boolean.TRUE),
    /*非授权信息*/
    NON_AUTHORITATIVE_INFORMATION(203,"服务器已成功处理了请求，但返回的信息可能来自另一来源。",Boolean.TRUE),
    /*无内容*/
    NO_CONTENT(204,"服务器成功处理了请求，但没有返回任何内容。",Boolean.TRUE),
    /*重置内容*/
    RESET_CONTENT(205,"请求已成功处理，但需重置内容",Boolean.TRUE),
    /*部分内容*/
    PARTIAL_CONTENT(206,"服务器成功处理了部分 GET 请求。",Boolean.TRUE),
    /*---------------------------------------*/

   /*http状态返回代码 3xx （重定向）*/
    /*多种选择*/
    MULTIPLE_CHOICE(300,"针对请求，服务器可执行多种操作。 服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择",Boolean.TRUE),
    /*永久移动*/
    MOVED_PERMANENTLY(301,"请求的网页已永久移动到新位置。 服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。",Boolean.TRUE),
    /*临时移动*/
    TEMPORARY_MOVEMENT(302,"服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。",Boolean.TRUE),
    /*查看其他位置*/
    VIEW_OTHER_LOCATIONS(303," 请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。",Boolean.TRUE),
    /*未修改*/
    NOT_CHANGED(304,"自从上次请求后，请求的网页未修改过。 服务器返回此响应时，不会返回网页内容。",Boolean.FALSE),
    /*使用代理*/
    USING_PROXY(305,"请求者只能使用代理访问请求的网页。 如果服务器返回此响应，还表示请求者应使用代理。",Boolean.FALSE),
    /*临时重定向*/
    TEMPORARY_REDIRECT(306,"服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。",Boolean.FALSE),
    /*---------------------------------------*/

    /*http状态返回代码 4xx（请求错误）*/
    /*错误请求*/
    FAILED(400, "系统正忙，请稍后再试, 服务器不理解请求的语法。", Boolean.FALSE),
    /*未授权*/
    UNAUTHORIZED(401, "没有认证,请求要求身份验证。", Boolean.FALSE),
    /*需要付费访问*/
    PAYMENT_REQUIRED(402, "Payment Required", Boolean.FALSE),
    /*禁止*/
    FORBIDDEN(403, "访问被拒绝, 服务器拒绝请求。", Boolean.FALSE),
    /*未找到*/
    NOT_FOUND(404, "Not Found, 服务器找不到请求的网页。", Boolean.FALSE),
    /*方法禁用*/
    METHOD_NOT_ALLOWED(405, "Method Not Allowed,禁用请求中指定的方法。", Boolean.FALSE),
    /*不接受*/
    NOT_ACCEPTABLE(406, "Not Acceptable,无法使用请求的内容特性响应请求的网页", Boolean.FALSE),
    /*需要代理授权*/
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required,此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。", Boolean.FALSE),
    /*请求超时*/
    REQUEST_TIMEOUT(408, "Request Timeout,服务器等候请求时发生超时。", Boolean.FALSE),
    /*冲突*/
    CONFLICT(409, "Conflict 服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。", Boolean.FALSE),
    /*已删除*/
    GONE(410, "Gone,如果请求的资源已永久删除，服务器就会返回此响应。", Boolean.FALSE),
    /*需要有效长度*/
    LENGTH_REQUIRED(411, "Length Required,器不接受不含有效内容长度标头字段的请求。", Boolean.FALSE),
    /*未满足前提条件*/
    PRECONDITION_FAILED(412, "Precondition Failed, 服务器未满足请求者在请求中设置的其中一个前提条件。", Boolean.FALSE),
    /*请求实体过大*/
    PAYLOAD_TOO_LARGE(413, "Payload Too Large 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。", Boolean.FALSE),
    /*请求的 URI 过长*/
    REQUEST_URI_TOO_LONG(414, "Request-URI Too Long 请求的 URI 过长", Boolean.FALSE),
    /*不支持的媒体类型*/
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type 不支持的媒体类型", Boolean.FALSE),
    /*请求范围不符合要求*/
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable 请求范围不符合要求", Boolean.FALSE),
    /*未满足期望值*/
    EXPECTATION_FAILED(417, "Expectation Failed未满足期望值", Boolean.FALSE),
    /**/
    I_AM_A_TEAPOT(418, "I'm a teapot", Boolean.FALSE),
    /*传入参数不对*/
    FAIL_INVALID_PARAM(4000, "请求参数不匹配",Boolean.FALSE),
    /*Token无效*/
    FAIL_INVALID_TOKEN(4001, "Token无效或已过期",Boolean.FALSE),
    /*token已被禁止*/
    TOKEN_ACCESS_FORBIDDEN(4002, "token已被禁止访问",Boolean.FALSE),
    /*没有权限执行该操作*/
    FAIL_NO_PERMISSION(4003, "无权执行该操作",Boolean.FALSE),
    /*请求资源不存在*/
    FAIL_NOT_FOUND(4004, "请求资源不存在",Boolean.FALSE),
    /*数据校验不通过*/
    FAIL_VALIDATION(4005, "数据校验不通过",Boolean.FALSE),
    /*操作执行失败*/
    FAIL_OPERATION(4006, "操作执行失败",Boolean.FALSE),
    /*无效操作*/
    INVALID_OPERATION(4007,"无效操作",Boolean.FALSE),
    /*请求连接超时*/
    FAIL_REQUEST_TIMEOUT(4008, "请求连接超时",Boolean.FALSE),
    /*认证不通过（用户名密码错误等认证失败场景）*/
    FAIL_AUTHENTICATION(4009, "认证不通过",Boolean.FALSE),
    /*---------------------------------------*/

    /*http状态返回代码 5xx（服务器错误）*/
    /*服务器内部错误*/
    SERVER_INTERNAL_ERROR(500,"服务器遇到错误，无法完成请求。",Boolean.FALSE),
    /*尚未实施*/
    NOT_YET_IMPLEMENTED(501,"服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。",Boolean.FALSE),
    /*错误网关*/
    BAD_GATEWAY(502,"服务器作为网关或代理，从上游服务器收到无效响应。",Boolean.FALSE),
    /*服务不可用*/
    SERVICE_UNAVAILABLE(503,"服务器目前无法使用（由于超载或停机维护）。通常，这只是暂时状态。",Boolean.FALSE),
    /*网关超时*/
    GATEWAY_TIMEOUT(504,"服务器作为网关或代理，但是没有及时从上游服务器收到请求。",Boolean.FALSE),
    /*HTTP 版本不受支持*/
    VERSION_IS_NOT_SUPPORTED(505,"务器不支持请求中所用的 HTTP 协议版本。",Boolean.FALSE),
    /*系统异常*/
    FAIL_EXCEPTION(5000, "系统异常",Boolean.FALSE),
    /*服务不可用*/
    FAIL_SERVICE_UNAVAILABLE(5003, "服务不可用",Boolean.FALSE),
    /*文件类型错误*/
    FILE_TYPE_ERROR(5004,"文件类型错误",Boolean.FALSE);
    ;


    private  int code;
    private String msg;
    private Boolean status;

    ResultCode(int code, String msg, Boolean status){
        this.code = code;
        this.msg = msg;
        this.status = status;
    }


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public boolean status() {
        return this.status;
    }

    @Override
    public void setMessage(String message) {
        this.msg = message;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public void setStatus(boolean status) {
       this.status = status;
    }
}
