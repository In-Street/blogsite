package cyf.blog.api.service;

import com.google.common.collect.Lists;
import cyf.blog.dao.mapper.MetasMapper;
import cyf.blog.dao.mapper.RelationshipsMapper;
import cyf.blog.dao.model.Metas;
import cyf.blog.dao.model.MetasExample;
import cyf.blog.dao.model.RelationshipsExample;
import cyf.blog.dao.model.RelationshipsKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
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

    public  List<Metas> categories(Integer code) {

        MetasExample metasExample = new MetasExample();
        metasExample.setOrderByClause("sort desc,mid desc");
        metasExample.createCriteria().andTypeEqualTo(code);
        List<Metas> metas = metasMapper.selectByExample(metasExample);
        return metas;
    }
}
