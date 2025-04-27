package com.skillnez.tennis_scoreboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Builder
@Table(name = "PLAYERS")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Player implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", unique = true, nullable = false, length = 64)
    private String name;

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId(){
        return this.id;
    }
}
