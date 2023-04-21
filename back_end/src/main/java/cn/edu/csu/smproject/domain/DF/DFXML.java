package cn.edu.csu.smproject.domain.DF;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Model")
public class DFXML {

    @JacksonXmlProperty(namespace = "o",localName = "RootObject")
    private RootObject rootObject;

    @JacksonXmlProperty(isAttribute = true,localName = "a")
    private String a;

    public RootObject getRootObject() {
        return rootObject;
    }

    public void setRootObject(RootObject rootObject) {
        this.rootObject = rootObject;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
