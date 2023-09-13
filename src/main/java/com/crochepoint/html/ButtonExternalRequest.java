package com.crochepoint.html;

import com.crochepoint.entities.HtmlComponent;

public class ButtonExternalRequest extends HtmlComponent {
    
    private String caption = "";
    private String url = "";
    
    public char atalho = '\0';
    public String confirmClick = null;

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
                    + (
                        confirmClick == null ? "" :
                        "if(!confirm(`" + confirmClick + "`)) return;"
                    )
                    + "axios.get('" + url + "')"
                        + ".then(result => console.log(`" + messageSuccess + "`))"
                        + ".catch(e => alert(e));"
                + "});"
                + (
                    atalho == '\0' ? "" :
                    "window.addEventListener('keyup', e => {"
                        + "if (e.key === '"+atalho+"')"
                            + "bt.click();"
                    + "})"
                )
            + "}"
        );

        return "<button id='"+this.getId()+"'>"
                + caption
            + "</button>"
            + sc.toString();
    }

}
