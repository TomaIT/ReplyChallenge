package it.mickeyhouse.replychallenge;

import lombok.Data;

@Data
public class Place {
    private Person person;

    // 0 --> DEVELOPER
    // 1 --> MANAGER
    // 2 --> WALL
    // 3 --> OCCUPATO
    private int type;

}
