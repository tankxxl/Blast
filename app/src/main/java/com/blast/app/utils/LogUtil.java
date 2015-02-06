/**
 * 
 */
package com.blast.app.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.util.Log;

/**
 * @author rgz
 * 
 */
public class LogUtil {
	private static final String TAG = "LogHelper";
	private static final String TAGError = "LogError";
	private static boolean mIsDebugMode = true; // 获取堆栈信息会影响性能，发布应用时记得关闭
//	 private static boolean mIsDebugMode = false;
	private static final String CLASS_METHOD_LINE_FORMAT = "--%s.%s()  Line:%d  (%s)";

	public static void trace() {
		trace("");
	}

	public static void trace(String temp) {
		if (mIsDebugMode) {
			StackTraceElement traceElement = Thread.currentThread()
					.getStackTrace()[3];
			String logText = String.format(CLASS_METHOD_LINE_FORMAT,
					traceElement.getClassName(), traceElement.getMethodName(),
					traceElement.getLineNumber(), traceElement.getFileName());
			Log.d(TAG, "SDK-" + temp + logText);
		}
	}

    public static void trace(Exception e) {
        if (mIsDebugMode) {
            StackTraceElement traceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String logText = String.format(CLASS_METHOD_LINE_FORMAT,
                    traceElement.getClassName(), traceElement.getMethodName(),
                    traceElement.getLineNumber(), traceElement.getFileName());

            Log.d(TAGError, getErrorInfoFromException(e) + logText);
        }
    }
    
    public static void trace(Throwable throwable) {
        if (mIsDebugMode) {
            StackTraceElement traceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String logText = String.format(CLASS_METHOD_LINE_FORMAT,
                    traceElement.getClassName(), traceElement.getMethodName(),
                    traceElement.getLineNumber(), traceElement.getFileName());

            Log.d(TAGError, getErrorInfoFromException(throwable) + logText);
        }
    }
    
    public static String getErrorInfoFromException(Throwable e) {
    	if (e == null)
    		return "";
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }

    public static String getErrorInfoFromException(Exception e) {
    	if (e == null)
    		return "";
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }

}