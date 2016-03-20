package de.stustafoosball.web.rest;

import de.stustafoosball.Application;
import de.stustafoosball.domain.Game;
import de.stustafoosball.repository.GameRepository;
import de.stustafoosball.repository.search.GameSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the GameResource REST controller.
 *
 * @see GameResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GameResourceIntTest {


    private static final Integer DEFAULT_TEAM1_SCORE = 1;
    private static final Integer UPDATED_TEAM1_SCORE = 2;

    private static final Integer DEFAULT_TEAM2_SCORE = 1;
    private static final Integer UPDATED_TEAM2_SCORE = 2;

    private static final Float DEFAULT_DURATION = 1F;
    private static final Float UPDATED_DURATION = 2F;

    @Inject
    private GameRepository gameRepository;

    @Inject
    private GameSearchRepository gameSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGameMockMvc;

    private Game game;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GameResource gameResource = new GameResource();
        ReflectionTestUtils.setField(gameResource, "gameSearchRepository", gameSearchRepository);
        ReflectionTestUtils.setField(gameResource, "gameRepository", gameRepository);
        this.restGameMockMvc = MockMvcBuilders.standaloneSetup(gameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        game = new Game();
        game.setTeam1_score(DEFAULT_TEAM1_SCORE);
        game.setTeam2_score(DEFAULT_TEAM2_SCORE);
        game.setDuration(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createGame() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game

        restGameMockMvc.perform(post("/api/games")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(game)))
                .andExpect(status().isCreated());

        // Validate the Game in the database
        List<Game> games = gameRepository.findAll();
        assertThat(games).hasSize(databaseSizeBeforeCreate + 1);
        Game testGame = games.get(games.size() - 1);
        assertThat(testGame.getTeam1_score()).isEqualTo(DEFAULT_TEAM1_SCORE);
        assertThat(testGame.getTeam2_score()).isEqualTo(DEFAULT_TEAM2_SCORE);
        assertThat(testGame.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void getAllGames() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get all the games
        restGameMockMvc.perform(get("/api/games?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(game.getId().intValue())))
                .andExpect(jsonPath("$.[*].team1_score").value(hasItem(DEFAULT_TEAM1_SCORE)))
                .andExpect(jsonPath("$.[*].team2_score").value(hasItem(DEFAULT_TEAM2_SCORE)))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())));
    }

    @Test
    @Transactional
    public void getGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", game.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(game.getId().intValue()))
            .andExpect(jsonPath("$.team1_score").value(DEFAULT_TEAM1_SCORE))
            .andExpect(jsonPath("$.team2_score").value(DEFAULT_TEAM2_SCORE))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGame() throws Exception {
        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

		int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Update the game
        game.setTeam1_score(UPDATED_TEAM1_SCORE);
        game.setTeam2_score(UPDATED_TEAM2_SCORE);
        game.setDuration(UPDATED_DURATION);

        restGameMockMvc.perform(put("/api/games")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(game)))
                .andExpect(status().isOk());

        // Validate the Game in the database
        List<Game> games = gameRepository.findAll();
        assertThat(games).hasSize(databaseSizeBeforeUpdate);
        Game testGame = games.get(games.size() - 1);
        assertThat(testGame.getTeam1_score()).isEqualTo(UPDATED_TEAM1_SCORE);
        assertThat(testGame.getTeam2_score()).isEqualTo(UPDATED_TEAM2_SCORE);
        assertThat(testGame.getDuration()).isEqualTo(UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void deleteGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

		int databaseSizeBeforeDelete = gameRepository.findAll().size();

        // Get the game
        restGameMockMvc.perform(delete("/api/games/{id}", game.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Game> games = gameRepository.findAll();
        assertThat(games).hasSize(databaseSizeBeforeDelete - 1);
    }
}
