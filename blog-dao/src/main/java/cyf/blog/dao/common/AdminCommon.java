package cyf.blog.dao.common;

import cyf.blog.util.UUID;
import org.springframework.stereotype.Component;

/**
 * 后台通用配置
 * @author Cheng Yufei
 * @create 2018-03-21 16:54
 **/
@Component
public class AdminCommon {


    public String random(int max,String str) {
        return UUID.random(1, max) + str;
    }
}
