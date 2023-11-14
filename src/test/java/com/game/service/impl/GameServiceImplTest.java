package com.game.service.impl;

import com.game.dto.request.GameRequest;
import com.game.exception.ApplicationException;
import com.game.models.Game;
import com.game.repository.GameRepository;
import org.junit.Assert;
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
public class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    private GameRequest gameRequest;

    private GameServiceImpl gameService;

    @Before
    public void setUp() {
        gameRequest = Mockito.mock(GameRequest.class);
        Mockito.when(gameRequest.getName()).thenReturn("testGame");
        gameService = new GameServiceImpl(gameRepository);
    }

    @Test(expected = ApplicationException.class)
    public void createGame_game_exists_with_name() {
        Mockito.when(this.gameRepository.findGameByGameName("testGame")).thenReturn(Optional.of(Mockito.mock(Game.class)));
        this.gameService.createGame(gameRequest);
    }

    @Test(expected = ApplicationException.class)
    public void createGame() {
        Game game = Mockito.mock(Game.class);
        Mockito.when(this.gameRepository.findGameByGameName("testGame")).thenReturn(Optional.of(game));
        Assert.assertNotNull(this.gameService.createGame(gameRequest));
    }

    @Test(expected = ApplicationException.class)
    public void deleteGame_game_not_found() {
        Mockito.when(this.gameRepository.findById(1001)).thenReturn(Optional.empty());
        this.gameService.deleteGame(1001);
    }

    @Test
    public void deleteGame() {
        Mockito.when(this.gameRepository.findById(1001)).thenReturn(Optional.of(Mockito.mock(Game.class)));
        this.gameService.deleteGame(1001);
    }

    @Test(expected = ApplicationException.class)
    public void getGameById_game_not_found() {
        Mockito.when(this.gameRepository.findById(1001)).thenReturn(Optional.empty());
        this.gameService.getGameById(1001);
    }

    @Test
    public void getGameById() {
        Mockito.when(this.gameRepository.findById(1001)).thenReturn(Optional.of(Mockito.mock(Game.class)));
        Assert.assertNotNull(this.gameService.getGameById(1001));
    }

    @Test
    public void fetchAllGames_no_games() {
        Mockito.when(this.gameRepository.findAll()).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, this.gameService.fetchAllGames().size());
    }

    @Test
    public void fetchAllGames() {
        Mockito.when(this.gameRepository.findAll()).thenReturn(Arrays.asList(Mockito.mock(Game.class)));
        Assert.assertEquals(1, this.gameService.fetchAllGames().size());
    }

    @Test
    public void fetchAllActiveGames_no_active_tasks() {
        Mockito.when(this.gameRepository.findGamesByActiveTrue()).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, this.gameService.fetchAllActiveGames().size());
    }

    @Test
    public void fetchAllActiveGames() {
        Mockito.when(this.gameRepository.findGamesByActiveTrue()).thenReturn(Arrays.asList(Mockito.mock(Game.class)));
        Assert.assertEquals(1, this.gameService.fetchAllActiveGames().size());
    }
}