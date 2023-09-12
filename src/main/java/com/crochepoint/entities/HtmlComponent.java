package com.crochepoint.entities;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class HtmlComponent {
    static private final int max_id_count = 100000;
    static private int id_count = 0;

    static protected int getNewId() {
        if (id_count >= max_id_count)
            return id_count = 0;
        return ++id_count;
    }

    private Set<Object> children = new LinkedHashSet<>();

    public String style = "";
    public String attrs = "";

    private String id = "_sid_" + getNewId();

    public void add(HtmlComponent child) {
        children.add(child);
    }

    public void add(String child) {
        children.add(new String(child.toString()));
    }

    public void delete(Object child) {
        children.remove(child);
    }
    
    public Set<Object> getChildren() {
        return children;
    }

    public String getChildrenToString() {
        StringBuffer result = new StringBuffer();
        for (Object c:children) {
            if (c instanceof String) {
                result.append(c);
            } else {
                result.append(c.toString());
            }
        }
        return result.toString();
    }

    public String getId() {
        return id;
    }

    public String getHTMLQueryElement() {
        return "document.querySelector('#"+getId()+"');";
    }

}
