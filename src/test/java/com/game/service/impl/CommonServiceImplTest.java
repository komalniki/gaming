package com.game.service.impl;

import com.game.dto.request.ScoreRequest;
import com.game.exception.ApplicationException;
import com.game.mappers.ScoreMapper;
import com.game.models.Game;
import com.game.models.ScoreBoard;
import com.game.models.User;
import com.game.properties.GameProperties;
import com.game.repository.GameRepository;
import com.game.repository.ScoreBoardRepository;
import com.game.repository.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CommonServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ScoreBoardRepository scoreBoardRepository;

    @Mock
    private GameProperties gameProperties;

    @Mock
    private ScoreMapper scoreMapper;

    private UserServiceImpl userService;

    private GameServiceImpl gameService;

    private ScoreServiceImpl scoreService;

    private CommonServiceImpl commonService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(usersRepository);
        gameService = new GameServiceImpl(gameRepository);
        scoreService = new ScoreServiceImpl(scoreMapper, scoreBoardRepository, gameProperties);
        commonService = new CommonServiceImpl(userService, gameService, scoreService);
    }

    @Test(expected = ApplicationException.class)
    public void deleteGameAndScore_game_not_exists() {
        Mockito.when(this.gameRepository.findById(1001)).thenReturn(Optional.empty());
        this.commonService.deleteGameAndScore(1001);
    }

    @Test
    public void deleteGameAndScore_no_scores() {
        Mockito.when(this.gameRepository.findById(1001)).thenReturn(Optional.of(Mockito.mock(Game.class)));
        Mockito.when(this.scoreBoardRepository.findScoreBoardsByGameId(1001)).thenReturn(new ArrayList<>());
        this.commonService.deleteGameAndScore(1001);
    }

    @Test
    public void deleteGameAndScore() {
        Mockito.when(this.gameRepository.findById(1001)).thenReturn(Optional.of(Mockito.mock(Game.class)));
        Mockito.when(this.scoreBoardRepository.findScoreBoardsByGameId(1001)).thenReturn(Arrays.asList(Mockito.mock(ScoreBoard.class)));
        this.commonService.deleteGameAndScore(1001);
    }

    @Test(expected = ApplicationException.class)
    public void publishScore_inactive_game() {
        ScoreRequest scoreRequest = Mockito.mock(ScoreRequest.class);
        Mockito.when(scoreRequest.getGameId()).thenReturn(1001);
        Mockito.when(scoreRequest.getUserId()).thenReturn(1001);
        User user = Mockito.mock(User.class);
        Mockito.when(user.isActive()).thenReturn(true);
        Game game = Mockito.mock(Game.class);
        Mockito.when(game.isActive()).thenReturn(false);
        Mockito.when(this.usersRepository.findById(1001)).thenReturn(Optional.of(user));
        Mockito.when(this.gameRepository.findById(1001)).thenReturn(Optional.of(game));
        this.commonService.publishScore(scoreRequest);
    }

    @Test
    public void publishScore() {
        ScoreRequest scoreRequest = Mockito.mock(ScoreRequest.class);
        Mockito.when(scoreRequest.getGameId()).thenReturn(1001);
        Mockito.when(scoreRequest.getUserId()).thenReturn(1001);
        User user = Mockito.mock(User.class);
        Mockito.when(user.isActive()).thenReturn(true);
        Game game = Mockito.mock(Game.class);
        Mockito.when(game.isActive()).thenReturn(true);
        Mockito.when(this.usersRepository.findById(1001)).thenReturn(Optional.of(user));
        Mockito.when(this.gameRepository.findById(1001)).thenReturn(Optional.of(game));
        this.commonService.publishScore(scoreRequest);
    }
}