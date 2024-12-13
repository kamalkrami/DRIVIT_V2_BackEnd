package net.kamal.carservices.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.kamal.carservices.enums.UserType;
import net.kamal.carservices.model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "USERS-SERVICES")
public interface UserRestClient {

    @GetMapping("/users")
    @CircuitBreaker(name = "UsersService",fallbackMethod = "defaultFindAllUsers")
    List<Users> findAllUsers();

    @GetMapping("/users/{id_user}")
    @CircuitBreaker(name = "UsersService",fallbackMethod = "defaultFindUserById")
    Users findUserById(@PathVariable Long id_user);

    @GetMapping("/users/supplier")
    @CircuitBreaker(name = "UsersService",fallbackMethod = "defaultFindAllUsers")
    List<Users> findUsersByStatus(UserType userType);

    default List<Users> defaultFindAllUsers(Exception exception){
        return List.of();
    }

    default Users defaultFindUserById(Long id_user,Exception exception){
        Users users = new Users();
        users.setId_user(id_user);
        users.setFirstName("Not Available");
        users.setLastName("Not Available");
        users.setUserName("Not Available");
        users.setPassWord("Not Available");
        users.setEmail("Not Available");
        users.setCin("Not Available");
        users.setStatus(UserType.USER);
        return users;
    }
}
