package com.peak.main.Request;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class RequestHotel {
    private String name;
    private String address;
    private String city;
    private String image;
}
