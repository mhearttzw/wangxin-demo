package com.wangxin.springboot.interceptor;

import com.wangxin.springboot.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private final static String REQUEST_ID = "requestId";
    private final static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    /**
     * 在请求处理之前调用（Controller方法调用之前）
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUUID = MDC.get(REQUEST_ID);
        if (requestUUID == null || "".equals(requestUUID)) {
            String xForwardedForHeader = request.getHeader("X-Forwarded-For");
            String remoteIp = request.getRemoteAddr();
            String uuid = CommonUtil.getUUID();
            logger.info("put requestId ({}) to logger", uuid);
            logger.info("request id:{}, client ip:{}, X-Forwarded-For:{}", uuid, remoteIp, xForwardedForHeader);
            MDC.put(REQUEST_ID, uuid);
        }
        return true;
    }

    /**
     * 在请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uuid = MDC.get(REQUEST_ID);
        logger.info("remove requestId ({}) from logger", uuid);
        MDC.remove(REQUEST_ID);
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应视图之后执行
     * （主要是执行资源清理工作）
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("ControllerInterceptor afterCompletion 整个请求处理完毕清除 logback MDC requestUUID");
        MDC.clear();
    }
}


