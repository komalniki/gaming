package com.game.service;

import com.game.dto.request.GameRequest;
import com.game.dto.response.GameResponse;
import com.game.models.Game;

import java.util.List;

public interface GameService {

    GameResponse createGame(GameRequest gameRequest);

    void deleteGame(Integer gameId);

    GameResponse getGameById(Integer gameId);

    public Game fetchGameById(Integer gameId);

    List<GameResponse> fetchAllGames();

    List<GameResponse> fetchAllActiveGames();
}