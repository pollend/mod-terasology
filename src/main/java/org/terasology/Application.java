package org.terasology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.terasology.config.PersistenceJPAConfig;
import org.terasology.config.SecurityConfig;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{Application.class, SecurityConfig.class}, args);
    }
}
