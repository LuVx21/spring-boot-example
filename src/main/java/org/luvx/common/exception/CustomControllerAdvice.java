package org.luvx.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: org.luvx.common.exception
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/21 19:52
 */
@Slf4j
@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler({Exception.class})
    public ModelAndView handException(HttpServletRequest request, Exception e) {
        log.error("全局异常处理: ", e);
        System.out.println(request.getRequestURI());

        ModelAndView mv = new ModelAndView();
        mv.addObject("message", e.getMessage());
        mv.setViewName("global_error");
        return mv;
    }
}
