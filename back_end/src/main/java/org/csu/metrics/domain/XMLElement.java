package org.csu.metrics.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class XMLElement {
    private String id;
    private String name;
    private XMLElement father;
    private final Set<XMLElement> children = new HashSet<>();
    private final Set<XMLBean> attributes = new HashSet<>();
    private final Set<Operation> operations = new HashSet<>();

    private int wmc;
    private int dit;
    private int noc;
    private int cbo;
}
