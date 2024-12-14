package net.kamal.configservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServicesApplication.class, args);
    }
}
