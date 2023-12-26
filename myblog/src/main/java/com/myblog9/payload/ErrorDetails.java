package com.myblog9.payload;

import java.util.Date;

public class ErrorDetails {

    private Date data;
    private  String message;
    private String description;

    public ErrorDetails(Date data, String message, String description) {
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public Date getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
