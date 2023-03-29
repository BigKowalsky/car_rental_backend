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
@Table(name = "SEGMENTS")
public class Segment {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "SEGMENT_ID", unique = true)
    private Long segmentId;

    @Column(name = "SEGMENT")
    private String segmentName;

    @Column(name = "DESCRIPTION")
    private String segmentDescription;

    @OneToMany(
            targetEntity = Car.class,
            mappedBy = "segment",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Car> cars = new ArrayList<>();

    public Segment(String segmentName, String segmentDescription, List<Car> cars) {
        this.segmentName = segmentName;
        this.segmentDescription = segmentDescription;
        this.cars = cars;
    }

    public Segment(String segmentName, String segmentDescription) {
        this.segmentName = segmentName;
        this.segmentDescription = segmentDescription;
    }
}
