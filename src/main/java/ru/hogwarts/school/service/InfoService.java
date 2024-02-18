package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
    @Value("${server.port:9090}")
    private String THIS_PORT;

    private Logger logger = LoggerFactory.getLogger(InfoService.class);

    public InfoService() {
    }

    public String getPort() {
        logger.info("Return port: {}", THIS_PORT);
        return THIS_PORT;
    }

}
