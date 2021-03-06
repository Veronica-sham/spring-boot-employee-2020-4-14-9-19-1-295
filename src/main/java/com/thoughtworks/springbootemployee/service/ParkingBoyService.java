package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    public List<ParkingBoy> findAllParkingBoy() {
        return parkingBoyRepository.findAll();
    }

    public List<ParkingBoy> createNewParkingBoy(ParkingBoy parkingBoy) {
        parkingBoyRepository.save(parkingBoy);
        return parkingBoyRepository.findAll();
    }

}
