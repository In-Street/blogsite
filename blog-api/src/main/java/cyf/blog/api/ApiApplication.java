package cyf.blog.api;

import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * boot入口
 *
 */
//@EnableCaching
@SpringBootApplication(
        scanBasePackages = {"cyf.blog.api", "cyf.blog.dao"}
)
public class ApiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiApplication.class)
                //类名重复bean的处理
                //.beanNameGenerator(new DefaultBeanNameGenerator())
                .run(args);
    }
}
