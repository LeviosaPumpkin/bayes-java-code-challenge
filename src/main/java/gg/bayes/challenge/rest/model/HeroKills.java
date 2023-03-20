package gg.bayes.challenge.rest.model;

import lombok.*;

@Value
@AllArgsConstructor
public class HeroKills {
    String hero;
    Long kills;
}
