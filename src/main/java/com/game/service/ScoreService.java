package com.game.service;

import com.game.dto.GameWiseScore;
import com.game.dto.UserWiseScore;
import com.game.dto.request.ScoreRequest;

import java.util.List;
import java.util.Map;

public interface ScoreService {

    void publishScore(ScoreRequest scoreRequest);

    void deleteScore(Integer gameId);

    Map<String, List<GameWiseScore>> fetchGameWiseScores();

    Map<String, List<UserWiseScore>> fetchUserWiseScores();

    Map<String, List<GameWiseScore>> fetchScoreByGameId(Integer gameId);

    Map<String, List<UserWiseScore>> fetchScoreByUserId(Integer userId);

    Map<String, List<GameWiseScore>> fetchRankingBoardForAllGames(boolean filter);

    Map<String, List<GameWiseScore>> fetchRankingBoardByGameId(Integer gameId, boolean filter);

}