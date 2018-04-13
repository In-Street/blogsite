package cyf.blog.api.controller.admin;

import cyf.blog.api.service.MetaService;
import cyf.blog.base.enums.db.MetaType;
import cyf.blog.base.model.Response;
import cyf.blog.dao.mapper.MetasMapper;
import cyf.blog.dao.model.Metas;
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
 * 友链管理
 * @author Cheng Yufei
 * @create 2018-04-13 17:18
 **/
@Controller
@RequestMapping("/admin/links")
public class LinksController {

    @Autowired
    private MetaService metaService;
    @Autowired
    private MetasMapper metasMapper;

    /**
     * 跳转友链管理
     * @param request
     * @return
     */
    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        List<Metas> metas = metaService.categories(MetaType.link.getCode());
        request.setAttribute("links", metas);
        return "admin/links";
    }


    @PostMapping(value = "save")
    @ResponseBody
    public Response saveLink(@RequestParam String title, @RequestParam String url,
                                   @RequestParam String logo, @RequestParam Integer mid,
                                   @RequestParam(value = "sort", defaultValue = "0") int sort) {
        try {
            Metas metas = new Metas();
            metas.setName(title);
            metas.setSlug(url);
            metas.setDescription(logo);
            metas.setSort(sort);
            metas.setType(MetaType.link.getCode());
            if (null != mid) {
                metas.setMid(mid);
                metasMapper.updateByPrimaryKeySelective(metas);
            } else {
                metasMapper.insertSelective(metas);
            }
        } catch (Exception e) {
            String msg = "友链保存失败";
            return Response.fail(msg);
        }
        return Response.ok();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Response delete(@RequestParam int mid) {
        try {
            metaService.delete(mid);
        } catch (Exception e) {
            String msg = "友链删除失败";
            return Response.fail(msg);
        }
        return Response.ok();
    }
}
