package cyf.blog.api.controller.admin;

import com.github.pagehelper.PageInfo;
import cyf.blog.api.controller.BaseController;
import cyf.blog.api.service.AttachService;
import cyf.blog.base.common.Constants;
import cyf.blog.base.enums.db.AttachType;
import cyf.blog.base.model.Response;
import cyf.blog.dao.common.Common;
import cyf.blog.dao.mapper.AttachMapper;
import cyf.blog.dao.model.Attach;
import cyf.blog.dao.model.Users;
import cyf.blog.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2018-04-17 14:01
 **/
@Controller
@RequestMapping("/admin/attach")
@Slf4j
public class AttachController extends BaseController {


    @Autowired
    private AttachService attachService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AttachMapper attachMapper;

    /**
     * 附件页面
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "")
    public String index(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "12") int limit) {
        PageInfo<Attach> attachPaginator = attachService.getAttachs(page, limit);
        request.setAttribute("attachs", attachPaginator);
        request.setAttribute(Constants.ATTACH_URL, Common.site_option(Constants.ATTACH_URL, Common.site_url("")));
        request.setAttribute("max_file_size", Constants.MAX_FILE_SIZE / 1024);
        return "admin/attach";
    }


    /**
     * 上传文件接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public Response upload(HttpServletRequest request, @RequestParam("file") MultipartFile[] multipartFiles) throws IOException {
        Users users = getLoginUser(stringRedisTemplate, request);
        Integer uid = users.getUid();
        List<String> errorFiles = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                String fname = multipartFile.getOriginalFilename();
                if (multipartFile.getSize() <= Constants.MAX_FILE_SIZE) {
                    String fkey = TextUtil.getFileKey(fname);
                    String ftype = TextUtil.isImage(multipartFile.getInputStream()) ? AttachType.image.getName() : AttachType.file.getName();
                    File file = new File(Constants.CLASSPATH + fkey);
                    try {
                        FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    attachService.save(fname, fkey, ftype, uid);
                } else {
                    return Response.fail("上传文件大于"+Constants.MAX_FILE_SIZE);
                }
            }
        } catch (Exception e) {
            return Response.fail();
        }
        return Response.ok();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Response delete(@RequestParam Integer id, HttpServletRequest request) {
        try {
            Attach attach = attachMapper.selectByPrimaryKey(id);
            if (null == attach) {
                return Response.fail("不存在该附件");
            }
            attachMapper.deleteByPrimaryKey(id);
            new File(Constants.CLASSPATH + attach.getFkey()).delete();
//            logService.insertLog(LogActions.DEL_ARTICLE.getAction(), attach.getFkey(), request.getRemoteAddr(), this.getUid(request));
        } catch (Exception e) {
            String msg = "附件删除失败";
            log.error(msg, e);
            return Response.fail(msg);
        }
        return Response.ok();
    }
}
