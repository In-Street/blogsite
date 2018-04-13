package cyf.blog.api.service;

import com.google.common.collect.Lists;
import cyf.blog.base.enums.db.MetaType;
import cyf.blog.dao.mapper.ContentsMapper;
import cyf.blog.dao.mapper.MetasMapper;
import cyf.blog.dao.mapper.RelationshipsMapper;
import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.Metas;
import cyf.blog.dao.model.MetasExample;
import cyf.blog.dao.model.RelationshipsExample;
import cyf.blog.dao.model.RelationshipsKey;
import cyf.blog.dao.model.bo.MetasBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
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
    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentsMapper contentsMapper;

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
     * 分类/标签管理页面：分类、标签获取及各个类型下的文章数量
     *
     * @param code
     * @return
     */
    public List<MetasBo> getMetas(Integer code) {

        MetasExample metasExample = new MetasExample();
        metasExample.setOrderByClause("sort desc,mid desc");
        metasExample.createCriteria().andTypeEqualTo(code);
        List<Metas> metas = metasMapper.selectByExample(metasExample);
        if (CollectionUtils.isEmpty(metas)) {
            return Collections.EMPTY_LIST;
        }
        List<MetasBo> bos = Lists.newArrayList();
        RelationshipsExample relationshipsExample = new RelationshipsExample();
        RelationshipsExample.Criteria relationCriteria = relationshipsExample.createCriteria();
        metas.forEach(m -> {
            MetasBo bo = new MetasBo();
            BeanUtils.copyProperties(m, bo);
            relationCriteria.andMidEqualTo(m.getMid());
            bo.setCount(relationshipsMapper.countByExample(relationshipsExample));
            bos.add(bo);
            relationCriteria.getAllCriteria().clear();
        });
        return bos;
    }

    /**
     * 编辑文章时 对分类、标签的存储及 relationship的存储
     *
     * @param contentId
     * @param names
     * @param type
     * @return
     */

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

    /**
     * 分类/标签管理页面：对分类名称的修改或新建
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveMeta(Integer type, String cname, Integer mid) throws Exception {

        MetasExample metasExample = new MetasExample();
        metasExample.setLimit(1);
        metasExample.createCriteria().andTypeEqualTo(type).andNameEqualTo(cname);
        List<Metas> metas = metasMapper.selectByExample(metasExample);
        if (!CollectionUtils.isEmpty(metas)) {
            throw new Exception("分类名称已存在");
        }
        Metas m = new Metas();
        m.setName(cname);
        if (Objects.nonNull(mid)) {
            m.setMid(mid);
            Metas oldM = metasMapper.selectByPrimaryKey(mid);
            String oldName = oldM.getName();
            metasMapper.updateByPrimaryKeySelective(m);
            //修改文章分类名称
            contentService.updateCategoryName(oldName, cname, mid);
        } else {
            m.setType(MetaType.category.getCode());
            metasMapper.insertSelective(m);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(int mid) {
        Metas metas = metasMapper.selectByPrimaryKey(mid);
        Integer metaType = metas.getType();
        String metasName = metas.getName();

        metasMapper.deleteByPrimaryKey(mid);
        //获取文章进行分类或标签修改
        List<RelationshipsKey> relationshipsKeys = relationshipService.getByMid(mid);
        if (CollectionUtils.isEmpty(relationshipsKeys)) {
            return;
        }
        List<Integer> cids = relationshipsKeys.stream().map(RelationshipsKey::getCid).collect(Collectors.toList());
        List<Contents> contents = contentService.getContentsByCids(cids);
        contents.forEach(c -> {
            Contents con = new Contents();
            con.setCid(c.getCid());
            if (Objects.equals(MetaType.category.getCode(), metaType)) {

                String[] split = StringUtils.split(c.getCategories(), ",");
                for (int i = 0; i < split.length; i++) {
                    if (Objects.equals(split[i], metasName)) {
                        if (i == split.length - 1) {
                            con.setCategories(StringUtils.remove(c.getCategories(), "," + metasName));
                        } else {
                            con.setCategories(StringUtils.remove(c.getCategories(), metasName + ","));
                        }
                    }
                }
            } else if (Objects.equals(MetaType.tag.getCode(), metaType)) {
                String[] split = StringUtils.split(c.getTags(), ",");
                for (int i = 0; i < split.length; i++) {
                    if (Objects.equals(split[i], metasName)) {
                        if (i == split.length - 1) {
                            con.setTags(StringUtils.remove(c.getTags(), "," + metasName));
                        } else {
                            con.setTags(StringUtils.remove(c.getTags(), metasName + ","));
                        }
                    }
                }
            }
            contentsMapper.updateByPrimaryKeySelective(con);
        });
        relationshipService.deleteByMid(mid);
    }
}
