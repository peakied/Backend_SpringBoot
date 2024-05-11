package com.peak.main.Request;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class RequestString {
    private String name;
}
