package com.crochepoint.html;

import com.crochepoint.entities.HtmlComponent;

public class Html extends HtmlComponent {
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
                + "<body>"
                    + super.getChildrenToString()
                + "</body>"
            + "</html>";
    }
}
