package cyf.blog.api.configuration;

import cyf.blog.base.model.Response;
import cyf.blog.util.FastJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ControllerAdvice  控制层的切面 捕获异常
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest req, HttpServletResponse response , Exception e)  {
        log.error("异常信息", e);
        HttpStatus status = getStatus(req);
        String s = FastJsonUtils.toJSONString(new Response(status));
        log.info("数据返回，{}",s);
        try {
            response.getWriter().write(s);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        log.info("异常捕获，{}", statusCode);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}