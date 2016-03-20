package de.stustafoosball.repository.search;

import de.stustafoosball.domain.Player;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Player entity.
 */
public interface PlayerSearchRepository extends ElasticsearchRepository<Player, Long> {
}
