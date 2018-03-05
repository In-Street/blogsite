package cyf.blog.api.controller;

import cyf.blog.api.service.UserService;
import cyf.blog.base.dto.PageInDto;
import cyf.blog.base.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cheng on 2017/7/10.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;



    @GetMapping(value = "/select/{pageIndex}")
    public Response select(@PathVariable Integer pageIndex) {
        PageInDto pageInDto = new PageInDto(pageIndex, 1);
        return new Response(userService.select1(pageInDto));
    }



}
