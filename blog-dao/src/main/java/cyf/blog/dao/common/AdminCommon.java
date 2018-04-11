package cyf.blog.dao.common;

import cyf.blog.dao.model.Metas;
import cyf.blog.util.UUID;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 判断category和cat的交集
     *
     * @param cats
     * @return
     */
    public static boolean exist_cat(Metas category, String cats) {
        String[] arr = StringUtils.split(cats, ",");
        if (null != arr && arr.length > 0) {
            for (String c : arr) {
                if (c.trim().equals(category.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
