package it.mickeyhouse.replychallenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class ReplyChallengeApplication implements CommandLineRunner {
    int MAXTIME = 20*60*1000;
    private final AsyncService asyncService;

    public ReplyChallengeApplication(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ReplyChallengeApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
        Map<Integer,String> files=new HashMap<>();
        //files.put(0,"./src/main/resources/a_solar.txt");
        //files.put(1,"./src/main/resources/b_dream.txt");
        files.put(2,"./src/main/resources/c_soup.txt");
        files.put(3,"./src/main/resources/d_maelstrom.txt");
        files.put(4,"./src/main/resources/e_igloos.txt");
        files.put(5,"./src/main/resources/f_glitch.txt");

        long start = System.currentTimeMillis();
        AtomicLong tot= new AtomicLong();
        files.entrySet().parallelStream().forEach(e->{
            try {
                asyncService.findAndSolve(e.getValue(), e.getKey(),MAXTIME);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        System.out.println("Score: "+tot.get());
        System.out.println("Total Time: "+(System.currentTimeMillis()-start)+" ms");

    }

}
