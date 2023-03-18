package gg.bayes.challenge.service;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import org.springframework.stereotype.Service;

import static gg.bayes.challenge.utils.LogUtils.*;
import static gg.bayes.challenge.utils.LogUtils.ACTOR_KILLER_PREFIX;

@Service
public class LogRecordCreationServiceImpl implements LogRecordCreationService {
    @Override
    public CombatLogEntryEntity buildCombatLogEntry(String log, MatchEntity match) {
        CombatLogEntryEntity.Type type = getType(log);
        if (type == null) return null;
        switch (type) {
            case SPELL_CAST:
                return buildSpellCastLog(log, match);
            case HERO_KILLED:
                return buildHeroKilled(log, match);
            case DAMAGE_DONE:
                return buildDamageDone(log, match);
            case ITEM_PURCHASED:
                return buildItemPurchased(log, match);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private CombatLogEntryEntity buildItemPurchased(String log, MatchEntity match) {
        //[00:15:19.996] npc_dota_hero_grimstroke buys item item_tango
        CombatLogEntryEntity entry = new CombatLogEntryEntity();
        entry.setMatch(match);
        entry.setTimestamp(getTimestamp(log));
        entry.setType(CombatLogEntryEntity.Type.ITEM_PURCHASED);
        entry.setActor(getActor(log));
        entry.setItem(getItem(log));
        return entry;
    }

    private CombatLogEntryEntity buildDamageDone(String log, MatchEntity match) {
        //[00:15:21.096] npc_dota_hero_rubick hits npc_dota_hero_earthshaker with dota_unknown for 30 damage (606->576)
        CombatLogEntryEntity entry = new CombatLogEntryEntity();
        entry.setMatch(match);
        entry.setTimestamp(getTimestamp(log));
        entry.setType(CombatLogEntryEntity.Type.DAMAGE_DONE);
        entry.setActor(getActor(log));
        entry.setTarget(getTarget(log));
        entry.setDamage(getDamage(log));
        return entry;
    }

    private CombatLogEntryEntity buildHeroKilled(String log, MatchEntity match) {
        //[00:15:08.266] npc_dota_lycan_wolf1 is killed by npc_dota_lycan_wolf1
        CombatLogEntryEntity entry = new CombatLogEntryEntity();
        entry.setMatch(match);
        entry.setTimestamp(getTimestamp(log));
        entry.setType(CombatLogEntryEntity.Type.HERO_KILLED);
        entry.setActor(getActorKiller(log));
        entry.setTarget(getActor(log));
        return entry;
    }

    private CombatLogEntryEntity buildSpellCastLog(String log, MatchEntity match) {
        //[00:15:24.662] npc_dota_hero_grimstroke casts ability grimstroke_dark_artistry (lvl 1) on dota_unknown
        CombatLogEntryEntity entry = new CombatLogEntryEntity();
        entry.setMatch(match);
        entry.setTimestamp(getTimestamp(log));
        entry.setType(CombatLogEntryEntity.Type.SPELL_CAST);
        entry.setActor(getActor(log));
        entry.setAbility(getAbility(log));
        entry.setAbilityLevel(getLevel(log));
        return entry;
    }

    private CombatLogEntryEntity.Type getType(String log) {
        if (log.contains("kill") && log.contains(ACTOR_PREFIX) && log.contains(ACTOR_KILLER_PREFIX)) return CombatLogEntryEntity.Type.HERO_KILLED;
        if (log.contains("buy")) return CombatLogEntryEntity.Type.ITEM_PURCHASED;
        if (log.contains("cast")) return CombatLogEntryEntity.Type.SPELL_CAST;
        if (log.contains("hit")) return CombatLogEntryEntity.Type.DAMAGE_DONE;
        return null;
    }
}
