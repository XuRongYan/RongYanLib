package com.rongyan.rongyanlibrary.rxHttpHelper.http;

/**
 * 对错误结果进行处理并抛出异常
 * Created by XRY on 2017/4/14.
 */

public class ApiException extends RuntimeException {
    private static final String NET_ERROR = "网络连接异常";
    private static final String NET_PERMISSION_ERROR = "网络权限异常";
    private static final String NULL_PARAMENT = "未收到参数，需要传参但未接受到任何参数";
    private static final String SUCCESS = "成功调取接口";
    private static final String PARAMENT_ERROR = "参数错误，参数值为空";
    private static final String PARAMENT_FORMAT_ERROR = "参数格式错误，不是json数据或者是其他要求数据";
    private static final String PARAMENT_LACK = "缺少特定名称的参数";
    private static final String DATA_NOT_FOUND = "缺少必须参数,缺少特定名称的参数";
    private static final String DATABASE_LINK_FAIL = "数据库连接失败";
    private static final String ADMIN_NOT_FOUND = "该用户不存在";
    private static final String PSW_ERROR = "登录密码错误";
    private static final String OBJECT_FORBIDDEN = "该对象已被禁用";
    private static final String KEYWORD_OCCUPY = "关键字段已被占用";
    private static final String OPERATION_FAIL = "操作失败";
    private static final String NOT_LOGIN = "未登录";
    private static final String SESSION_OVERDUE = "session过期";
    private static final String ADMIN_CROWD = "该用户被挤下线";
    private static final String SERVER_TINE_OUT = "服务器响应超时";
    private static final String SERVER_BUSY = "服务器忙";
    private static final String ILLEGAL_OPERATION = "非法操作";
    private static final String NO_OPERATION_ACCESS = "没有操作权限";
    private static final String RONGYUN_ERROR = "融云服务器异常";
    private static final String JIGUANG_ERROR = "极光服务器异常";
    private static final String DAYU_ERROR = "大于服务器异常";
    private static final String VERIFICATION_CODE_ERROR = "短信验证码不正确";
    private static final String VERIFICATION_CODE_TIMEOUT = "验证码超时";
    private static final String VERIFICATION_CODE_NOT_FOUND = "该手机未获取验证码";
    private static final String OTHER_CASE = "其他情况";
    private static final String NULL_RETURN = "返回值为空";
    private static final String RESULT_CODE_FAIL = "验证码错误";
    private static final String INVALID_USER = "用户不存在";
    private static final String LOGIN_ERROR = "用户名或密码错误";

    private static String message;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    private static String getApiExceptionMessage(int resultCode) {
        switch (resultCode) {
            case 103:
                message = OTHER_CASE;
            case 0:
                message = NULL_PARAMENT;
                break;
            case 2:
                message = PARAMENT_ERROR;
                break;
            case 201:
                message = PARAMENT_FORMAT_ERROR;
                break;
            case 202:
                message = PARAMENT_LACK;
                break;
            case 203:
                message = VERIFICATION_CODE_ERROR;
                break;
            case 204:
                message = VERIFICATION_CODE_TIMEOUT;
                break;
            case 205:
                message = VERIFICATION_CODE_NOT_FOUND;
                break;
            case 3:
                message = DATABASE_LINK_FAIL;
                break;
            case 4:
                message = INVALID_USER;
                break;
            case 5:
                message = LOGIN_ERROR;
            case 301:
                message = ADMIN_NOT_FOUND;
                break;
            case 302:
                message = PSW_ERROR;
                break;
            case 6:
                message = OBJECT_FORBIDDEN;
                break;
            case 7:
                message = KEYWORD_OCCUPY;
                break;
            case 8:
                message = RESULT_CODE_FAIL;
                break;
            case 9:
                message = OPERATION_FAIL;
                break;
            case 10:
                message = NOT_LOGIN;
                break;
            case 11:
                message = SESSION_OVERDUE;
                break;
            case 12:
                message = ADMIN_CROWD;
                break;
            case 13:
                message = SERVER_TINE_OUT;
                break;
            case 14:
                message = SERVER_BUSY;
                break;
            case 18:
                message = ILLEGAL_OPERATION;
                break;
            case 19:
                message = NO_OPERATION_ACCESS;
                break;
            case 21:
                message = RONGYUN_ERROR;
                break;
            case 22:
                message = JIGUANG_ERROR;
                break;
            case 23:
                message = DAYU_ERROR;
                break;
            case 20:
                message = VERIFICATION_CODE_ERROR;
                break;
        }
        return message;
    }

}
