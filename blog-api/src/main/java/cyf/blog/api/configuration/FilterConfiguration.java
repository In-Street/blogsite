package cyf.blog.api.configuration;


import cyf.blog.base.common.Constants;
import cyf.blog.base.model.Header;
import cyf.blog.base.model.LocalData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.lang.reflect.Field;

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
     * @return
     */
//    @Bean
    public FilterRegistrationBean headerFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter filter = new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                //反射映射header
                Header header = new Header();
                Field[] fields = header.getClass().getDeclaredFields();
                for (Field field : fields) {
                    String name = field.getName();
                    field.setAccessible(true);//设置允许访问
                    try {
                        field.set(header, request.getHeader(name));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                String sessionId = header.getSessionId();
                if (StringUtils.isNotBlank(sessionId)) {
                    String userJson = stringRedisTemplate.opsForValue().get(Constants.USER_LOGIN_KEY + sessionId);
                    if (StringUtils.isNotBlank(userJson)) {
                        LocalData.USER_JSON.set(userJson);
                        log.info("用户信息已设置,{}", userJson);
                        /*User dsmUser = FastJsonUtils.toBean(userJson, User.class);
                        header.setUid(dsmUser.getId());*/
                    }
                }

                LocalData.HEADER.set(header);

                filterChain.doFilter(request, response);

                LocalData.USER_JSON.remove();
                LocalData.HEADER.remove();
                System.out.println();
            }
        };
//        registration.addUrlPatterns("/v1/*","/app/*");
//        registration.addUrlPatterns("/user/*");
        registration.setFilter(filter);
        registration.setName("headerFilterRegistration");
        registration.setOrder(1);
        return registration;
    }


    /**
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
                        response.sendRedirect("/admin");
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
        };
        registration.setEnabled(true);
        registration.addUrlPatterns(urlPattern);
        registration.setOrder(110);
        registration.setName("loginFilterRegistration");
        return registration;
    }


}
