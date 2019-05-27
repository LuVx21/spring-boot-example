package org.luvx.common.exception;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.luvx.modules.common.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: org.luvx.common.exception
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/21 19:52
 */
@Slf4j
@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler({Exception.class})
    public R<String> handException(HttpServletRequest request, Exception e) {
        log.error("全局异常处理: ", e);
        return Response.restResult(request.getRequestURI(), ApiErrorCode.FAILED)
                .setMsg(e.getMessage());
    }
}
