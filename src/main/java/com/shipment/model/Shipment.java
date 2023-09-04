package com.shipment.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String address;

    @Column
    private String status;

    @Column
    private long orderId;
}
