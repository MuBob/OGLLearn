package com.ciii.bobmu.ogllearn.utils;

import android.util.Log;

/**
 * Created by bob on 2019/1/13.
 */

public class LogUtil {

    private static final boolean LOG_ON=true;

    private static final String TAG = "LogUtilTAG";
    private static class InstancHolder{
        private static final LogUtil instance=new LogUtil();
    }
    /** 五种Log日志类型 */
    /**
     * 调试日志类型
     */
    public static final int DEBUG = 111;
    /**
     * 错误日志类型
     */
    public static final int ERROR = 112;
    /**
     * 信息日志类型
     */
    public static final int INFO = 113;
    /**
     * 详细信息日志类型
     */
    public static final int VERBOSE = 114;
    /**
     * 警告日志类型
     */
    public static final int WARN = 115;

    public static void i(String Tag, String Message) {
        LogShow(Tag, Message, INFO);
    }

    public static void i(String Tag, String Message, Throwable t) {
        LogShow(Tag, createMessage(Message, t), INFO);
    }

    public static void e(String Tag, String Message) {
        LogShow(Tag, Message, ERROR);
    }

    public static void e(String Tag, String Message, Throwable t) {
        LogShow(Tag, createMessage(Message, t), ERROR);
    }

    public static void d(String Tag, String Message) {
        LogShow(Tag, Message, DEBUG);
    }

    public static void d(String Tag, String Message, Throwable t) {
        LogShow(Tag, createMessage(Message, t), DEBUG);
    }

    public static void w(String Tag, String Message) {
        LogShow(Tag, Message, WARN);
    }

    public static void w(String Tag, String Message, Throwable t) {
        LogShow(Tag, createMessage(Message, t), WARN);
    }

    public static void v(String Tag, String Message) {
        LogShow(Tag, Message, VERBOSE);
    }

    public static void v(String Tag, String Message, Throwable t) {
        LogShow(Tag, createMessage(Message, t), VERBOSE);
    }

    private static String createMessage(String msg, Throwable t) {
        return msg + "\r\nThrowable:" + t;
    }

    public static void LogShow(String Tag, String Message) {
        LogShow(Tag, Message, INFO);
    }

    private LogUtil() {
    }

    public static LogUtil getInstance() {
        return InstancHolder.instance;
    }

    /**
     * 显示，打印日志
     */
    private static void LogShow(String Tag, String Message, int Style) {
        if (LOG_ON) {
            switch (Style) {
                case DEBUG: {
                    Log.d(Tag, getSupper() + Message);
                }
                break;
                case ERROR: {
                    Log.e(Tag, getSupper() + Message);
                }
                break;
                case INFO: {
                    Log.i(Tag, getSupper() + Message);
                }
                break;
                case VERBOSE: {
                    Log.v(Tag, getSupper() + Message);
                }
                break;
                case WARN: {
                    Log.w(Tag, getSupper() + Message);
                }
                break;
                default:
                    break;
            }
        }
    }

    private static String getSupper() {
        String result = null;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//        printStackTrace(stackTrace);
        for (int callIndex =2; callIndex < stackTrace.length ; callIndex++) {
            StackTraceElement stackTraceElement = stackTrace[callIndex];
            if (stackTraceElement != null) {
                String className = stackTraceElement.getClassName();
//                android.util.Log.i(TAG, "LogUtil.getSupper: className="+className+", isMyself="+isMyself);
                if (LogUtil.class.getName().equalsIgnoreCase(className)) {
                    continue;
                } else if ("com.ciii.generate.basecsip.utils.Log".equalsIgnoreCase(className)) {
                    continue;
                } else if (result==null) {
                    result = " from MyClass " + className + "::" +  stackTraceElement.getMethodName() + "()-->";
                    continue;
                } else {
                    result = " from SuperClass " + className + "::" +  stackTraceElement.getMethodName() + "()-->";
                    break;
                }
            }
        }
        return result;
    }
}
