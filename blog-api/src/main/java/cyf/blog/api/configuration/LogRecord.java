package cyf.blog.api.configuration;

import cyf.blog.base.enums.OperateObject;
import cyf.blog.base.enums.OperateType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 后台操作记录
 * @author Cheng Yufei
 * @create 2018-03-24 15:26
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecord {

    OperateType operateType();

    OperateObject operateObject();


}
