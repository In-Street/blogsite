package cyf.blog.api.configuration;

import cyf.blog.dao.common.AdminCommon;
import cyf.blog.dao.common.Common;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cheng Yufei
 * @create 2018-03-07 15:47
 **/
@Component
public class Interception implements HandlerInterceptor {


    @Resource
    private Common commons;
    @Resource
    private AdminCommon adminCommon;

    /**
     * 调用处理程序之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

    /**
     * 呈现视图之前，将给定的模型向视图公开
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //页面中可直接${commons.site_option('site_title','My Blog')}
        request.setAttribute("commons", commons);
        request.setAttribute("admincommon",adminCommon);
    }

    /**
     * 完成请求处理后回调，呈现视图之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
