package com.linsir.core.mybatis.exception;

import com.linsir.core.code.ICode;
import com.linsir.core.code.ResultCode;
import com.linsir.core.exception.BaseException;
import com.linsir.core.mybatis.util.S;
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
     * 默认的验证异常
     */
   public ValidException() {
       super();
       code = ResultCode.FAIL_VALIDATION;
       logCode(code);
   }


    /**
     * 自定义 code代码
     * @param code
     */

   public ValidException(ICode code) {
       super(code.getMsg());
       this.code = code;
       logCode(code);
   }

    /**
     * 自定义验证信息
     * @param msg
     */
   public ValidException(String msg) {
       super(msg);
       this.code = ResultCode.FAIL_VALIDATION;
       logMsg(ResultCode.FAIL_VALIDATION,msg);
   }

    /**
     *
     * @param msg
     * @param code
     * @param args
     */
   public ValidException(String msg, ICode code, Object... args) {
        super(S.format(msg,args));
        this.code = code;
        logMsg(code,S.format(msg,args));
   }

   public static ValidException  getValidException() {
       return new ValidException();
   }


}
