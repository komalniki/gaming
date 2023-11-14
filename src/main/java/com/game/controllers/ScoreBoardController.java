package com.game.controllers;

import com.game.dto.Response;
import com.game.dto.request.ScoreRequest;
import com.game.service.CSVService;
import com.game.service.CommonService;
import com.game.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/score")
public class ScoreBoardController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CSVService csvService;

    @PostMapping(value = "/publish")
    public ResponseEntity<Response<Object>> publishScore(@RequestBody ScoreRequest scoreRequest) {
        this.commonService.publishScore(scoreRequest);
        return new ResponseEntity<>(Response.successResponse(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/game/all")
    public ResponseEntity<Response<Object>> fetchScoreForAllGames() {
        return new ResponseEntity<>(Response.successResponse(this.scoreService.fetchGameWiseScores()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/all")
    public ResponseEntity<Response<Object>> fetchScoreForAllUsers() {
        return new ResponseEntity<>(Response.successResponse(this.scoreService.fetchUserWiseScores()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/game")
    public ResponseEntity<Response<Object>> fetchScoreByGameId(@RequestParam(value = "gameId") Integer gameId) {
        return new ResponseEntity<>(Response.successResponse(this.scoreService.fetchScoreByGameId(gameId)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Response<Object>> fetchScoreByUserId(@RequestParam(value = "userId") Integer userId) {
        return new ResponseEntity<>(Response.successResponse(this.scoreService.fetchScoreByUserId(userId)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/ranking/board")
    public ResponseEntity<Response<Object>> getRankingBoard() {
        return new ResponseEntity<>(Response.successResponse(this.scoreService.fetchRankingBoardForAllGames()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/ranking/board/game")
    public ResponseEntity<Response<Object>> getRankingBoardForGame(@RequestParam(value = "gameId") Integer gameId) {
        return new ResponseEntity<>(Response.successResponse(this.scoreService.fetchRankingBoardByGameId(gameId)), HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public ResponseEntity<Response<Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Uploading new File");
        this.csvService.save(file);
        return new ResponseEntity<>(Response.successResponse(), HttpStatus.ACCEPTED);
    }
}