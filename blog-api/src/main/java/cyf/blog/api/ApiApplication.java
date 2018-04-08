package cyf.blog.api;

import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * boot入口
 *  添加base包的扫描是为了base里的类加@Component后可在其他类中注解使用，否则为null
 */
//@EnableCaching
@SpringBootApplication(
        scanBasePackages = {"cyf.blog.api", "cyf.blog.dao","cyf.blog.base"}
)
@EnableAsync
public class ApiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiApplication.class)
                //类名重复bean的处理
                .beanNameGenerator(new DefaultBeanNameGenerator())
                .run(args);
    }
}
