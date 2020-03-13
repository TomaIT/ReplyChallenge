package it.mickeyhouse.replychallenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class ReplyChallengeApplication implements CommandLineRunner {
    int n=1;

    public static void main(String[] args) {
        SpringApplication.run(ReplyChallengeApplication.class, args);
    }

    private long findAndSolve(String filename,int nFile) throws IOException {
        long bestScore = -1;
        Solution best = null;
        for(int i=0;i<n;i++) {
            InputData a = new InputData(filename);
            a.initPQueue();
            a.initPlacePairs();
            a.findSolution();
            a.finalFillPlaces();
            a.statistics();
            Solution s = new Solution(a.getPersons().toArray(new Person[0]), a.getFloor(),
                    a.getFloor().length, a.getFloor()[0].length);
            long t = s.getScore();
            if (t > bestScore) {
                bestScore = t;
                best = s;
            }
        }

        best.saveInFile("./src/main/resources/solutions/"+ nFile+"_"+bestScore+ ".txt");
        return bestScore;
    }

    @Override
    public void run(String... args) throws Exception { // 22.789.689 180s
        Map<Integer,String> files=new HashMap<>();
        files.put(0,"./src/main/resources/a_solar.txt");
        files.put(1,"./src/main/resources/b_dream.txt");
        files.put(2,"./src/main/resources/c_soup.txt");
        files.put(3,"./src/main/resources/d_maelstrom.txt");
        files.put(4,"./src/main/resources/e_igloos.txt");
        files.put(5,"./src/main/resources/f_glitch.txt");

        long start = System.currentTimeMillis();
        AtomicLong tot= new AtomicLong();
        files.entrySet().parallelStream().forEach(e->{
            try {
                long t = findAndSolve(e.getValue(), e.getKey());
                System.out.println(e.getKey() + ": " + t);
                tot.addAndGet(t);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        System.out.println("Score: "+tot.get());
        System.out.println("Total Time: "+(System.currentTimeMillis()-start)+" ms");

    }

}
