package com.wangxin.springboot.common.utils;

import com.wangxin.springboot.common.annotation.Log;
import javassist.NotFoundException;

public class LogAnnotationWrapperUtil {

    private volatile static LogAnnotationWrapperUtil awu;

    private LogAnnotationWrapperUtil() {

    }

    public static LogAnnotationWrapperUtil get() {
        if (awu == null) {
            synchronized (LogAnnotationWrapperUtil.class) {
                if (awu == null) {
                    awu = new LogAnnotationWrapperUtil();
                }
            }
        }
        return awu;
    }

    public void setLog(String logStr) throws NotFoundException {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        LogAnnotationUtil.getLogAnnotationUtil().setAnnotationUtilFieldValue(className, methodName, Log.class.getName(), "logStr", logStr);
    }
}
