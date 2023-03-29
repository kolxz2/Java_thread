package org.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFounder {



    public void startFoundPrime(int numberOfNumbers,  int numThreads) {
        foundPrime ( numberOfNumbers,   numThreads);
    }

    public void startFoundPrime(int numberOfNumbers) {
       /// this.numberOfNumbers = numberOfNumbers;
        // определяем количество доступных ядер процессора
        int numThreads = Runtime.getRuntime().availableProcessors();
        foundPrime ( numberOfNumbers,   numThreads);
    }

    private void foundPrime (int numberOfNumbers,  int numThreads){
        long m = System.currentTimeMillis();
        // создаем потокобезопасную очередь для хранения простых чисел
        ConcurrentLinkedQueue <Integer> primes = new ConcurrentLinkedQueue <>();
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
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // выводим первые 100 найденных простых чисел
       // System.out.println("Первые 100 простых чисел:");
        for (int i = 0; i < primeCount.get(); i++) {
            System.out.print(primes.poll() + " ");
        }
        System.out.println();
      //  System.out.println(" primes.size()" + primes.toArray().length);
        System.out.println(" Length of queue: " + primeCount.get() + " Thread num " + numThreads);
      //  System.out.println(" primeMax.get()" + primeMax.get());
        System.out.println("Time working "+(double) (System.currentTimeMillis() - m));
        System.out.println("------------------------------------------");
    }



}
