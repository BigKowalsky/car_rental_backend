package com.crudapp.car_rental_backend.service;

import com.crudapp.car_rental_backend.controller.exceptions.RentNotFoundException;
import com.crudapp.car_rental_backend.domain.Rent;
import com.crudapp.car_rental_backend.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;

    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    public Rent getRentById(Long rentId) throws RentNotFoundException {
        return rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
    }

    public void createRent(Rent rent) {
        rentRepository.save(rent);
    }

    public Rent updateRent(Rent rent) throws RentNotFoundException {
        Rent updatedRent = getRentById(rent.getRentId());
        updatedRent.setRentStatus(rent.getRentStatus());
        updatedRent.setCreationDate(rent.getCreationDate());
        updatedRent.setRentedCars(rent.getRentedCars());
        updatedRent.setCustomer(rent.getCustomer());
        rentRepository.save(updatedRent);
        return updatedRent;
    }

    public void deleteRent(Long rentId) {
        rentRepository.deleteById(rentId);
    }
}
