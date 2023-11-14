package com.game.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.dto.GameWiseScore;
import com.game.utils.CommonUtils;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameScoreResponse {

    private String gameName;

    private List<GameWiseScore> gameWiseScores;

    @Override
    public String toString() {
        return CommonUtils.getJson(this);
    }
}
