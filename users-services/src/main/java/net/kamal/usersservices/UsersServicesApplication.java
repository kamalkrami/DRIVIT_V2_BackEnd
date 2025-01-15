package net.kamal.usersservices;

import net.kamal.usersservices.entities.Users;
import net.kamal.usersservices.enums.UserType;
import net.kamal.usersservices.repositories.UsersRepository;
import org.apache.catalina.Globals;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@RefreshScope
public class UsersServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersServicesApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UsersRepository usersRepository){
        return args -> {
            List<Users> usersList = List.of(
                    Users.builder()
                            .firstName("Kamal")
                            .lastName("Krami")
                            .userName("kamal_krami")
                            .passWord("Kamal@123")
                            .cin("CD123456")
                            .email("Kamal@gmail.com")
                            .phone("0682467314")
                            .status(UserType.USER)
                            .build(),
                    Users.builder()
                            .firstName("Ayoub")
                            .lastName("Anejdam")
                            .userName("Ayoub_Anajdam")
                            .passWord("Ayoub@123")
                            .cin("CD987456")
                            .email("Ayoub@gmail.com")
                            .phone("0682467315")
                            .status(UserType.SUPPLIER)
                            .build(),
                    Users.builder()
                            .firstName("Admin")
                            .lastName("Admin")
                            .userName("Admin_Admin")
                            .passWord("Admin@123")
                            .cin("CD987466")
                            .email("Admin@gmail.com")
                            .phone("0652567515")
                            .status(UserType.ADMIN)
                            .build()
            );
            usersRepository.saveAll(usersList);
        };
    }
}
