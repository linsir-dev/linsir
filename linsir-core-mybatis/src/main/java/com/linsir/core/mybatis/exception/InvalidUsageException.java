
package com.linsir.core.mybatis.exception;



import com.linsir.core.code.ICode;
import com.linsir.core.code.ResultCode;
import com.linsir.core.exception.BaseException;
import com.linsir.core.mybatis.util.S;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;


/**
 * 无效使用异常类 InvalidUsageException
 *
 *  无效操作类
 * @author : linsir
 * @version : v1.2.0
 * @date 2025/01/25
 */
@Slf4j
public class InvalidUsageException extends BaseException {

    @Serial
    private static final long serialVersionUID = -1203618387183809985L;

    /**
     * 默认异常
     */
    public InvalidUsageException()
    {
        super();
    }

    /**
     * 附加消息
     * @param msg
     */
    public InvalidUsageException(String msg)
    {
        super(msg);
    }

    /**
     * 默认code 异常
     * @param code
     */
    public InvalidUsageException(ICode code)
    {
        super(code);
    }

    /**
     *
     * @param msg
     * @param code
     */
    public InvalidUsageException(String msg, ICode code)
    {
        super(msg, code);
    }

    /**
     *
     * @param msg
     * @param code
     * @param args
     */
    public InvalidUsageException( ICode code, String msg,Object... args)
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
    public InvalidUsageException(Throwable ex, String msg, ICode code, Object... args)
    {
        super(ex, msg, code, args);
    }


    /**
     * 默认的无效的异常
     * @return
     */
    public static InvalidUsageException getInvalidUsageException()
    {
        return new InvalidUsageException(ResultCode.INVALID_OPERATION);
    }

    /**
     * 根据信息获取默认的异常
     * @param msg
     * @return
     */
    public static InvalidUsageException getInvalidUsageException(String msg)
    {
        return new InvalidUsageException(msg, ResultCode.INVALID_OPERATION);
    }

}
