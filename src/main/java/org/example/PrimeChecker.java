package org.example;

/*import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;*/
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class PrimeChecker implements Runnable {

    private final int number;
    private final ConcurrentLinkedQueue<Integer> primes;
    private final AtomicInteger primeCount;

    public PrimeChecker(int number, ConcurrentLinkedQueue<Integer> primes, AtomicInteger primeCount) {
        this.number = number;
        this.primes = primes;
        this.primeCount = primeCount;
    }

    @Override
    public void run() {
        // если число простое то добаляем его в очередь простых
        // чисел и инкрементируем счётчик чисел
        if (isPrime(number)) {
            primes.add(number);
            primeCount.incrementAndGet();
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