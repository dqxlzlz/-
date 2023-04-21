package cn.edu.csu.smproject.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OwnedEnd {
    @JacksonXmlProperty(isAttribute = true,localName = "type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
