package com.crochepoint.crochepoint.resources;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.crochepoint.crochepoint.staticPage.Home;
import com.crochepoint.entities.TimeStartEndPointCrocheList;

@RestController
@RequestMapping(value="/")
public class HomeResource {
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private TimeStartEndPointCrocheList list;

    private void startList() {
        if (list == null)
            list = TimeStartEndPointCrocheList.loadLastTime();
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok().body(new Home().toString());
    }

    @GetMapping(value="/start")
    public ResponseEntity<String> startTime() {
        startList();
        list.start();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/end")
    public ResponseEntity<String> endTime() {
        startList();
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
            startList();
            try {
                emitter.send(list);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                emitters.remove(emitter);
            }
        }
    }
    
}