package cyf.blog.base.common;

import cyf.blog.util.TextUtil;

/**
 * @since 1.0
 */
public interface Constants {


    String USER_LOGIN_KEY = "user_login_key_";

    /**
     * redis 文章点击的key
     */
    String ARTICLE_HIT = "article_hit_";
    /**
     * 最大页码
     */
    int MAX_PAGE = 100;

    /**
     * 文章图标
     */
    String[] ARTICLE_ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock"};

    /**
     * redis中文章点击数，超过此值更新到数据库
     */
    int ARTICLE_MAX_HIT = 10;

    /**
     * 记录登录失败的次数的key
     */
    String ERROR_LOGIN_COUNT_KEY = "error_login_count_key_";
    /**
     * 允许登录失败的最大次数
     */
    int ERROR_LOGIN_COUNT = 2;
    /**
     * 登录用户session key
     */
    String LOGIN_SESSION_KEY = "login_user_";

    /**
     * 登录session失效时间（分钟）
     */
    int SESSION_LOSEEFFICACY = 120;

    /**
     * 操作日志记录
     */
    String LOGRECORD_OPERATE_TYPE = "logrecord_operate_type";
    String LOGRECORD_OPERATE_OBJECT = "logrecord_operate_object";

    /**
     * 文章标题最多可以输入的文字个数
     */
     int MAX_TITLE_COUNT = 200;

    /**
     * 文章最多可以输入的文字数
     */
    int MAX_TEXT_COUNT = 200000;

    /**
     * 成功返回
     */
     String SUCCESS_RESULT = "SUCCESS";

    /**
     * 最大获取文章条数
     */
     int MAX_POSTS = 9999;

    String CLASSPATH = TextUtil.getUplodFilePath();

    /**
     * 上传文件最大1M
     */
     Integer MAX_FILE_SIZE = 1048576;

    /**
     * 附件存放的URL，默认为网站地址，如集成第三方则为第三方CDN域名
     */
    String ATTACH_URL = "attach_url";

}
