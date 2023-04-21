package cn.edu.csu.smproject.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CKResult {

    private String name;

    private int wmc;

    private int rfc;

    private int dit;

    private int noc;

    private int cbo;

    private int lcom;

    @JacksonXmlProperty(localName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JacksonXmlProperty(localName = "wmc")
    public int getWmc() {
        return wmc;
    }

    public void setWmc(int wmc) {
        this.wmc = wmc;
    }

    @JacksonXmlProperty(localName = "rfc")
    public int getRfc() {
        return rfc;
    }

    public void setRfc(int rfc) {
        this.rfc = rfc;
    }

    @JacksonXmlProperty(localName = "dit")
    public int getDit() {
        return dit;
    }

    public void setDit(int dit) {
        this.dit = dit;
    }

    @JacksonXmlProperty(localName = "noc")
    public int getNoc() {
        return noc;
    }

    public void setNoc(int noc) {
        this.noc = noc;
    }

    @JacksonXmlProperty(localName = "cbo")
    public int getCbo() {
        return cbo;
    }

    public void setCbo(int cbo) {
        this.cbo = cbo;
    }

    @JacksonXmlProperty(localName = "lcom")
    public int getLcom() {
        return lcom;
    }

    public void setLcom(int lcom) {
        this.lcom = lcom;
    }
}
