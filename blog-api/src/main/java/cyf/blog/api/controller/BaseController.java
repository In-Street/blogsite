package cyf.blog.api.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cheng Yufei
 * @create 2018-03-06 18:45
 **/
public abstract class BaseController {


    public static String theme = "themes/default";
    public static String background = "admin";



    /**
     * 主页的页面主题
     * @param viewName
     * @return
     */
    public String render(String viewName) {
        return theme + "/" + viewName;
    }

    /**
     * 后台页面跳转
     * @param viewName
     * @return
     */
    public String skip(String viewName) {
        return background + "/" + viewName;
    }

    public BaseController title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
        return this;
    }

    public String render_404() {
        return "comm/error_404";
    }






}
