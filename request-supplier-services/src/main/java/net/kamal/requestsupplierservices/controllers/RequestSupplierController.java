package net.kamal.requestsupplierservices.controllers;

import lombok.AllArgsConstructor;
import net.kamal.requestsupplierservices.client.UserRestClient;
import net.kamal.requestsupplierservices.entities.RequestSupplier;
import net.kamal.requestsupplierservices.model.Users;
import net.kamal.requestsupplierservices.repositories.RequestSupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class RequestSupplierController {
    RequestSupplierRepository requestSupplierRepository;
    UserRestClient userRestClient;

    @GetMapping("/requestsupplier")
    public List<RequestSupplier> getAllRequestSupplier(){
        List<RequestSupplier> requestSuppliers = requestSupplierRepository.findAll();
        requestSuppliers.forEach(requestSupplier -> {
            requestSupplier.setUsers(userRestClient.findUserById(requestSupplier.getId_users()));
        });
        return requestSupplierRepository.findAll();
    }

    @GetMapping("/requestsupplier/{id_request}")
    public RequestSupplier getSupplierById(@PathVariable Long id_request){
        RequestSupplier requestSupplier  = requestSupplierRepository.findById(id_request).get();
        Users users =  userRestClient.findUserById(requestSupplier.getId_users());
        requestSupplier.setUsers(users);
        return requestSupplierRepository.findById(id_request).get();
    }

    @PostMapping("/requestsupplier/addRequestsupplier")
    public ResponseEntity<Map<String, Object>> addRequestSupplier(@RequestBody RequestSupplier requestsupplier){
        if (requestsupplier == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "msg", "Request Supplier cannot be null",
                "status", 400
        ));
        requestSupplierRepository.save(requestsupplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "msg", "Request Supplier has been added successfully",
                "status", 201
        ));
    }

    @DeleteMapping("/requestsupplier/deleterequestsupplier/{requestSupplierId}")
    public ResponseEntity<Map<String, Object>> deleteRequestSupplier(@PathVariable Long requestSupplierId){
        if (requestSupplierId == null) {
            //to check later
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "msg", "RequestSupplier ID cannot be null",
                    "status", 400
            ));
        }
        Optional<RequestSupplier> requestSupplier = requestSupplierRepository.findById(requestSupplierId);
        if (requestSupplier.isPresent()){
            requestSupplierRepository.deleteById(requestSupplierId);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "msg", "RequestSupplier has been deleted successfully",
                    "status", 200
            ));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "msg", "RequestSupplier not found",
                    "status", 404
            ));
        }
    }

    @PutMapping("requestsupplier/updaterequestsupplier")
    public ResponseEntity<Map<String, Object>> updateRequestSupplier(@RequestBody RequestSupplier requestSupplier){
        //to check later
        if (requestSupplier ==  null || requestSupplier.getId_request() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "msg", "RequestSupplier or CarRental ID cannot be null",
                    "status", 404
            ));
        }
        RequestSupplier updatedRequestSupplier = requestSupplierRepository.findById(requestSupplier.getId_request()).orElse(null);
        if(updatedRequestSupplier == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "msg", "RequestSupplier not found",
                    "status", 404
            ));
        }
        updatedRequestSupplier.setUsers(requestSupplier.getUsers());
        updatedRequestSupplier.setRequestDate(requestSupplier.getRequestDate());
        updatedRequestSupplier.setId_users(requestSupplier.getId_users());
        updatedRequestSupplier.setStatus(requestSupplier.getStatus());


        requestSupplierRepository.save(updatedRequestSupplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "msg", "RequestSupplier has been updated successfully",
                "status", 201
        ));
    }
}
