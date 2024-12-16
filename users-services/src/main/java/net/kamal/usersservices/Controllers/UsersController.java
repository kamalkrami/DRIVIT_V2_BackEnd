package net.kamal.usersservices.Controllers;

import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import net.kamal.usersservices.entities.Users;
import net.kamal.usersservices.enums.UserType;
import net.kamal.usersservices.repositories.UsersRepository;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UsersController {

    UsersRepository usersRepository;

    @GetMapping("users")
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    @GetMapping("users/{id_user}")
    public Users getUserById(@PathVariable Long id_user){
        return usersRepository.findById(id_user).get();
    }

    @GetMapping("users/type/{user_type}")
    public List<Users> getAllUsersByType(@PathVariable UserType user_type){
        return usersRepository.findUsersByStatus(user_type);
    }

    @PostMapping("users/adduser")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody Users user){
        if (user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "msg", "User cannot be null",
                "status", 400
        ));
        usersRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "msg", "User has been added successfully",
                "status", 201
        ));
    }

    @DeleteMapping("users/deleteuser/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long userId){
        if (userId == null) {
            //to check later
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "msg", "User ID cannot be null",
                    "status", 400
            ));
        }
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()){
             usersRepository.deleteById(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "msg", "User has been deleted successfully",
                    "status", 200
            ));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "msg", "User not found",
                    "status", 404
            ));
        }
    }

    @PutMapping("users/updateuser")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody Users user){
        //to check later
        if (user ==  null || user.getId_user() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "msg", "User or User ID cannot be null",
                    "status", 404
            ));
        }
        Users updatedUser = usersRepository.findById(user.getId_user()).orElse(null);
        if(updatedUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "msg", "User not found",
                    "status", 404
            ));
        }
        updatedUser.setUserName(user.getUserName());
        updatedUser.setPassWord(user.getPassWord());
        updatedUser.setCin(user.getCin());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPhone(user.getPhone());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setStatus(user.getStatus());

        usersRepository.save(updatedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "msg", "User has been updated successfully",
                "status", 201
        ));
    }

}
