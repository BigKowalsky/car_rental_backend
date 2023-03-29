package com.crudapp.car_rental_backend.mapper;

import com.crudapp.car_rental_backend.domain.Car;
import com.crudapp.car_rental_backend.domain.Segment;
import com.crudapp.car_rental_backend.domain.dto.SegmentDto;
import com.crudapp.car_rental_backend.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SegmentMapper {

    private CarRepository carRepository;

    public Segment mapToSegment(SegmentDto segmentDto) {
        return new Segment(
                segmentDto.getSegmentId(),
                segmentDto.getSegmentName(),
                segmentDto.getSegmentDescription(),
                findAllCarsById(segmentDto.getCarsId())
        );
    }

    public SegmentDto mapToSegmentDto(Segment segment) {
        return new SegmentDto(
                segment.getSegmentId(),
                segment.getSegmentName(),
                segment.getSegmentDescription(),
                segment.getCars().isEmpty() ? Collections.emptyList() : segment.getCars().stream()
                        .map(Car::getCarId)
                        .collect(Collectors.toList())
        );
    }

    public List<SegmentDto> mapToSegmentDtoList(List<Segment> segments) {
        return segments.stream()
                .map(s -> new SegmentDto(
                        s.getSegmentId(),
                        s.getSegmentName(),
                        s.getSegmentDescription(),
                        s.getCars().isEmpty() ? Collections.emptyList() : s.getCars().stream()
                                .map(Car::getCarId)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public List<Car> findAllCarsById(List<Long> carsId) {
        List<Car> result = new ArrayList<>();
        if (Objects.nonNull(carsId)) {
            for (Long id : carsId) {
                carRepository.findById(id).ifPresent(result::add);
            }
        }
        return result;
    }
}
