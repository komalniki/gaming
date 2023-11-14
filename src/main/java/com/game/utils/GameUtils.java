package com.game.utils;

import com.game.dto.request.GameRequest;
import com.game.dto.response.GameResponse;
import com.game.models.Game;

import java.sql.Timestamp;

public class GameUtils {

    private GameUtils() {
    }

    public static Game populateGameEntityForCreation(GameRequest gameRequest) {
        Game game = new Game();
        game.setGameName(gameRequest.getName());
        game.setCreationDate(new Timestamp(System.currentTimeMillis()));
        game.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
        game.setActive(Boolean.TRUE);
        return game;
    }

    public static void populateGameEntityForDeletion(Game game) {
        game.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
        game.setActive(Boolean.FALSE);
    }

    public static GameResponse populateGameResponse(Game game) {
        GameResponse response = new GameResponse();
        response.setId(game.getGameId());
        response.setName(game.getGameName());
        response.setActive(game.isActive());
        response.setCreationDate(game.getCreationDate());
        response.setLastModifiedDate(game.getLastModifiedDate());
        return response;
    }
}