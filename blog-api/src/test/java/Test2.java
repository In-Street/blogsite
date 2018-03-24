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

    @Test
    public void t3() {
        String a = "ab";
        String b = "ab";
        System.out.println(a == b);
        System.out.println("---------------------------");

        String a1 = new String("a");
        String a2 = new String("a");
        System.out.println(a1 == a2);

        System.out.println("---------------------------");

        System.out.println(a == a1);

    }

    @Test
    public void t4() {

        Integer a = new Integer(1);
        Integer b = new Integer(1);
        System.out.println(a==b);

        Integer a1 = 128;
        Integer a2 = 128;
        System.out.println(a1.equals(a2));

    }


}
