package de.stustafoosball.repository;

import de.stustafoosball.domain.Player;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Player entity.
 */
public interface PlayerRepository extends JpaRepository<Player,Long> {

    @Query("select player from Player player where player.account.login = ?#{principal.username}")
    List<Player> findByAccountIsCurrentUser();

}
