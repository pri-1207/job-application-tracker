package com.jobtracker.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for SpringDoc OpenAPI (Swagger UI) documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI jobTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Job Application Tracker API")
                        .description("RESTful API for managing job applications, companies, " +
                                     "interview stages, and application statuses. " +
                                     "Supports dynamic filtering and sorting.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Job Tracker Team")
                                .url("https://github.com/job-application-tracker"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://github.com/job-application-tracker"));
    }
}
