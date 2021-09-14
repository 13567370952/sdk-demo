package com.thng.sdk.vo;

public class TetsVo {
    private String day;
    private Double money;
    private Double quatity;

    public void setDay(String day) {
        this.day = day;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setQuatity(Double quatity) {
        this.quatity = quatity;
    }

    public Double getMoney() {
        return money;
    }

    public Double getQuatity() {
        return quatity;
    }

    public String getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "TetsVo{" +
                "day='" + day + '\'' +
                ", money=" + money +
                ", quatity=" + quatity +
                '}';
    }
}
