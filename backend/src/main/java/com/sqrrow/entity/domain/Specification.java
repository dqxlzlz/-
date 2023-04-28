package com.sqrrow.entity.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Specification {
    @JacksonXmlProperty(isAttribute = true,localName = "type")
    private String type;

    @JacksonXmlProperty(isAttribute = true,localName = "id")
    private String id;

    @JacksonXmlProperty(isAttribute = true,localName = "name")
    private String name;

    @JacksonXmlProperty(isAttribute = true,localName = "value")
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
