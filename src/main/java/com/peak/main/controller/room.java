package com.peak.main.controller;


import com.peak.main.Request.RequestString;
import com.peak.main.model.Hotel;
import com.peak.main.Request.RequestRoom;
import com.peak.main.model.Room;
import com.peak.main.repository.HotelRepository;
import com.peak.main.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("hotel/{hid}/room")
@RequiredArgsConstructor
public class room {

    private final HotelRepository hotelRepository;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response> addRoom(@PathVariable String hid, @RequestBody RequestRoom requestRoom) {
        if (requestRoom.getName() == null || requestRoom.getDescription() == null || requestRoom.getImage() == null)
            return ResponseEntity.badRequest().body(new Response("Fill the form"));

        Optional<Hotel> foundHotel = hotelRepository.findById(hid);
        if (foundHotel.isEmpty())
            return ResponseEntity.badRequest().body(new Response("Dont found this hotel"));

        foundHotel.get().getRooms().add(Room.builder().name(requestRoom.getName()).description(requestRoom.getDescription()).image(requestRoom.getImage()).build());
        hotelRepository.save(foundHotel.get());
        return ResponseEntity.ok(new Response(foundHotel.get()));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response> delRoom(@PathVariable String hid, @RequestBody RequestString name) {
        if (name == null) return ResponseEntity.badRequest().body(new Response("Fill the form"));

        Optional<Hotel> foundHotel = hotelRepository.findById(hid);
        if (foundHotel.isEmpty()) return ResponseEntity.badRequest().body(new Response("Dont found this hotel"));

        foundHotel.get().getRooms().removeIf(room -> room.getName().equals(name.getName()));
        hotelRepository.save(foundHotel.get());
        return ResponseEntity.ok(new Response(foundHotel.get()));
    }
}
