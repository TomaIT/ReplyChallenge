package it.mickeyhouse.replychallenge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Data
@NoArgsConstructor
public class InputData {
    private Place[][] floor;
    private TreeSet<Person> persons=new TreeSet<>();

    private LinkedList<Developer> developers=new LinkedList<>();
    private LinkedList<Manager> managers=new LinkedList<>();

    private LinkedList<Coord> devPlaces=new LinkedList<>();
    private LinkedList<Coord> managePlaces=new LinkedList<>();


    public InputData(String fileName) throws IOException {
        this.loadDataFromFile(fileName);
    }

    public void loadDataFromFile(String fileName) throws IOException {
        File fileDir = new File(fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), StandardCharsets.UTF_8));
        String str;
        str = in.readLine();
        int row=Integer.parseInt(str.split(" ")[1]);
        int col=Integer.parseInt(str.split(" ")[0]);
        floor=new Place[row][col];
        for(int i=0;i<row;i++){
            int j=0;
            str = in.readLine();
            for(char c : str.toCharArray()){
                floor[i][j]=new Place(c);
                switch (c){
                    case '#':
                        break;
                    case '_':
                        devPlaces.add(new Coord(i,j));
                        break;
                    case 'M':
                        managePlaces.add(new Coord(i,j));
                        break;
                }
                j++;
                if(j>=col)break;
            }
        }

        int nDev=Integer.parseInt(in.readLine());
        for(int i=0;i<nDev;i++){
            Developer d=new Developer();
            d.setOrderFile(persons.size());
            String[] s=in.readLine().split(" ");
            d.setSociety(s[0]);
            d.setBonus(Integer.parseInt(s[1]));
            int n=Integer.parseInt(s[2]);
            for(int j=3;j<3+n;j++){
                d.addSkill(s[j]);
            }
            persons.add(d);
            developers.add(d);
        }

        int nM=Integer.parseInt(in.readLine());
        for(int i=0;i<nM;i++){
            Manager d=new Manager();
            d.setOrderFile(persons.size());
            String[] s=in.readLine().split(" ");
            d.setSociety(s[0]);
            d.setBonus(Integer.parseInt(s[1]));
            persons.add(d);
            managers.add(d);
        }
        in.close();
    }

    public void findRandomSolution(){
        Collections.shuffle(developers);
        Collections.shuffle(managers);
        Collections.shuffle(devPlaces);
        Collections.shuffle(managePlaces);

        boolean flag = true;
        while (flag){
            flag = false;
            if(!devPlaces.isEmpty() && !developers.isEmpty()){
                Person a = developers.poll();
                Coord c = devPlaces.poll();
                a.setXPosition(c.getX());
                a.setYPosition(c.getY());
                a.setPlaced(true);
                floor[c.getX()][c.getY()].setPerson(a);
                flag = true;
            }
            if(!managePlaces.isEmpty() && !managers.isEmpty()){
                Person a = managers.poll();
                Coord c = managePlaces.poll();
                a.setXPosition(c.getX());
                a.setYPosition(c.getY());
                a.setPlaced(true);
                floor[c.getX()][c.getY()].setPerson(a);
                flag = true;
            }
        }

/*
        for(int i=0;i<floor.length;i++){
            for(int j=0;j<floor[0].length;j++){
                switch (floor[i][j].getType()){
                    case '#':
                        break;
                    case '_':
                        if(!developers.isEmpty()){
                            Person a = developers.poll();
                            a.setXPosition(j);
                            a.setYPosition(i);
                            a.setPlaced(true);
                            floor[i][j].setPerson(a);
                        }
                        break;
                    case 'M':
                        if(!managers.isEmpty()){
                            Person a = managers.poll();
                            a.setXPosition(j);
                            a.setYPosition(i);
                            a.setPlaced(true);
                            floor[i][j].setPerson(a);
                        }
                        break;
                }
            }
        }*/
    }
}

@Data
@AllArgsConstructor
class Coord{
    private int x;
    private int y;
}

