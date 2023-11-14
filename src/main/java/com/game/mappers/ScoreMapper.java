package com.game.mappers;

import com.game.dto.Scores;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScoreMapper {

    List<Scores> getScoreBoardForGame(Map<String, Object> map);

}