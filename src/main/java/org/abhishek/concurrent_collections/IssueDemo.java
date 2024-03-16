package org.abhishek.concurrent_collections;

import java.util.*;

public class IssueDemo {

    public static void main(String[] args) throws InterruptedException {
        // This uses the intrinsic lock of the object
        List<Integer> list = Collections.synchronizedList(new LinkedList<>());

        Thread t1 = new Thread(()-> {
            for(int i = 0 ; i < 1000; i++) {
                list.add(i);
            }
        });
        Thread t2 = new Thread(()-> {
            for(int i = 0 ; i < 1000; i++) {
                list.add(i);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        // The size is not similar due to multiple threads calling the same list
        // for correct size we must access the list in synchronized way.
        System.out.println("Size of list :"+ list.size());
    }
}
