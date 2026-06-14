package com.patrol.system.aspect;

import com.patrol.framework.annotation.OperLog;
import com.patrol.system.entity.SysOperLog;
import com.patrol.system.mapper.SysOperLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 操作日志切面
 * <p>
 * 拦截被 @OperLog 标注的方法，自动记录操作日志到 sys_oper_log。
 * 日志写入失败不影响主业务。
 *
 * @author patrol-team
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperLogAspect {

    private final SysOperLogMapper sysOperLogMapper;

    /** 排除的敏感参数名 */
    private static final String[] SENSITIVE_PARAMS = {
            "password", "newPassword", "oldPassword",
            "token", "secret", "authorization"
    };

    @Around("@annotation(operLog)")
    public Object around(ProceedingJoinPoint joinPoint, OperLog operLog) throws Throwable {
        long startTime = System.currentTimeMillis();

        SysOperLog logEntity = new SysOperLog();
        logEntity.setCreateTime(LocalDateTime.now());
        logEntity.setOperStatus(1); // 默认成功

        try {
            // 请求信息
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                logEntity.setRequestUri(request.getRequestURI());
                logEntity.setRequestMethod(request.getMethod());
                logEntity.setIp(getClientIp(request));
            }

            // 操作人
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()
                    && !"anonymousUser".equals(auth.getPrincipal())) {
                logEntity.setOperator(auth.getName());
            }

            // 请求参数（脱敏）
            if (operLog.recordArgs()) {
                String args = Arrays.stream(joinPoint.getArgs())
                        .filter(arg -> !(arg instanceof HttpServletRequest))
                        .filter(arg -> !(arg instanceof javax.servlet.http.HttpServletResponse))
                        .map(arg -> {
                            String s = String.valueOf(arg);
                            // 截断过长参数
                            return s.length() > 500 ? s.substring(0, 500) + "..." : s;
                        })
                        .collect(Collectors.joining(", "));
                logEntity.setRequestArgs(sanitize(args));
            }

            // 执行业务
            Object result = joinPoint.proceed();

            // 返回结果
            long costTime = System.currentTimeMillis() - startTime;
            logEntity.setCostTime(costTime);
            if (operLog.recordResult() && result != null) {
                String resultStr = String.valueOf(result);
                if (resultStr.length() > 1000) resultStr = resultStr.substring(0, 1000) + "...";
                logEntity.setResponseBody(resultStr);
            }

            // 异步写入
            saveLog(logEntity);

            log.info("[OperLog] {} {} | {}ms | operator={} | status=success",
                    logEntity.getRequestMethod(), logEntity.getRequestUri(),
                    costTime, logEntity.getOperator());

            return result;

        } catch (Throwable e) {
            long costTime = System.currentTimeMillis() - startTime;
            logEntity.setOperStatus(0);
            logEntity.setCostTime(costTime);
            logEntity.setErrorMsg(e.getMessage() != null
                    ? e.getMessage().substring(0, Math.min(e.getMessage().length(), 1000))
                    : "未知错误");
            saveLog(logEntity);

            log.warn("[OperLog] {} {} | {}ms | operator={} | status=fail | {}",
                    logEntity.getRequestMethod(), logEntity.getRequestUri(),
                    costTime, logEntity.getOperator(), e.getMessage());
            throw e;
        }
    }

    private void saveLog(SysOperLog logEntity) {
        try {
            sysOperLogMapper.insert(logEntity);
        } catch (Exception e) {
            // 日志写入失败不影响主业务
            log.error("操作日志写入失败", e);
        }
    }

    /** 获取客户端 IP */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip != null && ip.contains(",") ? ip.split(",")[0].trim() : ip;
    }

    /** 简单脱敏 */
    private String sanitize(String content) {
        if (content == null) return null;
        for (String key : SENSITIVE_PARAMS) {
            content = content.replaceAll(
                    "(?i)\"" + key + "\"\\s*:\\s*\"[^\"]*\"",
                    "\"" + key + "\":\"***\"");
        }
        return content;
    }
}
