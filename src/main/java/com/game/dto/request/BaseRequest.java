package com.game.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BaseRequest {

    @NotEmpty(message = "Name Cannot be Empty")
    private String name;

}