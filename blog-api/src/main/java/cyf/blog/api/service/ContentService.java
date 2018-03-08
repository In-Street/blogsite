package cyf.blog.api.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cyf.blog.base.enums.db.ContentStatus;
import cyf.blog.base.enums.db.ContentType;
import cyf.blog.dao.mapper.ContentsMapper;
import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.ContentsExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2018-03-06 17:34
 **/
@Service
@Slf4j
public class ContentService {

    @Autowired
    private ContentsMapper contentsMapper;

    public PageInfo getContents(int pageNum,int pageSize) {
        ContentsExample example = new ContentsExample();
        example.setOrderByClause("created desc,cid");
        example.createCriteria().andStatusEqualTo(ContentStatus.publish.getCode()).andTypeEqualTo(ContentType.post.getCode());
        PageHelper.startPage(pageNum, pageSize);
        List<Contents> contents = contentsMapper.selectByExample(example);
        PageInfo<Contents> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }
}
