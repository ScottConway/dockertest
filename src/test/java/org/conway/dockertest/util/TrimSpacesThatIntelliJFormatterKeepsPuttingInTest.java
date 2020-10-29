package org.conway.dockertest.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TrimSpacesThatIntelliJFormatterKeepsPuttingInTest {
    TrimSpacesThatIntellijFormatterKeepsPuttingIn processor = new TrimSpacesThatIntellijFormatterKeepsPuttingIn();

    @DisplayName("processor returns null when null passed in")
    @Test
    public void processNull() {
        assertNull(processor.processString(null));
    }

    @DisplayName("Remove all that pesky space.")
    @ParameterizedTest
    @MethodSource("peskyStringsNeedingProcessing")
    public void processString(String data, String expected) {
        assertEquals(expected, processor.processString(data));
    }

    private static Stream<Arguments> peskyStringsNeedingProcessing() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", ""),
                Arguments.of("         ", ""),
                Arguments.of("test", "test"),
                Arguments.of("   test", "test"),
                Arguments.of("   test   ", "test"),
                Arguments.of("test   ", "test")
        );
    }
}
