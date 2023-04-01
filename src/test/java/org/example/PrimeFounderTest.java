package org.example;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.Assert.*;

public class PrimeFounderTest {


    private static final int numThreads = 10;

    @Test
    public void testPrimeSearch() {
        int[] expectedLastPrimes = {541, 3571, 7919, 104729};//ожидаемые значения последнего числа
        int[] expectedSizes = {100, 500, 1000, 10000}; // max кол-во чисел к выводу
        for (int i = 0; i < expectedLastPrimes.length; i++) {
            int expectedLastPrime = expectedLastPrimes[i];
            int expectedSize = expectedSizes[i];
            ArrayBlockingQueue<Integer> primes;
            primes = PrimeFounder.foundPrime(expectedSize, numThreads);
            // Получаем последнее простое число
            Integer[] primeArray = primes.toArray(new Integer[0]);
            int lastPrime = primeArray[primeArray.length - 1];
            // Проверяем, что количество простых чисел равно ожидаемому
            assertEquals(expectedSize, primes.size());
            // Проверяем, что последнее простое число равно ожидаемому
            assertEquals(expectedLastPrime, lastPrime);
            List<Integer> sortedPrimes = new ArrayList<>(primes);
            Collections.sort(sortedPrimes);
            for (int j = 0; j < expectedSize; j++) {
                System.out.print(sortedPrimes.get(j) + " ");
            }
            System.out.println();
        }
    }
}