package com.example.demo.generic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EnvironmentPathConfiguration implements PathConfiguration {

    private Environment environment;

    EnvironmentPathConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getProperty(String key) {
        return environment.getProperty(key);
    }

}
