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
    private boolean marker=false;

    @Override
    public int compareTo(Person o) {
        return orderFile - o.orderFile;
    }

    public char getType(){
        if(this instanceof Developer)return '_';
        if(this instanceof Manager)return 'M';
        return 'X';
    }

    public void places(Coord coord){
        placed = true;
        xPosition = coord.getX();
        yPosition = coord.getY();
    }

    public int getWP(Person p){
        if(this instanceof Manager || p instanceof Manager)return 0;

        Developer a = (Developer) this;
        Developer b = (Developer) p;

        HashSet<String> c = new HashSet<>(a.getSkills());
        c.addAll(b.getSkills());
        int common = a.getSkills().size() + b.getSkills().size() - c.size();

        return common * (c.size()-common);
    }

    public int getBP(Person p){
        if(!p.society.equals(this.society))return 0;
        return p.bonus*this.bonus;
    }
}
