package org.csu.metrics.domain;

import lombok.Data;

@Data
public class Relation {
    private String id;
    private XMLElement element_1;
    private XMLElement element_2;
    private Type type;

    public enum Type
    {
        GENERALIZATION,
        ASSOCIATION,
        AGGREGATION,
        COMPOSITION,
        DEPENDENCY
    }
}
