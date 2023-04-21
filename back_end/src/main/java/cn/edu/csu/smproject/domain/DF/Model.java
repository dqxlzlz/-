package cn.edu.csu.smproject.domain.DF;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;

public class Model {
    @JacksonXmlElementWrapper(namespace = "c",localName = "Processes")
    @JacksonXmlProperty(namespace = "o",localName = "Process")
    private ArrayList<java.lang.Process> processArrayList;

    @JacksonXmlElementWrapper(namespace = "c",localName = "Flows")
    @JacksonXmlProperty(namespace = "o",localName = "Flow")
    private ArrayList<Flow> flowArrayList;

    @JacksonXmlElementWrapper(namespace = "c",localName = "OrganizationUnits")
    @JacksonXmlProperty(namespace = "o",localName = "OrganizationUnit")
    private  ArrayList<OrganizationUnit> organizationUnitArrayList;

    @JacksonXmlElementWrapper(namespace = "c",localName = "Resources")
    @JacksonXmlProperty(namespace = "o",localName = "Resource")
    private ArrayList<Resource> resourceArrayList;

    @JacksonXmlProperty(isAttribute = true,localName = "Id")
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
