package com.linsir.core.mybatis.exception;

import com.linsir.core.code.ICode;
import com.linsir.core.code.ResultCode;
import com.linsir.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

/**
 * @author linsir
 * @title: ValidExeption
 * @projectName linsir
 * @description: 验证异常
 * @date 2022/3/4 13:28
 */
@Slf4j
public class ValidException extends BaseException {

    @Serial
    private static final long serialVersionUID = -1203618387183809985L;

    /**
     * 默认
     */
    public ValidException() {
        super();
    }

    /**
     * 附加消息
     * @param msg
     */
    public ValidException(String msg)
    {
        super(msg);
    }

    /**
     * 默认code 异常
     * @param code
     */
    public ValidException(ICode code)
    {
        super(code);
    }

    /**
     *
     * @param msg
     * @param code
     */
    public ValidException(String msg, ICode code)
    {
        super(msg, code);
    }

    /**
     *
     * @param msg
     * @param code
     * @param args
     */
    public ValidException(String msg, ICode code, Object... args)
    {
        super( code,msg, args);
    }


    /**
     *
     * @param ex
     * @param msg
     * @param code
     * @param args
     */
    public ValidException(Throwable ex, String msg, ICode code, Object... args)
    {
        super(ex, msg, code, args);
    }

    /**
     * 默认的无效的异常
     * @return
     */
    public static InvalidUsageException getValidException()
    {
        return new InvalidUsageException(ResultCode.FAIL_VALIDATION);
    }

    /**
     * 根据信息获取默认的异常
     * @param msg
     * @return
     */
    public static InvalidUsageException getValidException(String msg)
    {
        return new InvalidUsageException(msg, ResultCode.FAIL_VALIDATION);
    }
}
