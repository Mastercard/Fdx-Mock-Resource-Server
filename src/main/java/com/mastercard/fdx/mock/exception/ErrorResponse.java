package com.mastercard.fdx.mock.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int code;
    private final String error;
    private final String errorDescription;

    @Override
    public String toString() {
        var json = new JSONObject();
        json.put("code", code);
        json.put("error", error);
        json.put("error_description", errorDescription);
        return json.toString();
    }
}
