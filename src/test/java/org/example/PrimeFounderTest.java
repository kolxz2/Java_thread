package org.example;

import org.junit.Test;

import static org.junit.Assert.*;
public class PrimeFounderTest {

    @Test
    public void startFoundPrime100() {
        PrimeFounder primeFounder = new PrimeFounder();
        assertEquals(541, primeFounder.startFoundPrime(100));
    }

    @Test
    public void startFoundPrime1000() {
        PrimeFounder primeFounder = new PrimeFounder();
        assertEquals(7919, primeFounder.startFoundPrime(1000));
    }
    @Test
    public void startFoundPrime10000() {
        PrimeFounder primeFounder = new PrimeFounder();
        assertEquals(104729, primeFounder.startFoundPrime(10000));
    }
    @Test
    public void startFoundThread10Prime100() {
        PrimeFounder primeFounder = new PrimeFounder();
        assertEquals(541, primeFounder.startFoundPrime(100, 10));
    }@Test
    public void startFoundThread10Prime1000() {
        PrimeFounder primeFounder = new PrimeFounder();
        assertEquals(7919, primeFounder.startFoundPrime(1000, 10));
    }@Test
    public void startFoundThread10Prime10000() {
        PrimeFounder primeFounder = new PrimeFounder();
        assertEquals(104729, primeFounder.startFoundPrime(10000, 10));
    }


}