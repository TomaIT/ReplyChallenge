package it.mickeyhouse.replychallenge;

import lombok.Data;

@Data
public class Place{
    private Person person = null;
    private char type;
    private int x;
    private int y;
    private int nearPersons = 0;

    public Place(char type,int x,int y){
        this.type=type;
        this.x=x;
        this.y=y;
    }

    public void incNP(){
        nearPersons++;
    }
}
