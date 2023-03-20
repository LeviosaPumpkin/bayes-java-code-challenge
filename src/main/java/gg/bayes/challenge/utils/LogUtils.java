package gg.bayes.challenge.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class LogUtils {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    public final static String ACTOR_PREFIX = "] npc_dota_hero_";
    private final static int ACTOR_PREFIX_LENGTH = ACTOR_PREFIX.length();
    public final static String ACTOR_KILLER_PREFIX = " by npc_dota_hero_";
    private final static int ACTOR_KILLER_PREFIX_LENGTH = ACTOR_KILLER_PREFIX.length();
    private final static String TARGET_PREFIX = " hits npc_dota_";
    private final static int TARGET_PREFIX_LENGTH = TARGET_PREFIX.length();
    private final static String ITEM_PREFIX = " item_";
    private final static int ITEM_PREFIX_LENGTH = ITEM_PREFIX.length();
    private final static String DAMAGE_PREFIX = " for ";
    private final static int DAMAGE_PREFIX_LENGTH = DAMAGE_PREFIX.length();
    private final static String CAST_PREFIX = " (lvl ";
    private final static int CAST_PREFIX_LENGTH = CAST_PREFIX.length();
    private final static String ABILITY_PREFIX = " ability ";
    private final static int ABILITY_PREFIX_LENGTH = ABILITY_PREFIX.length();

    public static long getTimestamp(String log) {
        String timeStr = log.substring(0, log.indexOf("]"));
        LocalTime time = LocalTime.from(formatter.parse(timeStr));
        return time.toEpochSecond(LocalDate.EPOCH, ZoneOffset.MIN);
    }

    public static String getActor(String log) {
        return log.substring(log.indexOf(ACTOR_PREFIX) + ACTOR_PREFIX_LENGTH,
                log.indexOf(" ", log.indexOf(ACTOR_PREFIX) + ACTOR_PREFIX_LENGTH));
    }

    public static String getActorKiller(String log) {
        return log.substring(log.indexOf(ACTOR_KILLER_PREFIX) + ACTOR_KILLER_PREFIX_LENGTH);
    }

    public static String getTarget(String log) {
        return log.substring(log.indexOf(TARGET_PREFIX) + TARGET_PREFIX_LENGTH,
                log.indexOf(" ", log.indexOf(TARGET_PREFIX) + TARGET_PREFIX_LENGTH));
    }

    public static String getItem(String log) {
        return log.substring(log.indexOf(ITEM_PREFIX) + ITEM_PREFIX_LENGTH);
    }

    public static String getAbility(String log) {
        return log.substring(log.indexOf(ABILITY_PREFIX) + ABILITY_PREFIX_LENGTH,
                log.indexOf(" ", log.indexOf(ABILITY_PREFIX) + ABILITY_PREFIX_LENGTH));
    }

    public static Integer getDamage(String log) {
        return Integer.parseInt(log.substring(log.indexOf(DAMAGE_PREFIX) + DAMAGE_PREFIX_LENGTH,
                log.indexOf(" ", log.indexOf(DAMAGE_PREFIX) + DAMAGE_PREFIX_LENGTH)));
    }

    public static Integer getLevel(String log) {
        return Integer.parseInt(log.substring(log.indexOf(CAST_PREFIX) + CAST_PREFIX_LENGTH,
                log.indexOf(")")));
    }
}
