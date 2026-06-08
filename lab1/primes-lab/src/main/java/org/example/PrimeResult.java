package org.example;

public class PrimeResult {
    private int number;
    private boolean prime;

    public PrimeResult() {
    }

    public PrimeResult(int number, boolean prime) {
        this.number = number;
        this.prime = prime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isPrime() {
        return prime;
    }

    public void setPrime(boolean prime) {
        this.prime = prime;
    }
}