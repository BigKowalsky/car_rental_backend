package com.crudapp.car_rental_backend.repository;

import com.crudapp.car_rental_backend.domain.Segment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface SegmentRepository extends CrudRepository<Segment, Long> {

    @Override
    Optional<Segment> findById(Long id);

    List<Segment> findAll();
    void deleteById(Long id);

    @Override
    Segment save(Segment segment);
}
