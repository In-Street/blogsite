package cyf.blog.api.controller.admin;

import com.github.pagehelper.PageInfo;
import cyf.blog.api.service.ArticleService;
import cyf.blog.api.service.ContentService;
import cyf.blog.api.service.MetaService;
import cyf.blog.base.enums.db.MetaType;
import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.Metas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2018-04-11 10:45
 **/
@Controller("ArticleController")
@RequestMapping("/admin/article")
public class ArticleController {


    @Autowired
    private ArticleService articleService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private MetaService metaService;


    /**
     * 文章管理列表
     * @param page
     * @param limit
     * @param request
     * @return
     */
    @GetMapping(value = "")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit, HttpServletRequest request) {


        PageInfo<Contents> articles = articleService.getArticles(page, limit);
        request.setAttribute("articles", articles);
        return "admin/article_list";
    }

    @GetMapping(value = "/{cid}")
    public String editArticle(@PathVariable String cid, HttpServletRequest request) {

        Contents contents = contentService.getContentsById(Integer.valueOf(cid));
        request.setAttribute("contents", contents);

        List<Metas> categories = metaService.categories(MetaType.category.getCode());
        request.setAttribute("categories", categories);
        request.setAttribute("active", "article");
        return "admin/article_edit";
    }

}
