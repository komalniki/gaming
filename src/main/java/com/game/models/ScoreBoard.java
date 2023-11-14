package com.game.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "score_board")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreBoard {

    @Id
    @Column(name = "score_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer scoreId;

    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "score")
    private double score;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

    @Column(name = "active")
    private boolean active;

}