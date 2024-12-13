package net.kamal.carrentalservices.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "CAR-SERVICES")
public interface CarsRestClient {
}
