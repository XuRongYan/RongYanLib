package com.rongyan.rongyanlibrary.rxHttpHelper.http;

/**
 * Created by XRY on 2017/4/14.
 */

public class HttpResult<T> {
    private int resultCode;
    private String resultMessage;
    private T resultData;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getData() {
        return resultData;
    }

    public void setData(T data) {
        this.resultData = data;
    }
}
