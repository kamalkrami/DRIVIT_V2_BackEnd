package net.kamal.requestsupplierservices.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.kamal.requestsupplierservices.enums.UserType;
import net.kamal.requestsupplierservices.model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "USERS-SERVICES")
public interface UserRestClient {

    @GetMapping("/users")
    @CircuitBreaker(name = "UsersService",fallbackMethod = "defaultFindAllUsers")
    List<Users> findAllUsers();

    @GetMapping("/users/{id_user}")
    @CircuitBreaker(name = "UsersService",fallbackMethod = "defaultFindUserById")
    Users findUserById(@PathVariable Long id_user);

    @GetMapping("/users/type/{user_type}")
    @CircuitBreaker(name = "UsersService",fallbackMethod = "defaultFindAllUsers")
    List<Users> getUsersByType(@PathVariable UserType user_type);

    @PutMapping("/users/updateuser")
    ResponseEntity<Map<String, Object>> updateUser(@RequestBody Users user);

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
