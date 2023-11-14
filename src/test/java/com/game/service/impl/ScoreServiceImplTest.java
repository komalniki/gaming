package com.game.service.impl;

import com.game.dto.GameWiseScore;
import com.game.dto.Scores;
import com.game.mappers.ScoreMapper;
import com.game.properties.GameProperties;
import com.game.repository.ScoreBoardRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ScoreServiceImplTest {

    @Mock
    private ScoreBoardRepository scoreBoardRepository;

    @Mock
    private GameProperties gameProperties;

    @Mock
    private ScoreMapper scoreMapper;

    private ScoreServiceImpl scoreService;

    @Before
    public void setUp() {
        scoreService = new ScoreServiceImpl(scoreMapper, scoreBoardRepository, gameProperties);
    }


    @Test
    public void fetchGameWiseScores() {
        Scores scores1 = getScoreObject("Game1", "User1", 10);
        Scores scores2 = getScoreObject("Game1", "User1", 20);
        Scores scores3 = getScoreObject("Game2", "User2", 10);
        Map<String, Object> map = new HashMap<>();
        map.put("filter", false);
        List<Scores> scoresList = Arrays.asList(scores1, scores2, scores3);
        Mockito.when(this.scoreMapper.getScoreBoardForGame(map)).thenReturn(scoresList);
        Assert.assertEquals(2, this.scoreService.fetchGameWiseScores().size());
    }

    @Test
    public void fetchUserWiseScores() {
        Scores scores1 = getScoreObject("Game1", "User1", 10);
        Scores scores2 = getScoreObject("Game1", "User1", 20);
        Scores scores3 = getScoreObject("Game2", "User1", 10);
        Map<String, Object> map = new HashMap<>();
        map.put("filter", false);
        List<Scores> scoresList = Arrays.asList(scores1, scores2, scores3);
        Mockito.when(this.scoreMapper.getScoreBoardForGame(map)).thenReturn(scoresList);
        Assert.assertEquals(1, this.scoreService.fetchUserWiseScores().size());
    }

    @Test
    public void fetchScoreByGameId() {
        Scores scores1 = getScoreObject("Game1", "User1", 10);
        Scores scores2 = getScoreObject("Game1", "User1", 20);
        Map<String, Object> map = new HashMap<>();
        map.put("filter", false);
        map.put("gameId", 1001);
        List<Scores> scoresList = Arrays.asList(scores1, scores2);
        Mockito.when(this.scoreMapper.getScoreBoardForGame(map)).thenReturn(scoresList);
        Assert.assertEquals(1, this.scoreService.fetchScoreByGameId(1001).size());
    }

    @Test
    public void fetchScoreByUserId() {
        Scores scores1 = getScoreObject("Game1", "User1", 10);
        Scores scores2 = getScoreObject("Game1", "User1", 20);
        Map<String, Object> map = new HashMap<>();
        map.put("filter", false);
        map.put("userId", 1001);
        List<Scores> scoresList = Arrays.asList(scores1, scores2);
        Mockito.when(this.scoreMapper.getScoreBoardForGame(map)).thenReturn(scoresList);
        Assert.assertEquals(1, this.scoreService.fetchScoreByUserId(1001).size());
    }

    @Test
    public void fetchRankingBoardForAllGames() {
        Scores scores1 = getScoreObject("Game1", "User1", 10);
        Scores scores2 = getScoreObject("Game1", "User1", 20);
        Scores scores3 = getScoreObject("Game2", "User1", 10);
        Map<String, Object> map = new HashMap<>();
        map.put("filter", true);
        List<Scores> scoresList = Arrays.asList(scores1, scores2, scores3);
        Mockito.when(this.scoreMapper.getScoreBoardForGame(map)).thenReturn(scoresList);
        Assert.assertEquals(2, this.scoreService.fetchRankingBoardForAllGames().size());
    }

    @Test
    public void fetchRankingBoardByGameId() {
        Scores scores1 = getScoreObject("Game1", "User1", 10);
        Scores scores2 = getScoreObject("Game1", "User1", 20);
        Map<String, Object> map = new HashMap<>();
        map.put("filter", true);
        map.put("gameId", 1001);
        List<Scores> scoresList = Arrays.asList(scores1, scores2);
        Mockito.when(this.gameProperties.getLimit()).thenReturn(5);
        Mockito.when(this.scoreMapper.getScoreBoardForGame(map)).thenReturn(scoresList);
        Map<String, List<GameWiseScore>> response = this.scoreService.fetchRankingBoardByGameId(1001);
        Assert.assertEquals(1, response.size());
        Assert.assertEquals(2, response.get("Game1").size());
    }

    private Scores getScoreObject(String game, String user, double score) {
        Scores scores = new Scores();
        scores.setScore(score);
        scores.setGame(game);
        scores.setUser(user);
        scores.setDate(new Timestamp(System.currentTimeMillis()));
        return scores;
    }
}