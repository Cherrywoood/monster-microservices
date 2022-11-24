package com.example.infectionservice.controller;

import com.example.infectionservice.dto.InfectedThingDTO;
import com.example.infectionservice.mapper.InfectedThingMapper;
import com.example.infectionservice.service.InfectedThingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/infected-things")
public class InfectedThingController {

    private final InfectedThingService infectedThingService;
    //TODO:Pageable
    //private final PageMapper<InfectedThingDTO> pageMapper;
    private final InfectedThingMapper infectedThingMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InfectedThingDTO save(@Valid @RequestBody InfectedThingDTO infectionDTO) {
        return infectedThingMapper.mapEntityToDto(infectedThingService.save(infectionDTO));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InfectedThingDTO findById(@PathVariable UUID id) {
        return infectedThingMapper.mapEntityToDto(infectedThingService.findById(id));
    }

    //todo: pageable
//    @GetMapping
//    public ResponseEntity<PageDTO<InfectedThingDTO>> findAll(@RequestParam(defaultValue = "0")
//                                                             @Min(value = 0, message = "must not be less than zero") int page,
//                                                             @RequestParam(defaultValue = "5")
//                                                             @Max(value = 50, message = "must not be more than 50 characters") int size,
//                                                             @RequestParam(required = false) UUID doorId) {
//        Page<InfectedThingEntity> pageThings = infectedThingService.findAll(page, size, doorId);
//        if (pageThings.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(pageMapper.mapToDto(pageThings.map(infectedThingMapper::mapEntityToDto)), HttpStatus.OK);
//        }
//
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        infectedThingService.delete(id);
    }

}
