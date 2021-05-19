package com.mikolajczyk.frontend.redudo.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PropertiesConfig {

    @Value("${api.root}")
    private String apiRoot;
}
