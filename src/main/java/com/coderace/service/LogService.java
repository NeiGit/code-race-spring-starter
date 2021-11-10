package com.coderace.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {
    private String name;


    public LogService setName(String name) {
        this.name = name;
        return this;
    }

    public void info(String message) {
        this.log("INFO", message);
    }

    public void warning(String message) {
        this.log("WARNING", message);
    }

    public void error(String message) {
        this.log("ERROR", message);
    }

    private void log(String severity, String message) {
        System.out.printf("%s - [%s] - %s - %s%n", LocalDateTime.now(), severity, this.name, message);
    }
}
