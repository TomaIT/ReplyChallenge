package it.mickeyhouse.replychallenge;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Developer extends Person {
    private Set<String> skills=new HashSet<>();

    public void addSkill(String skill){skills.add(skill);}

}
