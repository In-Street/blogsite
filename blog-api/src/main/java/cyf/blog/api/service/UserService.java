package cyf.blog.api.service;

import com.alibaba.fastjson.JSONObject;
import cyf.blog.base.common.Constants;
import cyf.blog.dao.mapper.UsersMapper;
import cyf.blog.dao.model.Users;
import cyf.blog.dao.model.UsersExample;
import cyf.blog.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Cheng Yufei
 * @create 2018-03-18 上午10:56
 **/
@Service
@Slf4j
public class UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Users login(String username, String password, String sessionId) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new RuntimeException("用户名或密码为空");
        }

        //密码输错key
        String key = Constants.ERROR_LOGIN_COUNT_KEY + username;
        BoundValueOperations<String, String> bound = stringRedisTemplate.boundValueOps(key);
        if (bound.getExpire() > 0) {
            throw new RuntimeException("输错密码已达3次，1分钟后再试");
        }

        String pwd = TextUtil.MD5encode(username + password);
        UsersExample usersExample = new UsersExample();
        UsersExample.Criteria criteria = usersExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        long count = usersMapper.countByExample(usersExample);
        if (count == 0) {
            throw new RuntimeException("用户名不存在");
        }

        usersExample.clear();
        usersExample.setLimit(1);
        usersExample.createCriteria().andPasswordEqualTo(pwd).andUsernameEqualTo(username);
        List<Users> users = usersMapper.selectByExample(usersExample);

        if (!CollectionUtils.isEmpty(users)) {
            stringRedisTemplate.delete(key);
            stringRedisTemplate.opsForValue().set(Constants.LOGIN_SESSION_KEY + sessionId, JSONObject.toJSONString(users.get(0)), Constants.SESSION_LOSEEFFICACY, TimeUnit.MINUTES);
            return users.get(0);
        }

        //输入密码次数时间限制
        String value = bound.get();
        if (value == null) {
            bound.increment(1);
        } else if (Constants.ERROR_LOGIN_COUNT == Integer.valueOf(value)) {
            //达到最大失败登录次数开始1分钟计时
            bound.expire(1, TimeUnit.MINUTES);
            bound.increment(1);
        } else {
            Long expire = bound.getExpire();
            if (expire > 0) {
                throw new RuntimeException("输错密码已达3次，1分钟后再试");
            } else {
                bound.increment(1);
            }
        }
        throw new RuntimeException("用户名或密码错误");
    }

    public int saveProfile(String screenName, String email, Integer uid) {
        if (StringUtils.isBlank(screenName) || StringUtils.isBlank(email)) {
            return 0;
        }
        Users users = new Users();
        users.setScreenName(screenName);
        users.setEmail(email);
        users.setUid(uid);
        return usersMapper.updateByPrimaryKeySelective(users);
    }
}
