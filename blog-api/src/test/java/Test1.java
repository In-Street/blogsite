import org.junit.Test;

import java.util.Random;

/**
 * @author Cheng Yufei
 * @create 2018-03-14 14:54
 **/
public class Test1 {

    static Random random = new Random();

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

}
