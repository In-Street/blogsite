package cyf.blog.api.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cyf.blog.dao.mapper.AttachMapper;
import cyf.blog.dao.model.Attach;
import cyf.blog.dao.model.AttachExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2018-04-17 14:02
 **/
@Service
@Slf4j
public class AttachService {

    @Autowired
    private AttachMapper attachMapper;


    public PageInfo<Attach> getAttachs(Integer pageIndex,Integer limit) {

        AttachExample example = new AttachExample();
        example.setOrderByClause("id desc");
        PageHelper.startPage(pageIndex, limit);
        List<Attach> attaches = attachMapper.selectByExample(example);
        return new PageInfo<>(attaches);
    }

    public void save(String fname, String fkey, String ftype, Integer author) {
        Attach attach = new Attach();
        attach.setFname(fname);
        attach.setAuthorId(author);
        attach.setFkey(fkey);
        attach.setFtype(ftype);
        attachMapper.insertSelective(attach);
    }

}
