package cyf.blog.api.service;

import cyf.blog.base.enums.db.ContentStatus;
import cyf.blog.base.enums.db.ContentType;
import cyf.blog.base.enums.db.Metatype;
import cyf.blog.dao.mapper.ContentsMapper;
import cyf.blog.dao.mapper.MetasMapper;
import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.ContentsExample;
import cyf.blog.dao.model.Metas;
import cyf.blog.dao.model.MetasExample;
import cyf.blog.dao.model.bo.ArchiveBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 站点服务：归档
 *
 * @author Cheng Yufei
 * @create 2018-03-11 下午3:58
 **/
@Service
@Slf4j
public class SiteService {

    @Autowired
    private ContentsMapper contentsMapper;
    @Autowired
    private MetasMapper metasMapper;

    /**
     * 归档
     * @return
     */
    public List<ArchiveBo> getArchives() {
        //根据时间group，按x年x月 分组
        List<ArchiveBo> archiveBos = contentsMapper.getArchiveBos();
        if (CollectionUtils.isEmpty(archiveBos)) {
            return archiveBos;
        }
        ContentsExample example = new ContentsExample();
        //根据分组时间找文章
        archiveBos.forEach(a->{
            example.setOrderByClause("created desc,cid desc");
            String date = a.getDate();
            Date dateParse = null;
            try {
                 dateParse = FastDateFormat.getInstance("yyyy-MM").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            example.createCriteria().andCreatedGreaterThanOrEqualTo(dateParse).andCreatedLessThan(DateUtils.addMonths(dateParse, 1))
                    .andTypeEqualTo(ContentType.post.getCode()).andStatusEqualTo(ContentStatus.publish.getCode());
            List<Contents> contents = contentsMapper.selectByExample(example);
            a.setArticles(contents);
            example.clear();
        });

        return archiveBos;
    }

    public List<Metas> getLinks() {
        MetasExample example = new MetasExample();
        example.setOrderByClause("sort asc");
        example.createCriteria().andTypeEqualTo(Metatype.link.getCode());
        List<Metas> metas = metasMapper.selectByExample(example);
        return metas;
    }
}
