package com.game.service.impl;

import com.game.dto.request.ScoreRequest;
import com.game.exception.ApplicationException;
import com.game.service.CommonService;
import com.game.service.GameService;
import com.game.service.ScoreService;
import com.game.service.UserService;
import com.game.utils.ScoreBoardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    private UserService userService;

    private final GameService gameService;

    private final ScoreService scoreService;

    @Autowired
    public CommonServiceImpl(UserService userService,
                             GameService gameService,
                             ScoreService scoreService) {
        this.userService = userService;
        this.gameService = gameService;
        this.scoreService = scoreService;
    }

    @Override
    public void deleteGameAndScore(Integer gameId) {
        this.gameService.deleteGame(gameId);
        this.scoreService.deleteScore(gameId);
    }

    @Override
    public void publishScore(ScoreRequest scoreRequest) {
        log.info("Publishing score :{}", scoreRequest);
        if (this.userService.fetchUserById(scoreRequest.getUserId()).isActive() && this.gameService.getGameById(scoreRequest.getGameId()).isActive()) {
            this.scoreService.publishScore(scoreRequest);
        } else {
            throw new ApplicationException("Please Check if User and Game are operational");
        }
    }
}
