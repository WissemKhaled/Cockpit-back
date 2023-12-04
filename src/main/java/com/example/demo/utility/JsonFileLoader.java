package com.example.demo.utility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JsonFileLoader {
    private static final Logger log = LoggerFactory.getLogger(JsonFileLoader.class);

    public String loadJsonFileContent(String filePath) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error loading JSON file content from '{}'", filePath, e);
            return null;
        }
    }
}
