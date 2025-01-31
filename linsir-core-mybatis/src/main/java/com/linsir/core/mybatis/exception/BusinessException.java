package com.linsir.core.mybatis.exception;


import com.linsir.core.code.ICode;
import com.linsir.core.code.ResultCode;
import com.linsir.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

/**
 * description：业务异常
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/30 17:12
 */

@Slf4j
public class BusinessException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 默认异常
     */
    public BusinessException()
    {
        super();
    }

    /**
     * 附加消息
     * @param msg
     */
    public BusinessException(String msg)
    {
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
