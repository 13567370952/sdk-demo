package com.thng.sdk.vo;

public class ThngInVo {
    private String method = "POST";
    private String uri;
    private String data;

    public ThngInVo() {
    }

    public String getMethod() {
        return method;
    }

//    public void setMethod(String method) {
//        this.method = method;
//    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
