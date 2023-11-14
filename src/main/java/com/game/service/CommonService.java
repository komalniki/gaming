package com.game.service;

import com.game.dto.request.ScoreRequest;

public interface CommonService {

    void deleteGameAndScore(Integer gameId);

    void publishScore(ScoreRequest scoreRequest);


}
