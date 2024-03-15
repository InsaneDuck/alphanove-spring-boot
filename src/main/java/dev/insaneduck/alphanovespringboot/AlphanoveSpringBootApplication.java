package dev.insaneduck.alphanovespringboot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Test API", version = "1.0", description = "Api Endpoints for learning"))

public class AlphanoveSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlphanoveSpringBootApplication.class, args);
    }

}
