package org.example;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        // определяем количество доступных ядер процессора
        int numThreads = Runtime.getRuntime().availableProcessors();
        // создаем исполнительный сервис с фиксированным количеством потоков
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        // создаем потокобезопасную очередь для хранения простых чисел
        ConcurrentLinkedQueue<Integer> primes = new ConcurrentLinkedQueue<>();
        // используем AtomicInteger для потокобезопасного подсчета количества найденных простых чисел
        AtomicInteger primeCount = new AtomicInteger(0);
        int currentNumber = 0;

        // генерация 100 простых чисел
        while (primeCount.get() < 100) {
            currentNumber++;
            // создаем новую задачу и добавляем ее в очередь исполнения
            executor.execute(new PrimeChecker(currentNumber, primes, primeCount));
        }

        // завершаем работу исполнительного сервиса
        executor.shutdown();

        try {
            // ожидание завершения выполнения всех задач
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // выводим первые 100 найденных простых чисел
        System.out.println("Первые 100 простых чисел:");
        for (int i = 0; i < 100; i++) {
            System.out.print(primes.poll() + " ");
        }
    }


}
