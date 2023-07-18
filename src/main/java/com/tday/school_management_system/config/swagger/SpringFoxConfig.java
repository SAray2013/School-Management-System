package com.tday.school_management_system.config.swagger;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;

@Configuration
public class SpringFoxConfig {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findRequiredField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }
    
    private ApiKey apiKey(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    
    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
    		.securityContexts(Arrays.asList(securityContext()))
    		.securitySchemes(Arrays.asList(apiKey()))
            .select()
            .apis( RequestHandlerSelectors.basePackage( "com.tday.school_management_system") )            
            .paths(PathSelectors.any())                          
            .build()
            .apiInfo(apiInfo());                                           
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
          "School Management System", 
          "School management has always been — and still is — an extensive undertaking on the part of educational institutions around the world. It requires painstakingly monitoring the academic progress (or otherwise) of all parties involved, towards constant learning.", 
          "v1.1.0", 
          null, 
          new Contact(null, null, null), 
          null, null, Collections.emptyList());
    }
    
}