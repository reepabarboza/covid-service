package com.covid.model;

import lombok.Data;

@Data
public class RapidResponse {
    private String statusCode;
    private String error;
    private String message;
    private com.covid.model.Data data;
}
