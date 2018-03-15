import org.junit.Test;

import java.util.Map;

/**
 * @author Cheng Yufei
 * @create 2018-03-14 14:54
 **/
public class Test1 {

    @Test
    public void t1() {
        Object key = "taylor";
        int hashCode = key.hashCode();
        System.out.println(hashCode);
        int i = hashCode & (32);
        System.out.println(i);

    }

    @Test
    public void t2() {
        System.out.println(Math.pow(2, 2));
        int i = 119 % (16);
        System.out.println(i);
        int i1 = 119 & (16 - 1);
        System.out.println(i1);

    }

}
