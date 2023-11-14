package com.game.utils;

import com.game.dto.GameWiseScore;
import com.game.dto.Scores;
import com.game.dto.UserWiseScore;
import com.game.dto.request.ScoreRequest;
import com.game.models.ScoreBoard;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreBoardUtils {

    private ScoreBoardUtils() {

    }

    public static ScoreBoard populateScoreEntity(ScoreRequest scoreRequest) {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.setUserId(scoreRequest.getUserId());
        scoreBoard.setGameId(scoreRequest.getGameId());
        scoreBoard.setScore(scoreRequest.getScore());
        scoreBoard.setCreationDate(new Timestamp(System.currentTimeMillis()));
        scoreBoard.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
        scoreBoard.setActive(Boolean.TRUE);
        return scoreBoard;
    }

    public static Map<String, List<GameWiseScore>> getRankingForGames(List<Scores> scoresList, Integer limit) {
        Map<String, List<GameWiseScore>> gameMap = convertScoreListToGameObject(scoresList);
        for (Map.Entry<String, List<GameWiseScore>> key : gameMap.entrySet()) {
            List<GameWiseScore> gameWiseScoreList = gameMap.get(key.getKey()).stream()
                    .sorted(Comparator.comparing(GameWiseScore::getScore, Comparator.reverseOrder()))
                    .limit(limit)
                    .collect(Collectors.toList());
            gameMap.replace(key.getKey(), gameWiseScoreList);
        }
        return gameMap;
    }

    public static Map<String, List<GameWiseScore>> convertScoreListToGameObject(List<Scores> scoresList) {
        Map<String, List<GameWiseScore>> gameMap = new HashMap<>();
        for (Scores scores : scoresList) {
            if (gameMap.containsKey(scores.getGame())) {
                gameMap.get(scores.getGame()).add(convertScoreToGameWiseScore(scores));
            } else {
                List<GameWiseScore> list = new ArrayList<>();
                list.add(convertScoreToGameWiseScore(scores));
                gameMap.put(scores.getGame(), list);
            }
        }
        return gameMap;
    }

    public static Map<String, List<UserWiseScore>> convertScoreListToUserObject(List<Scores> scoresList) {
        Map<String, List<UserWiseScore>> gameMap = new HashMap<>();
        for (Scores scores : scoresList) {
            if (gameMap.containsKey(scores.getUser())) {
                gameMap.get(scores.getUser()).add(convertScoreToUserWiseScore(scores));
            } else {
                List<UserWiseScore> list = new ArrayList<>();
                list.add(convertScoreToUserWiseScore(scores));
                gameMap.put(scores.getUser(), list);
            }
        }
        return gameMap;
    }

    private static GameWiseScore convertScoreToGameWiseScore(Scores scores) {
        GameWiseScore gameWiseScore = new GameWiseScore();
        gameWiseScore.setScore(scores.getScore());
        gameWiseScore.setUserName(scores.getUser());
        gameWiseScore.setDate(scores.getDate());
        return gameWiseScore;
    }

    private static UserWiseScore convertScoreToUserWiseScore(Scores scores) {
        UserWiseScore userWiseScore = new UserWiseScore();
        userWiseScore.setScore(scores.getScore());
        userWiseScore.setGameName(scores.getGame());
        userWiseScore.setDate(scores.getDate());
        return userWiseScore;
    }
}
