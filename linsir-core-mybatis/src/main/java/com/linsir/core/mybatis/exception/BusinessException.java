package com.linsir.core.mybatis.exception;

import com.linsir.core.code.ICode;
import com.linsir.core.code.ResultCode;
import com.linsir.core.exception.BaseException;

import java.io.Serial;

/**
 * 通用的业务异常类 BusinessException
 * (json形式返回值同JsonResult，便于前端统一处理)
 *
 * @author : 1.2.0
 * @version : v2.0
 * @Date 2019-07-11  11:10
 */
public class BusinessException extends BaseException {

    @Serial
    private static final long serialVersionUID = 6947618826898130771L;

    /**
    * 默认
    * */
    public BusinessException() {
        super();
    }

    /**
     *
     * @param msg
     */
    public BusinessException(String msg) {
        super(msg);
    }

    /**
     * 默认code 异常
     * @param code
     */
    public BusinessException(ICode code)
    {
        super(code);
    }

    public BusinessException(ICode code, Exception e)
    {
        super(code,e);
    }

    /**
     *
     * @param msg
     * @param code
     */
    public BusinessException(String msg, ICode code)
    {
        super(msg, code);
    }

    /**
     *
     * @param msg
     * @param code
     * @param args
     */
    public BusinessException( ICode code,String msg, Object... args)
    {
        super(code, msg, args);
    }


    /**
     *
     * @param ex
     * @param msg
     * @param code
     * @param args
     */
    public BusinessException(Throwable ex, String msg, ICode code, Object... args)
    {
        super(ex, msg, code, args);
    }


    /**
     * 默认的 业务异常
     * @return
     */
    public static BusinessException getBusinessException()
    {
        return new BusinessException(ResultCode.FAIL_OPERATION);
    }

    /**
     * 默认业务异常操作
     * @param msg
     * @return
     */
    public static BusinessException getBusinessException(String msg)
    {
        return new BusinessException(msg, ResultCode.FAIL_OPERATION);
    }

}
