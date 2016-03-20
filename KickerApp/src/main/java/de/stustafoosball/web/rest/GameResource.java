package de.stustafoosball.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.stustafoosball.domain.Game;
import de.stustafoosball.repository.GameRepository;
import de.stustafoosball.repository.search.GameSearchRepository;
import de.stustafoosball.web.rest.util.HeaderUtil;
import de.stustafoosball.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Game.
 */
@RestController
@RequestMapping("/api")
public class GameResource {

    private final Logger log = LoggerFactory.getLogger(GameResource.class);
        
    @Inject
    private GameRepository gameRepository;
    
    @Inject
    private GameSearchRepository gameSearchRepository;
    
    /**
     * POST  /games -> Create a new game.
     */
    @RequestMapping(value = "/games",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Game> createGame(@RequestBody Game game) throws URISyntaxException {
        log.debug("REST request to save Game : {}", game);
        if (game.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("game", "idexists", "A new game cannot already have an ID")).body(null);
        }
        Game result = gameRepository.save(game);
        gameSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("game", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /games -> Updates an existing game.
     */
    @RequestMapping(value = "/games",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Game> updateGame(@RequestBody Game game) throws URISyntaxException {
        log.debug("REST request to update Game : {}", game);
        if (game.getId() == null) {
            return createGame(game);
        }
        Game result = gameRepository.save(game);
        gameSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("game", game.getId().toString()))
            .body(result);
    }

    /**
     * GET  /games -> get all the games.
     */
    @RequestMapping(value = "/games",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Game>> getAllGames(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Games");
        Page<Game> page = gameRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/games");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /games/:id -> get the "id" game.
     */
    @RequestMapping(value = "/games/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        log.debug("REST request to get Game : {}", id);
        Game game = gameRepository.findOne(id);
        return Optional.ofNullable(game)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /games/:id -> delete the "id" game.
     */
    @RequestMapping(value = "/games/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        log.debug("REST request to delete Game : {}", id);
        gameRepository.delete(id);
        gameSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("game", id.toString())).build();
    }

    /**
     * SEARCH  /_search/games/:query -> search for the game corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/games/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Game> searchGames(@PathVariable String query) {
        log.debug("REST request to search Games for query {}", query);
        return StreamSupport
            .stream(gameSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
