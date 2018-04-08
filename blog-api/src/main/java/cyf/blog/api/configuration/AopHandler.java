package cyf.blog.api.configuration;

import com.alibaba.fastjson.JSONObject;
import cyf.blog.base.common.Constants;
import cyf.blog.base.enums.OperateObject;
import cyf.blog.base.enums.OperateResult;
import cyf.blog.base.enums.OperateType;
import cyf.blog.base.model.Response;
import cyf.blog.dao.mapper.LogsMapper;
import cyf.blog.dao.mapper.UsersMapper;
import cyf.blog.dao.model.Logs;
import cyf.blog.dao.model.Users;
import cyf.blog.dao.model.UsersExample;
import cyf.blog.util.FastJsonUtils;
import cyf.blog.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * AOP
 */
@Aspect
@Component
@Slf4j
public class AopHandler {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private LogsMapper logsMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    /**
     * 打印Controller层日志
     *
     * @param pjp            切点
     * @param requestMapping 注解类型
     * @return Object
     * @throws Throwable Throwable
     */
    @Around("@annotation(requestMapping)")
    public Object printMethodsExecutionTime(ProceedingJoinPoint pjp, RequestMapping requestMapping) throws Throwable {
        long start = System.currentTimeMillis();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String requestURI = request.getRequestURI();


        log.info(">>>>>> 开始请求: {},{}() with argument[s] = {}", requestURI/*pjp.getSignature().getDeclaringTypeName()*/, pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));

        Object result = pjp.proceed();

        String json = "";
        if (result != null) {
            json = FastJsonUtils.toJSONString(result);
        }
        long usedTime = System.currentTimeMillis() - start;
        log.info("<<<<<< 结束请求: {},{}(),耗时:{}ms with result = {}", requestURI, pjp.getSignature().getName(), usedTime, json);

        String pcallback = request.getParameter("pcallback");
        if (StringUtils.isNoneBlank(pcallback)) {
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
    @Around("@annotation(logRecord)")
    public Object logRecord(ProceedingJoinPoint joinPoint, LogRecord logRecord) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        Object[] args = joinPoint.getArgs();
        Object proceed = joinPoint.proceed();

        Integer operateType = (Integer) request.getAttribute(Constants.LOGRECORD_OPERATE_TYPE);
        Integer operateObject = (Integer) request.getAttribute(Constants.LOGRECORD_OPERATE_OBJECT);
        boolean success = ((Response) proceed).isSuccess();
        OperateResult operateResult = success ? OperateResult.success : OperateResult.fail;

        asyncTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                log.info("-----------------异步(" + Thread.currentThread().getName() + "): 操作日志存储----------------------");
                int authorId = 0;
                String authorName = null;
                if (requestURI.contains("/toLogin")) {
                    String username = (String) args[0];
                    UsersExample usersExample = new UsersExample();
                    usersExample.setLimit(1);
                    UsersExample.Criteria criteria = usersExample.createCriteria();
                    criteria.andUsernameEqualTo(username);
                    List<Users> users = usersMapper.selectByExample(usersExample);
                    if (!CollectionUtils.isEmpty(users)) {
                        authorId = users.get(0).getUid();
                        authorName = users.get(0).getUsername();
                    }
                } else {
                    String s = stringRedisTemplate.opsForValue().get(Constants.LOGIN_SESSION_KEY + request.getSession().getId());
                    Users users = JSONObject.parseObject(s, Users.class);
                    authorId = users.getUid();
                    authorName = users.getUsername();
                }
                if (null != operateType && null != operateObject) {
                    saveLogs(OperateType.get(operateType).toString(), OperateObject.get(operateObject).toString(), authorId, authorName, IpUtil.getClientIp(request), operateResult.toString());
                }
            }
        });
        return proceed;
    }

    /**
     * 操作日志存储
     *
     * @param operateType
     * @param operaObject
     * @param authorId
     * @param authorName
     * @param ip
     * @param operateResult
     */
    private void saveLogs(String operateType, String operaObject, Integer authorId, String authorName, String ip, String operateResult) {
        Logs logs = new Logs();
        logs.setOperateType(operateType);
        logs.setOperateObject(operaObject);
        logs.setAuthorId(authorId);
        logs.setIp(ip);
        logs.setAuthorName(authorName);
        logs.setOperateResult(operateResult);
        logsMapper.insertSelective(logs);
    }
}
