package it.mickeyhouse.replychallenge;

import lombok.Data;

import java.util.HashSet;

@Data
public class Person implements Comparable<Person> {
    private String society;
    private int bonus;
    private int orderFile;

    private int xPosition=-1;
    private int yPosition=-1;
    private boolean placed=false;

    @Override
    public int compareTo(Person o) {
        return orderFile - o.orderFile;
    }

    public int getWP(Person p){

    }

    public int getBP(Person p){

    }
}
