package cyf.blog.util;

import org.slf4j.Logger;

/**
 * 日志打印工具类<br/>
 */
public class LogUtil {

    public static ThreadLocal<String> uuid = new ThreadLocal<>();

    static{
        uuid.set("[初始化] ");
    }

    public static void info(Logger log, String msg, Object... value) {
        log.info(uuid.get() + msg, value);
    }

    public static void debug(Logger log, String msg, Object... value){
        log.debug(uuid.get() + msg, value);
    }

    public static void warn(Logger log, String msg, Object... value){
        log.warn(uuid.get() + msg, value);
    }

    public static void error(Logger log, String msg, Object... value) {
        log.error(uuid.get() + msg, value);
    }

    public static void error(Logger log, String msg, Throwable var2) {
        log.error(uuid.get() + msg, var2);
    }

}
