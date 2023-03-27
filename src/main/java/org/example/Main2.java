package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class Main2 {
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int LENGTH_ARRAY_SIZE = 1000;

    public static void main(String[] args) {
        long start = System.nanoTime();
        PrimeNumberFinder primeNumberFinder = new PrimeNumberFinder(LENGTH_ARRAY_SIZE, MAX_THREADS );
        primeNumberFinder.printArray();
        System.out.println(System.nanoTime() - start);
    }
}

class PrimeNumberFinder{

    private static ArrayList<Integer> numbsArray;
    private static ArrayList<Thread> threadArrayList;
    public PrimeNumberFinder(int initialCapacity, int countOfTread) {

        numbsArray = new ArrayList<Integer>(initialCapacity);
        threadArrayList = new ArrayList<Thread>(countOfTread);
        int beginningOfRange = 0;
        int segmentSize = 50;
        while (numbsArray.size() < initialCapacity){

            for (int i = 0; i < countOfTread; i++){
                int start = i * segmentSize + 2;

                int end = Math.min(start + segmentSize - 1, beginningOfRange + segmentSize);
                System.out.println(" end " + end + " start " + start);
                beginningOfRange +=segmentSize;
                Thread threadItem = new PrimeFinderThread(start, end);
                threadItem.start();
                threadArrayList.add(threadItem);
            }
/*            for (Thread thread : threadArrayList) {
                thread.start();
            }*/

            for (Thread thread : threadArrayList) {

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
           // Thread.yield();
        }
        Arrays.sort(new ArrayList[]{numbsArray});
        Set<Integer> targetSet = new HashSet<Integer>(numbsArray);

        System.out.println(targetSet);
        System.out.println(targetSet.size());
        System.out.println(initialCapacity);
    }

    public void printArray(){

    }

    private class PrimeFinderThread extends Thread{

        private int startSegment;
        private int endSegment;

        public PrimeFinderThread(int startSegment, int endSegment) {
            this.startSegment = startSegment;
            this.endSegment = endSegment;
        }

        @Override
        public void run() {

            for(int i = startSegment; i <= endSegment; i++){
                if(isPrime(i)){
                    // todo добавлять чилсо в конечный массив
                  //  synchronized (numbsArray) {
                        numbsArray.add(i);
                //    }
                   // numbsArray.add(i);
                }
            }

        }

        private boolean isPrime(int num){
            if (num <= 1) {
                return false;
            }
            for (int i = 2; i <= Math.sqrt(num); i++) {
                if (num % i == 0) {
                    return false;
                }
            }
            return true;
        }

    }
}


