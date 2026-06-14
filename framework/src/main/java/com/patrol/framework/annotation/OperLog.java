package com.patrol.framework.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * <p>
 * 标注在 Controller 方法上，自动记录操作日志。
 *
 * @author patrol-team
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {

    /** 操作描述，如"新增用户" */
    String value() default "";

    /** 操作类型 */
    OperType type() default OperType.OTHER;

    /** 是否记录请求参数 */
    boolean recordArgs() default true;

    /** 是否记录返回结果 */
    boolean recordResult() default false;

    enum OperType {
        CREATE, UPDATE, DELETE, QUERY, EXPORT, IMPORT, LOGIN, OTHER
    }
}
