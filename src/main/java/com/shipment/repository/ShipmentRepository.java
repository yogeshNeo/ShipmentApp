package com.shipment.repository;

import com.shipment.model.Shipment;
import org.springframework.data.repository.CrudRepository;

public interface ShipmentRepository extends CrudRepository<Shipment,Long> {

}