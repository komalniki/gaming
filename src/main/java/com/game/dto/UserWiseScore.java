package com.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.utils.CommonUtils;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWiseScore {

    private String gameName;

    private double score;

    private Timestamp date;

    @Override
    public String toString() {
        return CommonUtils.getJson(this);
    }
}