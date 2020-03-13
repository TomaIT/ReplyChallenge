package it.mickeyhouse.replychallenge;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class PlacePair {
    Place a;
    Place b;
    PlacePairType type;

    public PlacePair(Place a,Place b, PlacePairType placePairType){
        this.a=a;
        this.b=b;
        this.type=placePairType;
    }
    
}

enum PlacePairType {
    DD,
    MM,
    DM,
    MD
}
