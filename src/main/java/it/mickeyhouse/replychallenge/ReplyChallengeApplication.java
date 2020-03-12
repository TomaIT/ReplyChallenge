package it.mickeyhouse.replychallenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Random;

@SpringBootApplication
public class ReplyChallengeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReplyChallengeApplication.class, args);
    }

    private void findAndSolveRandom(String filename,String out) throws IOException {
        InputData a=new InputData(filename);
        a.findRandomSolution();
        Solution s = new Solution(a.getPersons().toArray(new Person[0]),a.getFloor(),
                a.getFloor().length,a.getFloor()[0].length);
        s.saveInFile("./src/main/resources/"+out+".txt");
        System.out.println(s.getScore());
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
