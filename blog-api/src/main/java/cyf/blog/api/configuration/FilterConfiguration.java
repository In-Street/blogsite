package cyf.blog.api.configuration;


import cyf.blog.base.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器配置类
 *
 * @since 1.0
 */
@Configuration
@Slf4j
public class FilterConfiguration {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 刚开始启动项目不运行 doFilterInternal() 方法，直接到 registration.addUrlPatterns ，访问controller 时 执行
     * <p>
     * 仅执行 doFilterInternal()
     *
     * 后台登录过滤
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean loginFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter filter = new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String sessionId = request.getSession().getId();
                log.info("sessionId:{}", sessionId);
                String loginSession = stringRedisTemplate.opsForValue().get(Constants.LOGIN_SESSION_KEY + sessionId);
                log.info("登陆过滤器 session={}", loginSession);
                String requestURI = request.getRequestURI();

                if (loginSession == null) {
                    log.info("用户未登录");
                    if (requestURI.equals("/auth/login")) {
                        filterChain.doFilter(request, response);
                    } else {
                        response.sendRedirect("/auth/login");
                    }
                } else {
                    //已登录情况下，访问登录页直接跳转admin/index
                    if (requestURI.equals("/auth/login")) {
                        response.sendRedirect("/admin/index");
                    } else {
                        filterChain.doFilter(request, response);
                    }
                }
            }
        };

        registration.setFilter(filter);

        String[] urlPattern = new String[]{
                "/admin",
                "/admin/index",
                "/auth/login",
                "/admin/article/*",
                "/admin/page/*",
                "/admin/category/*",
                "/admin/links/*",
                "/admin/setting/*",
                "/admin/attach/*"
        };
        registration.setEnabled(true);
        registration.addUrlPatterns(urlPattern);
        registration.setOrder(110);
        registration.setName("loginFilterRegistration");
        return registration;
    }


}
