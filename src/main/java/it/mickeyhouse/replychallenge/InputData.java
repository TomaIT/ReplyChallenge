package it.mickeyhouse.replychallenge;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.TreeSet;

@Data
@NoArgsConstructor
public class InputData {
    private Place[][] floor;
    private TreeSet<Person> persons=new TreeSet<>();


    public InputData(String fileName) throws IOException {
        this.loadDataFromFile(fileName);
    }

    public void loadDataFromFile(String fileName) throws IOException {
        //TODO
        File fileDir = new File(fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), StandardCharsets.UTF_8));
        String str;
        str = in.readLine();
        int row=Integer.parseInt(str.split(" ")[1]);
        int col=Integer.parseInt(str.split(" ")[0]);
        floor=new int[row][col];
        for(int i=0;i<row;i++){
            int j=0;
            str = in.readLine();
            for(char c : str.toCharArray()){
                switch (c){
                    case '#':
                        floor[i][j]=2;
                        break;
                    case '_':
                        floor[i][j]=0;
                        break;
                    case 'M':
                        floor[i][j]=1;
                        break;
                    default:
                        throw new RuntimeException("1");
                }
                j++;
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
        }

        int nM=Integer.parseInt(in.readLine());
        for(int i=0;i<nM;i++){
            Manager d=new Manager();
            d.setOrderFile(persons.size());
            String[] s=in.readLine().split(" ");
            d.setSociety(s[0]);
            d.setBonus(Integer.parseInt(s[1]));
            persons.add(d);
        }
        in.close();
    }

    public void findRandomSolution(){

    }
}
