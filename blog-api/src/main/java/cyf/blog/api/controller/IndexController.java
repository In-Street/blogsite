package cyf.blog.api.controller;

import com.github.pagehelper.PageInfo;
import cyf.blog.api.service.ContentService;
import cyf.blog.base.common.Constants;
import cyf.blog.base.enums.db.ContentStatus;
import cyf.blog.dao.model.Contents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 首页
 */
@Controller
@Slf4j
public class IndexController extends BaseController{


    @Autowired
    private ContentService contentService;


    /**
     * 首页
     *
     * @return
     */
    @GetMapping(value = "/")
    public String index(HttpServletRequest request) {
        return index(request, 1, 12);
    }

    /**
     * 首页分页
     *
     * @param request   request
     * @param pageIndex 第几页
     * @param limit     每页大小
     * @return 主页
     */
    @GetMapping(value = "/page/{pageIndex}")
    public String index(HttpServletRequest request, @PathVariable int pageIndex, @RequestParam(value = "limit", defaultValue = "12") int limit) {

        pageIndex = pageIndex < 0 || pageIndex > Constants.MAX_PAGE ? 1 : pageIndex;
        PageInfo<Contents> articles = contentService.getContents(pageIndex, limit);
        request.setAttribute("articles", articles);
        if (pageIndex > 1) {
           title(request, "第" + pageIndex + "页");
        }
        return render("index");
    }

    /**
     * 文章页
     *
     * @param request 请求
     * @param cid     文章主键
     * @return
     */
    @GetMapping(value = {"article/{cid}", "article/{cid}.html"})
    public String getArticle(HttpServletRequest request, @PathVariable String cid) {
        Contents contents = contentService.getContentsById(Integer.valueOf(cid));
        if (null == contents || Objects.equals(ContentStatus.draft.getCode(),contents.getStatus())) {
            return this.render_404();
        }
        request.setAttribute("article", contents);
        request.setAttribute("is_post", true);
       /* completeArticle(request, contents);
        updateArticleHit(contents.getCid(), contents.getHits());*/
        return this.render("post");


    }
}
