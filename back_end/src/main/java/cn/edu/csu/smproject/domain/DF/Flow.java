package cn.edu.csu.smproject.domain.DF;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Flow {
    @JacksonXmlProperty(isAttribute = true,localName = "Id")
    private String Id;

    @JacksonXmlProperty(namespace = "a",localName = "Name")
    private String name;

    @JacksonXmlProperty(namespace = "c",localName = "Object1")
    private Object1 object1;

    @JacksonXmlProperty(namespace = "c",localName = "Object2")
    private Object2 object2;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object1 getObject1() {
        return object1;
    }

    public void setObject1(Object1 object1) {
        this.object1 = object1;
    }

    public Object2 getObject2() {
        return object2;
    }

    public void setObject2(Object2 object2) {
        this.object2 = object2;
    }
}
