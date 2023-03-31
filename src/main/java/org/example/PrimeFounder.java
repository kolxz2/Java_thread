package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFounder {

    public int startFoundPrime(int numberOfNumbers,  int numThreads) {
        ConcurrentSkipListSet <Integer> primes = foundPrime ( numberOfNumbers,   numThreads);
        return printPrimeNumbers( numberOfNumbers,  primes);
    }

    public int startFoundPrime(int numberOfNumbers) {
        // определяем количество доступных ядер процессора
        int numThreads = Runtime.getRuntime().availableProcessors();
        ConcurrentSkipListSet <Integer> primes = foundPrime ( numberOfNumbers,   numThreads);
        //printPrimeNumbers( numberOfNumbers,  primes);
        return printPrimeNumbers( numberOfNumbers,  primes);
    }

    private int printPrimeNumbers(int numberOfNumbers, ConcurrentSkipListSet <Integer> primes){
        int lastNum = 0;
        List<Integer> sortedPrimes = new ArrayList<>(primes);
        for (int i = 0; i < numberOfNumbers; i++) {
            lastNum = sortedPrimes.get(i);
            System.out.print(lastNum + " ");
        }
        System.out.println();
        return lastNum;
    }

    private ConcurrentSkipListSet <Integer> foundPrime(int numberOfNumbers,  int numThreads){
        long m = System.currentTimeMillis();
        // создаем потокобезопасную очередь для хранения простых чисел
        ConcurrentSkipListSet <Integer> primes = new ConcurrentSkipListSet <>();
        // используем AtomicInteger для потокобезопасного подсчета количества найденных простых чисел
         AtomicInteger primeCount = new AtomicInteger(0);
        AtomicInteger primeMax = new AtomicInteger(numberOfNumbers);
         int currentNumber = 0;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        while (primeCount.get() < primeMax.get()) {
            currentNumber++;
            // создаем новую задачу и добавляем ее в очередь исполнения
            executor.execute(new PrimeChecker(currentNumber, primes, primeCount));
            if(primeCount.get() == primeMax.get()){
                // завершаем работу исполнительного сервиса
                executor.shutdownNow();
            }
        }

        try {
            // ожидание завершения выполнения всех задач
            if (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)){
                System.out.println("Failed to terminate executor");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Time working "+(double) (System.currentTimeMillis() - m));
        return primes;
    }
}
