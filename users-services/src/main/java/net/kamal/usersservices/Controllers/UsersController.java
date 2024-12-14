package net.kamal.usersservices.Controllers;

import lombok.AllArgsConstructor;
import net.kamal.usersservices.entities.Users;
import net.kamal.usersservices.enums.UserType;
import net.kamal.usersservices.repositories.UsersRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UsersController {

    UsersRepository usersRepository;

    @GetMapping("/users")
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    @GetMapping("/users/{id_user}")
    public Users getUserById(@PathVariable Long id_user){
        return usersRepository.findById(id_user).get();
    }

    @GetMapping("/users/type/{user_type}")
    public List<Users> getAllUsersByType(@PathVariable UserType user_type){
        return usersRepository.findUsersByStatus(user_type);
    }

}
