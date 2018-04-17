import cyf.blog.util.TextUtil;
import jdk.nashorn.internal.ir.ContinueNode;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
        System.out.println(a == b);

        Integer a1 = 128;
        Integer a2 = 128;
        System.out.println(a1.equals(a2));

    }

    @Test
    public void t5() {
        String s = "d";
        String str = "a,b,c,d";
        String[] split = StringUtils.split(str, ",");
        for (int i = 0; i < split.length; i++) {
            if (Objects.equals(s, split[i])) {
                if (i == split.length - 1) {
                    str = StringUtils.remove(str, "," + s);
                } else {
                    str = StringUtils.remove(str, s + ",");
                }
            }
        }
        System.out.println(str);
    }

    @Test
    public void concurrent() {
        ConcurrentMap map = new ConcurrentHashMap();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        ((ConcurrentHashMap) map).forEachValue(2, s -> {
//            System.out.println(s);
        });

        ((ConcurrentHashMap) map).searchKeys(2, s -> {
            if (s.equals(3)) {
                return s;
            } else {
                return 0;
            }
        });
    }

    @Test
    public void t6() {
        StringBuilder ret = new StringBuilder();
        String[] var3 = {"a","b","c"};
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4;var5++) {
            String item = var3[var5];
            ret.append(',').append(item);
        }
        System.out.println(ret.length() > 0 ? ret.substring(1) : ret.toString());
    }

}
