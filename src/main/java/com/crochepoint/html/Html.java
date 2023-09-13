package com.crochepoint.html;

import com.crochepoint.entities.HtmlComponent;

public class Html extends HtmlComponent {
    static private String bgColor = "#000;";
    static private String textColor = "rgba(255,255,255,0.8);";
    
    private String title = "Chroche pontos";

    public Html(String title) {
        this.title = title;
    }

    public String toString() {
        return "<!DOCTYPE html>"
            + "<html lang=\"pt-br\">"
                + "<head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                    + "<title>" + title + "</title>"
                    + "<script src=\"https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js\"></script>"
                + "</head>"
                + "<style>"
                + "body { display:flex; flex-direction: column; gap:20px; font-family:Verdana, Arial; background-color: "+bgColor+"; color:"+textColor+" }"
                + "button { border:none; padding:10px 20px; border-radius:4px; cursor:pointer; background-color: #07a; color:"+textColor+" }"
                + "table { width: 100%; border-collapse: collapse; }"
                + "table th, table td { border: solid 1px #aaa; padding: 10px; font-weight:16px; color:"+Html.getTextColor()+"; }"
                + "</style>"
                + "<body>"
                    + super.getChildrenToString()
                + "</body>"
            + "</html>";
    }

    public static String getBgColor() {
        return bgColor;
    }

    public static String getTextColor() {
        return textColor;
    }

    public String getTitle() {
        return title;
    }
}
