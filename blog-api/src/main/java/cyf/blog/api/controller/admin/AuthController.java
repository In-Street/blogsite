package cyf.blog.api.controller.admin;

import cyf.blog.api.configuration.LogRecord;
import cyf.blog.api.controller.BaseController;
import cyf.blog.api.service.UserService;
import cyf.blog.base.common.Constants;
import cyf.blog.base.enums.OperateObject;
import cyf.blog.base.enums.OperateType;
import cyf.blog.base.model.Response;
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
import java.io.IOException;

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
    @LogRecord(operateType = OperateType.login, operateObject = OperateObject.system)
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
}
