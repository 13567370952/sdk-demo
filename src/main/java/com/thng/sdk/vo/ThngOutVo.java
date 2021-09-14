package com.thng.sdk.vo;

import java.util.List;
import java.util.Map;

public class ThngOutVo {
    private String result;
    private String message;
//    private List<Map<String, Object>> data;
    private String data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
