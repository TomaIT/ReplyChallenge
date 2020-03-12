package it.mickeyhouse.replychallenge;

import lombok.Data;

@Data
public class Person implements Comparable<Person> {
    private String society;
    private int bonus;
    private int orderFile;

    private int xPosition;
    private int yPosition;
    private boolean placed;

    @Override
    public int compareTo(Person o) {
        return orderFile - o.orderFile;
    }
}
