package com.crochepoint.crochepoint.resources;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.crochepoint.entities.TimeStartEndPointCrocheList;
import com.crochepoint.html.ButtonExternalRequest;
import com.crochepoint.html.Div;
import com.crochepoint.html.Html;
import com.crochepoint.html.Script;
import com.crochepoint.html.TagA;

@RestController
@RequestMapping(value="/")
public class HomeResource {
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/")
    public ResponseEntity<String> home() {
        Html html = new Html("Página inicial");



        Div dv1 = new Div();
        ButtonExternalRequest buttonStart = new ButtonExternalRequest("START", "/start");
        dv1.add(buttonStart);

        Div dv2 = new Div();
        ButtonExternalRequest buttonEnd= new ButtonExternalRequest("END", "/end");
        dv2.add(buttonEnd);

        Div dv3 = new Div();

        Script sc = new Script("const ev = new EventSource(\"/timeresponse\");"
                + "ev.onmessage = function(ev) {"
                    + "const data = JSON.parse(ev.data);"
                    // + "console.log(data);"
                    + "const infos = " + dv3.getHTMLQueryElement()
                    + "infos.innerHTML = JSON.stringify(data, null, 4).replace(/\\n/g,'<br \\/>').replace(/ /g,'&nbsp;');"
                + "}\n"
                + "ev.onerror = function(event) {"
                    + "console.error(\"Erro na conexão SSE:\", event);"
                + "};"
                );

        html.add(dv1);
        html.add(dv2);
        html.add(dv3);
        html.add(sc);

        return ResponseEntity.ok().body(html.toString());
    }

    @GetMapping(value="/start")
    public ResponseEntity<String> startTime() {
        TimeStartEndPointCrocheList list = new TimeStartEndPointCrocheList();
        list.start();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/end")
    public ResponseEntity<String> endTime() {
        TimeStartEndPointCrocheList list = new TimeStartEndPointCrocheList();
        list.end();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/timeresponse")
    public SseEmitter Event() {

        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    @Scheduled(fixedRate = 1000)
    public void sendUpdates() {
        for (SseEmitter emitter: emitters) {
            try {
                TimeStartEndPointCrocheList list = new TimeStartEndPointCrocheList();
                emitter.send(list);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                emitters.remove(emitter);
            }
        }
    }
    
}