package cyf.blog.api.controller.admin;

import cyf.blog.api.configuration.LogRecord;
import cyf.blog.api.controller.BaseController;
import cyf.blog.api.service.OptionService;
import cyf.blog.api.service.SiteService;
import cyf.blog.base.enums.OperateObject;
import cyf.blog.base.enums.OperateType;
import cyf.blog.base.model.Response;
import cyf.blog.dao.mapper.OptionsMapper;
import cyf.blog.dao.model.Options;
import cyf.blog.dao.model.OptionsExample;
import cyf.blog.dao.model.bo.BackResponseBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Slf4j
public class SettingController {


    @Autowired
    private OptionsMapper optionsMapper;
    @Autowired
    private OptionService optionService;
    @Autowired
    private SiteService siteService;

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

    /**
     * 保存系统设置
     */
    @PostMapping(value = "")
    @ResponseBody
    @LogRecord(operateType = OperateType.edit_setting,operateObject = OperateObject.setting)
    public Response saveSetting(@RequestParam(required = false) String site_theme, HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> querys = new HashMap<>();
            parameterMap.forEach((key, value) -> {
                querys.put(key, join(value));
            });
            optionService.saveOptions(querys);
            if (StringUtils.isNotBlank(site_theme)) {
                BaseController.theme = "themes/" + site_theme;
            }
            return Response.ok();
        } catch (Exception e) {
            String msg = "保存设置失败";
            return Response.fail(msg);
        }
    }


    /**
     * 系统备份
     *
     * @return
     */
    @PostMapping(value = "backup")
    @ResponseBody
    public Response backup(@RequestParam String bk_type, @RequestParam String bk_path,
                                 HttpServletRequest request) {
        if (StringUtils.isBlank(bk_type)) {
            return Response.fail("请确认信息输入完整");
        }
        try {
            BackResponseBo backResponse = siteService.backup(bk_type, bk_path, "yyyyMMddHHmm");
//            logService.insertLog(LogActions.SYS_BACKUP.getAction(), null, request.getRemoteAddr(), this.getUid(request));
            return Response.ok(backResponse);
        } catch (Exception e) {
            String msg = "备份失败";
            if (e instanceof Exception) {
                msg = e.getMessage();
            } else {
                log.error(msg, e);
            }
            return Response.fail(msg);
        }
    }
    /**
     * 数组转字符串
     *
     * @param arr
     * @return
     */
    private String join(String[] arr) {
        StringBuilder ret = new StringBuilder();
        String[] var3 = arr;
        int var4 = arr.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String item = var3[var5];
            ret.append(',').append(item);
        }

        return ret.length() > 0 ? ret.substring(1) : ret.toString();
    }
}
