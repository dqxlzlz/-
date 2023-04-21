package cn.edu.csu.smproject.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

//我们的xml里面的"message"标签就存储在这个TicketReuqest对象里面，这里一个根结点，所以称为RootElement
@JacksonXmlRootElement(localName ="message")
public class TicketRequest {

    //我们将XML里面的"orderList"元素里面的"order"元素放入到这里orderList中
    @JacksonXmlElementWrapper(localName ="orderlist")
    @JacksonXmlProperty(localName ="order")
    private List<OrderRequest> orderList;

    public List<OrderRequest> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderRequest> orderList) {
        this.orderList = orderList;
    }

}
