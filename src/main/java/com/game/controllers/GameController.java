package com.game.controllers;

import com.game.dto.Response;
import com.game.dto.request.GameRequest;
import com.game.dto.response.GameResponse;
import com.game.service.CommonService;
import com.game.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private CommonService commonService;

    @PostMapping(value = "/create")
    public ResponseEntity<Response<GameResponse>> createGame(@Valid @RequestBody GameRequest gameRequest) {
        return new ResponseEntity<>(Response.successResponse(this.gameService.createGame(gameRequest)), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Response<Object>> deleteGame(@RequestParam(value = "gameId") Integer gameId) {
        this.commonService.deleteGameAndScore(gameId);
        return new ResponseEntity<>(Response.successResponse(), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/getGameById")
    public ResponseEntity<Response<GameResponse>> getGameDetailsById(@RequestParam(value = "gameId") Integer gameId) {
        return new ResponseEntity<>(Response.successResponse(this.gameService.getGameById(gameId)), HttpStatus.FOUND);
    }

    @GetMapping(value = "/getAllGames")
    public ResponseEntity<Response<List<GameResponse>>> getAllGameDetails() {
        log.info("Fetching all Games");
        return new ResponseEntity<>(Response.successResponse(this.gameService.fetchAllGames()), HttpStatus.FOUND);
    }

    @GetMapping(value = "/getAllActiveGames")
    public ResponseEntity<Response<List<GameResponse>>> getAllActiveGameDetails() {
        log.info("Fetching All Active Games");
        return new ResponseEntity<>(Response.successResponse(this.gameService.fetchAllActiveGames()), HttpStatus.FOUND);
    }
}