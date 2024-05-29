package com.backend.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "GAMES")
@Data
public class Game {

    private @Id @GeneratedValue Long id;
    @NotBlank
    private Long player1Id;
    @NotBlank
    private Long player2Id;
    private Status status;

    @Size(min = 0, max = 16)
    private int player1VictoryPoints;
    @Size(min = 0, max = 16)
    private int player2VictoryPoints;
    @Size(min = 0, max = 6)
    private int player1TacOpsPoints;
    @Size(min = 0, max = 6)
    private int player2TacOpsPoints;
    @Size(min = 0, max = 2)
    private boolean player1Painted;
    @Size(min = 0, max = 2)
    private boolean player2Painted;

    @Size(min = 0, max = 24)
    private int player1Total;
    @Size(min = 0, max = 24)
    private int player2Total;

    public Game() {
        this.status = Status.IN_PROGRESS;
    }

    public Game(Long player1Id, Long player2Id) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.status = Status.IN_PROGRESS;
        player1VictoryPoints = 0;
        player2VictoryPoints = 0;
        player1TacOpsPoints = 0;
        player2TacOpsPoints = 0;
        player1Painted = false;
        player2Painted = false;
        player1Total = 0;
        player2Total = 0;

    }

    public Game(Status status) {
        this.status = status;
    }

    public int getPlayer1Total() {
        return player1VictoryPoints + player1TacOpsPoints + (player1Painted ? 2 : 0);
    }

    public int getPlayer2Total() {
        return player2VictoryPoints + player2TacOpsPoints + (player2Painted ? 2 : 0);
    }

    public void setPlayer1Total() {
        this.player1Total = this.getPlayer1Total();
    }

    public void setPlayer2Total() {
        this.player2Total = this.getPlayer2Total();
    }

    public void setPlayer1VictoryPoints(int player1VictoryPoints) {
        this.player1VictoryPoints = player1VictoryPoints;
        this.setPlayer1Total();
    }

    public void setPlayer2VictoryPoints(int player2VictoryPoints) {
        this.player2VictoryPoints = player2VictoryPoints;
        this.setPlayer2Total();
    }

    public void setPlayer1TacOpsPoints(int player1TacOpsPoints) {
        this.player1TacOpsPoints = player1TacOpsPoints;
        this.setPlayer1Total();
    }

    public void setPlayer2TacOpsPoints(int player2TacOpsPoints) {
        this.player2TacOpsPoints = player2TacOpsPoints;
        this.setPlayer2Total();
    }

    public void setPlayer1Painted(boolean player1Painted) {
        this.player1Painted = player1Painted;
        this.setPlayer1Total();
    }

    public void setPlayer2Painted(boolean player2Painted) {
        this.player2Painted = player2Painted;
        this.setPlayer2Total();
    }
}
