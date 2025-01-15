package net.kamal.usersservices.Controllers;

import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import net.kamal.usersservices.entities.Users;
import net.kamal.usersservices.enums.UserType;
import net.kamal.usersservices.model.AuthRequest;
import net.kamal.usersservices.repositories.UsersRepository;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
                "msg", "User Updated successfully",
                "status", 200
        ));
    }

    @PostMapping("/users/authUser")
    public ResponseEntity<Map<String, Object>> authUser(@RequestBody(required = false) AuthRequest authRequest) {
        if (authRequest == null || authRequest.getEmail() == null || authRequest.getPassWord() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "msg", "Invalid Auth Data 1",
                    "status", 404,
                    "user", "null"
            ));
        }

        String email = authRequest.getEmail();
        String password = authRequest.getPassWord();

        if (email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "msg", "Invalid Auth Data 2",
                    "status", 404,
                    "user", "null"
            ));
        } else {
            Users users = usersRepository.findByEmailAndPassWord(email, password);
            if (users == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "msg", "Failed To Auth User",
                        "status", 404,
                        "user", "null"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "msg", "User successfully Auth",
                        "status", 200,
                        "user", users
                ));
            }
        }
    }

    @PostMapping("/users/registerUser")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody(required = false) Users users) {
        if (users == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "msg", "Invalid User Data",
                    "status", 400
            ));
        }

        try {
            // Check for duplicates
            List<String> duplicateFields = new ArrayList<>();

            if (usersRepository.findByCin(users.getCin()) != null) {
                duplicateFields.add("cin");
            }
            if (usersRepository.findByEmail(users.getEmail()) != null) {
                duplicateFields.add("email");
            }
            if (usersRepository.findByPhone(users.getPhone()) != null) {
                duplicateFields.add("phone");
            }

            // If any duplicates are found, return a single response
            if (!duplicateFields.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "msg", "User Already Exists With Fields: " + String.join(", ", duplicateFields),
                        "status", 409
                ));
            }

            // Save the user if no duplicates are found
            usersRepository.save(users);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "msg", "User successfully registered",
                    "status", 200
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "msg", "Failed to register user",
                    "status", 500
            ));
        }
    }

}
