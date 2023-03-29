package com.crudapp.car_rental_backend.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "RENTS")
public class Rent {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "RENT_ID", unique = true)
    private Long rentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "RENT_STATUS")
    private RentStatus rentStatus;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

    @ManyToMany(
            fetch = FetchType.EAGER,
            mappedBy = "rents")
    private List<Car> rentedCars = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    public Rent(RentStatus rentStatus, LocalDate creationDate, List<Car> rentedCars, Customer customer) {
        this.rentStatus = rentStatus;
        this.creationDate = creationDate;
        this.rentedCars = rentedCars;
        this.customer = customer;
    }

    public Rent(RentStatus rentStatus, LocalDate creationDate) {
        this.rentStatus = rentStatus;
        this.creationDate = creationDate;
    }
}
