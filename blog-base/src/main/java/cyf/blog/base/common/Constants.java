package cyf.blog.base.common;

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
}
