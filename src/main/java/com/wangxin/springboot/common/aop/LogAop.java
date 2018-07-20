package com.wangxin.springboot.common.aop;

import com.wangxin.springboot.common.annotation.Log;
import com.wangxin.springboot.common.util.LogAnnotationUtil;
import com.wangxin.springboot.common.util.LogFileUtil;
import javassist.NotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.Method;

@Component
@Aspect
public class LogAop {

    private Logger logger = LoggerFactory.getLogger(LogAop.class);

    // 设置切点表达式，匹配com.wangxin.springboot包及其子包下面所有类的public方法
    @Pointcut("execution(* com.wangxin.springboot.controller..*(..))")
    private void pointcut(){

    }

    @After(value = "pointcut()")
    public void After(JoinPoint joinPoint) throws NotFoundException, ClassNotFoundException {
        // 拿到切点的类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String logStr = LogAnnotationUtil.getLogAnnotationUtil().getAnnotationFieldValue(className,
                methodName, Log.class.getName(), "logStr");
        if (!StringUtils.isEmpty(logStr)) {
            logger.error("获取日志：" + logStr);
            // debug级别日志什么时候会出现呢？
            logger.debug("获取日志：" + logStr);
            logger.error("error级别日志：" + logStr);
            // 数据库、文件记录操作
            // TODO
            LogFileUtil.write(logStr);
        }
    }

    // 方法后置切面
    // JoinPoint对象封装了SpringAop中切面方法的信息，提供当前被通知方法的目标对象、代理对象、方法参数等数据，
    // 在切面方法中添加JoinPoint参数，就可以获取到封装了该方法信息的JoinPoint对象。
    /*@After(value = "pointcut()")
    public void After(JoinPoint joinPoint) throws NotFoundException, ClassNotFoundException {
        // 拿到切点的类名、方法名、方法参数
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        // 反射加载切点类，遍历类中所有的方法
        Class<?> targetClass = Class.forName(className);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            // 如果遍历到类中的方法名与切点的方法名一致，并且参数个数也一致，就说明切点找到了
            if (method.getName().equalsIgnoreCase(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length) {
                    // 获取切点上的注解
                    Log logAnnotation = method.getAnnotation(Log.class);
                    if (logAnnotation != null) {
                        // 获取注解中的日志信息，并输出
                        String logStr = logAnnotation.logStr();
                        // debug级别日志什么时候会出现呢？
                        logger.debug("获取日志：" + logStr);
                        logger.error("error级别日志：" + logStr);
                        // 数据库、文件记录操作
                        // TODO
                        LogFileUtil.write(logStr);
                    }
                }
            }
        }
    }*/


}
