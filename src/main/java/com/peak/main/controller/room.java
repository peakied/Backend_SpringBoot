package com.peak.main.controller;


import com.peak.main.model.Hotel;
import com.peak.main.model.RequestRoom;
import com.peak.main.model.Room;
import com.peak.main.repository.HotelRepository;
import com.peak.security.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity addRoom(@PathVariable String hid, @RequestBody RequestRoom requestRoom) {
        if (requestRoom.getName() == null || requestRoom.getDescription() == null || requestRoom.getImage() == null)
            return ResponseEntity.badRequest().body(new Response("Fill the form"));

        Optional<Hotel> foundHotel = hotelRepository.findById(hid);
        if (foundHotel.isEmpty())
            return ResponseEntity.badRequest().body("Dont found this hotel");

        foundHotel.get().getRooms().add(Room.builder().name(requestRoom.getName()).description(requestRoom.getDescription()).image(requestRoom.getImage()).build());
        hotelRepository.save(foundHotel.get());
        return ResponseEntity.ok(foundHotel.get());
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delRoom(@PathVariable String hid, @RequestBody String name) {
        if (name == null)
            return ResponseEntity.badRequest().body(new Response("Fill the form"));

        Optional<Hotel> foundHotel = hotelRepository.findById(hid);
        if (foundHotel.isEmpty())
            return ResponseEntity.badRequest().body("Dont found this hotel");

        List<Room> room = foundHotel.get().getRooms().stream().filter(r -> r.getName().equals(name)).toList();
        foundHotel.get().setRooms(room);
        hotelRepository.save(foundHotel.get());
        return ResponseEntity.ok(foundHotel.get());
    }
}
