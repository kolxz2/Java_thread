package org.example;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class PrimeChecker implements Runnable {

    private final int number;
    private final ArrayBlockingQueue<Integer> primes;
    private final AtomicInteger primeCount;

    public PrimeChecker(int number, ArrayBlockingQueue<Integer> primes, AtomicInteger primeCount) {
        this.number = number;
        this.primes = primes;
        this.primeCount = primeCount;
    }

    @Override
    public void run() {
        // если число простое то добаляем его в очередь простых
        // чисел и инкрементируем счётчик чисел
        try{
            if (isPrime(number)) {
                primes.add(number);
                primeCount.incrementAndGet();
            }
        } catch (Exception e){
            Thread.currentThread().interrupt();
        }



    }

    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}