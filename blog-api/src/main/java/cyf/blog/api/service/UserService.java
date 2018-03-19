package cyf.blog.api.service;

import cyf.blog.dao.mapper.UsersMapper;
import cyf.blog.dao.model.Users;
import cyf.blog.dao.model.UsersExample;
import cyf.blog.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2018-03-18 上午10:56
 **/
@Service
@Slf4j
public class UserService {

    @Autowired
    private UsersMapper usersMapper;

    public Users login(String username, String password) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new RuntimeException("用户名或密码为空");
        }
        String pwd = TextUtil.MD5encode(username + password);
        UsersExample usersExample = new UsersExample();
        usersExample.setLimit(1);
        usersExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(pwd);
        List<Users> users = usersMapper.selectByExample(usersExample);

        if (CollectionUtils.isEmpty(users)) {
            throw new RuntimeException("用户名或密码错误");
        }

        return users.get(0);

    }
}
