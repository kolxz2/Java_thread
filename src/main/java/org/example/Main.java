package org.example;


class Main {

    public static void main(String[] args) {
        PrimeFounder primeFounder = new PrimeFounder();
        long m = System.currentTimeMillis();
        primeFounder.startFoundPrime(100);
        System.out.println("Time working "+(double) (System.currentTimeMillis() - m));
    }
}
