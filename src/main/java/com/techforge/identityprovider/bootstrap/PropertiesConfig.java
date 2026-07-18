package com.techforge.identityprovider.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class PropertiesConfig {

    @Value("${clients}")
    private Resource resource;

    @Bean
    public OAuthClientProperties oAuthClientProperties(){

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try(InputStream in = resource.getInputStream()){
            return mapper.readValue(in.readAllBytes(), OAuthClientProperties.class);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        return null;

    }

}
