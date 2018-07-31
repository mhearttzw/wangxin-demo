package com.wangxin.springboot.common.aop;

import com.wangxin.springboot.common.annotation.Log;
import com.wangxin.springboot.common.utils.LogAnnotationUtil;
import com.wangxin.springboot.common.utils.LogFileUtil;
import javassist.NotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@Component
@Aspect
public class LogAop {

    private Logger logger = LoggerFactory.getLogger(LogAop.class);

    // 设置切点表达式，匹配com.wangxin.springboot包及其子包下面所有类的public方法
    // 配置多个切点
    @Pointcut("execution(* com.wangxin.springboot.controller..*(..))||execution(* com.wangxin.springboot.service..*(..))")
    private void pointcut(){

    }

    @Before(value = "pointcut()")
    public void After(JoinPoint joinPoint) throws NotFoundException, ClassNotFoundException {
        // 拿到切点的类名、方法名、目标方法的参数对象
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String logStr = LogAnnotationUtil.getLogAnnotationUtil().getAnnotationFieldValue(className,
                methodName, Log.class.getName(), "logStr");
        if (!StringUtils.isEmpty(logStr)) {
            logger.info("\n类["+className+"]-方法["+methodName+"]开始执行------");
            // logger.error("函数名：" + methodName);
            for (int i = 0; i < args.length; i++) {
                logger.info("参数名：" + args[i]);
            }
            // TODO debug级别日志什么时候会出现呢？
            //logger.debug("获取日志：" + logStr);
            //logger.info("详细日志信息：" + logStr);
            // 数据库、文件记录操作
            // TODO
            /*LogFileUtil.write("类名：" + className + "\r\n");
            LogFileUtil.write("函数名：" + methodName + "\r\n");
            for (int i = 0; i < args.length; i++) {
                LogFileUtil.write("参数名：" + args[i]);
            }
            LogFileUtil.write("\r\n功能：" + logStr);*/
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
