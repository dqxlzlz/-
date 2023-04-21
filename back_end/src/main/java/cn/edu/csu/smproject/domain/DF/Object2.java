package cn.edu.csu.smproject.domain.DF;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Object2 {
    @JacksonXmlProperty(namespace = "o",localName = "OrganizationUnit")
    private OrganizationUnit organizationUnit;

    public OrganizationUnit getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(OrganizationUnit organizationUnit) {
        this.organizationUnit = organizationUnit;
    }
}
