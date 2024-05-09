package com.peak.main.model;


import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
@Data
@Builder
public class Hotel {

    @Id
    private @MongoId ObjectId id;
    private String hotelName;
    private String hotelAddress;
    private String hotelCity;
    private List<Room> rooms;
}
