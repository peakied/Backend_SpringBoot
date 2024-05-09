package com.peak.main.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Room {
    private String name;
    private String description;
    private String image;

    public Room(String name, String description, String image) {
        this.setName(name);
        this.setDescription(description);
        this.setImage(image);
    }
}
