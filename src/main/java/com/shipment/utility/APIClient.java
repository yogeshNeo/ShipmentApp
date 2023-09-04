package com.shipment.utility;

import com.shipment.model.Shipment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "NOTIFICATION-SERVICE", url = "http://localhost:8084")
public interface APIClient {

    @PostMapping(value = "/sendNotification")
    void sendNotification(Shipment shipment);

}
