package com.submit.component.aspect;


import com.submit.exception.ApiException;
import com.submit.utils.AspectJUtils;
import com.submit.utils.IpUtils;
import com.submit.utils.QuickRequestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.submit.exception.ApiException.ERROR.REQUEST_BEYOND_LIMIT;


/**
 * @author xuexiang
 * @since 2018/8/7 下午2:06
 */
@Aspect
@Component
public class LimitedRequestAspect {

    @Before("@within(org.springframework.web.bind.annotation.RestController) && @annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, LimitedRequest limit) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = IpUtils.getRealIp(request);
        String url = request.getRequestURL().toString();
        String methodName = AspectJUtils.getMethodName(joinPoint);
        String key = "requestLimit_".concat(url).concat(ip).concat(methodName);

        if (QuickRequestUtils.isQuickRequest(key, limit)) {
            throw new ApiException("请求过于频繁！", REQUEST_BEYOND_LIMIT);
        }
    }
}
