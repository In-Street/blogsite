package cyf.blog.api.controller.admin;

import cyf.blog.api.service.MetaService;
import cyf.blog.base.enums.db.MetaType;
import cyf.blog.base.model.Response;
import cyf.blog.dao.model.bo.MetasBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 分类/标签管理
 * @author Cheng Yufei
 * @create 2018-04-13 9:52
 **/
@Controller
@RequestMapping("/admin/category")
public class CategoryController {


    @Autowired
    private MetaService metaService;

    /**
     * 分类/标签管理 跳转
     * @param request
     * @return
     */
    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        List<MetasBo> categories = metaService.getMetas(MetaType.category.getCode());
        List<MetasBo> tags = metaService.getMetas(MetaType.tag.getCode());
        request.setAttribute("categories", categories);
        request.setAttribute("tags", tags);
        return "admin/category";
    }

    /**
     * 分类保存
     * @param cname
     * @param mid
     * @return
     */
    @PostMapping(value = "save")
    @ResponseBody
    public Response saveCategory(@RequestParam String cname, @RequestParam Integer mid) {
        try {
            metaService.saveMeta(MetaType.category.getCode(), cname, mid);
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
        return Response.ok();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Response delete(@RequestParam int mid) {
        try {
            metaService.delete(mid);
        } catch (Exception e) {
            String msg = "删除失败";
            return Response.fail(msg);
        }
        return Response.ok();
    }

}
