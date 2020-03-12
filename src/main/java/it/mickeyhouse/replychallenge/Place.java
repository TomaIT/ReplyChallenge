package it.mickeyhouse.replychallenge;

import lombok.Data;

@Data
public class Place {
    private Person person = null;


    private char type;

    public Place(char type){
        this.type=type;
    }

}
