package cn.edu.csu.smproject.domain.DF;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;

@JacksonXmlRootElement(localName = "message")
public class FPResponse {
    @JacksonXmlElementWrapper(localName = "processes")
    @JacksonXmlProperty(localName = "process")
    private ArrayList<java.lang.Process> processArrayList;

    @JacksonXmlElementWrapper(localName = "flows")
    @JacksonXmlProperty(localName = "flow")
    private ArrayList<Flow> flowArrayList;

    @JacksonXmlElementWrapper(localName = "organizationUnits")
    @JacksonXmlProperty(localName = "organizationUnit")
    private ArrayList<OrganizationUnit> organizationUnitArrayList;

    @JacksonXmlElementWrapper(localName = "resources")
    @JacksonXmlProperty(localName = "resource")
    private ArrayList<Resource> resourceArrayList;

    public ArrayList<java.lang.Process> getProcessArrayList() {
        return processArrayList;
    }

    public void setProcessArrayList(ArrayList<java.lang.Process> processArrayList) {
        this.processArrayList = processArrayList;
    }

    public ArrayList<Flow> getFlowArrayList() {
        return flowArrayList;
    }

    public void setFlowArrayList(ArrayList<Flow> flowArrayList) {
        this.flowArrayList = flowArrayList;
    }

    public ArrayList<OrganizationUnit> getOrganizationUnitArrayList() {
        return organizationUnitArrayList;
    }

    public void setOrganizationUnitArrayList(ArrayList<OrganizationUnit> organizationUnitArrayList) {
        this.organizationUnitArrayList = organizationUnitArrayList;
    }

    public ArrayList<Resource> getResourceArrayList() {
        return resourceArrayList;
    }

    public void setResourceArrayList(ArrayList<Resource> resourceArrayList) {
        this.resourceArrayList = resourceArrayList;
    }
}
