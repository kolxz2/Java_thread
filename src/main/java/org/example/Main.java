package org.example;


class Main {

    public static void main(String[] args) {
        PrimeFounder primeFounder = new PrimeFounder();
        System.out.println("Last num is: " + primeFounder.startFoundPrime(100));
        System.out.println("Last num is: " + primeFounder.startFoundPrime(1000));
        System.out.println("Last num is: " + primeFounder.startFoundPrime(10000));
        System.out.println("Last num is: " + primeFounder.startFoundPrime(100, 10));
        System.out.println("Last num is: " + primeFounder.startFoundPrime(1000, 10));
        System.out.println("Last num is: " + primeFounder.startFoundPrime(10000, 10));
    }
}
