package cn.edu.csu.smproject.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Generalization {
    @JacksonXmlProperty(isAttribute = true,localName = "id")
    private String id;

    @JacksonXmlProperty(isAttribute = true,localName = "general")
    private String general;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }
}
