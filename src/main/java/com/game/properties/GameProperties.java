package com.game.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
public class GameProperties {

    @Value("${ranking.board.limit}")
    private Integer limit;

    @Value("${ranking.board.headers}")
    private String[] headers;

}