package net.kamal.requicetsupplierservices.controllers;

import lombok.AllArgsConstructor;
import net.kamal.requicetsupplierservices.client.UserRestClient;
import net.kamal.requicetsupplierservices.entities.RequicetSupplier;
import net.kamal.requicetsupplierservices.model.Users;
import net.kamal.requicetsupplierservices.repositories.RequicetSupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class RequicetSupplierController {
    RequicetSupplierRepository requicetSupplierRepository;
    UserRestClient userRestClient;

    @GetMapping("/requicetsupplier")
    public List<RequicetSupplier> getAllRequicetSupplier(){
        List<RequicetSupplier> requicetSuppliers = requicetSupplierRepository.findAll();
        requicetSuppliers.forEach(requicetSupplier -> {
            requicetSupplier.setUsers(userRestClient.findUserById(requicetSupplier.getId_users()));
        });
        return requicetSupplierRepository.findAll();
    }

    @GetMapping("/requicetsupplier/{id_requicet}")
    public RequicetSupplier getSupplierById(@PathVariable Long id_requicet){
        RequicetSupplier requicetSupplier  = requicetSupplierRepository.findById(id_requicet).get();
        Users users =  userRestClient.findUserById(requicetSupplier.getId_users());
        requicetSupplier.setUsers(users);
        return requicetSupplierRepository.findById(id_requicet).get();
    }

    @PostMapping("/requicetsupplier/addRequicetSupplier")
    public ResponseEntity<Map<String, Object>> addRequicetSupplier(@RequestBody RequicetSupplier requicetSupplier){
        if (requicetSupplier == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "msg", "RequicetSupplier cannot be null",
                "status", 400
        ));
        requicetSupplierRepository.save(requicetSupplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "msg", "RequicetSupplier has been added successfully",
                "status", 201
        ));
    }
}
