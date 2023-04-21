package cn.edu.csu.smproject.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "message")
public class LKResponse {
    private List<LKResult> lkResultList;

    @JacksonXmlElementWrapper(localName = "lkResultList")
    @JacksonXmlProperty(localName = "result")
    public List<LKResult> getLkResultList() {
        return lkResultList;
    }

    public void setLkResultList(List<LKResult> lkResultList) {
        this.lkResultList = lkResultList;
    }
}
