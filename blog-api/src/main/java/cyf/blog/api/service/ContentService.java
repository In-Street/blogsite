package cyf.blog.api.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import cyf.blog.base.common.Constants;
import cyf.blog.base.enums.db.ContentStatus;
import cyf.blog.base.enums.db.ContentType;
import cyf.blog.base.enums.db.MetaType;
import cyf.blog.dao.mapper.ContentsMapper;
import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.ContentsExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页文章列表、文章详情
 *
 * @author Cheng Yufei
 * @create 2018-03-06 17:34
 **/
@Service
@Slf4j
public class ContentService {

    @Autowired
    private ContentsMapper contentsMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MetaService metaService;

    public PageInfo getContents(int pageNum, int pageSize) {
        ContentsExample example = new ContentsExample();
        example.setOrderByClause("created desc,cid");
        example.createCriteria().andStatusEqualTo(ContentStatus.publish.getCode()).andTypeEqualTo(ContentType.post.getCode());
        PageHelper.startPage(pageNum, pageSize);
        List<Contents> contents = contentsMapper.selectByExample(example);
        PageInfo<Contents> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }

    public Contents getContentsById(Integer cid) {
        Contents contents = contentsMapper.selectByPrimaryKey(cid);
        updateArticleHit(Constants.ARTICLE_HIT + cid, cid);
        return contents;
    }

    public Contents getContentByPageName(String pageName) {
        Contents contents = null;
        if (StringUtils.isBlank(pageName)) {
            return contents;
        }
        ContentsExample example = new ContentsExample();
        example.setLimit(1);
        example.createCriteria().andSlugEqualTo(pageName);
        List<Contents> contentsList = contentsMapper.selectByExampleWithBLOBs(example);
        if (!CollectionUtils.isEmpty(contentsList)) {
            contents = contentsList.get(0);
        }
        return contents;
    }


    public PageInfo getContentsByCids(int pageNum, int pageSize,List<Integer> cids) {
        ContentsExample example = new ContentsExample();
        example.setOrderByClause("created desc,cid");
        example.createCriteria().andStatusEqualTo(ContentStatus.publish.getCode()).andTypeEqualTo(ContentType.post.getCode()).andCidIn(cids);
        PageHelper.startPage(pageNum, pageSize);
        List<Contents> contents = contentsMapper.selectByExample(example);
        PageInfo<Contents> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }



    /**
     * 更新文章点击
     *
     * @param key
     * @param cid 文章id
     */
    public void updateArticleHit(String key, Integer cid) {
        Long increment = stringRedisTemplate.opsForValue().increment(key, 1);
        //超过阀值更新数据库
        if (increment == Constants.ARTICLE_MAX_HIT) {
            contentsMapper.selfPlusMinusByPrimaryKey("hits", "+", increment.intValue(), cid);
            stringRedisTemplate.delete(key);
        }
    }

    /**
     * admin - 更新文章
     * @param contents
     * @return
     */
    public String updateArticle(Contents contents) {
        if (null == contents) {
            return "文章对象为空";
        }
        if (StringUtils.isBlank(contents.getTitle())) {
            return "文章标题不能为空";
        }
        if (StringUtils.isBlank(contents.getContent())) {
            return "文章内容不能为空";
        }
        int titleLength = contents.getTitle().length();
        if (titleLength > Constants.MAX_TITLE_COUNT) {
            return "文章标题过长";
        }
        int contentLength = contents.getContent().length();
        if (contentLength > Constants.MAX_TEXT_COUNT) {
            return "文章内容过长";
        }
        if (StringUtils.isBlank(contents.getSlug())) {
            contents.setSlug(null);
        }

        Integer cid = contents.getCid();
        contents.setContent(EmojiParser.parseToAliases(contents.getContent()));

        contentsMapper.updateByPrimaryKeySelective(contents);
        metaService.saveMetas(cid, contents.getTags(), MetaType.tag.getCode());
        metaService.saveMetas(cid, contents.getCategories(), MetaType.category.getCode());
        return Constants.SUCCESS_RESULT;
    }

    public void updateMap(Integer cid) {
        Map<String, Object> fieldMap = new HashMap<>();
        String hits = "100";
        String comments = "2000";
        fieldMap.put("hits", hits);
        fieldMap.put("comments_num", comments);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fieldMap", fieldMap);
        paramMap.put("cid", cid);
        contentsMapper.multiplePlusMinusByPrimaryKey(paramMap);
    }
}
