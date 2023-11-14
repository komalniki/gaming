package com.game.service.impl;

import com.game.dto.request.GameRequest;
import com.game.dto.response.GameResponse;
import com.game.exception.ApplicationException;
import com.game.models.Game;
import com.game.repository.GameRepository;
import com.game.service.GameService;
import com.game.utils.GameUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;


    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameResponse createGame(GameRequest gameRequest) {
        log.info("Request to create Game with : {}", gameRequest);
        if (this.checkIfGameExistsWithProvidedName(gameRequest.getName())) {
            throw new ApplicationException("Game Already Exists with Provided Name");
        }
        return GameUtils.populateGameResponse(this.gameRepository.save(GameUtils.populateGameEntityForCreation(gameRequest)));
    }

    @Override
    public void deleteGame(Integer gameId) {
        log.info("Request received to Delete Game with Id: {}", gameId);
        Game game = this.fetchGameById(gameId);
        GameUtils.populateGameEntityForDeletion(game);
        this.gameRepository.save(game);
    }

    @Override
    public GameResponse getGameById(Integer gameId) {
        log.info("Request received to Fetch Game Details with Id: {}", gameId);
        return GameUtils.populateGameResponse(this.fetchGameById(gameId));
    }

    @Override
    public List<GameResponse> fetchAllGames() {
        return this.gameRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(GameUtils::populateGameResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GameResponse> fetchAllActiveGames() {
        return this.gameRepository.findGamesByActiveTrue().stream()
                .filter(Objects::nonNull)
                .map(GameUtils::populateGameResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Game fetchGameById(Integer gameId) {
        return this.gameRepository.findById(gameId)
                .orElseThrow(() -> new ApplicationException("No Game Exists with given Id : " + gameId));
    }

    private boolean checkIfGameExistsWithProvidedName(String gameName) {
        return this.gameRepository.findGameByGameName(gameName).isPresent();
    }
}