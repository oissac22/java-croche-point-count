package com.crochepoint.html;

import com.crochepoint.entities.HtmlComponent;

public class ButtonExternalRequest extends HtmlComponent {
    
    private String caption = "";
    private String url = "";

    public String messageSuccess = "Processo efetuado com sucesso";

    public ButtonExternalRequest(String caption, String url) {
        this.caption = caption;
        this.url = url;
    }

    public String toString() {
        Script sc = new Script(
            "{"
                + "const bt = " + this.getHTMLQueryElement()
                + "bt.addEventListener('click', () => {"
                    + "axios.get('" + url + "')"
                        + ".then(result => console.log(`" + messageSuccess + "`))"
                        + ".catch(e => alert(e));"
                + "});"
            + "}"
        );

        return "<button id='"+this.getId()+"'>"
                + caption
            + "</button>"
            + sc.toString();
    }

}
