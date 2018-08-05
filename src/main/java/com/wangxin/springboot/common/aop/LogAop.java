package com.wangxin.springboot.common.aop;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangxin.springboot.common.annotation.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class LogAop {

    private Logger logger = LoggerFactory.getLogger(LogAop.class);
    private static final String STRING_START = "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n";
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String STRING_END   = "\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n";


    // 设置切点表达式，匹配com.wangxin.springboot包及其子包下面所有类的public方法
    // 配置多个切点
    @Pointcut("execution(* com.wangxin.springboot.controller..*(..))||execution(* com.wangxin.springboot.service..*(..))")
    private void pointcut(){

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();

        StringBuffer classAndMethod = new StringBuffer();

        Log classAnnotation = targetClass.getAnnotation(Log.class);
        Log methodAnnotation = method.getAnnotation(Log.class);

        if (classAnnotation != null) {
            if (classAnnotation.ignore()) {
                return joinPoint.proceed();
            }
            classAndMethod.append(classAnnotation.logStr()).append("-");
        }

        if (methodAnnotation != null) {
            if (methodAnnotation.ignore()) {
                return joinPoint.proceed();
            }
            classAndMethod.append(methodAnnotation.logStr());
        }

        String target = "类：" +targetClass.getName() + " 方法：" + method.getName();

        String params = null;
        params = JSONObject.toJSONStringWithDateFormat(joinPoint.getArgs(), dateFormat, SerializerFeature.WriteMapNullValue);

        logger.info(STRING_START + "{} 开始调用--> {} 参数:{}", classAndMethod.toString(), target, params);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeConsuming = System.currentTimeMillis() - start;

        logger.info("\n{} 调用结束<-- {} 返回值:{} 耗时:{}ms" + STRING_END, classAndMethod.toString(), target, JSONObject.toJSONStringWithDateFormat(result, dateFormat, SerializerFeature.WriteMapNullValue), timeConsuming);
        return result;
    }

    /*@Before(value = "pointcut()")
    public void After(JoinPoint joinPoint) throws NotFoundException, ClassNotFoundException {
        // 拿到切点的类名、方法名、目标方法的参数对象
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        //
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();

        StringBuffer classAndMethod = new StringBuffer();

        Log classAnnotation = targetClass.getAnnotation(Log.class);
        Log methodAnnotation = method.getAnnotation(Log.class);

        classAndMethod.append(classAnnotation.logStr()).append("-");
        classAndMethod.append(methodAnnotation.logStr());

        String target = targetClass.getName() + "#" + method.getName();

        log.info(STRING_START + "{} 开始调用--> {} 参数:{}", classAndMethod.toString(), target, params);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeConsuming = System.currentTimeMillis() - start;

        log.info("\n{} 调用结束<-- {} 返回值:{} 耗时:{}ms" + STRING_END, classAndMethod.toString(), target, JSONObject.toJSONStringWithDateFormat(result, dateFormat, SerializerFeature.WriteMapNullValue), timeConsuming);

//        String logStr = LogAnnotationUtil.getLogAnnotationUtil().getAnnotationFieldValue(className,
//                methodName, Log.class.getName(), "logStr");
        String params = null;
        // TODO 下面这行将数组直接转化为字符串，可记
        params = JSONObject.toJSONStringWithDateFormat(joinPoint.getArgs(), dateFormat, SerializerFeature.WriteMapNullValue);
        logger.info(STRING_START + "{} 开始调用--> {} 参数:{}", className, methodName, params);
        logger.info("函数功能：{}", logStr);

        *//*if (!StringUtils.isEmpty(logStr)) {
            logger.info("类["+className+"]-方法["+methodName+"]开始执行------");
            for (int i = 0; i < args.length; i++) {
                logger.info("参数名：{}", args[i]);
            }
            logger.info("函数功能：{}", logStr);
            // TODO 数据库、文件记录操作
        }*//*
    }*/

    @AfterReturning(returning = "object", pointcut = "pointcut()")
    public void afterReturning(Object object) {

    }


}
