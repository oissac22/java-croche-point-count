package com.crochepoint.abstracts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class HtmlComponent implements Serializable {
    private List<HtmlComponent> children = new ArrayList<>();

    public void add(HtmlComponent child) {
        children.add(child);
    }

    public void delete(HtmlComponent child) {
        children.remove(child);
    }
    
    public List<HtmlComponent> getChildren() {
        return children;
    }

    public String getChildrenToString() {
        String result = "";
        for (HtmlComponent c:children) {
            result += c.toString();
        }
        return result;
    }

    abstract public String toString();

}
