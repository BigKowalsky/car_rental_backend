package com.crudapp.car_rental_backend.controller;

import com.crudapp.car_rental_backend.controller.exceptions.SegmentNotFoundException;
import com.crudapp.car_rental_backend.domain.Segment;
import com.crudapp.car_rental_backend.domain.dto.SegmentDto;
import com.crudapp.car_rental_backend.mapper.SegmentMapper;
import com.crudapp.car_rental_backend.service.SegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/segment")
public class SegmentController {

    private final SegmentMapper segmentMapper;
    private final SegmentService segmentService;

    @GetMapping
    public ResponseEntity<List<SegmentDto>> getAllSegments() {
        return ResponseEntity.ok(segmentMapper.mapToSegmentDtoList(segmentService.getAllSegments()));
    }

    @GetMapping(value = "{segmentId}")
    public ResponseEntity<SegmentDto> getSegment(@PathVariable Long segmentId) throws SegmentNotFoundException {
        return ResponseEntity.ok(segmentMapper.mapToSegmentDto(segmentService.getSegmentById(segmentId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSegment(@RequestBody SegmentDto SegmentDto) {
        Segment segment = segmentMapper.mapToSegment(SegmentDto);
        segmentService.createSegment(segment);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SegmentDto> updateSegment(@RequestBody SegmentDto segmentDto) throws SegmentNotFoundException {
        return ResponseEntity.ok(segmentMapper.mapToSegmentDto(segmentService.updateSegment(segmentMapper.mapToSegment(segmentDto))));
    }

    @DeleteMapping(value = "{segmentId}")
    public ResponseEntity<Void> deleteSegment(@PathVariable Long segmentId) {
        segmentService.deleteSegment(segmentId);
        return ResponseEntity.ok().build();
    }
}
