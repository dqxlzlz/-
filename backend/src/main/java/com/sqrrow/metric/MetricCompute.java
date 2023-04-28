package com.sqrrow.metric;

import com.sqrrow.entity.*;
import com.sqrrow.entity.domain.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.*;

public class MetricCompute {

    private final Map<String, XMLElement> clazzMap = new HashMap<>();
    private final Map<String, Relation> relationshipMap = new HashMap<>();

    public List<XMLElement> metricAnalyze(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);

        findClasses(document);
        findRelationships(document);

//        计算加权方法数量
        computeWmc();
//        计算继承树的深度
        computeDit();
//        计算直接子类数量
        computeNoc();
//        计算耦合度
        computeCbo();
        return new ArrayList<>(clazzMap.values());
    }

    private void computeWmc() {
        clazzMap.forEach((key, clazz) -> clazz.setWmc(clazz.getOperations().size()));
    }

    private void computeDit() {
        clazzMap.forEach((key, clazz) -> {
            int count = 0;
            XMLElement father = clazz.getFather();
            while (father != null) {
                count++;
                father = father.getFather();
            }
            clazz.setDit(count);
        });
    }

    private void computeNoc() {
        clazzMap.forEach((key, clazz) -> clazz.setNoc(clazz.getChildren().size()));
    }

    private void computeCbo() {
//        耦合就是计算非继承的关系总数量
        relationshipMap.forEach((key, relationship) -> {
            if (relationship.getType() != Relation.Type.GENERALIZATION) {
                int cbo = relationship.getElement_1().getCbo();
                relationship.getElement_1().setCbo(cbo + 1);

                cbo = relationship.getElement_2().getCbo();
                relationship.getElement_2().setCbo(cbo + 1);
            }
        });
    }

//    查询类的总数量
    private void findClasses(Document document) {
        Element rootElement = document.getRootElement();
        Element classesElement = rootElement.element("Classes");
        List<Element> classElementList = classesElement.elements();
        for (Element element : classElementList) {
            XMLElement clazz = new XMLElement();
            clazz.setId(element.attributeValue("Id"));
            clazz.setName(element.elementText("Name"));

            Set<XMLBean> attributes = clazz.getAttributes();
            List<Element> attributeList = element.element("Attributes").elements();
            for (Element attributeElement: attributeList) {
                XMLBean attribute = new XMLBean();
                attribute.setId(attributeElement.attributeValue("Id"));
                attribute.setName(attributeElement.elementText("Name"));
                attribute.setDataType(attributeElement.elementText("DataType"));
                attribute.setVisibility(attributeElement.elementText("Attribute.Visibility"));
                attributes.add(attribute);
            }

            Set<Operation> operations = clazz.getOperations();
            List<Element> operationList = element.element("Operations").elements();
            for (Element operationElement: operationList) {
                Operation operation = new Operation();
                operation.setId(operationElement.attributeValue("Id"));
                operation.setName(operationElement.elementText("Name"));
                operation.setReturnType(operationElement.elementText("ReturnType"));
                operations.add(operation);
            }

            clazzMap.put(clazz.getId(), clazz);
        }
    }

//    查询关系总数量
    private void findRelationships(Document document) {
        Element rootElement = document.getRootElement();

        Map<String, Relation.Type> typeMap = new HashMap<>();
        typeMap.put("Generalizations", Relation.Type.GENERALIZATION);
        typeMap.put("Associations", Relation.Type.ASSOCIATION);
        typeMap.put("Aggregations", Relation.Type.AGGREGATION);
        typeMap.put("Compositions", Relation.Type.COMPOSITION);
        typeMap.put("Dependencies", Relation.Type.DEPENDENCY);

        typeMap.forEach((key, type) -> {
            Element relationshipsElement = rootElement.element(key);
            if (relationshipsElement != null) {
                List<Element> relationshipList = relationshipsElement.elements();
                for (Element element: relationshipList) {
                    Relation relationship = new Relation();
                    relationship.setId(element.attributeValue("Id"));
                    relationship.setType(type);

                    XMLElement clazz1 = clazzMap.get(element.element("Object1").element("Class").attributeValue("Ref"));
                    XMLElement clazz2 = clazzMap.get(element.element("Object2").element("Class").attributeValue("Ref"));

                    if (type == Relation.Type.GENERALIZATION) {
                        clazz2.setFather(clazz1);
                        clazz1.getChildren().add(clazz2);
                    }

                    relationship.setElement_1(clazz1);
                    relationship.setElement_2(clazz2);
                    relationshipMap.put(relationship.getId(), relationship);
                }
            }
        });
    }
}
