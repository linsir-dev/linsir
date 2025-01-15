package com.linsir.core.vo.jsonResults;


import com.linsir.core.code.BaseCode;
import com.linsir.core.code.ICode;
import com.linsir.core.plugin.JsonResultFilter;
import com.linsir.core.util.S;
import com.linsir.core.util.V;
import com.linsir.core.util.ContextHolder;
import com.linsir.core.vo.IResult;
import com.linsir.core.vo.Pagination;

import java.io.Serial;
import java.io.Serializable;

/**
 * author ：linsir
 * description：返回Json数据标注格式
 * date ：2025/1/13 19:50
 */
public class JsonResult<T> implements IResult<Integer,String,T>, Serializable {

    @Serial
    private static final long serialVersionUID = 1001L;

    /***
     * 状态码
     */
    private Integer code;
    /***
     * 消息内容
     */
    private String msg;
    /***
     * 返回结果数据
     */
    private T data;


    /**
     * 默认成功，无返回数据
     */
    public JsonResult(){
    }


    /**
     * 成功或失败
     */
    public JsonResult(boolean ok){
        this(ok? BaseCode.SUCCESS: BaseCode.FAIL_OPERATION);
    }

    /**
     * 默认成功，有返回数据
     */
    public JsonResult(T data){
        this.code = BaseCode.SUCCESS.getCode();
        this.msg = BaseCode.SUCCESS.getMsg();
        initMsg(null);
        this.data = data;
    }


    /**
     * 默认成功，有返回数据、及附加提示信息
     */
    public JsonResult(T data, String additionalMsg){
        this.code = BaseCode.SUCCESS.getCode();
        this.msg = BaseCode.SUCCESS.getMsg();
        initMsg(additionalMsg);
        this.data = data;
    }


    /**
     * 只返回状态码，不返回数据
     * @param code
     */
    public JsonResult(ICode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        initMsg(null);
        this.data = null;
    }

    /***
     * 非成功，指定状态及附加提示信息
     * @param code
     * @param additionalMsg
     */
    public JsonResult(ICode code, String additionalMsg){
        this.code = code.getCode();
        this.msg = code.getMsg();
        initMsg(additionalMsg);
        this.data = null;
    }


    /**
     * 非成功，指定状态、返回数据
     * @param code
     * @param data
     */
    public JsonResult(ICode code, T data){
        this.code = code.getCode();
        this.msg = code.getMsg();
        initMsg(null);
        this.data = data;
    }

    /**
     * 非成功，指定状态、返回数据、及附加提示信息
     */
    public JsonResult(ICode code, T data, String additionalMsg){
        this.code = code.getCode();
        this.msg = code.getMsg();
        initMsg(additionalMsg);
        this.data = data;
    }


    /***
     * 自定义JsonResult
     * @param code
     * @param message
     * @param data
     */
    public JsonResult(int code, String message, T data){
        this.code = code;
        this.msg = message;
        this.data = data;
    }



    /**
     * 设置status，如果msg为空则msg设置为status.label
     * @param code
     * @return
     */
    public JsonResult<T> code(ICode code){
        this.code = code.getCode();
        if(this.msg == null){
            this.msg = code.getMsg();
        }
        return this;
    }


    /**
     * 设置返回数据
     * @param data
     * @return
     */
    public JsonResult<T> data(T data){
        this.data = data;
        return this;
    }

    /**
     * 设置msg
     * @param additionalMsg
     * @return
     */
    public JsonResult<T> msg(String additionalMsg){
        initMsg(additionalMsg);
        return this;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /***
     * 绑定分页信息
     * @param pagination
     */
    @SuppressWarnings("unchecked")
    public PagingJsonResult<T> bindPagination(Pagination pagination){
        return new PagingJsonResult<T>(this, pagination);
    }

    /**
     * 赋值msg（去掉重复前缀以支持与BusinessException嵌套使用）
     * @param additionalMsg
     */
    private void initMsg(String additionalMsg){
        if(V.notEmpty(additionalMsg)){
            if(S.startsWith(additionalMsg, this.msg)){
                this.msg = additionalMsg;
            }
            else{
                this.msg += ": " + additionalMsg;
            }
        }
    }

    /***
     * 请求处理成功
     */
    public static <T> JsonResult<T> OK(){
        return new JsonResult<>(BaseCode.SUCCESS);
    }


    /***
     * 请求处理成功
     */
    public static <T> JsonResult<T> OK(T data){
        return new JsonResult<>(BaseCode.SUCCESS, data);
    }


    /***
     * 部分成功（一般用于批量处理场景，只处理筛选后的合法数据）
     */
    public static <T> JsonResult<T> WARN_PARTIAL_SUCCESS(String msg){
        return new JsonResult<T>(BaseCode.WARN_PARTIAL_SUCCESS).msg(msg);
    }
    /***
     * 有潜在的性能问题
     */
    public static <T> JsonResult<T> WARN_PERFORMANCE_ISSUE(String msg){
        return new JsonResult<T>(BaseCode.WARN_PERFORMANCE_ISSUE).msg(msg);
    }
    /***
     * 传入参数不对
     */
    public static <T> JsonResult<T> FAIL_INVALID_PARAM(String msg){
        return new JsonResult<T>(BaseCode.FAIL_INVALID_PARAM).msg(msg);
    }
    /***
     * Token无效或已过期
     */
    public static <T> JsonResult<T> FAIL_INVALID_TOKEN(String msg){
        return new JsonResult<T>(BaseCode.FAIL_INVALID_TOKEN).msg(msg);
    }
    /***
     * 没有权限执行该操作
     */
    public static <T> JsonResult<T> FAIL_NO_PERMISSION(String msg){
        return new JsonResult<T>(BaseCode.FAIL_NO_PERMISSION).msg(msg);
    }
    /***
     * 请求资源不存在
     */
    public static <T> JsonResult<T> FAIL_NOT_FOUND(String msg){
        return new JsonResult<T>(BaseCode.FAIL_NOT_FOUND).msg(msg);
    }
    /***
     * 数据校验不通过
     */
    public static <T> JsonResult<T> FAIL_VALIDATION(String msg){
        return new JsonResult<T>(BaseCode.FAIL_VALIDATION).msg(msg);
    }
    /***
     * 操作执行失败
     */
    public static <T> JsonResult<T> FAIL_OPERATION(String msg){
        return new JsonResult<T>(BaseCode.FAIL_OPERATION).msg(msg);
    }
    /***
     * 系统异常
     */
    public static <T> JsonResult<T> FAIL_EXCEPTION(String msg){
        return new JsonResult<T>(BaseCode.FAIL_EXCEPTION).msg(msg);
    }
    /***
     * 服务不可用
     */
    public static <T> JsonResult<T> FAIL_FAIL_REQUEST_TIMEOUT(String msg){
        return new JsonResult<T>(BaseCode.FAIL_REQUEST_TIMEOUT).msg(msg);
    }
    /***
     * 服务不可用
     */
    public static <T> JsonResult<T> FAIL_SERVICE_UNAVAILABLE(String msg){
        return new JsonResult<T>(BaseCode.FAIL_SERVICE_UNAVAILABLE).msg(msg);
    }

    /***
     * 认证不通过
     */
    public static <T> JsonResult<T> FAIL_AUTHENTICATION(String msg){
        return new JsonResult<T>(BaseCode.FAIL_AUTHENTICATION).msg(msg);
    }

    /**
     * 过滤jsonResult结果，用于全局忽略某些字段等场景
     * @param data
     * @param <T>
     * @return
     */
    private static boolean jsonResultFilterChecked = false;
    private static JsonResultFilter jsonResultFilter;
    private static <T> T filterJsonResultData(T data){
        // 不启用过滤
        if(jsonResultFilterChecked && jsonResultFilter == null){
            return data;
        }
        if(!jsonResultFilterChecked){
            jsonResultFilter = ContextHolder.getBean(JsonResultFilter.class);
            jsonResultFilterChecked = true;
        }
        if(jsonResultFilter != null){
            jsonResultFilter.filterData(data);
        }
        return data;
    }
}
