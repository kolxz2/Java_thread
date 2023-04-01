package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFounder {

    public void startFoundPrime(int numberOfNumbers,  int numThreads) {
        ArrayBlockingQueue <Integer> primes = foundPrime ( numberOfNumbers,   numThreads);
        printPrimeNumbers( numberOfNumbers,  primes);
    }

    public void startFoundPrime(int numberOfNumbers) {
        // определяем количество доступных ядер процессора
        int numThreads = Runtime.getRuntime().availableProcessors();
        ArrayBlockingQueue <Integer> primes = foundPrime ( numberOfNumbers,   numThreads);
        //printPrimeNumbers( numberOfNumbers,  primes);
        printPrimeNumbers( numberOfNumbers,  primes);
    }

    private void printPrimeNumbers(int numberOfNumbers, ArrayBlockingQueue <Integer> primes){
        List<Integer> sortedPrimes = new ArrayList<>(primes);
        for (int i = 0; i < numberOfNumbers; i++) {
            System.out.print(sortedPrimes.get(i) + " ");
        }
        System.out.println();

    }

    static ArrayBlockingQueue <Integer> foundPrime(int numberOfNumbers, int numThreads){

        // создаем потокобезопасную очередь для хранения простых чисел
        ArrayBlockingQueue <Integer> primes = new ArrayBlockingQueue<>(numberOfNumbers);
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
        executor.shutdownNow();

        try {
            // ожидание завершения выполнения всех задач
            if (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)){
                System.out.println("Failed to terminate executor");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Queue size "+ primes.size());
        return primes;
    }
}
