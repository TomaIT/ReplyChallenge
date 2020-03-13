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

    public int getNearPersons(){
        if(a.getNearPersons()>=0 && b.getNearPersons()>=0)
            return a.getNearPersons()+b.getNearPersons();
        if(a.getNearPersons()>=0)return a.getNearPersons();
        if(b.getNearPersons()>=0)return b.getNearPersons();
        return Integer.MAX_VALUE;
    }
}

enum PlacePairType {
    DD,
    MM,
    DM,
    MD
}
