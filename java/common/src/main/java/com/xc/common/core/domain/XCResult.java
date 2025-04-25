package com.xc.common.core.domain;
import lombok.Data;

import java.io.Serializable;

/**
 * @param <T>
 */
@Data
public class XCResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;
    private T data;

    // 常用成功状态码
    public static final Integer SUCCESS_CODE = 200;
    // 常用失败状态码
    public static final Integer FAIL_CODE = 500;

    // ok方法重载
    public static <T> XCResult<T> ok() {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(SUCCESS_CODE);
        XCResult.setMsg("success");
        return XCResult;
    }

    public static <T> XCResult<T> ok(String msg) {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(SUCCESS_CODE);
        XCResult.setMsg(msg);
        return XCResult;
    }

    public static <T> XCResult<T> ok(T data) {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(SUCCESS_CODE);
        XCResult.setMsg("success");
        XCResult.setData(data);
        return XCResult;
    }

    public static <T> XCResult<T> ok(String msg, T data) {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(SUCCESS_CODE);
        XCResult.setMsg(msg);
        XCResult.setData(data);
        return XCResult;
    }

    // fail方法重载
    public static <T> XCResult<T> fail() {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(FAIL_CODE);
        XCResult.setMsg("fail");
        return XCResult;
    }

    public static <T> XCResult<T> fail(String msg) {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(FAIL_CODE);
        XCResult.setMsg(msg);
        return XCResult;
    }

    public static <T> XCResult<T> fail(Integer code, String msg) {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(code);
        XCResult.setMsg(msg);
        return XCResult;
    }

    public static <T> XCResult<T> fail(Integer code, String msg, T data) {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(code);
        XCResult.setMsg(msg);
        XCResult.setData(data);
        return XCResult;
    }
}
