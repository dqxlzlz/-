package cn.edu.csu.smproject.domain.DF;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Resource {
    @JacksonXmlProperty(isAttribute = true,localName = "Id")
    private String id;

    @JacksonXmlProperty(namespace = "a",localName = "Name")
    private String name;

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
}
