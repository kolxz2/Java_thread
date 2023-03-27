package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        long start = System.nanoTime();
        Finder primeNumberFinder = new Finder(214748364, 20);
        try {
            System.out.println(Arrays.toString(primeNumberFinder.findPrimes().toArray()));
         //   System.out.println(Arrays.toString(primeNumberFinder.findPrimes().toArray()).length());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.nanoTime() - start);
    }
}


class Finder {
    private final int maxNumber;
    private final int numThreads;
    private final boolean[] primes;

    public Finder(int maxNumber, int numThreads) {
        this.maxNumber = maxNumber;
        this.numThreads = numThreads;
        primes = new boolean[maxNumber + 1];
    }

    public List<Integer> findPrimes() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        int segmentSize = (maxNumber - 1) / numThreads + 1;
        for (int i = 0; i < numThreads; i++) {
            int start = i * segmentSize + 2;
            int end = Math.min(start + segmentSize - 1, maxNumber);
            threads.add(new PrimeFinderThread(start, end));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 2; i <= maxNumber; i++) {
            if (primes[i]) {
                result.add(i);
            }
        }
        return result;
    }

    private class PrimeFinderThread extends Thread {
        private final int start;
        private final int end;

        public PrimeFinderThread(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i <= end; i++) {
                if (isPrime(i)) {
                    primes[i] = true;
                }
            }
        }

        private boolean isPrime(int number) {
            if (number < 2) {
                return false;
            }

            for (int i = 2; i * i <= number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }

            return true;
        }
    }
}
