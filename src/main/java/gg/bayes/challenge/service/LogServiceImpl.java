package gg.bayes.challenge.service;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.CombatLogEntryEntity.Type;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.persistence.repository.CombatLogEntryRepository;
import gg.bayes.challenge.persistence.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static gg.bayes.challenge.utils.LogUtils.*;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private CombatLogEntryRepository repository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private LogRecordCreationService logRecordCreationService;
    @Override
    public Long writeLog(String logString) {
        String [] log = logString.split("\\[");
        final MatchEntity match = new MatchEntity();
        Set<CombatLogEntryEntity> set = Arrays.stream(log)
                .map(l -> logRecordCreationService.buildCombatLogEntry(l, match))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        match.setCombatLogEntries(set);
        MatchEntity matchEntity = matchRepository.save(match);
        repository.saveAll(set);
        return matchEntity.getId();
    }
}
