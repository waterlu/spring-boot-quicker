package cn.lu.boot.quicker.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lutiehua on 2017/9/22.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult handleBusinessException(Exception e) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseResult.EXCEPTION);
        responseResult.setMessage(e.getMessage());
        responseResult.setData(null);
        return responseResult;
    }

}