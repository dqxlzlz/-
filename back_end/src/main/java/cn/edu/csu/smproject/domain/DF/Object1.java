package cn.edu.csu.smproject.domain.DF;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Object1 {
    @JacksonXmlProperty(namespace = "o",localName = "Process")
    private java.lang.Process process;

    public java.lang.Process getProcess() {
        return process;
    }

    public void setProcess(java.lang.Process process) {
        this.process = process;
    }
}
