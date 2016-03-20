package de.stustafoosball.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "game")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "team1_score")
    private Integer team1_score;
    
    @Column(name = "team2_score")
    private Integer team2_score;
    
    @Column(name = "duration")
    private Float duration;
    
    @ManyToOne
    @JoinColumn(name = "team1_player1_id")
    private Player team1_player1;

    @ManyToOne
    @JoinColumn(name = "team1_player2_id")
    private Player team1_player2;

    @ManyToOne
    @JoinColumn(name = "team2_player1_id")
    private Player team2_player1;

    @ManyToOne
    @JoinColumn(name = "team2_player2_id")
    private Player team2_player2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTeam1_score() {
        return team1_score;
    }
    
    public void setTeam1_score(Integer team1_score) {
        this.team1_score = team1_score;
    }

    public Integer getTeam2_score() {
        return team2_score;
    }
    
    public void setTeam2_score(Integer team2_score) {
        this.team2_score = team2_score;
    }

    public Float getDuration() {
        return duration;
    }
    
    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Player getTeam1_player1() {
        return team1_player1;
    }

    public void setTeam1_player1(Player player) {
        this.team1_player1 = player;
    }

    public Player getTeam1_player2() {
        return team1_player2;
    }

    public void setTeam1_player2(Player player) {
        this.team1_player2 = player;
    }

    public Player getTeam2_player1() {
        return team2_player1;
    }

    public void setTeam2_player1(Player player) {
        this.team2_player1 = player;
    }

    public Player getTeam2_player2() {
        return team2_player2;
    }

    public void setTeam2_player2(Player player) {
        this.team2_player2 = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        if(game.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + id +
            ", team1_score='" + team1_score + "'" +
            ", team2_score='" + team2_score + "'" +
            ", duration='" + duration + "'" +
            '}';
    }
}
