package gg.bayes.challenge.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Value
@AllArgsConstructor
public class HeroDamage {
    String target;
    @JsonProperty("damage_instances")
    Long damageInstances;
    @JsonProperty("total_damage")
    Long totalDamage;
}
