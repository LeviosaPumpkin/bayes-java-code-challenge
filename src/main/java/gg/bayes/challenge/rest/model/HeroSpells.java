package gg.bayes.challenge.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@AllArgsConstructor
public class HeroSpells {
    String spell;
    Long casts;
}
