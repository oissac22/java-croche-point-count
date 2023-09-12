package com.crochepoint.html;

import com.crochepoint.entities.HtmlComponent;

public class Div extends HtmlComponent {

    @Override
    public String toString() {
        return "<div id="+getId()+">"
            + this.getChildrenToString()
            + "</div>";
    }
    
}
