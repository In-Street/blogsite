package cyf.blog.api.controller.admin;

import cyf.blog.api.controller.BaseController;
import cyf.blog.api.service.UserService;
import cyf.blog.base.model.Response;
import cyf.blog.dao.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cheng Yufei
 * @create 2018-03-17 下午8:36
 **/
@Controller("AuthController")
@RequestMapping("/admin")
public class AuthController extends BaseController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String toTogin() {
        return skip("login.html");
    }

    @PostMapping("/login")
    @ResponseBody
    public Response login(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) String remeber_me,
                                HttpServletRequest request, HttpServletResponse response) {

        try {
            Users loginUser = userService.login(username, password);
            request.getSession().setAttribute("", loginUser);
            //记录登录日志

        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
        return Response.ok();
    }


}
