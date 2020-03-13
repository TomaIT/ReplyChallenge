package it.mickeyhouse.replychallenge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemPQ {
    int score;

    public ItemPQ(int score){
        this.score = score;
    }
}

@EqualsAndHashCode(callSuper = true)
@Data
class PeoplePair extends ItemPQ{
    Person a;
    Person b;

    public PeoplePair(Person a,Person b){
        super(a.getBP(b)+a.getWP(b));
        this.a=a;
        this.b=b;
    }
}

@EqualsAndHashCode(callSuper = true)
@Data
class PeoplePlace extends ItemPQ{
    Person person;
    Place place;

    public PeoplePlace(Place[][] floor,Person a,Place place){
        super();
        int i = place.getY();
        int j = place.getX();
        int score = 0;

        if( i+1 < floor.length) score += a.getBP(floor[i+1][j].getPerson()) + a.getWP(floor[i+1][j].getPerson());
        if( j+1 < floor[i].length) score += a.getBP(floor[i][j+1].getPerson()) + a.getWP(floor[i][j+1].getPerson());
        if( i-1 >= 0 ) score += a.getBP(floor[i-1][j].getPerson()) + a.getWP(floor[i-1][j].getPerson());
        if( j-1 >= 0 ) score += a.getBP(floor[i][j-1].getPerson()) + a.getWP(floor[i][j-1].getPerson());

        this.setScore(score);

        this.person = a;
        this.place = place;
    }
}