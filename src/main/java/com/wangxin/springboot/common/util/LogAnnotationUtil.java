package com.wangxin.springboot.common.util;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

// 动态查看、修改注解中属性
public class LogAnnotationUtil {

    // volatile关键字防止指令重排
    private volatile static LogAnnotationUtil logAnnotationUtil;

    public LogAnnotationUtil() {

    }

    public static LogAnnotationUtil getLogAnnotationUtil() {
        if (logAnnotationUtil == null) {
            synchronized (LogAnnotationUtil.class) {
                if (logAnnotationUtil == null) {
                    logAnnotationUtil = new LogAnnotationUtil();
                }
            }
        }
        return logAnnotationUtil;
    }

    /**
     * 修改注解上的属性值
     * @param className     当前类名
     * @param methodName    当前方法名
     * @param annoName      方法上的注解名
     * @param fieldName     注解中的属性名
     * @param fieldValue    注解中的属性值
     * @throws NotFoundException
     */
    public void setAnnotationUtilFieldValue(String className, String methodName, String annoName,
                                            String fieldName, String fieldValue) throws NotFoundException{
        ClassPool classPool = ClassPool.getDefault();
        // 下面两段新加
        ClassClassPath classPath = new ClassClassPath((this.getClass()));
        classPool.insertClassPath(classPath);
        // 此行在Linux部署时报错
        CtClass ct = classPool.get(className);
        // getDeclaredMethod()返回类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，
        // 但不包括继承的方法。
        CtMethod ctMethod = ct.getDeclaredMethod(methodName);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        ConstPool constPool = methodInfo.getConstPool();
        AnnotationsAttribute attr = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
        Annotation annotation = attr.getAnnotation(annoName);
        // TODO 这里没有对注解的参数个数进行比较
        if (annotation != null) {
            annotation.addMemberValue(fieldName, new StringMemberValue(fieldValue, constPool));
            attr.setAnnotation(annotation);
            methodInfo.addAttribute(attr);
        }
    }

    public String getAnnotationFieldValue(String className, String methodName, String annoName,
                                          String fieldName) throws NotFoundException {
        ClassPool classPool = ClassPool.getDefault();
        // 下面两段新加
        ClassClassPath classPath = new ClassClassPath((this.getClass()));
        classPool.insertClassPath(classPath);
        // 此行在Linux部署时报错
        CtClass ctClass = classPool.get(className);
        CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        AnnotationsAttribute attr = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
        String value = "";
        if (attr != null) {
            Annotation an = attr.getAnnotation(annoName);
            if (an != null) {
                value = ((StringMemberValue) an.getMemberValue(fieldName)).getValue();
            }
        }
        return value;
    }

}
