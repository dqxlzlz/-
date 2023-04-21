package cn.edu.csu.smproject.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PackagedElement {
    @JacksonXmlProperty(isAttribute = true,localName = "type")
    private String type;

    @JacksonXmlProperty(isAttribute = true,localName = "id")
    private String id;

    @JacksonXmlProperty(isAttribute = true,localName = "name")
    private String name;

    @JacksonXmlElementWrapper(localName = "ownedOperation",useWrapping = false)
    @JacksonXmlProperty(localName = "ownedOperation")
    private ArrayList<OwnedOperation> ownedOperations;

    @JacksonXmlProperty(localName = "generalization")
    private  Generalization generalization;

    @JacksonXmlElementWrapper(localName = "ownedEnd",useWrapping = false)
    @JacksonXmlProperty(localName = "ownedEnd")
    private ArrayList<OwnedEnd> ownedEnds;

    @JacksonXmlProperty(isAttribute = true,localName = "supplier")
    private String supplier;

    @JacksonXmlProperty(isAttribute = true,localName = "client")
    private String client;

    @JacksonXmlElementWrapper(localName = "ownedAttribute",useWrapping = false)
    @JacksonXmlProperty(localName = "ownedAttribute")
    private ArrayList<OwnedAttribute> ownedAttributes;

    @JacksonXmlElementWrapper(localName = "node",useWrapping = false)
    @JacksonXmlProperty(localName = "node")
    private ArrayList<Node> nodes;

    //表示wmc的数量
    private int wmc;

    //表示在继承树中的深度
    private int depth;

    //表示直接子类的数量
    private int noc;

    //表示耦合关系的数量
    private int cbo;

    //表示RFC的数量
    private int rfc;

    //保存rfc方法的集合
    //<String,String> -->  <ClassId,MethodName>
    private HashMap<String,Integer> rfcMap;

    //保存属性集合
    //String 代表 属性的名字
    private HashMap<String,OwnedAttribute> attrMap;

    //表示LCOM的大小
    private int lcom;

    //表示圈复杂度
    private int vg;

    //表示所有的方法数
    private int totalNumberOfMethod;

    //表示所有方法名的集合
    //<String>
    private HashSet<String> methodSet;

    //表示所有属性名的集合
    //<String>
    private HashSet<String> attrSet;

    //表示所有的属性数
    private int totalNumberOfAttr;

    //表示方法重写数
    private int noo;

    //表示方法增加数
    private int noa;

    //表示特征化指数
    private double si;

    public PackagedElement() {
        rfcMap = new HashMap<String, Integer>();
        attrMap = new HashMap<String,OwnedAttribute>();
        methodSet = new HashSet<String>();
        attrSet = new HashSet<String>();
    }

    public ArrayList<OwnedOperation> getOwnedOperations() {
        return ownedOperations;
    }

    public void setOwnedOperations(ArrayList<OwnedOperation> ownedOperations) {
        this.ownedOperations = ownedOperations;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWmc() {
        return wmc;
    }

    public void setWmc(int wmc) {
        this.wmc = wmc;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Generalization getGeneralization() {
        return generalization;
    }

    public void setGeneralization(Generalization generalization) {
        this.generalization = generalization;
    }

    public int getNoc() {
        return noc;
    }

    public void setNoc(int noc) {
        this.noc = noc;
    }

    public ArrayList<OwnedEnd> getOwnedEnds() {
        return ownedEnds;
    }

    public void setOwnedEnds(ArrayList<OwnedEnd> ownedEnds) {
        this.ownedEnds = ownedEnds;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getCbo() {
        return cbo;
    }

    public void setCbo(int cbo) {
        this.cbo = cbo;
    }

    public ArrayList<OwnedAttribute> getOwnedAttributes() {
        return ownedAttributes;
    }

    public void setOwnedAttributes(ArrayList<OwnedAttribute> ownedAttributes) {
        this.ownedAttributes = ownedAttributes;
    }

    public int getRfc() {
        return rfc;
    }

    public void setRfc(int rfc) {
        this.rfc = rfc;
    }

    public HashMap<String, Integer> getRfcMap() {
        return rfcMap;
    }

    public void setRfcMap(HashMap<String, Integer> rfcMap) {
        this.rfcMap = rfcMap;
    }

    public HashMap<String, OwnedAttribute> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(HashMap<String, OwnedAttribute> attrMap) {
        this.attrMap = attrMap;
    }

    public int getLcom() {
        return lcom;
    }

    public void setLcom(int lcom) {
        this.lcom = lcom;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public int getVg() {
        return vg;
    }

    public void setVg(int vg) {
        this.vg = vg;
    }

    public int getTotalNumberOfMethod() {
        return totalNumberOfMethod;
    }

    public void setTotalNumberOfMethod(int totalNumberOfMethod) {
        this.totalNumberOfMethod = totalNumberOfMethod;
    }

    public HashSet<String> getMethodSet() {
        return methodSet;
    }

    public void setMethodSet(HashSet<String> methodSet) {
        this.methodSet = methodSet;
    }

    public HashSet<String> getAttrSet() {
        return attrSet;
    }

    public void setAttrSet(HashSet<String> attrSet) {
        this.attrSet = attrSet;
    }

    public int getTotalNumberOfAttr() {
        return totalNumberOfAttr;
    }

    public void setTotalNumberOfAttr(int totalNumberOfAttr) {
        this.totalNumberOfAttr = totalNumberOfAttr;
    }

    public int getNoo() {
        return noo;
    }

    public void setNoo(int noo) {
        this.noo = noo;
    }

    public int getNoa() {
        return noa;
    }

    public void setNoa(int noa) {
        this.noa = noa;
    }

    public double getSi() {
        return si;
    }

    public void setSi(double si) {
        this.si = si;
    }
}
