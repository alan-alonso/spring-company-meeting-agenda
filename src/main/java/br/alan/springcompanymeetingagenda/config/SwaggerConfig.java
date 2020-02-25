package br.alan.springcompanymeetingagenda.config;

import java.util.Arrays;
import java.util.List;
import com.google.common.base.Predicates;
import com.google.common.net.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import br.alan.springcompanymeetingagenda.utils.Mappings;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket companyMeetingAgendaApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("br.alan.springcompanymeetingagenda"))
                .build().apiInfo(this.metaData())
                .securitySchemes(
                        Arrays.asList(new ApiKey("JWT", HttpHeaders.AUTHORIZATION, "header")))
                .securityContexts(Arrays.asList(this.securityContext()))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build()))
                .globalResponseMessage(RequestMethod.POST,
                        Arrays.asList(new ResponseMessageBuilder()
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                .build()))
                .globalResponseMessage(RequestMethod.PUT, Arrays.asList(new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build()))
                .globalResponseMessage(RequestMethod.DELETE,
                        Arrays.asList(new ResponseMessageBuilder()
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                .build()));
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
                .description("REST API for Spring Company Meeting Agenda App")
                .version("0.0.1-SNAPSHOT").license("MIT")
                .licenseUrl("https://opensource.org/licenses/MIT")
                .contact(new Contact("Alan Alonso", null, "myemail@email.com")).build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(this.defaultAuth())
                .forPaths(Predicates.or(PathSelectors.ant(Mappings.RESOURCE_TYPES_PATH + "/**"),
                        PathSelectors.ant(Mappings.RESOURCES_PATH + "/**"),
                        PathSelectors.ant(Mappings.ADMIN_PATH + "/**"),
                        PathSelectors.ant(Mappings.MEETINGS_PATH + "/**"),
                        PathSelectors.ant(Mappings.AUTH_PATH + "/me")))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope =
                new AuthorizationScope("ADMIN", "System Administrator");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
