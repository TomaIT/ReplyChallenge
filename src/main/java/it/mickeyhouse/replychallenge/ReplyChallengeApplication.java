package it.mickeyhouse.replychallenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class ReplyChallengeApplication implements CommandLineRunner {
    int n=3;

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
    private void mamma(String filename,String out) throws IOException {
       InputData a = new InputData(filename);
       a.calcEdges();
       //while (!a.getPersonQueue().isEmpty())System.out.println(a.getPersonQueue().poll());
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

        int i=0;
        for(String s:x){
            System.out.println(i);
            mamma(s,String.valueOf(i));
            i++;

        }
    }

}
