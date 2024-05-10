package com.peak.main.controller;


import com.peak.main.model.Hotel;
import com.peak.main.model.RequestHotel;
import com.peak.main.repository.HotelRepository;
import com.peak.main.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping ("/hotel")
@RequiredArgsConstructor
public class hotel {

    private final HotelRepository hotelRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(new Response(hotelRepository.findAll()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response> addHotel(@RequestBody RequestHotel requestHotel) {
        Hotel hotel = Hotel.builder()
                .hotelName(requestHotel.getName())
                .hotelAddress(requestHotel.getAddress())
                .hotelCity(requestHotel.getCity())
                .rooms(new ArrayList<>())
                .build();
        hotelRepository.save(hotel);
        return ResponseEntity.ok(new Response(hotel));
    }

    @DeleteMapping("/{hid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response> delHotel(@PathVariable String hid) {
        hotelRepository.deleteById(hid);
        return ResponseEntity.ok(new Response("[]"));
    }
}
