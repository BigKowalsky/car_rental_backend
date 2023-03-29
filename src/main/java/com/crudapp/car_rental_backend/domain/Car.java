package com.crudapp.car_rental_backend.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "CARS")
public class Car {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "CAR_ID", unique = true)
    private Long carId;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "YEAR")
    private int productionYear;

    @Column(name = "BASIC_PRICE")
    private BigDecimal basicPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private CarStatus carStatus;

    @ManyToOne()
    @JoinColumn(name = "SEGMENT_ID")
    private Segment segment;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_CAR_RENT",
            joinColumns = {@JoinColumn(name = "CAR_ID", referencedColumnName = "CAR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "RENT_ID", referencedColumnName = "RENT_ID")}
    )
    private List<Rent> rents = new ArrayList<>();

    public Car(String model, int productionYear, BigDecimal basicPrice, CarStatus carStatus, Segment segment) {
        this.model = model;
        this.productionYear = productionYear;
        this.basicPrice = basicPrice;
        this.carStatus = carStatus;
        this.segment = segment;
    }

    public Car(String model, int productionYear, BigDecimal basicPrice, CarStatus carStatus) {
        this.model = model;
        this.productionYear = productionYear;
        this.basicPrice = basicPrice;
        this.carStatus = carStatus;
    }
}
