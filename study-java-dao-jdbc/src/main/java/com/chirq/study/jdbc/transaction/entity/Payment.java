package com.chirq.study.jdbc.transaction.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Payment {

    private Long id;

    private long userId;

    private BigDecimal payMoney;

    private Date payTime;

    private Integer payStatus;

    private String payMsg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayMsg() {
        return payMsg;
    }

    public void setPayMsg(String payMsg) {
        this.payMsg = payMsg;
    }

    @Override
    public String toString() {
        return "Payment [id=" + id + ", userId=" + userId + ", payMoney=" + payMoney + ", payTime=" + payTime + ", payStatus=" + payStatus + ", payMsg=" + payMsg + "]";
    }
}
