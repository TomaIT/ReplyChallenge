package it.mickeyhouse.replychallenge;

import javafx.print.Collation;
import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Solution {

    private Person[] people;
    private Place[][] places;

    private int widht;
    private int height;


    public Solution(Person[] people, Place[][] places, int width, int height) {
        this.people = people;
        this.places = places;
        this.widht = width;
        this.height = height;
    }

    public void saveInFile(String fileName) throws IOException {
        File f = new File(fileName);
        FileUtils.writeStringToFile(f, this.toString(), StandardCharsets.UTF_8);

    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Person p: people) {
            if (p.isPlaced())
                stringBuilder.append(p.getXPosition()).append(" ").append(p.getYPosition()).append("\n");
            else
                stringBuilder.append("X").append("\n");
        }

        return stringBuilder.toString();
    }

    public long getScore() {
        long score = 0;

        for (int i = 0; i < widht; i++) {

            for (int j = 0; j < height; j++) {
                Person p1 = places[i][j].getPerson();
                Person p2;
                if (p1 == null)
                    continue;

                if (i+1 < widht) {
                    p2 = places[i+1][j].getPerson();
                    if (p2 != null)
                        score += p1.getWP(p2) + p1.getBP(p2);
                }
                if (j+1 < height) {
                    p2 = places[i][j+1].getPerson();
                    if (p2 != null)
                        score += p1.getWP(p2) + p1.getBP(p2);
                }
            }
        }
        return score;
    }

    public boolean isNear(int x1, int y1, int x2, int y2) {
        int tot = 0;
        int distance = Math.abs(x1 - x2);
        if (distance > 1)
            return false;
        tot += distance;
        distance = Math.abs(y1 - y2);
        if (distance > 1)
            return false;
        tot += distance;

        if(tot == 1)
            return true;
        return false;
    }

}
