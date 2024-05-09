package com.peak.main.model;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class RequestRoom {
    String name;
    String description;
    String image;
}
