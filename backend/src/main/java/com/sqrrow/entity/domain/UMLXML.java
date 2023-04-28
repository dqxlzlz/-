package com.sqrrow.entity.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.HashMap;

@JacksonXmlRootElement(localName = "uml:Model")
public class UMLXML {

    @JacksonXmlElementWrapper(localName = "packagedElement",useWrapping = false)
    @JacksonXmlProperty(localName = "packagedElement")
    private ArrayList<PackagedElement> packagedElements;

    //记录所有类的集合
    private HashMap<String,PackagedElement> classMap;

    //记录所有接口的集合
    private HashMap<String,PackagedElement> interfaceMap;

    public UMLXML() {
        classMap = new HashMap<String,PackagedElement>();
        interfaceMap = new HashMap<String,PackagedElement>();

    }

    public ArrayList<PackagedElement> getPackagedElements() {
        return packagedElements;
    }

    public void setPackagedElements(ArrayList<PackagedElement> packagedElements) {
        this.packagedElements = packagedElements;
    }

    public HashMap<String, PackagedElement> getClassMap() {
        return classMap;
    }

    public void setClassMap(HashMap<String, PackagedElement> classMap) {
        this.classMap = classMap;
    }

    public HashMap<String, PackagedElement> getInterfaceMap() {
        return interfaceMap;
    }

    public void setInterfaceMap(HashMap<String, PackagedElement> interfaceMap) {
        this.interfaceMap = interfaceMap;
    }
}
