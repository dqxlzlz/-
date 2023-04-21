package cn.edu.csu.smproject.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OrderRequest {

    public String lotteryType;

    public String phase;

    public String orderId;

    public String playType;

    public String betCode;

    public String multiple;

    public String amount;

    public String endTime;

    public String add;

    public OrderRequest() {
    }

    public OrderRequest(String lotteryType, String phase, String orderId, String playType, String betCode, String multiple, String amount, String endTime, String add) {
        this.lotteryType = lotteryType;
        this.phase = phase;
        this.orderId = orderId;
        this.playType = playType;
        this.betCode = betCode;
        this.multiple = multiple;
        this.amount = amount;
        this.endTime = endTime;
        this.add = add;
    }

    @JacksonXmlProperty(localName = "lotterytype")
    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    @JacksonXmlProperty(localName = "phase")
    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    @JacksonXmlProperty(localName = "orderid")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JacksonXmlProperty(localName = "playtype")
    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    @JacksonXmlProperty(localName = "betcode")
    public String getBetCode() {
        return betCode;
    }

    public void setBetCode(String betCode) {
        this.betCode = betCode;
    }

    @JacksonXmlProperty(localName = "multiple")
    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    @JacksonXmlProperty(localName = "amount")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @JacksonXmlProperty(localName = "endtime")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @JacksonXmlProperty(localName = "add")
    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }
}
