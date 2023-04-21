package cn.edu.csu.smproject.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.HashSet;

public class OwnedOperation {
    @JacksonXmlProperty(isAttribute = true,localName = "id")
    private String id;

    @JacksonXmlProperty(isAttribute = true,localName = "name")
    private String name;

    @JacksonXmlProperty(isAttribute = true,localName = "visibility")
    private String visibility;

    @JacksonXmlProperty(isAttribute = true,localName = "precondition")
    private String precondition;

    @JacksonXmlProperty(isAttribute = true,localName = "postcondition")
    private String postcondition;

    @JacksonXmlProperty(isAttribute = true,localName = "bodyCondition")
    private String bodyCondition;

    @JacksonXmlElementWrapper(localName = "ownedRule",useWrapping = false)
    @JacksonXmlProperty(localName = "ownedRule")
    private ArrayList<OwnedRule> ownedRules;

    //方法的属性集合
    private HashSet<String> attrSet;

    public OwnedOperation() {
        attrSet = new HashSet<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPrecondition() {
        return precondition;
    }

    public void setPrecondition(String precondition) {
        this.precondition = precondition;
    }

    public String getPostcondition() {
        return postcondition;
    }

    public void setPostcondition(String postcondition) {
        this.postcondition = postcondition;
    }

    public String getBodyCondition() {
        return bodyCondition;
    }

    public void setBodyCondition(String bodyCondition) {
        this.bodyCondition = bodyCondition;
    }

    public ArrayList<OwnedRule> getOwnedRules() {
        return ownedRules;
    }

    public void setOwnedRules(ArrayList<OwnedRule> ownedRules) {
        this.ownedRules = ownedRules;
    }

    public HashSet<String> getAttrSet() {
        return attrSet;
    }

    public void setAttrSet(HashSet<String> attrSet) {
        this.attrSet = attrSet;
    }
}
