package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Primes {
    private static final Logger log = LoggerFactory.getLogger(Primes.class);

    public static void main(String[] args) {
        for (int i = 2; i <= 100; i++) {
            if (isPrime(i)) {
                log.info("{}", i);
            }
        }

        PrimeResult result = new PrimeResult(97, true);
        String json = JsonUtil.toJson(result);
        log.info("JSON: {}", json);

        PrimeResult restored =  JsonUtil.fromJson(json, PrimeResult.class);
        log.info("Restored: number={}, prime={}", restored.getNumber(), restored.isPrime());
    }

    public static boolean isPrime(int n) {
        if (n < 2) {
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