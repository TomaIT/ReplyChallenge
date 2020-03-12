package it.mickeyhouse.replychallenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class ReplyChallengeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReplyChallengeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        InputData input=new InputData("./src/main/resources/a_solar.txt");
        input.findRandomSolution();
        System.out.println(input);
    }

}
