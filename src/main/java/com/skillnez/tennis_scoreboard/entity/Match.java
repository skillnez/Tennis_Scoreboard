package com.skillnez.tennis_scoreboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "MATCHES")
@Getter
@Setter
public class Match implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PLAYER1_ID")
    private Player player1;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PLAYER2_ID")
    private Player player2;

    @Column(name = "WINNER")
    private int winnerId;

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
