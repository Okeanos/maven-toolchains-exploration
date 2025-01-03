package com.nikolasgrottendieck;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AdditionalTest {

	@ParameterizedTest
	@ValueSource(ints = {2, 3, 4})
	void parameterizedTest(int param) {
		assertTrue(param > 1);
	}
}
