package com.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.utils.CommonUtils;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Response<T> {

    private boolean success;
    private T body;
    private String error;

    private Response() {
    }

    public static <T> Response<T> successResponse(T body) {
        Response<T> response = new Response<>();
        response.success = true;
        response.body = body;
        return response;
    }

    public static Response<Object> successResponse() {
        Response<Object> response = new Response<>();
        response.success = true;
        return response;
    }

    public static <T> Response<T> failureResponse(String error) {
        Response<T> response = new Response<>();
        response.success = false;
        response.error = error;
        return response;
    }

    public static <T> Response<T> failureResponseWithBody(T body) {
        Response<T> response = new Response<>();
        response.success = false;
        response.body = body;
        return response;
    }

    @Override
    public String toString() {
        return CommonUtils.getJson(this);
    }
}