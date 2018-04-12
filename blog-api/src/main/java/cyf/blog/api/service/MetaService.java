package cyf.blog.api.service;

import com.google.common.collect.Lists;
import cyf.blog.dao.mapper.MetasMapper;
import cyf.blog.dao.mapper.RelationshipsMapper;
import cyf.blog.dao.model.Metas;
import cyf.blog.dao.model.MetasExample;
import cyf.blog.dao.model.RelationshipsExample;
import cyf.blog.dao.model.RelationshipsKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Cheng Yufei
 * @create 2018-03-17 上午10:26
 **/
@Service
@Slf4j
public class MetaService {

    @Autowired
    private MetasMapper metasMapper;
    @Autowired
    private RelationshipsMapper relationshipsMapper;
    @Autowired
    private RelationshipService relationshipService;

    public List<Integer> getCidsByNameAndType(Integer Type, String name) {

        List<Integer> cids = Lists.newArrayList();
        MetasExample metasExample = new MetasExample();
        metasExample.createCriteria().andTypeEqualTo(Type).andNameEqualTo(name);
        List<Metas> metas = metasMapper.selectByExample(metasExample);
        if (CollectionUtils.isEmpty(metas)) {
            return cids;
        }
        List<Integer> mids = metas.stream().map(Metas::getMid).collect(Collectors.toList());

        RelationshipsExample relationshipsExample = new RelationshipsExample();
        relationshipsExample.createCriteria().andMidIn(mids);
        List<RelationshipsKey> relationshipsKeys = relationshipsMapper.selectByExample(relationshipsExample);
        if (CollectionUtils.isEmpty(relationshipsKeys)) {
            return cids;
        }
        cids = relationshipsKeys.stream().map(RelationshipsKey::getCid).collect(Collectors.toList());
        return cids;


    }

    public List<Metas> categories(Integer code) {

        MetasExample metasExample = new MetasExample();
        metasExample.setOrderByClause("sort desc,mid desc");
        metasExample.createCriteria().andTypeEqualTo(code);
        List<Metas> metas = metasMapper.selectByExample(metasExample);
        return metas;
    }

    /**
     * 编辑文章时 对分类、标签的存储及 relationship的存储
     * @param contentId
     * @param names
     * @param type
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveMetas(Integer contentId, String names, Integer type) {

        if (StringUtils.isNotBlank(names) && Objects.nonNull(type)) {
            String[] nameArr = StringUtils.split(names, ",");
            for (String name : nameArr) {
                handleMetasAndRelation(contentId, name, type);
            }
        }

    }


    private void handleMetasAndRelation(Integer contentId, String name, Integer type) {
        MetasExample metasExample = new MetasExample();
        metasExample.setLimit(1);
        metasExample.createCriteria().andNameEqualTo(name).andTypeEqualTo(type);
        List<Metas> metasList = metasMapper.selectByExample(metasExample);
        Integer mid = null;
        if (!CollectionUtils.isEmpty(metasList)) {
            mid = metasList.get(0).getMid();
        } else {
            Metas m = new Metas();
            m.setName(name);
            m.setType(type);
            metasMapper.insertSelective(m);
            mid = m.getMid();
        }
        if (Objects.nonNull(mid)) {
            boolean exist = relationshipService.isExist(contentId, mid);
            if (exist) {
                return;
            }
            RelationshipsKey key = new RelationshipsKey();
            key.setCid(contentId);
            key.setMid(mid);
            relationshipsMapper.insert(key);
        }

    }


}
