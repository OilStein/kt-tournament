package com.backend.objects;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "GAMES")
@Data
public class Game {

    private @Id @GeneratedValue Long id;
    private Long player1Id;
    private Long player2Id;
    private Status status;


    @OneToOne
    private Score player1Score;
    @OneToOne
    private Score player2Score;

    public Game() {
        this.status = Status.IN_PROGRESS;
    }

    public Game(Long player1Id, Long player2Id, Score player1Score, Score player2Score) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.status = Status.IN_PROGRESS;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

}
