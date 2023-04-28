package com.sqrrow.entity.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "message")
public class CKResponse {
    private List<CKResult> ckResultList;

    @JacksonXmlElementWrapper(localName = "ckResultList")
    @JacksonXmlProperty(localName = "result")
    public List<CKResult> getCkResultList() {
        return ckResultList;
    }

    public void setCkResultList(List<CKResult> ckResultList) {
        this.ckResultList = ckResultList;
    }
}
