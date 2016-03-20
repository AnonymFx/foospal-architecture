package de.stustafoosball.web.rest;

import de.stustafoosball.Application;
import de.stustafoosball.domain.Player;
import de.stustafoosball.repository.PlayerRepository;
import de.stustafoosball.repository.search.PlayerSearchRepository;

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
 * Test class for the PlayerResource REST controller.
 *
 * @see PlayerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlayerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_WINS = 1;
    private static final Integer UPDATED_WINS = 2;

    private static final Integer DEFAULT_LOSSES = 1;
    private static final Integer UPDATED_LOSSES = 2;

    private static final Integer DEFAULT_TIES = 1;
    private static final Integer UPDATED_TIES = 2;

    private static final Integer DEFAULT_MMR = 1;
    private static final Integer UPDATED_MMR = 2;

    private static final Float DEFAULT_AVG_DURATION = 1F;
    private static final Float UPDATED_AVG_DURATION = 2F;

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private PlayerSearchRepository playerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPlayerMockMvc;

    private Player player;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlayerResource playerResource = new PlayerResource();
        ReflectionTestUtils.setField(playerResource, "playerSearchRepository", playerSearchRepository);
        ReflectionTestUtils.setField(playerResource, "playerRepository", playerRepository);
        this.restPlayerMockMvc = MockMvcBuilders.standaloneSetup(playerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        player = new Player();
        player.setName(DEFAULT_NAME);
        player.setWins(DEFAULT_WINS);
        player.setLosses(DEFAULT_LOSSES);
        player.setTies(DEFAULT_TIES);
        player.setMmr(DEFAULT_MMR);
        player.setAvg_duration(DEFAULT_AVG_DURATION);
    }

    @Test
    @Transactional
    public void createPlayer() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().size();

        // Create the Player

        restPlayerMockMvc.perform(post("/api/players")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(player)))
                .andExpect(status().isCreated());

        // Validate the Player in the database
        List<Player> players = playerRepository.findAll();
        assertThat(players).hasSize(databaseSizeBeforeCreate + 1);
        Player testPlayer = players.get(players.size() - 1);
        assertThat(testPlayer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlayer.getWins()).isEqualTo(DEFAULT_WINS);
        assertThat(testPlayer.getLosses()).isEqualTo(DEFAULT_LOSSES);
        assertThat(testPlayer.getTies()).isEqualTo(DEFAULT_TIES);
        assertThat(testPlayer.getMmr()).isEqualTo(DEFAULT_MMR);
        assertThat(testPlayer.getAvg_duration()).isEqualTo(DEFAULT_AVG_DURATION);
    }

    @Test
    @Transactional
    public void getAllPlayers() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the players
        restPlayerMockMvc.perform(get("/api/players?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(player.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].wins").value(hasItem(DEFAULT_WINS)))
                .andExpect(jsonPath("$.[*].losses").value(hasItem(DEFAULT_LOSSES)))
                .andExpect(jsonPath("$.[*].ties").value(hasItem(DEFAULT_TIES)))
                .andExpect(jsonPath("$.[*].mmr").value(hasItem(DEFAULT_MMR)))
                .andExpect(jsonPath("$.[*].avg_duration").value(hasItem(DEFAULT_AVG_DURATION.doubleValue())));
    }

    @Test
    @Transactional
    public void getPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get the player
        restPlayerMockMvc.perform(get("/api/players/{id}", player.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(player.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.wins").value(DEFAULT_WINS))
            .andExpect(jsonPath("$.losses").value(DEFAULT_LOSSES))
            .andExpect(jsonPath("$.ties").value(DEFAULT_TIES))
            .andExpect(jsonPath("$.mmr").value(DEFAULT_MMR))
            .andExpect(jsonPath("$.avg_duration").value(DEFAULT_AVG_DURATION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlayer() throws Exception {
        // Get the player
        restPlayerMockMvc.perform(get("/api/players/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

		int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player
        player.setName(UPDATED_NAME);
        player.setWins(UPDATED_WINS);
        player.setLosses(UPDATED_LOSSES);
        player.setTies(UPDATED_TIES);
        player.setMmr(UPDATED_MMR);
        player.setAvg_duration(UPDATED_AVG_DURATION);

        restPlayerMockMvc.perform(put("/api/players")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(player)))
                .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> players = playerRepository.findAll();
        assertThat(players).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = players.get(players.size() - 1);
        assertThat(testPlayer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlayer.getWins()).isEqualTo(UPDATED_WINS);
        assertThat(testPlayer.getLosses()).isEqualTo(UPDATED_LOSSES);
        assertThat(testPlayer.getTies()).isEqualTo(UPDATED_TIES);
        assertThat(testPlayer.getMmr()).isEqualTo(UPDATED_MMR);
        assertThat(testPlayer.getAvg_duration()).isEqualTo(UPDATED_AVG_DURATION);
    }

    @Test
    @Transactional
    public void deletePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

		int databaseSizeBeforeDelete = playerRepository.findAll().size();

        // Get the player
        restPlayerMockMvc.perform(delete("/api/players/{id}", player.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Player> players = playerRepository.findAll();
        assertThat(players).hasSize(databaseSizeBeforeDelete - 1);
    }
}
