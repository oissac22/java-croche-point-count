package com.crochepoint.crochepoint.staticPage;

import com.crochepoint.html.ButtonExternalRequest;
import com.crochepoint.html.Div;
import com.crochepoint.html.Html;
import com.crochepoint.html.Script;
import com.crochepoint.html.Table;

public class Home {
 
    public String toString() {
        Html html = new Html("Página inicial");

        Div divButtonStart = new Div();
        ButtonExternalRequest buttonStart = new ButtonExternalRequest("START (S)", "/start");
        buttonStart.atalho = 's';
        divButtonStart.add(buttonStart);

        Div divButtonEnd = new Div();
        ButtonExternalRequest buttonEnd= new ButtonExternalRequest("END (E)", "/end");
        buttonEnd.atalho = 'e';
        divButtonEnd.add(buttonEnd);

        Div divButtonNew = new Div();
        ButtonExternalRequest buttonNew = new ButtonExternalRequest("NOVA LISTA DE CONTAGEM", "/new");
        buttonNew.confirmClick = "Deseja limpar tudo e iniciar uma nova lista de contagem?";
        divButtonNew.add(buttonNew);

        Div divTableList = new Div();
        Table tbl = new Table();
        tbl.addTitle("Iniciou");
        tbl.addTitle("Terminou");
        tbl.addTitle("Tempo passado");
        divTableList.add(tbl);

        Div divTotalTime = new Div();

        Script sc = new Script("const ev = new EventSource(\"/timeresponse\");"
                + "ev.onmessage = function(ev) {"
                    + "const data = JSON.parse(ev.data);"
                    + "const tbl_body = " + tbl.getHTMLTBodyQueryElement()
                    + "const listInfostblb = [];"
                    + "data.listTimes.map( item => {"
                            + "listInfostblb.push(`<tr>"
                                + "<td>${new Date(item.timeStart).toLocaleString()}</td>"
                                + "<td>${!item.timeEnd ? '' : new Date(item.timeEnd).toLocaleString()}</td>"
                                + "<td>${new Date(item.timePassed + 3 * 60 * 60 * 1000).toLocaleTimeString()}</td>"
                            + "</tr>`);"
                        + "});"
                    + "tbl_body.innerHTML = listInfostblb.join('');"
                    + "const totalTime = " + divTotalTime.getHTMLQueryElement()
                    + "totalTime.innerHTML = `Tempo total passado: ${new Date(data.totalPassedTime + 3 * 60 * 60 * 1000).toLocaleTimeString()}`;"
                    + "const btStart = " + buttonStart.getHTMLQueryElement()
                    + "const btEnd = " + buttonEnd.getHTMLQueryElement()
                    + "btStart.style.display = data.started ? 'none' : 'initial';"
                    + "btEnd.style.display = !data.started ? 'none' : 'initial';"
                + "};"
                + "ev.onerror = function(event) {"
                    + "console.error(\"Erro na conexão SSE:\", event);"
                + "};"
                );

        html.add(divButtonStart);
        html.add(divButtonEnd);
        html.add(divTableList);
        html.add(divTotalTime);
        html.add(sc);
        html.add(divButtonNew);

        return html.toString();
    }

}