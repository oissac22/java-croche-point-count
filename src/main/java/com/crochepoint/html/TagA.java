package com.crochepoint.html;

import com.crochepoint.entities.HtmlComponent;

public class TagA extends HtmlComponent {

    public String href = "";
    public String target = "";

    public TagA() {
        super();
    }

    public TagA(String href) {
        super();
        this.href = href;
    }

    public TagA(String href, String target) {
        super();
        this.href = href;
        this.target = target;
    }

    @Override
    public String toString() {
        return "<a id="+getId()+" href='" + href + "' target='" + target + "'>"
                + this.getChildrenToString()
            + "</a>";
    }
    
}
