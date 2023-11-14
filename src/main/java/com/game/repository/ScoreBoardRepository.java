package com.game.repository;

import com.game.models.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreBoardRepository extends JpaRepository<ScoreBoard, Integer> {

    List<ScoreBoard> findScoreBoardsByGameId(Integer gameId);
}