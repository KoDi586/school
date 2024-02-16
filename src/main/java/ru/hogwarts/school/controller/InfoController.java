package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
@RequestMapping(value = "/info")
public class InfoController {

    private final InfoService service;

    @Autowired
    public InfoController(InfoService service) {
        this.service = service;
    }

    @GetMapping(value = "/port")
    public ResponseEntity<String> getPort() {
        String port = service.getPort();
        return new ResponseEntity<>(port, HttpStatus.OK);
    }

}
