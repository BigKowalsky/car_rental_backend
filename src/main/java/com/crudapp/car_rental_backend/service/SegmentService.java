package com.crudapp.car_rental_backend.service;

import com.crudapp.car_rental_backend.controller.exceptions.SegmentNotFoundException;
import com.crudapp.car_rental_backend.domain.Segment;
import com.crudapp.car_rental_backend.repository.SegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SegmentService {

    private final SegmentRepository segmentRepository;

    public List<Segment> getAllSegments() {
        return segmentRepository.findAll();
    }

    public Segment getSegmentById(Long segmentId) throws SegmentNotFoundException {
        return segmentRepository.findById(segmentId).orElseThrow(SegmentNotFoundException::new);
    }

    public void createSegment(Segment segment) {
        segmentRepository.save(segment);
    }

    public Segment updateSegment(Segment segment) throws SegmentNotFoundException {
        Segment updatedSegment = getSegmentById(segment.getSegmentId());
        updatedSegment.setSegmentName(segment.getSegmentName());
        updatedSegment.setSegmentDescription(segment.getSegmentDescription());
        updatedSegment.setCars(segment.getCars());
        segmentRepository.save(updatedSegment);
        return updatedSegment;
    }

    public void deleteSegment(final Long segmentId) {
        segmentRepository.deleteById(segmentId);
    }
}
