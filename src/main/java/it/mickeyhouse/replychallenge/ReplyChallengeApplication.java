package it.mickeyhouse.replychallenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
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

    @Override
    public void run(String... args) throws Exception {
        findAndSolveRandom("./src/main/resources/a_solar.txt","a");
        findAndSolveRandom("./src/main/resources/b_dream.txt","b");
        findAndSolveRandom("./src/main/resources/c_soup.txt","c");
        findAndSolveRandom("./src/main/resources/d_maelstrom.txt","d");
        findAndSolveRandom("./src/main/resources/e_igloos.txt","e");
        findAndSolveRandom("./src/main/resources/f_glitch.txt","f");

    }

}
