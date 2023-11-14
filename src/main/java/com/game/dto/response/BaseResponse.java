package com.game.dto.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseResponse {

    private Integer id;

    private String name;

    private Timestamp creationDate;

    private boolean active;
}
