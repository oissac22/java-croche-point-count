package com.crochepoint.html;

import com.crochepoint.entities.HtmlComponent;

public class Script extends HtmlComponent {

    private String script = "";

    public Script(String script) {
        this.script = script;
    }

    @Override
    public String toString() {
        return "<script type=\"text/javascript\">"
            + script
            + "</script>";
    }
    
}
