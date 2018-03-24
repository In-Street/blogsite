package cyf.blog.api.configuration;

import cyf.blog.base.common.Constants;
import cyf.blog.dao.model.Logs;
import cyf.blog.util.FastJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * AOP
 */
@Aspect
@Component
@Slf4j
public class AopHandler {


    /**
     * 打印Controller层日志
     * @param pjp 切点
     * @param requestMapping 注解类型
     * @return Object
     * @throws Throwable  Throwable
     */
    @Around("@annotation(requestMapping)")
    public Object printMethodsExecutionTime(ProceedingJoinPoint pjp, RequestMapping requestMapping) throws Throwable {
        long start = System.currentTimeMillis();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String requestURI = request.getRequestURI();


        log.info(">>>>>> 开始请求: {},{}() with argument[s] = {}", requestURI/*pjp.getSignature().getDeclaringTypeName()*/,pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));

        Object result = pjp.proceed();

        String json = "";
        if(result != null){
            json = FastJsonUtils.toJSONString(result);
        }
        long usedTime = System.currentTimeMillis() - start;
        log.info("<<<<<< 结束请求: {},{}(),耗时:{}ms with result = {}",requestURI,pjp.getSignature().getName(),usedTime, json);

        String pcallback = request.getParameter("pcallback");
        if(StringUtils.isNoneBlank(pcallback)){
        	response.addHeader("Content-Type", "application/json;charset=UTF-8");
        	try {
				response.getWriter().write(pcallback + "(");
				response.getWriter().write(json);
				response.getWriter().write(")");
                response.getWriter().close();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return result;
    }

    @Pointcut("execution(* cyf.blog.api.controller.admin.*Controller.*(..))")
    public void execute() {
    }
    /**
     * 后台存储操纵日志
     */
    @Before("execute()")
    public void logRecord() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        Object operateType = request.getAttribute(Constants.LOGRECORD_OPERATE_TYPE);
        Object operateObject = request.getAttribute(Constants.LOGRECORD_OPERATE_OBJECT);
        if (null != operateType && null!=operateObject) {

        }
    }

    private void saveLogs(String operateType,String operaObject,Integer authorId,String ip) {
        Logs logs = new Logs();
        logs.setOperateType(operateType);
        logs.setOperateObject(operaObject);
        logs.setAuthorId(authorId);
        logs.setIp(ip);



    }
}
