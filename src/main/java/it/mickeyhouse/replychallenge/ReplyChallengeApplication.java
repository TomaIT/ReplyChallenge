package it.mickeyhouse.replychallenge;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.PriorityQueue;

@SpringBootApplication
public class ReplyChallengeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReplyChallengeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
