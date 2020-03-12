package it.mickeyhouse.replychallenge;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class Developer extends Person {
    private Set<String> skills;

}
