package com.crudapp.car_rental_backend.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "CUSTOMERS")
public class Customer {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "CUSTOMER_ID", unique = true)
    private Long customerId;

    @Column(name = "CUSTOMER")
    private String customerName;

    @Column(name = "EMAIL_ADDRESS")
    private String email;

    @OneToMany(
            targetEntity = Rent.class,
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Rent> rents = new ArrayList<>();

    public Customer(String customerName, String email) {
        this.customerName = customerName;
        this.email = email;
    }
}
