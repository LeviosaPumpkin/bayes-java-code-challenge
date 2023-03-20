package gg.bayes.challenge.rest.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class HeroItem {
    String item;
    Long timestamp;
}
