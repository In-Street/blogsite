package cyf.blog.api.controller.admin;

import com.alibaba.fastjson.JSONObject;
import cyf.blog.api.controller.BaseController;
import cyf.blog.api.service.UserService;
import cyf.blog.base.common.Constants;
import cyf.blog.base.model.Response;
import cyf.blog.dao.model.Users;
import cyf.blog.util.TextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Cheng Yufei
 * @create 2018-03-17 下午8:36
 **/
@Controller("AuthController")
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/login")
    public String toTogin() {
        return skip("login.html");
    }

    @PostMapping("/toLogin")
    @ResponseBody
//    @LogRecord(operateType = OperateType.login, operateObject = OperateObject.system)
    public Response login(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) String remeber_me,
                          HttpServletRequest request, HttpServletResponse response) {

        try {
            String sessionId = request.getSession().getId();
            String s = stringRedisTemplate.opsForValue().get(Constants.LOGIN_SESSION_KEY + sessionId);
            if (StringUtils.isNotBlank(s)) {
                return Response.ok();
            }
            userService.login(username, password, sessionId);
         /*   if (StringUtils.isNotBlank(remeber_me)) {
                TextUtil.setCookie(response, login.getUid());
            }*/
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
        return Response.ok();
    }

    /**
     * 注销
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = request.getSession().getId();
        Boolean delete = stringRedisTemplate.delete(Constants.LOGIN_SESSION_KEY + sessionId);
        if (delete) {
            response.sendRedirect("/auth/login");
        } else {
            throw new IOException("注销失败");
        }
    }


    /**
     * 个人设置页面
     */
    @GetMapping(value = "/profile")
    public String profile(HttpServletRequest request) {
        Users users = getLoginUser(stringRedisTemplate, request);
        request.setAttribute("login_user", users);
        return "admin/profile";
    }


    /**
     * 保存个人信息
     */
    @PostMapping(value = "/profile")
    @ResponseBody
    public Response saveProfile(@RequestParam String screenName, @RequestParam String email, HttpServletRequest request) {
        Users users = getLoginUser(stringRedisTemplate, request);
        if (Objects.isNull(users)) {
            return Response.fail("登录信息过期，请重新登录");
        }
        int i = userService.saveProfile(screenName, email, users.getUid());
        if (!(i > 0)) {
            return Response.fail();
        }
        users.setScreenName(screenName);
        users.setEmail(email);
        String sessionId = request.getSession().getId();
        if (stringRedisTemplate.hasKey(Constants.LOGIN_SESSION_KEY + sessionId)) {
            stringRedisTemplate.opsForValue().getAndSet(Constants.LOGIN_SESSION_KEY + sessionId, JSONObject.toJSONString(users));
            stringRedisTemplate.expire(Constants.LOGIN_SESSION_KEY + sessionId, Constants.SESSION_LOSEEFFICACY, TimeUnit.MINUTES);
        } else {
            return Response.fail("登录信息过期，请重新登录");
        }
        return Response.ok();
    }

    @PostMapping("/password")
    @ResponseBody
    public Response updatePwd(@RequestParam String oldPassword, @RequestParam String password, HttpServletRequest request, HttpSession session) {

        Users users = getLoginUser(stringRedisTemplate, request);
        if (Objects.isNull(users)) {
            return Response.fail("登录信息过期，请重新登录");
        }
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password)) {
            return Response.fail("填写信息不完整");
        }

        if(!Objects.equals(users.getPassword(),TextUtil.MD5encode(users.getUsername() + oldPassword))){
            return Response.fail("旧密码不正确");
        }
        if (password.length() < 6 || password.length() > 14) {
            return Response.fail("请输入6-14位密码");
        }
        String newPwd = TextUtil.MD5encode(users.getUsername() + password);
        int result = userService.updatePwd(newPwd, users);
        if (result == 0) {
            return Response.fail("修改失败");
        }
        String sessionId = request.getSession().getId();
        if (stringRedisTemplate.hasKey(Constants.LOGIN_SESSION_KEY + sessionId)) {
            users.setPassword(newPwd);
            stringRedisTemplate.opsForValue().getAndSet(Constants.LOGIN_SESSION_KEY + sessionId, JSONObject.toJSONString(users));
            stringRedisTemplate.expire(Constants.LOGIN_SESSION_KEY + sessionId, Constants.SESSION_LOSEEFFICACY, TimeUnit.MINUTES);
        } else {
            return Response.fail("登录信息过期，请重新登录");
        }
        return Response.ok();
    }
}
