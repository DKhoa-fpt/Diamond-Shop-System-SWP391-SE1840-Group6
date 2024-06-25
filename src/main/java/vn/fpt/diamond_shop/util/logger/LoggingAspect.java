package vn.fpt.diamond_shop.util.logger;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;


@Aspect
@Component
@Log4j2
public class LoggingAspect {
//    private final StopWatch stopWatch = new StopWatch();
    protected static final Gson GSON = new Gson();

    //define ref annotation
    @Pointcut("@annotation(vn.fpt.diamond_shop.util.logger.LogActivities)")
    public void webLog() {
    }

    @AfterThrowing(value = "webLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint proceedingJoinPoint, Throwable e) throws Throwable {
//        stopWatch.stop();
        WrapRequest<Object> logReq = logRequest(proceedingJoinPoint);
        log.info(GSON.toJson(logReq));
        throw e.initCause(e.getCause());
    }

    @Around("webLog()")
    public Object logMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        WrapRequest<Object> logReq = logRequest(proceedingJoinPoint);
        //calculate method execution time
//        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
//        stopWatch.stop();

        WrapResponse<Object> logRes = new WrapResponse<>();
        BeanUtils.copyProperties(logReq, logRes);
        logRes.setResponse(result);
//        logRes.setExecutionTime(stopWatch.getLastTaskTimeMillis());

        //Log method execution time
        log.info(GSON.toJson(logReq));
        log.info(GSON.toJson(logRes));
        MDC.remove("requestId");
        return result;
    }

    private WrapRequest<Object> logRequest(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        WrapRequest<Object> logReq = new WrapRequest();
        HttpServletRequest request = null;
        Object requestBody = null;

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        requestBody = this.getRequestBody(method, joinPoint.getArgs());

        logReq.setRequest(requestBody);
        if (!ObjectUtils.isEmpty(attributes)) {
            request = attributes.getRequest();
            logReq.setMethod(request.getMethod());
            logReq.setRequestPath(request.getRequestURI());
            logReq.setHeaders(this.getRequestHeader(request));
        }
        return logReq;
    }

    private Map<String, String> getRequestHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> requestHeader = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            requestHeader.put(key, value);
        }
        return requestHeader;
    }

    private Object getRequestBody(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }

            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();

                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.isEmpty()) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

    protected static class WrapRequest<T> {
        private Map<String, String> headers;
        private String requestPath;
        private String method;
        private T request;

        public WrapRequest() {
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        public String getRequestPath() {
            return requestPath;
        }

        public void setRequestPath(String requestPath) {
            this.requestPath = requestPath;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public T getRequest() {
            return request;
        }

        public void setRequest(T request) {
            this.request = request;
        }
    }

    protected static class WrapResponse<T> {
        private Map<String, String> headers;
        private String requestPath;
        private String method;
        private T response;
        private long executionTime;

        public WrapResponse() {
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        public String getRequestPath() {
            return requestPath;
        }

        public void setRequestPath(String requestPath) {
            this.requestPath = requestPath;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public T getResponse() {
            return response;
        }

        public void setResponse(T response) {
            this.response = response;
        }

        public long getExecutionTime() {
            return executionTime;
        }

        public void setExecutionTime(long executionTime) {
            this.executionTime = executionTime;
        }
    }
}