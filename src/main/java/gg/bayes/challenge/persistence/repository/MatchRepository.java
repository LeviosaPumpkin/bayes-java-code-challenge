package gg.bayes.challenge.persistence.repository;

import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
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

    @Query("select new gg.bayes.challenge.rest.model.HeroSpells(log.ability, count(*)) " +
            "from CombatLogEntryEntity log " +
            "where type = 'SPELL_CAST' " +
            "and match_id = ?1 " +
            "and actor = ?2 " +
            "group by log.ability")
    List<HeroSpells> findHeroSpellsByMatch(Long matchId, String hero);

    //select target, count(*), sum(damage) from dota_combat_log where match_id = 1 and actor = 'bloodseeker' and entry_type = 'DAMAGE_DONE' group by target;
    @Query("select new gg.bayes.challenge.rest.model.HeroDamage(log.target, count(*), sum(log.damage)) " +
            "from CombatLogEntryEntity log " +
            "where type = 'DAMAGE_DONE' " +
            "and match_id = ?1 " +
            "and actor = ?2 " +
            "group by log.target")
    List<HeroDamage> findHeroDamageByMatch(Long matchId, String hero);
}