package com.crochepoint.html;

import java.util.ArrayList;
import java.util.List;

import com.crochepoint.entities.HtmlComponent;

public class Table extends HtmlComponent {

    private List<String> titles = new ArrayList<>();

    public void addTitle(String title) {
        titles.add(title);
    }
    
    public String toString() {
        StringBuffer titleAndTr = new StringBuffer();

        for (String t: titles) {
            titleAndTr.append("<th>" + t + "</th>");
        }

        return "<table id='"+getId()+"'>"
                + "<thead>"
                    + titleAndTr.toString()
                + "</thead>"
                + "<tbody id='" + getIdTBody() + "'>"
                + "</tbody>"
            + "</table>";
    }

    public String getIdTBody() {
        return getId() + "_tb";
    }

    public String getHTMLTBodyQueryElement() {
        return "document.querySelector('#"+getIdTBody()+"');";
    }

}
