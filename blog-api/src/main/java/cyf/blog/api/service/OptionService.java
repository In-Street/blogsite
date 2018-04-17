package cyf.blog.api.service;

import cyf.blog.dao.mapper.OptionsMapper;
import cyf.blog.dao.model.Options;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author Cheng Yufei
 * @create 2018-04-17 10:04
 **/
@Service
@Slf4j
public class OptionService {

    @Autowired
    private OptionsMapper optionsMapper;


    public void saveOptions(Map<String, String> options) {

        if (CollectionUtils.isEmpty(options)) {
            return;
        }
        options.forEach((k,v)->{
            Options op = new Options();
            op.setName(k);
            op.setValue(v);
            Options ops = optionsMapper.selectByPrimaryKey(k);
            if (Objects.nonNull(ops)) {
                optionsMapper.updateByPrimaryKeySelective(op);
            } else {
                optionsMapper.insertSelective(op);
            }

        });

    }
}
