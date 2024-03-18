package dev.insaneduck.alphanovespringboot.config;

import dev.insaneduck.alphanovespringboot.entities.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(
                Course.class,
                CourseData.class,
                EnrollmentData.class,
                ExpenseItem.class,
                FoodMenu.class,
                Instructor.class,
                Role.class,
                Student.class,
                User.class,
                Book.class);
    }
}
