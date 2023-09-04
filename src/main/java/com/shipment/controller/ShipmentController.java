package com.shipment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipment.model.CustomerOrder;
import com.shipment.model.InventoryEvent;
import com.shipment.model.Shipment;
import com.shipment.repository.ShipmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ShipmentController {

    @Autowired
    private ShipmentRepository repository;

    @Autowired
    private KafkaTemplate<String, InventoryEvent> kafkaTemplate;

    @KafkaListener(topics = "new-inventory", groupId = "inventory-group")
    public void shipOrder(String event) throws JsonMappingException, JsonProcessingException {

        Shipment shipment = new Shipment();
        InventoryEvent inventoryEvent = new ObjectMapper().readValue(event, InventoryEvent.class);
        CustomerOrder order = inventoryEvent.getOrder();
        try {

            if (order.getAddress() == null) {
                throw new Exception("Address not present");
            }

            shipment.setAddress(order.getAddress());
            shipment.setOrderId(order.getOrderId());

            shipment.setStatus("success");

            log.info("shipment saved :::::");
            this.repository.save(shipment);
            // do other shipment logic ..
        } catch (Exception e) {
            shipment.setOrderId(order.getOrderId());
            shipment.setStatus("failed");
            this.repository.save(shipment);

            InventoryEvent reverseEvent = new InventoryEvent();

            reverseEvent.setType("INVENTORY_REVERSED");
            System.out.println(order);
            reverseEvent.setOrder(order);
            log.info("shipment failed with reversed-inventory topic :::::");

            this.kafkaTemplate.send("reversed-inventory", reverseEvent);

        }
    }

}
