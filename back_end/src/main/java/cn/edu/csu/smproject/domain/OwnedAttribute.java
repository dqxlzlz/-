package cn.edu.csu.smproject.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OwnedAttribute {
    @JacksonXmlProperty(isAttribute = true,localName = "name")
    private String name;

    @JacksonXmlProperty(isAttribute = true,localName = "type")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
