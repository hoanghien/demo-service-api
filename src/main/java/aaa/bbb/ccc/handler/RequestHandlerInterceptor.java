package aaa.bbb.ccc.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Slf4j
public class RequestHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equalsIgnoreCase("GET")) {
            if  (!request.getRequestURI().startsWith("/assets")) {
                if (request.getQueryString() != null) {
//                    log.info(request.getRequestURI() + "?" + request.getQueryString());
                } else {
//                    log.info(request.getRequestURI());
                }
            }

//            if (request.getRequestURI().startsWith("/tt")) {
//            }
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerKey = headerNames.nextElement();
            if (headerKey.equalsIgnoreCase("msisdn")
                    || headerKey.equalsIgnoreCase("x-real-ip")
                    || headerKey.equalsIgnoreCase("x-requested-with")
                    || headerKey.equalsIgnoreCase("referer")) {
//                log.debug("HEADER_DATA:" + headerKey + "=" + request.getHeader(headerKey));
            }
        }

        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.addHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.addHeader("Expires", "0"); // Proxies.
//        String rootUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
//        request.getSession().setAttribute("base_url", "/");
        return true;
    }
}
