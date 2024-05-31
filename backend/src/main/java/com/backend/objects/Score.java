package com.backend.objects;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Score {

    private @Id @GeneratedValue Long id;

    private int primaryPoints;
    private int secondaryPoints;
    private int painted;

    public Score() {
        this.primaryPoints = 0;
        this.secondaryPoints = 0;
        this.painted = 0;
    }

    int calculateScore() {
        return primaryPoints + secondaryPoints + painted;
    }
}
