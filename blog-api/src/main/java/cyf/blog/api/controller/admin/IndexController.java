package cyf.blog.api.controller.admin;

import cyf.blog.api.service.ContentService;
import cyf.blog.api.service.LogService;
import cyf.blog.api.service.SiteService;
import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.Logs;
import cyf.blog.dao.model.bo.StatisticsBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2018-03-17 下午6:50
 **/
@Controller("IndexController")
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private LogService logService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private SiteService siteService;
    /**
     * 页面跳转
     * @return
     */
    @GetMapping(value = {"","/index"})
    public String index(HttpServletRequest request){

//        List<CommentVo> comments = siteService.recentComments(5);
        List<Contents> contents = contentService.getContents(1, 5).getList();
        StatisticsBo statistics = siteService.getStatistics();

        List<Logs> logs = logService.getLogs(1, 5);

//        request.setAttribute("comments", comments);
        request.setAttribute("articles", contents);
        request.setAttribute("statistics", statistics);
        request.setAttribute("logs", logs);
        return "admin/index";
    }
}
