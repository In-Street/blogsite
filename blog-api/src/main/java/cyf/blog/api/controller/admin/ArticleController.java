package cyf.blog.api.controller.admin;

import com.github.pagehelper.PageInfo;
import cyf.blog.api.controller.BaseController;
import cyf.blog.api.service.ArticleService;
import cyf.blog.api.service.ContentService;
import cyf.blog.api.service.MetaService;
import cyf.blog.base.common.Constants;
import cyf.blog.base.enums.db.ContentType;
import cyf.blog.base.enums.db.MetaType;
import cyf.blog.base.model.Response;
import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.Metas;
import cyf.blog.dao.model.Users;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2018-04-11 10:45
 **/
@Controller("ArticleController")
@RequestMapping("/admin/article")
public class ArticleController extends BaseController {


    @Autowired
    private ArticleService articleService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private MetaService metaService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


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

    /**
     * 跳转文章编辑页
     * @param cid
     * @param request
     * @return
     */
    @GetMapping(value = "/{cid}")
    public String editArticle(@PathVariable String cid, HttpServletRequest request) {

        Contents contents = contentService.getContentsById(Integer.valueOf(cid));
        request.setAttribute("contents", contents);

        List<Metas> categories = metaService.categories(MetaType.category.getCode());
        request.setAttribute("categories", categories);
        request.setAttribute("active", "article");
        return "admin/article_edit";
    }

    /**
     * 文章编辑
     * @param contents
     * @param request
     * @return
     */
    @PostMapping(value = "/modify")
    @ResponseBody
    public Response modifyArticle(Contents contents, HttpServletRequest request) {
        Users loginUser = getLoginUser(stringRedisTemplate, request);
        contents.setAuthorId(loginUser.getUid());
        contents.setType(ContentType.post.getCode());
        String result = contentService.updateArticle(contents);
        if (!Constants.SUCCESS_RESULT.equals(result)) {
            return Response.fail(result);
        }
        return Response.ok();
    }

    /**
     * 文章页(预览)
     *
     * @param request 请求
     * @param cid     文章主键
     * @return
     */
    @GetMapping(value = {"/preview/{cid}", "article/{cid}.html"})
    public String articlePreview(HttpServletRequest request, @PathVariable String cid) {
        Contents contents = contentService.getContentsById(Integer.valueOf(cid));
        if (null == contents) {
            return this.render_404();
        }
        request.setAttribute("article", contents);
        request.setAttribute("is_post", true);
//        completeArticle(request, contents);
        return this.render("post");
    }

    /**
     * admin - 文章删除
     * @param cid
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Response delete(@RequestParam int cid, HttpServletRequest request) {
        String result = contentService.deleteByCid(cid);
        if (!Constants.SUCCESS_RESULT.equals(result)) {
            return Response.fail(result);
        }
        return Response.ok();
    }

    /**
     * 跳转文章发布页
     * @param request
     * @return
     */
    @GetMapping(value = "/publish")
    public String newArticle(HttpServletRequest request) {
        List<Metas> categories = metaService.categories(MetaType.category.getCode());
        request.setAttribute("categories", categories);
        return "admin/article_edit";
    }

    /**
     * 文章发布
     * @param contents
     * @param request
     * @return
     */
    @PostMapping(value = "/publish")
    @ResponseBody
    public Response publishArticle(Contents contents, HttpServletRequest request) {
        Users users = getLoginUser(stringRedisTemplate, request);
        contents.setAuthorId(users.getUid());
        contents.setType(ContentType.post.getCode());
        if (StringUtils.isBlank(contents.getCategories())) {
            contents.setCategories("默认分类");
        }
        String result = contentService.publish(contents);
        if (!Constants.SUCCESS_RESULT.equals(result)) {
            return Response.fail(result);
        }
        return Response.ok();
    }
}
