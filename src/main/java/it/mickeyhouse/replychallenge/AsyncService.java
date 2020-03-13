package it.mickeyhouse.replychallenge;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

@Service
public class AsyncService {

    public void findBestSwapping(InputData a,long start,long MAXTIME){
        long totTime = 0;
        int c=0;
        LinkedList<Person> developers = new LinkedList<>(a.getDevelopers());
        LinkedList<Person> managers = new LinkedList<>(a.getManagers());
        while (true) {
            long s=System.currentTimeMillis();
            Collections.shuffle(developers);
            Collections.shuffle(managers);
            for (int i = 0; i < developers.size() - 1; i++) {
                Person x = developers.get(i);
                i++;
                Person y = developers.get(i);
                a.checkScoreSwap(x, y);
            }
            for (int i = 0; i < managers.size() - 1; i++) {
                Person x = managers.get(i);
                i++;
                Person y = managers.get(i);
                a.checkScoreSwap(x, y);
            }
            totTime+=System.currentTimeMillis()-s;c++;
            if((System.currentTimeMillis()-start)+(totTime/c)>MAXTIME)break;
        }
    }

    @Async("threadPoolTaskExecutor")
    public void findAndSolve(String filename,int nFile,long MAXTIME) throws IOException {
        long start = System.currentTimeMillis();
        InputData a = new InputData(filename);
        a.initPQueue();
        a.initPlacePairs();
        a.findSolution();
        a.finalFillPlaces();

        Solution s1 = new Solution(a.getPersons().toArray(new Person[0]), a.getFloor(),
                a.getFloor().length, a.getFloor()[0].length);
        long t1 = s1.getScore();

        System.out.println(nFile+": "+t1);

        findBestSwapping(a,start,MAXTIME);

        Solution s = new Solution(a.getPersons().toArray(new Person[0]), a.getFloor(),
                a.getFloor().length, a.getFloor()[0].length);
        long t = s.getScore();
        System.out.println(nFile+": "+t);

        s.saveInFile("./src/main/resources/solutions/"+ nFile+"_"+t+ ".txt");
    }
}
