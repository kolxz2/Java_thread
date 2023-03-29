package org.example;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Main {

    public static void main(String[] args) {
        PrimeFounder primeFounder = new PrimeFounder();
        primeFounder.startFoundPrime(100);
        primeFounder.startFoundPrime(1000);
        primeFounder.startFoundPrime(10000);
        primeFounder.startFoundPrime(100, 10);
        primeFounder.startFoundPrime(1000, 10);
        primeFounder.startFoundPrime(10000, 10);


    }
}
