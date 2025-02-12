package com.linsir.core.mybatis.controller;


import com.linsir.core.code.ResultCode;
import com.linsir.core.mybatis.util.V;
import com.linsir.core.mybatis.vo.JsonResult;
import com.linsir.core.results.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * description：ExceptionHandler
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/11 20:17
 */
@RestControllerAdvice
@Slf4j
public class GlobalController {


    //（1）全局数据绑定
    //应用到所有@RequestMapping注解方法
    //此处将键值对添加到全局，注解了@RequestMapping的方法都可以获得此键值对
    @ModelAttribute
    public void addUser(Model model) {
        model.addAttribute("msg", "此处将键值对添加到全局，注解了@RequestMapping的方法都可以获得此键值对");
    }

    //（2）全局数据预处理
    //应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
    //用来设置WebDataBinder
    @InitBinder("user")
    public void initBinder(WebDataBinder binder) {
    }


    /**
     * 服务器500错误
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleException(Exception e) {
        log.error("Unexpected exception occurred: ", e);
        return new JsonResult(ResultCode.SERVER_INTERNAL_ERROR);
    }




    @ResponseBody
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public R validationExceptionHandler(Exception ex) {
        BindingResult br = null;
        R result=null;
        if(ex instanceof BindException){
            br = ((BindException)ex).getBindingResult();
        }
        if (br != null && br.hasErrors()) {
            result = new JsonResult(ResultCode.FAIL_INVALID_PARAM);
            String validateErrorMsg = V.getBindingError(br);
            log.warn("数据校验失败, {}: {}", br.getObjectName(), validateErrorMsg);
        }
        return result;
    }
}
