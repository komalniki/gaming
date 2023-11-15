package com.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.utils.CommonUtils;
import lombok.Data;

import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameWiseScore {

    private String userName;

    private double score;

    private Date date;

    @Override
    public String toString() {
        return CommonUtils.getJson(this);
    }
}