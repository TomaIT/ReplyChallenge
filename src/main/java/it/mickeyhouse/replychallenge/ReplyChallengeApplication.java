package it.mickeyhouse.replychallenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class ReplyChallengeApplication implements CommandLineRunner {
    int n=1;

    public static void main(String[] args) {
        SpringApplication.run(ReplyChallengeApplication.class, args);
    }

    private void findAndSolveRandom(String filename,String out) throws IOException {
        long bestScore = -1;
        Solution best = null;
        for(int i=0;i<n;i++) {
            InputData a = new InputData(filename);
            a.findRandomSolution();
            Solution s = new Solution(a.getPersons().toArray(new Person[0]), a.getFloor(),
                    a.getFloor().length, a.getFloor()[0].length);
            long t=s.getScore();
            if(t>bestScore){
                bestScore = t;
                best = s;
            }
        }
        System.out.println(bestScore);
        best.saveInFile("./src/main/resources/" + out + ".txt");
    }

    private long findAndSolve(String filename,String out) throws IOException {
        long bestScore = -1;
        Solution best = null;
        for(int i=0;i<n;i++) {
            InputData a = new InputData(filename);
            a.calcEdges();
            a.findSolution();
            Solution s = new Solution(a.getPersons().toArray(new Person[0]), a.getFloor(),
                    a.getFloor().length, a.getFloor()[0].length);
            long t = s.getScore();
            if (t > bestScore) {
                bestScore = t;
                best = s;
            }
        }
        best.saveInFile("./src/main/resources/solutions/"+ out+"_"+bestScore+ ".txt");
        return bestScore;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> x=new ArrayList<>();
        x.add("./src/main/resources/a_solar.txt");
        x.add("./src/main/resources/b_dream.txt");
        x.add("./src/main/resources/c_soup.txt");
        x.add("./src/main/resources/d_maelstrom.txt");
        x.add("./src/main/resources/e_igloos.txt");
        x.add("./src/main/resources/f_glitch.txt");

        long start = System.currentTimeMillis();
        int i=0;
        long tot=0;
        for(String s:x){
            long t=findAndSolve(s,String.valueOf(i));
            System.out.println(i+": "+t);
            i++;
            tot+=t;
        }
        System.out.println("Score: "+tot);
        System.out.println("Total Time: "+(System.currentTimeMillis()-start)+" ms");

    }

}
