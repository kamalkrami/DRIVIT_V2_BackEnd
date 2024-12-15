package net.kamal.requicetsupplierservices.controllers;

import lombok.AllArgsConstructor;
import net.kamal.requicetsupplierservices.client.UserRestClient;
import net.kamal.requicetsupplierservices.entities.RequicetSupplier;
import net.kamal.requicetsupplierservices.model.Users;
import net.kamal.requicetsupplierservices.repositories.RequicetSupplierRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
