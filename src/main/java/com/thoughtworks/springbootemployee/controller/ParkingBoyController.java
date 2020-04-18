package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("parking-boys")
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingBoy> getAll() {
        return service.findAllParkingBoy();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<ParkingBoy> createNewParkingBoy(@RequestBody ParkingBoy parkingBoy) {
        return service.createNewParkingBoy(parkingBoy);
    }


}
