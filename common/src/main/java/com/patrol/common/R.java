package com.patrol.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回体
 *
 * @param <T> 数据类型
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    private int code;

    /** 消息 */
    private String msg;

    /** 数据 */
    private T data;

    /** 时间戳 */
    private long timestamp;

    public R() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMsg("操作成功");
        return r;
    }

    public static <T> R<T> ok(T data) {
        R<T> r = ok();
        r.setData(data);
        return r;
    }

    public static <T> R<T> ok(String msg, T data) {
        R<T> r = ok(data);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> error() {
        R<T> r = new R<>();
        r.setCode(500);
        r.setMsg("系统异常");
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = error();
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> error(int code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> error(int code, String msg, T data) {
        R<T> r = error(code, msg);
        r.setData(data);
        return r;
    }
}
