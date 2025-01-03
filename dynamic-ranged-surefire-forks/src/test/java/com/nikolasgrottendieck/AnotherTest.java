package com.nikolasgrottendieck;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AnotherTest {
	@ParameterizedTest(
		name = "{index} ==> param ''{0}''"
	)
	@ValueSource(ints = {2, 3, 4})
	void testNamedParameter(int param) {
		assertTrue(param > 1);
	}
}
