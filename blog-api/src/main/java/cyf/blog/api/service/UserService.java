package cyf.blog.api.service;

import cyf.blog.base.dto.PageInDto;
import cyf.blog.dao.mapper.UserMapper;
import cyf.blog.dao.model.User;
import cyf.blog.dao.model.UserExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by cheng on 2017/7/10.
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    UserMapper userMapper;


    public List<User> select1(PageInDto pageInDto) {
        UserExample example = new UserExample();
        example.setLimit(pageInDto.getLimit());
        example.setOffset(pageInDto.getOffset());
        List<User> list = userMapper.selectByExample(example);
        return list;
    }

}
