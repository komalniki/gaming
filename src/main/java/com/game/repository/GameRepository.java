package com.game.repository;

import com.game.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findGameByGameName(String gameName);

    List<Game> findGamesByActiveTrue();
}