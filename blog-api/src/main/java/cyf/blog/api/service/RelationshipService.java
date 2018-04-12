package cyf.blog.api.service;

import cyf.blog.dao.mapper.RelationshipsMapper;
import cyf.blog.dao.model.RelationshipsExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Cheng Yufei
 * @create 2018-04-12 10:59
 **/
@Service
@Slf4j
public class RelationshipService {

    @Autowired
    private RelationshipsMapper relationshipsMapper;

    public boolean isExist(Integer cid, Integer mid) {
        RelationshipsExample shipExample = new RelationshipsExample();
        shipExample.setLimit(1);
        shipExample.createCriteria().andCidEqualTo(cid).andMidEqualTo(mid);
        return relationshipsMapper.countByExample(shipExample) > 0 ? true : false;
    }
}
