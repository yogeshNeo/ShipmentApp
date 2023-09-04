package com.shipment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ShipmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipmentApplication.class, args);
		log.info("Inventory App Running on http://localhost:8083");
	}

}
