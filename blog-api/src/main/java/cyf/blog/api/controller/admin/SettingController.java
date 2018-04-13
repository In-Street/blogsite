package cyf.blog.api.controller.admin;

import cyf.blog.dao.mapper.OptionsMapper;
import cyf.blog.dao.model.Options;
import cyf.blog.dao.model.OptionsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统设置
 * @author Cheng Yufei
 * @create 2018-04-13 17:39
 **/
@Controller
@RequestMapping("/admin/setting")
public class SettingController {


    @Autowired
    private OptionsMapper optionsMapper;

    /**
     * 跳转系统设置
     * @param request
     * @return
     */
    @GetMapping(value = "")
    public String setting(HttpServletRequest request) {
        List<Options> voList = optionsMapper.selectByExample(new OptionsExample());
        Map<String, String> options = new HashMap<>();
        voList.forEach((option) -> {
            options.put(option.getName(), option.getValue());
        });
        if (options.get("site_record") == null) {
            options.put("site_record", "");
        }
        request.setAttribute("options", options);
        return "admin/setting";
    }
}
