package br.com.Alyson.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {



        // Via Extension.  localhost:8081/api/person/v1/2.xml or localhost:8081/api/person/v1/2.JSON Deprecated on Spring Boot 2.6

        // Via QUERY PARAM localhost:8081/api/person/v1/2?mediaType=xml

        configurer.favorParameter(true)
                .parameterName("mediaType")
                .ignoreAcceptHeader(true).useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json",MediaType.APPLICATION_JSON)
                .mediaType("json",MediaType.APPLICATION_XML);

    }
}
