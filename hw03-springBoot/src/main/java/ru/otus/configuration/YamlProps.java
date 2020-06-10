package ru.otus.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "questions")
public class YamlProps {
    private String path;
    private Integer toPass;
}
