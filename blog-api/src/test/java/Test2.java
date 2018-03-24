import cyf.blog.util.TextUtil;
import org.junit.Test;

/**
 * @author Cheng Yufei
 * @create 2018-03-23 16:54
 **/
public class Test2 {

    @Test
    public void t1() {
        String email = "yufeicheng";
        String hash = TextUtil.MD5encode(email.trim().toLowerCase());
        String res = "https://github.com/identicons/" + hash + ".png";
        System.out.println(res);
    }

    @Test
    public void t2() {
        String encode = TextUtil.MD5encode("cyf123");
        System.out.println(encode);
    }


}
