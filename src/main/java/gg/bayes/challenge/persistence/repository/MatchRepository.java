package gg.bayes.challenge.persistence.repository;

import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
    @Query("select new gg.bayes.challenge.rest.model.HeroKills(log.actor, count(*)) " +
            "from CombatLogEntryEntity log " +
            "where type = 'HERO_KILLED' " +
            "and match_id = ?1 " +
            "group by log.actor")
    List<HeroKills> findHeroKillsByMatch(Long matchId);

    @Query("select new gg.bayes.challenge.rest.model.HeroItem(log.item, log.timestamp) " +
            "from CombatLogEntryEntity log " +
            "where type = 'ITEM_PURCHASED' " +
            "and match_id = ?1 " +
            "and actor = ?2")
    List<HeroItem> findHeroItemsByMatch(Long matchId, String hero);
}