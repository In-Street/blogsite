package cyf.blog.api.controller;

import com.alibaba.fastjson.JSONObject;
import cyf.blog.base.common.Constants;
import cyf.blog.dao.model.Users;
import org.springframework.data.redis.core.StringRedisTemplate;

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
     *
     * @param viewName
     * @return
     */
    public String render(String viewName) {
        return theme + "/" + viewName;
    }

    /**
     * 后台页面跳转
     *
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

    /**
     * 获取登录用户
     * @param stringRedisTemplate
     * @param request
     * @return
     */
    public Users getLoginUser(StringRedisTemplate stringRedisTemplate, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        String s = stringRedisTemplate.opsForValue().get(Constants.LOGIN_SESSION_KEY + sessionId);
        Users users = JSONObject.parseObject(s, Users.class);
        return users;
    }


}
