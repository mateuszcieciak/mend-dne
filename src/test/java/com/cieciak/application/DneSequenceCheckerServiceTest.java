package com.cieciak.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class DneSequenceCheckerServiceTest {

    private DneSequenceCheckerService dneSequenceCheckerService;

    @BeforeEach
    void setUp() {
        dneSequenceCheckerService = new DneSequenceCheckerService();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfInputArraySizeIsLessThanThree() {
        int[] input = {1, 2};
        assertThatThrownBy(() -> dneSequenceCheckerService.containsDneSequence(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input array must contain at least three integer numbers");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfInputArrayIsEmpty() {
        int[] input = {};
        assertThatThrownBy(() -> dneSequenceCheckerService.containsDneSequence(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input array must contain at least three integer numbers");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfInputArrayIsNull() {
        int[] input = null;
        assertThatThrownBy(() -> dneSequenceCheckerService.containsDneSequence(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input array must contain at least three integer numbers");
    }

    @ParameterizedTest
    @MethodSource("testDataForDneSequence")
    void shouldCheckIfThereIsDneSequence(int[] input, boolean hasDneSequence) {
        boolean result = dneSequenceCheckerService.containsDneSequence(input);
        assertThat(result).isEqualTo(hasDneSequence);
    }

    private static Stream<Arguments> testDataForDneSequence() {
        return Stream.of(
                Arguments.of(new int[]{1, 6, 2}, true),
                Arguments.of(new int[]{1, 2, 6}, false),
                Arguments.of(new int[]{4, 1, 7, 8, 7, 2}, true),
                Arguments.of(new int[]{1, 2, 3, 7}, false)
        );
    }
}