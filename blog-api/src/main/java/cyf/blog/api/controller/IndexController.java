package cyf.blog.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页
 */
@Controller
@Slf4j
public class IndexController  {


    /**
     * 首页
     * @return
     */
    @GetMapping(value = "/")
    public String index() {
        return "themes/default/index";
    }


}
