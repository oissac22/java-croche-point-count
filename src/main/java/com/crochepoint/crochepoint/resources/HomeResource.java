package com.crochepoint.crochepoint.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crochepoint.html.Html;

@RestController
@RequestMapping(value="/")
public class HomeResource {

    @GetMapping
    public ResponseEntity<String> home() {
        Html html = new Html("Página inicial");
        return ResponseEntity.ok().body(html.toString());
    }
    
}
