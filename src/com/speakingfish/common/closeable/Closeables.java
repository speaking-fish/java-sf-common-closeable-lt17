package com.speakingfish.common.closeable;

import java.lang.reflect.Method;

/**
 * @since 1.5
 * 
 * checks close() dynamically for correctly run in lesser than 1.7 environment (for ex: java.sql.*)
 * checks 1.7 AutoCloseable dynamically for correctly run in 1.7 environment
 *
 */
public class Closeables {

    public static final Class<?> __class__java_lang_AutoCloseable;
    public static final Method   __class__java_lang_AutoCloseable__close;
    
    protected static final String METHOD_NAME_close = "close";
    
    static {
        Class<?> resultClass;
        Method resultMethod;
        try {
            resultClass = Class.forName("java.lang.AutoCloseable");
            
            resultMethod = resultClass.getMethod(METHOD_NAME_close);
            
        } catch(Exception e) {
            resultClass  = null;
            resultMethod = null;
        }
        __class__java_lang_AutoCloseable        = resultClass ;
        __class__java_lang_AutoCloseable__close = resultMethod;
    }
    
    public static void catchClose(java.io.Closeable value) {
        try {
            value.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    protected static Method tryGetDuckTypeCloseMethod(Class<?> src) {
        try {
            return src.getClass().getMethod(METHOD_NAME_close);
        } catch(NoSuchMethodException e) {
            return null;
        }
    }

    public boolean canClose(Object value) {
        if(value instanceof java.io.Closeable) {
            return true;
        } else if((null != __class__java_lang_AutoCloseable) && __class__java_lang_AutoCloseable.isInstance(value)) {
            return true;
        } else if(null != tryGetDuckTypeCloseMethod(value.getClass())) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean tryClose(Object value) throws Exception {
        if(value instanceof java.io.Closeable) {
            ((java.io.Closeable) value).close();
            return true;
        } else if((null != __class__java_lang_AutoCloseable) && __class__java_lang_AutoCloseable.isInstance(value)) {
            __class__java_lang_AutoCloseable__close.invoke(value);
            return true;
        } else {
            final Method closeMethod = tryGetDuckTypeCloseMethod(value.getClass());
            if(null != closeMethod) {
                closeMethod.invoke(value);
                return true;
            } else {
                return false;
            }
        }
    }
    
    public static boolean tryCatchClose(Object value) {
        if(value instanceof java.io.Closeable) {
            try {
                ((java.io.Closeable) value).close();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return true;
        } else if((null != __class__java_lang_AutoCloseable) && __class__java_lang_AutoCloseable.isInstance(value)) {
            try {
                __class__java_lang_AutoCloseable__close.invoke(value);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            final Method closeMethod = tryGetDuckTypeCloseMethod(value.getClass());
            if(null != closeMethod) {
                try {
                    closeMethod.invoke(value);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                return false;
            }
        }
    }

}
