package com.peak.main.controller;


import com.peak.main.model.Hotel;
import com.peak.main.model.RequestHotel;
import com.peak.main.model.Room;
import com.peak.main.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("/hotel")
@RequiredArgsConstructor
public class hotel {

    private final HotelRepository hotelRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(hotelRepository.findAll());
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity addHotel(@RequestBody RequestHotel requestHotel) {
        Hotel hotel = Hotel.builder()
                .hotelName(requestHotel.getName())
                .hotelAddress(requestHotel.getAddress())
                .hotelCity(requestHotel.getCity())
                .rooms(new ArrayList<>())
                .build();
        hotelRepository.save(hotel);
        return ResponseEntity.ok(hotel);
    }
}
