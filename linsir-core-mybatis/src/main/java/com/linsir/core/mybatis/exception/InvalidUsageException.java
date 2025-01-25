
package com.linsir.core.mybatis.exception;



import com.linsir.core.code.ICode;
import com.linsir.core.code.ResultCode;
import com.linsir.core.exception.BaseException;
import com.linsir.core.mybatis.util.S;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

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

    public InvalidUsageException()
    {
        super(ResultCode.INVALID_OPERATION.getMsg());
        this.code = ResultCode.INVALID_OPERATION;
        logCode(this.code);
    }

    /**
     * 根据code返回数据
     * @param code
     */
    public InvalidUsageException(ICode code) {
        super(code.getMsg());
        this.code = code;
        logCode(code);
    }

    public InvalidUsageException(ICode code, String msg) {
        super(msg);
        logCode(code);
        this.code = code;
    }

    /**
     * 自定义内容提示 错误编码：{}, 错误状态：{},错误信息:{}
     *
     * @param msg
     */
    public InvalidUsageException(String msg, ICode code, Object... args) {
        super(S.format(msg, args));
        logMsg(code,msg);
        this.code = code;
    }

    /**
     * 自定义内容提示
     *
     * @param msg
     */
    public InvalidUsageException(Throwable ex, String msg, ICode code, Object... args) {
        super(S.format(msg, args), ex);
        logMsg(code,msg);
        this.code = code;
    }

    /**
     * 默认无效的方法
     * */
    public static InvalidUsageException getInvalidUsageException(ICode code)
    {
        return new InvalidUsageException(code);
    }

}
