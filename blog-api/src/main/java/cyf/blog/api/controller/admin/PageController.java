package cyf.blog.api.controller.admin;

import com.github.pagehelper.PageInfo;
import cyf.blog.api.controller.BaseController;
import cyf.blog.api.service.ContentService;
import cyf.blog.base.common.Constants;
import cyf.blog.base.enums.db.ContentType;
import cyf.blog.base.model.Response;
import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.ContentsExample;
import cyf.blog.dao.model.Users;
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

/**
 * 页面管理
 * @author Cheng Yufei
 * @create 2018-04-12 15:48
 **/
@Controller
@RequestMapping("/admin/page")
public class PageController extends BaseController {

    @Autowired
    private ContentService contentService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 跳转页面管理
     * @param page
     * @param limit
     * @param request
     * @return
     */
    @GetMapping(value = "")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit, HttpServletRequest request) {
        ContentsExample contentExample = new ContentsExample();
        contentExample.setOrderByClause("created desc");
        contentExample.createCriteria().andTypeEqualTo(ContentType.page.getCode());
        PageInfo<Contents> contentsPaginator = contentService.getContentsByType(page,limit,ContentType.page.getCode());
        request.setAttribute("articles", contentsPaginator);
        return "admin/page_list";
    }

    /**
     *跳转编辑
     * @param cid
     * @param request
     * @return
     */
    @GetMapping(value = "/{cid}")
    public String editPage(@PathVariable String cid, HttpServletRequest request) {
        Contents contents = contentService.getContentsById(Integer.valueOf(cid));
        request.setAttribute("contents", contents);
        return "admin/page_edit";
    }

    /**
     * page 页面编辑
     * @param cid
     * @param title
     * @param content
     * @param status
     * @param slug
     * @param allowComment
     * @param allowPing
     * @param request
     * @return
     */
    @PostMapping(value = "modify")
    @ResponseBody
    public Response modifyArticle(@RequestParam Integer cid, @RequestParam String title,
                                  @RequestParam String content,
                                  @RequestParam String status, @RequestParam String slug,
                                  @RequestParam(required = false) Integer allowComment, @RequestParam(required = false) Integer allowPing, HttpServletRequest request) {

        Users loginUser = getLoginUser(stringRedisTemplate, request);
        Contents contents = new Contents();
        contents.setCid(cid);
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(Integer.valueOf(status));
        contents.setSlug(slug);
        contents.setType(ContentType.page.getCode());
        if (null != allowComment) {
            contents.setAllowComment(allowComment == 1);
        }
        if (null != allowPing) {
            contents.setAllowPing(allowPing == 1);
        }
        contents.setAuthorId(loginUser.getUid());
        String result = contentService.updateArticle(contents);
        if (!Constants.SUCCESS_RESULT.equals(result)) {
            return Response.fail(result);
        }
        return Response.ok();
    }

    /**
     * 添加新页面 跳转
     * @return
     */
    @GetMapping(value = "new")
    public String newPage() {
        return "admin/page_edit";
    }

    /**
     * new page  - 发布
     * @param title
     * @param content
     * @param status
     * @param slug
     * @param allowComment
     * @param allowPing
     * @param request
     * @return
     */
    @PostMapping(value = "publish")
    @ResponseBody
    public Response publishPage(@RequestParam String title, @RequestParam String content,
                                      @RequestParam String status, @RequestParam String slug,
                                      @RequestParam(required = false) Integer allowComment, @RequestParam(required = false) Integer allowPing, HttpServletRequest request) {

        Users loginUser = getLoginUser(stringRedisTemplate, request);
        Contents contents = new Contents();
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(Integer.valueOf(status));
        contents.setSlug(slug);
        contents.setType(ContentType.page.getCode());
        if (null != allowComment) {
            contents.setAllowComment(allowComment == 1);
        }
        if (null != allowPing) {
            contents.setAllowPing(allowPing == 1);
        }
        contents.setAuthorId(loginUser.getUid());
        String result = contentService.publish(contents);
        if (!Constants.SUCCESS_RESULT.equals(result)) {
            return Response.fail(result);
        }
        return Response.ok();
    }
}
