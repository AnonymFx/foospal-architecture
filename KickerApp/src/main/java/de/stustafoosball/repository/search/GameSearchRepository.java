package de.stustafoosball.repository.search;

import de.stustafoosball.domain.Game;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Game entity.
 */
public interface GameSearchRepository extends ElasticsearchRepository<Game, Long> {
}
