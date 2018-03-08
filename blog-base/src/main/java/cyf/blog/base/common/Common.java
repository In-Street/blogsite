package cyf.blog.base.common;

import cyf.blog.dao.model.Contents;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用配置
 * @author Cheng Yufei
 * @create 2018-03-07 14:59
 **/
@Component
public class Common {

    public static String site_title() {
        return site_option("site_title");
    }

    /**
     * 返回文章链接地址
     *
     * @param contents
     * @return
     */
    public static String permalink(Contents contents) {
        System.out.println();
        return permalink(contents.getCid(), contents.getSlug());
    }

    public static String permalink(Integer cid, String slug) {
        return site_url("/article/" + (StringUtils.isNotBlank(slug) ? slug : cid.toString()));
    }

    /**
     * 返回网站链接下的全址
     *
     * @param sub 后面追加的地址
     * @return
     */
    public static String site_url(String sub) {
        return site_option("site_url") + sub;
    }

    /**
     * 网站配置项
     *
     * @param key
     * @return
     */
    public static String site_option(String key) {
        return site_option(key, "");
    }

    /**
     * 网站配置项
     *
     * @param key
     * @param defalutValue 默认值
     * @return
     */
    public static String site_option(String key, String defalutValue) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        //系统设置的map存储
//        String str = WebConst.initConfig.get(key);
        String str = "";
        return StringUtils.isNotBlank(str) ? str : defalutValue;
    }

    /**
     * 显示文章缩略图，顺序为：文章第一张图 -> 随机获取
     *
     * @return
     */
    public static String show_thumb(Contents contents) {
        int cid = contents.getCid();
        int size = cid % 20;
        size = size == 0 ? 1 : size;
        return "/user/img/rand/" + size + ".jpg";
    }
    /**
     * 获取社交的链接地址
     *
     * @return
     */
    public static Map<String, String> social() {
        final String prefix = "social_";
        Map<String, String> map = new HashMap<>();
     /*   map.put("weibo", WebConst.initConfig.get(prefix + "weibo"));
        map.put("zhihu", WebConst.initConfig.get(prefix + "zhihu"));
        map.put("github", WebConst.initConfig.get(prefix + "github"));
        map.put("twitter", WebConst.initConfig.get(prefix + "twitter"));*/
        return map;
    }
}
