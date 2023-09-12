package com.crochepoint.html;

import java.util.ArrayList;
import java.util.List;

import com.crochepoint.entities.HtmlComponent;

public class Table extends HtmlComponent {

    private static boolean styleOk = false;

    private List<String> titles = new ArrayList<>();

    private static String getStyle() {
        if (styleOk) {
            return "";
        }
        styleOk = true;
        return "<style>\n"
                + "table { width: 100%; border-collapse: collapse; }\n"
                + "table th, table td { border: solid 1px #aaa; padding: 10px; font-weight:16px; }\n"
            + "</style>";
    }

    public void addTitle(String title) {
        titles.add(title);
    }
    
    public String toString() {
        StringBuffer titleAndTr = new StringBuffer();

        for (String t: titles) {
            titleAndTr.append("<th>" + t + "</th>");
        }

        return getStyle()
            + "<table id='"+getId()+"'>"
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
