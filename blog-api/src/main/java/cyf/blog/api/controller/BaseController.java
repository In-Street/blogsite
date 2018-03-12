package cyf.blog.api.controller;

import cyf.blog.base.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cheng Yufei
 * @create 2018-03-06 18:45
 **/
public abstract class BaseController {


    public static String THEME = "themes/default";



    /**
     * 主页的页面主题
     * @param viewName
     * @return
     */
    public String render(String viewName) {
        return THEME + "/" + viewName;
    }

    public BaseController title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
        return this;
    }

    public String render_404() {
        return "comm/error_404";
    }






}
