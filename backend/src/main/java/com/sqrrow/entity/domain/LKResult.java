package com.sqrrow.entity.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class LKResult {
    private String name;

    private int totalNumberOfMethod;

    private int totalNumberOfAttr;

    private int noo;

    private int noa;

    private double si;

    @JacksonXmlProperty(localName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JacksonXmlProperty(localName = "totalNumberOfMethod")
    public int getTotalNumberOfMethod() {
        return totalNumberOfMethod;
    }

    public void setTotalNumberOfMethod(int totalNumberOfMethod) {
        this.totalNumberOfMethod = totalNumberOfMethod;
    }

    @JacksonXmlProperty(localName = "totalNumberOfAttr")
    public int getTotalNumberOfAttr() {
        return totalNumberOfAttr;
    }

    public void setTotalNumberOfAttr(int totalNumberOfAttr) {
        this.totalNumberOfAttr = totalNumberOfAttr;
    }

    @JacksonXmlProperty(localName = "noo")
    public int getNoo() {
        return noo;
    }

    public void setNoo(int noo) {
        this.noo = noo;
    }

    @JacksonXmlProperty(localName = "noa")
    public int getNoa() {
        return noa;
    }

    public void setNoa(int noa) {
        this.noa = noa;
    }

    @JacksonXmlProperty(localName = "si")
    public double getSi() {
        return si;
    }

    public void setSi(double si) {
        this.si = si;
    }
}
