package cyf.blog.api.controller.admin;

import cyf.blog.api.service.MetaService;
import cyf.blog.base.enums.db.MetaType;
import cyf.blog.dao.model.bo.MetasBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
