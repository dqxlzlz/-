package com.sqrrow.entity.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName ="message")
public class TicketResponse {

    private List<OrderResponse> orderList;

    @JacksonXmlElementWrapper(localName ="orderlist")
    @JacksonXmlProperty(localName ="order")
    public List<OrderResponse> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderResponse> orderList) {
        this.orderList = orderList;
    }
}
