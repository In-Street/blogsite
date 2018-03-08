package cyf.blog.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Cheng Yufei
 * @create 2018-03-07 16:37
 **/
@Configuration
public class InterceptConfig extends WebMvcConfigurationSupport{

    @Autowired
    private Interception interception;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interception).addPathPatterns("/**");
    }
    /**
     * 配置静态访问资源(不进行配置，静态资源无法访问)
     * 1.spring boot 默认将/** 的所有访问映射到：classpath:/resources,classpath:/static,classpath:/public,classpath:/META-INF/resources
     *   此时可通过 localhost:8090/user/img/logo.png 进行访问.
     * 2. 如果要自定义访问路径，需要拦截器里添加：addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
     *   此时访问 localhost:8090/static/user/img/logo.png
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
