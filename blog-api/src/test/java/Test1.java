import cyf.blog.api.ApiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Cheng Yufei
 * @create 2018-03-14 14:54
 **/
@SpringBootTest(classes = {ApiApplication.class})
@RunWith(SpringRunner.class)
public class Test1 {

    static Random random = new Random();

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void t1() {
        int max = 4;
        int min = 1;
//        int res = random.nextInt(max - min + 1) + min;
        int res = random.nextInt(4) + 1;

        System.out.println(res);

    }

    @Test
    public void t2() {
        System.out.println(Math.pow(2, 2));
        int i = 119 % (16);
        System.out.println(i);
        int i1 = 119 & (16 - 1);
        System.out.println(i1);

    }

    /**
     * redis 设置自增及计时
     */
    @Test
    public void t3() {
        String key = "error_";
        BoundValueOperations<String, String> bound = stringRedisTemplate.boundValueOps(key);
        String value = null;
       /* for (int i = 0; i < 10; i++) {
            value = bound.get();
            if (null == value) {
                bound.increment(1);
            } else if (Integer.valueOf(value) == 2) {
                bound.expire(1, TimeUnit.MINUTES);
                bound.increment(1);
            } else {
                Long expire = bound.getExpire();
                if (expire > 0) {
                    System.out.println("1分钟后重试");
                } else {
                    bound.increment(1);
                }
            }
        }*/
        System.out.println(bound.get());
    }




}
