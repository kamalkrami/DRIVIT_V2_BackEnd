package net.kamal.carrentalservices.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "USERS-SERVICES")
public interface UsersRestClient {

}
