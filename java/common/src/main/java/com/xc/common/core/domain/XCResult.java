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
    private static final Integer SUCCESS_CODE = 200;
    // 成功msg
    private static final String SUCCESS_MSG = "success";
    // 常用失败状态码
    private static final Integer FAIL_CODE = 500;
    // 失败msg
    private static final String FAIL_MSG = "fail";

    // ok方法重载
    public static <T> XCResult<T> ok() {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(SUCCESS_CODE);
        XCResult.setMsg(SUCCESS_MSG);
        XCResult.setData(null);
        return XCResult;
    }

    public static <T> XCResult<T> ok(T data) {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(SUCCESS_CODE);
        XCResult.setMsg(SUCCESS_MSG);
        XCResult.setData(data);
        return XCResult;
    }

    // fail方法重载
    public static <T> XCResult<T> fail() {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(FAIL_CODE);
        XCResult.setMsg(FAIL_MSG);
        XCResult.setData(null);
        return XCResult;
    }

    public static <T> XCResult<T> fail(String msg) {
        XCResult<T> XCResult = new XCResult<>();
        XCResult.setCode(FAIL_CODE);
        XCResult.setMsg(msg);
        XCResult.setData(null);
        return XCResult;
    }
}
