package de.stustafoosball.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "player")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "wins")
    private Integer wins;
    
    @Column(name = "losses")
    private Integer losses;
    
    @Column(name = "ties")
    private Integer ties;
    
    @Column(name = "mmr")
    private Integer mmr;
    
    @Column(name = "avg_duration")
    private Float avg_duration;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private User account;

    @OneToMany(mappedBy = "team1_player1")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Game> games_as_team1_player1s = new HashSet<>();

    @OneToMany(mappedBy = "team1_player2")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Game> games_as_team1_player2s = new HashSet<>();

    @OneToMany(mappedBy = "team2_player1")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Game> games_as_team2_player1s = new HashSet<>();

    @OneToMany(mappedBy = "team2_player2")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Game> games_as_team2_player2s = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Integer getWins() {
        return wins;
    }
    
    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }
    
    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getTies() {
        return ties;
    }
    
    public void setTies(Integer ties) {
        this.ties = ties;
    }

    public Integer getMmr() {
        return mmr;
    }
    
    public void setMmr(Integer mmr) {
        this.mmr = mmr;
    }

    public Float getAvg_duration() {
        return avg_duration;
    }
    
    public void setAvg_duration(Float avg_duration) {
        this.avg_duration = avg_duration;
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User user) {
        this.account = user;
    }

    public Set<Game> getGames_as_team1_player1s() {
        return games_as_team1_player1s;
    }

    public void setGames_as_team1_player1s(Set<Game> games) {
        this.games_as_team1_player1s = games;
    }

    public Set<Game> getGames_as_team1_player2s() {
        return games_as_team1_player2s;
    }

    public void setGames_as_team1_player2s(Set<Game> games) {
        this.games_as_team1_player2s = games;
    }

    public Set<Game> getGames_as_team2_player1s() {
        return games_as_team2_player1s;
    }

    public void setGames_as_team2_player1s(Set<Game> games) {
        this.games_as_team2_player1s = games;
    }

    public Set<Game> getGames_as_team2_player2s() {
        return games_as_team2_player2s;
    }

    public void setGames_as_team2_player2s(Set<Game> games) {
        this.games_as_team2_player2s = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        if(player.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", wins='" + wins + "'" +
            ", losses='" + losses + "'" +
            ", ties='" + ties + "'" +
            ", mmr='" + mmr + "'" +
            ", avg_duration='" + avg_duration + "'" +
            '}';
    }
}
