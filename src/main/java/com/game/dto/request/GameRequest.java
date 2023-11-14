package com.game.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.utils.CommonUtils;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameRequest extends BaseRequest {


    @Override
    public String toString() {
        return CommonUtils.getJson(this);
    }
}