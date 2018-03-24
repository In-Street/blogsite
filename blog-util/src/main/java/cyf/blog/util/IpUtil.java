package cyf.blog.util;


import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Cheng Yufei
 * @create 2018-03-24 17:42
 **/
public class IpUtil {

    /**
     * 获取客户端IP
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {

        String clientIp = request.getHeader("X-Real-IP");
        if (StringUtil.isEmpty(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("x-forwarded-for");
        }
        if (StringUtil.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        if ("127.0.0.1".equals(clientIp) || "localhost".equals(clientIp)) {
            try {
                clientIp = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return clientIp;
    }
}
