package com.e3mall.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 全局异常处理
 * @author zhaomeng
 * @Description:
 * @date 2018/10/20 19:11
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception exception) {
        //显示到控制台
        exception.printStackTrace();
        //打印日志
        logger.error("系统发生错误",exception);
        logger.debug("打印系统日志");
        logger.info("系统发生异常");
        //发邮件,发短信
        //显示错误页面
        ModelAndView  modelAndView=new ModelAndView();
        modelAndView.setViewName("/error/exception");
        return modelAndView;

    }
}
