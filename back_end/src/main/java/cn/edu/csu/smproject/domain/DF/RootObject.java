package cn.edu.csu.smproject.domain.DF;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RootObject {
    @JacksonXmlProperty(isAttribute = true,localName = "Id")
    private String id;

    @JacksonXmlProperty(namespace = "c",localName = "Children")
    private Children children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Children getChildren() {
        return children;
    }

    public void setChildren(Children children) {
        this.children = children;
    }
}
