package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

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

    public Long sumMillionNumbers() {
        // долгое решение
//        return withoutParallel();
        return withParallel();
    }

    public Long withoutParallel() {
        return Stream

                .iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .reduce(0L, Long::sum);
    }
    public Long withParallel() {
        Long sum;
        sum = Stream
                .iterate(1L, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0L, Long::sum);
        return sum;
    }
}
