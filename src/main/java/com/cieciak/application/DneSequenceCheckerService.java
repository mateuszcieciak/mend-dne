package com.cieciak.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
@Slf4j
public class DneSequenceCheckerService {

    public boolean containsDneSequence(final int[] numbers) {
        log.info("Started validation of input array: {}", numbers);
        validateInput(numbers);
        log.info("Validation successful, started checking input array for DNE sequence: {}", numbers);
        return hasDneSequence(numbers);
    }

    private static boolean hasDneSequence(final int[] numbers) {
        int[] minimumArray = numbers.clone();
        for (int i = 1; i < minimumArray.length; i++) {
            minimumArray[i] = Math.min(minimumArray[i - 1], minimumArray[i]);
        }
        TreeSet<Integer> sortedNumbers = new TreeSet<>();
        for (int i = numbers.length - 1; i > 0; i--) {
            int currentNumber = numbers[i];
            if (sortedNumbers.lower(currentNumber) != null) {
                int lowerRightNumber = sortedNumbers.lower(currentNumber);
                int minimumLeftNumber = minimumArray[i - 1];
                if (minimumLeftNumber < lowerRightNumber) {
                    log.info("DNE sequence found in input array: {}", numbers);
                    return true;
                }
            }
            sortedNumbers.add(currentNumber);
        }
        log.info("No DNE sequence found in input array: {}", numbers);
        return false;
    }

    private void validateInput(final int[] numbers) {
        if (numbers == null || numbers.length < 3) {
            log.error("Validation failed, input array must contain at least three integer numbers");
            throw new IllegalArgumentException("Input array must contain at least three integer numbers");
        }
    }
}
