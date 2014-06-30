package com.manolovn.android.apt_sample;

import android.util.Log;
import com.manolovn.android.apt_sample.annotations.processor.DebugProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * MethodDebug
 *
 * @author manolovn
 */
public final class MethodDebug {

    private MethodDebug() {
        throw new AssertionError("No instances");
    }

    public static void scan(Object target) {
        Class<?> targetClass = target.getClass();
        Log.d("method debug", "target class >>> " + targetClass.getName());
        try {
            Class<?> aClass = Class.forName(targetClass + DebugProcessor.SUFFIX);
            try {
                Method method = aClass.getMethod("execute");
                try {
                    method.invoke(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //scan(targetClass.getSuperclass());
        }
    }

}
