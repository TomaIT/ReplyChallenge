package it.mickeyhouse.replychallenge;

import java.util.*;

public class RandomPriorityQueue<E> extends PriorityQueue { // 25.034.522
    private static final Random random = new Random();
    private static final int maxN = 32;
    private static final Object[] objects = new Object[maxN];

    public RandomPriorityQueue(int initialCapacity,
                               Comparator<? super E> comparator){
        super(initialCapacity,comparator);
    }

    /*
    To small n value
     */
    public Object poll(int N){
        if( N <= 1 || N > maxN )return poll();

        int r;
        if( N <= size() ) r = random.nextInt(N);
        else r = random.nextInt(size());

        for(int i=0;i<r;i++) objects[i]=poll();

        Object ret=poll();

        for(int i=0;i<r;i++)add(objects[i]);

        return ret;
    }

}
