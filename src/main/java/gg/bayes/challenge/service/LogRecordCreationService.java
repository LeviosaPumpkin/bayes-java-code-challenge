package gg.bayes.challenge.service;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;

public interface LogRecordCreationService {
    CombatLogEntryEntity buildCombatLogEntry(String log, MatchEntity match);
}
