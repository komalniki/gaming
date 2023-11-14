package com.game.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "games")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game {

    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

    @Column(name = "active")
    private boolean active;
}