package com.game.service.impl;

import com.game.dto.GameWiseScore;
import com.game.dto.Scores;
import com.game.dto.UserWiseScore;
import com.game.dto.request.ScoreRequest;
import com.game.mappers.ScoreMapper;
import com.game.models.ScoreBoard;
import com.game.properties.GameProperties;
import com.game.repository.ScoreBoardRepository;
import com.game.service.ScoreService;
import com.game.utils.ScoreBoardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreBoardRepository scoreBoardRepository;

    private final GameProperties gameProperties;

    private final ScoreMapper scoreMapper;

    @Autowired
    public ScoreServiceImpl(ScoreMapper scoreMapper,
                            ScoreBoardRepository scoreBoardRepository,
                            GameProperties gameProperties) {
        this.scoreMapper = scoreMapper;
        this.scoreBoardRepository = scoreBoardRepository;
        this.gameProperties = gameProperties;
    }

    @Override
    public void publishScore(ScoreRequest scoreRequest) {
        log.info("Saving score for : {}", scoreRequest);
        this.scoreBoardRepository.save(ScoreBoardUtils.populateScoreEntity(scoreRequest));
    }

    @Override
    public void deleteScore(Integer gameId) {
        log.info("Marking Scores for Deletion for Game: {}", gameId);
        List<ScoreBoard> scoreBoardList = this.scoreBoardRepository.findScoreBoardsByGameId(gameId).stream()
                .filter(Objects::nonNull)
                .peek(scoreBoard -> {
                    scoreBoard.setActive(Boolean.FALSE);
                    scoreBoard.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
                })
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(scoreBoardList)) {
            this.scoreBoardRepository.saveAll(scoreBoardList);
        } else {
            log.error("No Scores Available For Given Game");
        }
    }

    @Override
    public Map<String, List<GameWiseScore>> fetchGameWiseScores() {
        return ScoreBoardUtils.convertScoreListToGameObject(this.getAllScores(null, null, false));
    }

    @Override
    public Map<String, List<UserWiseScore>> fetchUserWiseScores() {
        return ScoreBoardUtils.convertScoreListToUserObject(this.getAllScores(null, null, false));
    }

    @Override
    public Map<String, List<GameWiseScore>> fetchScoreByGameId(Integer gameId) {
        return ScoreBoardUtils.convertScoreListToGameObject(this.getAllScores(null, gameId, false));
    }

    @Override
    public Map<String, List<UserWiseScore>> fetchScoreByUserId(Integer userId) {
        return ScoreBoardUtils.convertScoreListToUserObject(this.getAllScores(userId, null, false));
    }

    @Override
    public Map<String, List<GameWiseScore>> fetchRankingBoardForAllGames(boolean filter) {
        return ScoreBoardUtils.getRankingForGames(this.getAllScores(null, null, filter), this.gameProperties.getLimit());
    }

    @Override
    public Map<String, List<GameWiseScore>> fetchRankingBoardByGameId(Integer gameId, boolean filter) {
        return ScoreBoardUtils.getRankingForGames(this.getAllScores(null, gameId, filter), this.gameProperties.getLimit());

    }

    private List<Scores> getAllScores(Integer userId, Integer gameId, boolean filter) {
        Map<String, Object> map = new HashMap<>();
        if(userId != null) {
            map.put("userId", userId);
        }
        if(gameId != null) {
            map.put("gameId", gameId);
        }
        map.put("filter", filter);
        return this.scoreMapper.getScoreBoardForGame(map);
    }
}
